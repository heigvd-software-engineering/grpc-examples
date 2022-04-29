package ch.heigvd.android_kotlin_client

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ch.heigvd.java.helloworld.GreeterGrpcKt
import ch.heigvd.java.helloworld.helloRequest
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.*
import java.io.Closeable

class MainActivity : AppCompatActivity() {
    private val uri by lazy { Uri.parse(resources.getString(R.string.server_url)) }
    private val greeterService by lazy { GreeterRCP(uri) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_validate: Button = findViewById(R.id.btn_validate)
        btn_validate.setOnClickListener {
            greeting()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        greeterService.close()
    }

    private fun greeting() = runBlocking {
        val input_name: EditText = findViewById(R.id.input_name)
        val name = input_name.text.toString();
        val text_hello: TextView = findViewById(R.id.hello)

        val request = launch {
            greeterService.sayHello(name)
        }
        request.join()
        if (greeterService.responseState.value.isNotEmpty()) {

            text_hello.text = greeterService.responseState.value
        }
    }
}

class GreeterRCP(uri: Uri) : Closeable {
    val responseState = mutableStateOf("")

    private val channel = let {
        println("Connecting to ${uri.host}:${uri.port}")

        val builder = ManagedChannelBuilder.forAddress(uri.host, uri.port)
        if (uri.scheme == "https") {
            builder.useTransportSecurity()
        } else {
            builder.usePlaintext()
        }

        builder.executor(Dispatchers.IO.asExecutor()).build()
    }

    private val greeter = GreeterGrpcKt.GreeterCoroutineStub(channel)

    suspend fun sayHello(name: String) {
        try {
            val request = helloRequest { this.name = name }
            val response = greeter.sayHello(request)
            responseState.value = response.message
        } catch (e: Exception) {
            responseState.value = e.message ?: "Unknown Error"
            e.printStackTrace()
        }
    }

    override fun close() {
        channel.shutdownNow()
    }
}

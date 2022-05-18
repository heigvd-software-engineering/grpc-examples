package ch.heigvd.kotlin

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import ch.heigvd.kotlin.helloworld.GreeterGrpcKt
import ch.heigvd.kotlin.helloworld.helloRequest
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

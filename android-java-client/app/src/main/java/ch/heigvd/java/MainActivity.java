/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ch.heigvd.java;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.heigvd.java.R;
import ch.heigvd.java.helloworld.GreeterGrpc;
import ch.heigvd.java.helloworld.HelloReply;
import ch.heigvd.java.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Main activity for the Geospatial API example.
 *
 * <p>This example shows how to use the Geospatial APIs. Once the device is localized, anchors can
 * be created at the device's geospatial location. Anchor locations are persisted across sessions
 * and will be recreated once localized.
 */
public class MainActivity extends AppCompatActivity {



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button = findViewById(R.id.btn_validate);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sayHello();
      }
    });
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
    super.onRequestPermissionsResult(requestCode, permissions, results);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {
    super.onPointerCaptureChanged(hasCapture);
  }


  @Override
  protected void onPostResume() {
    super.onPostResume();
  }

  private void sayHello() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    EditText input = findViewById(R.id.input_name);
    String name = input.getText().toString();

    executor.execute(new Runnable() {
      @Override
      public void run() {
        try {
          ManagedChannel channel = ManagedChannelBuilder.forAddress("10.0.2.2", 9090).usePlaintext().build();

          GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
          HelloRequest helloRequest = HelloRequest.newBuilder().setName(name).build();
          HelloReply helloReply = stub.sayHello(helloRequest);
          TextView textHello = findViewById(R.id.hello);
          textHello.setText(helloReply.getMessage());
        } catch (Exception e) {
          StringWriter sw = new StringWriter();
          PrintWriter pw = new PrintWriter(sw);
          e.printStackTrace(pw);
          pw.flush();
        }
      }
    });
  }
}



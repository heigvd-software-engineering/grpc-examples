# gRPC Android Kotlin client

Android Kotlin client is a demo client developed in kotlin for an Android device to communicate with a gRPC server.

## Prerequisite

- Java 17
- Gradle
- Android Studio (highly recommended)

## Run

The easiest way to run the application in local is to run it through Android Studio with the embedded emulator.

- Open the project with Android Studio.

- Configure and create an emulator in Android Studio
  
- Run the `app` on the emulated device

## Important information

- To change the server address, change the `server_url` in the [string.xml](./app/src/main/res/values/strings.xml) file.

- To communicate from the emulator to your local machine, you need to communicate through the `10.0.2.2` ip address.

> It's the emulator proxy address

- Don't forget to turn the wifi on your emulator.

- The proto file defining the gRPC client/server is in the [proto](./app/src/main/proto) folder.

> The default view in Android studio si `Android` and you want see the proto folder. You can change it into project view to see all the files.
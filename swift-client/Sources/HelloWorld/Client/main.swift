import ArgumentParser
import GRPC
import HelloWorldModel
import NIOCore
import NIOPosix

func greet(name: String?, client greeter: Helloworld_GreeterClient) {
  // Form the request with the name, if one was provided.
  let request = Helloworld_HelloRequest.with {
    $0.name = name ?? ""
  }

  // Make the RPC call to the server.
  let sayHello = greeter.sayHello(request)

  // wait() on the response to stop the program from exiting before the response is received.
  do {
    let response = try sayHello.response.wait()
    print("Greeter received: \(response.message)")
  } catch {
    print("Greeter failed: \(error)")
  }
}

struct HelloWorld: ParsableCommand {
  @Option(help: "The port to connect to")
  var port: Int = 8080

  @Argument(help: "The name to greet")
  var name: String?

  func run() throws {
    // Setup an `EventLoopGroup` for the connection to run on.
    //
    // See: https://github.com/apple/swift-nio#eventloops-and-eventloopgroups
    let group = MultiThreadedEventLoopGroup(numberOfThreads: 1)

    // Make sure the group is shutdown when we're done with it.
    defer {
      try! group.syncShutdownGracefully()
    }

    // Configure the channel, we're not using TLS so the connection is `insecure`.
    let channel = try GRPCChannelPool.with(
      target: .host("localhost", port: self.port),
      transportSecurity: .plaintext,
      eventLoopGroup: group
    )

    // Close the connection when we're done with it.
    defer {
      try! channel.close().wait()
    }

    // Provide the connection to the generated client.
    let greeter = Helloworld_GreeterClient(channel: channel)

    // Do the greeting.
    greet(name: self.name, client: greeter)
  }
}

HelloWorld.main()

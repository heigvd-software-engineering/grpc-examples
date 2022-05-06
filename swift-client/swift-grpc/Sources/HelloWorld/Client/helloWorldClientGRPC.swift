//
//  File.swift
//  
//
//  Created by James Smith on 06.05.22.
//

import Foundation
import GRPC
import HelloWorldModel
import NIOCore
import NIOPosix

public final class HelloWorldClientGRPC {
    private var port: Int!
    
    public init(port: Int) {
        self.port = port
    }
    
    public func greet(name: String?) -> String {
        // Form the request with the name, if one was provided.
        let request = Helloworld_HelloRequest.with {
            $0.name = name ?? ""
        }
        
        let group = MultiThreadedEventLoopGroup(numberOfThreads: 1)
        
        // Make sure the group is shutdown when we're done with it.
        defer {
            try! group.syncShutdownGracefully()
        }
        
        do {
            // Configure the channel, we're not using TLS so the connection is `insecure`.
            let channel = try GRPCChannelPool.with(
                target: .host("localhost", port: port),
                transportSecurity: .plaintext,
                eventLoopGroup: group
            )
            // Close the connection when we're done with it.
            defer {
                try! channel.close().wait()
            }
            // Provide the connection to the generated client.
            let greeter = Helloworld_GreeterClient(channel: channel)
            
            
            
            
            // Make the RPC call to the server.
            let sayHello = greeter.sayHello(request)
            
            // wait() on the response to stop the program from exiting before the response is received.
            do {
                let response = try sayHello.response.wait()
                return response.message
            } catch {
                print("Greeter failed: \(error)")
            }
        } catch {
            print("An error occured while creating the connection to the gRPC server")
        }
        return ""
    }
}

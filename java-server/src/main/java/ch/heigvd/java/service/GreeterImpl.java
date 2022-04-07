package ch.heigvd.java.service;

import io.grpc.stub.StreamObserver;

public class GreeterImpl extends ch.heigvd.java.helloworld.GreeterGrpc.GreeterImplBase {
  @Override
  public void sayHello(ch.heigvd.java.helloworld.HelloRequest req, StreamObserver<ch.heigvd.java.helloworld.HelloReply> responseObserver) {
    ch.heigvd.java.helloworld.HelloReply reply = ch.heigvd.java.helloworld.HelloReply.newBuilder().setMessage("Hello " + req.getName() + ". The world is greeting you, young padawan.").build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}

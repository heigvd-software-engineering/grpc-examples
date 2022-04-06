const {HelloRequest, HelloResponse} = require('./helloworld_pb.js');
const {GreeterClient} = require('./helloworld_grpc_web_pb.js');

var helloworldService = new GreeterClient('http://localhost:8080');


export function greeting(name) {
  var request = new HelloRequest();
  request.setName(name);
  
  helloworldService.sayHello(request, {}, function(err, response) {
    console.log(response.array);
    document.getElementById("hello").innerHTML = response.array[0];
  });
}

const path = require('path');

module.exports = {
  entry: './src/grpc-client.js',
  output: {
    path: path.resolve(__dirname, 'lib'),
    filename: 'grpc.js',
    library: "grpc",
  },
};
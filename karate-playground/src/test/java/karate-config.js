function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  var port = karate.properties['local.server.port'];
  port = 8888;
  var config = {
    port: port,
    localUrl:'http://localhost:' + port
  }
  return config;
}
# Building
~~~bash
./gradlew clean bootJar
~~~

# Running
~~~bash
java -Djdk.httpclient.HttpClient.log=VALUE -jar http-test.jar URL
# example 
java -Djdk.httpclient.HttpClient.log=request,headers -jar build/libs/http-test.jar https://jsonplaceholder.typicode.com/todos/1  
~~~

## Params
~~~
# HttpClient
# ref: https://docs.oracle.com/en/java/javase/21/docs/api/java.net.http/module-summary.html
-Djdk.httpclient.HttpClient.log=request,headers
-Djdk.httpclient.HttpClient.log=request,headers,trace
-Djdk.httpclient.HttpClient.log=request,headers,ssl
-Djdk.httpclient.HttpClient.log=request,headers,ssl,trace

# Keystores
-Djavax.net.ssl.trustStore=FILE
-Djavax.net.ssl.trustStorePassword=PASS
-Djavax.net.ssl.keyStore=FILE
-Djavax.net.ssl.keyStorePassword=PASS

# javax.net.debug
# ref: https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html
-Djavax.net.debug=all
-Djavax.net.debug=handshake
-Djavax.net.debug=ssl:handshake:data
-Djavax.net.debug=SSL,handshake,data,trustmanager
~~~

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

## Logging
~~~
-Djdk.httpclient.HttpClient.log=request,headers
-Djdk.httpclient.HttpClient.log=request,headers,trace
-Djdk.httpclient.HttpClient.log=request,headers,ssl
-Djdk.httpclient.HttpClient.log=request,headers,ssl,trace
~~~

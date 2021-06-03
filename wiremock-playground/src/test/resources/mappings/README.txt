Schemas:
https://github.com/tomakehurst/wiremock/tree/master/src/main/resources/swagger
https://github.com/tomakehurst/wiremock/tree/master/src/main/resources/swagger/schemas

Can be used with the json files.
E.g.:
{
  "$schema": "https://raw.githubusercontent.com/tomakehurst/wiremock/master/src/main/resources/swagger/schemas/stub-mapping.yaml",
  "request": {
    "method": "GET",
    "url": "/body-file"
  },
  "response": {
    "status": 200,
    "bodyFileName": "file1.json",
    "headers": {
      "Content-Type": "application/json"
    }
  }
}
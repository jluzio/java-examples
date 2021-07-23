Feature: stateful mock server

  Background:
    * configure cors = true
    * def uuid = function(){ return java.util.UUID.randomUUID() + '' }
    * def cats = {}

  Scenario: pathMatches('/cats') && methodIs('post')
    * def cat = request
    * def id = uuid()
    * cat.id = id
    * cats[id] = cat
    * def response = cat

  Scenario: pathMatches('/cats')
    * def response = $cats.*

  Scenario: pathMatches('/cats/{id}')
    * def response = cats[pathParams.id]

  Scenario:
#    * def responseStatus = 404
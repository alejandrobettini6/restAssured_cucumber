Feature: Example feature file

    As a tester I want to show how this framework works

    Scenario: As a user I want to validate that the total number of pokemons is correct
        Given I send a GET request to the endpoint "/pokemon"
        Then I should get a response with status code 200
        Then I validate that I got a list of 1118 pokemons

    Scenario: As a user I want to validate that if a pokemon does not exist the status is 404
        Given I send a GET request to the endpoint "/pokemon/not-a-pokemon"
        Then I should get a response with status code 404

    Scenario: I validate the json format of the response
        Given I send a GET request to the endpoint "/pokedex/"
        Then I validate that the response is in json format is equal to

    Scenario: transformar un archivo o algo asi
        Given I send a GET request to the endpoint "/pokemon"
        When I load all the info in one file
        Then I validate the file content
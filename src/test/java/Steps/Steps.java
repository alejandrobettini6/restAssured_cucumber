package Steps;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.Scanner;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.junit.Assert;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Steps {

    //I declare the basic url that will be used to test the project
    String baseUri = "https://pokeapi.co/api/v2";
    Calendar calendar = Calendar.getInstance();
    String fileName = "";
    private Response response;

    //I declare all steps definitions with the same name as the feature file.
    //You can change the parameters in the feature file to match the parameters in the step definition.
    @Given("I send a GET request to the endpoint {string}")
    public void sendGetRequest(String resource) {
        //I send a get request to the base url + the specific resource defined in the feature file
        this.response = given()
                .baseUri(baseUri)
                .when()
                .get(resource);
    }

    @Then("I should get a response with status code {int}")
    public void checkStatusCode(int statusCode) {
        //I check the status code of the response
        assertTrue("Status code is not " + statusCode, response.getStatusCode() == statusCode);
    }

    //The step can be generic and reusable on many occasions (like the first examples), 
    //but also more specific with the one presented below, with some hardcoded data
    @Then("I validate that I got a list of {int} pokemons")
    public void validateListOfUsers(int expectedListNumber) {
        //The "count" attribute is hardcoded since it is the attribute that returns the number of pokemons
        //But we can change from the feature file the expected number of pokemons in case that value changes, 
        //both when adding and removing (UI, DB or API) pokemons in the future
        assertTrue(response.getBody().jsonPath().getInt("count") == expectedListNumber);
    }

    @When("I validate that the response is in json format is equal to")
    public void validateSchema() {
        given()
                .get(baseUri)
                .then()
                .assertThat()
                //we have an example of a json schema in the project
                .body(matchesJsonSchemaInClasspath("schema.json"));

    }

    @When("I load all the info in one file")
    public void loadInfoInOneFile() {

        try {
            // Creating an object of a file
            //creo un nombre de archivo para que nunca se repita
            String filename = calendar.getTime().getDay()+"-"+calendar.getTime().getHours()+"-"+calendar.getTime().getMinutes()+"-Pokemon";
            this.fileName = filename+".json";
            //creo un archivo con el nombre que le asigno
            File myObj = new File(this.fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            //se crea una instancia de la clase para escribir en el archivo antes creado
            FileWriter myWriter = new FileWriter(this.fileName);
            // Writes this content into the specified file
            myWriter.write(this.response.body().asPrettyString());
            // Closing is necessary to retrieve the resources allocated
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Then("I validate the file content")
    public void validateFileContent() {
        String dataOutputString= "";
        try {
            // Creating an object of the file for reading the data
            File myObj = new File(this.fileName);
            Scanner myReader = new Scanner(myObj);
            //Reading the data from the file and saving it in a string
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                dataOutputString = dataOutputString + data;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Assert.assertTrue(dataOutputString.contains("count"));
        Assert.assertTrue(dataOutputString.contains("1118"));
        Assert.assertTrue(dataOutputString.contains("bulbasaur"));
        Assert.assertTrue(dataOutputString.contains("pidgeot"));
        
    }
}


package govtech.stepDefinitions;

import govtech.utils.Constants;
import govtech.utils.Utils;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static govtech.CommonActions.CommonActions.setUpAndQueryDataBaseByNatId;
import static govtech.CommonActions.CommonActions.setUpDatabaseAndDeleteEntry;
import static io.restassured.RestAssured.given;

public class APIActions {
    private JSONObject jsonBody;
    private Response response;
    private RequestSpecification request;
    private String previousUpdatedAt;
    private String currentUpdatedAt;
    private String generatedNatId;

    public void generateValidJSONBody() {
        jsonBody = new JSONObject();
        jsonBody.put("natid", Constants.DEFAULT_NATID);
        jsonBody.put("name", "hello");
        jsonBody.put("gender", "MALE");
        jsonBody.put("birthDate", "2020-01-01T00:00:00");
        jsonBody.put("deathDate", "2022-01-01T00:00:00");
        jsonBody.put("salary", 10.00);
        jsonBody.put("taxPaid", 1);
        jsonBody.put("browniePoints", 9);
    }

    public void setRequestHeaderAndBody() {
        RestAssured.baseURI = Constants.BASE_URL;
        request = given().header("Content-Type", "application/json");
        request.body(jsonBody.toString());
    }


    @Given("A valid payload with below details")
    public void a_valid_payload_with_below_details(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);

        jsonBody = new JSONObject();

        jsonBody.put("natid", list.get(0).get("natid"));
        jsonBody.put("name", list.get(0).get("name"));
        jsonBody.put("gender", list.get(0).get("gender"));
        jsonBody.put("birthDate", list.get(0).get("birthDate"));
        if (list.get(0).get("deathDate").equals("null")) {
            jsonBody.put("deathDate", JSONObject.NULL);

        } else {
            jsonBody.put("deathDate", list.get(0).get("deathDate"));
        }
        jsonBody.put("salary", list.get(0).get("salary"));
        jsonBody.put("taxPaid", list.get(0).get("taxPaid"));

        if (list.get(0).get("browniePoints").equals("null")) {
            jsonBody.put("browniePoints", JSONObject.NULL);
        } else {
            jsonBody.put("browniePoints", list.get(0).get("browniePoints"));
        }
        setRequestHeaderAndBody();
    }

    @When("I send a POST request to {string}")
    public void i_send_a_post_request_to(String string) {
        response = request.when()
                .post(string);
    }

    @Then("The status code is {int}")
    public void the_status_code_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Given("A valid payload with natid {word}")
    public void a_valid_payload_with_natid(String natId) {
        generateValidJSONBody();
        jsonBody.put("natid", natId);

        setRequestHeaderAndBody();
    }

    @Then("errorMsg is {string}")
    public void error_msg_is(String expectedErrorMsg) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String actualErrorMsg = jsonPathEvaluator.get("errorMsg");
        Assert.assertTrue("Expected: " + expectedErrorMsg + ", Actual: " + actualErrorMsg, actualErrorMsg.contains(expectedErrorMsg));

    }

    @Given("A valid payload with name {string}")
    public void a_valid_payload_with_name(String name) {
        // Write code here that turns the phrase above into concrete actions
        generateValidJSONBody();
        jsonBody.put("name", name);

        setRequestHeaderAndBody();
    }

    @Given("A valid payload with gender {string}")
    public void a_valid_payload_with_gender(String gender) {
        generateValidJSONBody();
        jsonBody.put("gender", gender);

        setRequestHeaderAndBody();
    }

    @Given("A valid payload with birthDate {string}")
    public void a_valid_payload_with_birthDate(String birthDate) {
        generateValidJSONBody();
        if (birthDate.equals("null")) {
            jsonBody.put("birthDate", JSONObject.NULL);
        } else {
            jsonBody.put("birthDate", birthDate);
        }
        setRequestHeaderAndBody();
    }

    @Given("A valid payload with deathDate {string}")
    public void a_valid_payload_with_deathDate(String deathDate) {
        generateValidJSONBody();
        if (deathDate.equals("null")) {
            jsonBody.put("deathDate", JSONObject.NULL);
        } else {
            jsonBody.put("deathDate", deathDate);
        }
        setRequestHeaderAndBody();
    }

    @Given("A valid payload with salary {double}")
    public void a_valid_payload_with_salary(Double salary) {
        generateValidJSONBody();
        jsonBody.put("salary", salary);
        setRequestHeaderAndBody();
    }

    @Given("A valid payload with taxPaid {double}")
    public void a_valid_payload_with_taxPaid(Double taxPaid) {
        generateValidJSONBody();
        jsonBody.put("taxPaid", taxPaid);
        setRequestHeaderAndBody();
    }

    @Given("A valid payload with null browniePoints {string} and null deathDate {string}")
    public void a_valid_payload_with_null_brownie_points_and_null_death_date(String browniePoints, String deathDate) {
        generateValidJSONBody();
        if (browniePoints.equals("null")) {
            jsonBody.put("browniePoints", JSONObject.NULL);
        } else {
            jsonBody.put("browniePoints", browniePoints);
        }

        if (deathDate.equals("null")) {
            jsonBody.put("deathDate", JSONObject.NULL);
        } else {
            jsonBody.put("deathDate", deathDate);
        }

        setRequestHeaderAndBody();
    }

    @Given("A valid payload containing an existing natid {string}")
    public void a_valid_payload_containing_an_existing_natid(String natId) {
        generateValidJSONBody();
        jsonBody.put("natid", natId);

        setRequestHeaderAndBody();
    }

    @Given("Valid database entry of hero with natid {string}")
    public void valid_database_entry_of_hero_with_natid(String natId) throws SQLException {
        ResultSet results = setUpAndQueryDataBaseByNatId("SELECT updated_at FROM working_class_heroes WHERE natid= ?", natId);

        if (results.next()) {
            previousUpdatedAt = results.getString("updated_at");
        }

    }

    @Then("verify no changes has been made to the database entry with natid {string}")
    public void verify_no_changes_has_been_made_to_the_database_entry_with_natid(String natId) throws SQLException {
        ResultSet results = setUpAndQueryDataBaseByNatId("SELECT updated_at FROM working_class_heroes WHERE natid= ?", natId);
        if (results.next()) {
            currentUpdatedAt = results.getString("updated_at");
        }
        Assert.assertEquals(previousUpdatedAt, currentUpdatedAt);
    }

    @Given("A valid payload with natid not existing in the database table")
    public void a_valid_payload_with_natid_not_existing_in_the_database_table() throws SQLException {
        generateValidJSONBody();
        while (true) {
            generatedNatId = "natid-" + Utils.generateRandomNumber(1, 10000000);
            ResultSet results = setUpAndQueryDataBaseByNatId("SELECT updated_at FROM working_class_heroes WHERE natid= ?", generatedNatId);
            if (!results.next()) {
                break;
            }
        }
        jsonBody.put("natid", generatedNatId);
        setRequestHeaderAndBody();
    }

    @Then("record is created in database table with natid as in payload")
    public void record_is_created_in_database_table_with_natid_as_in_payload() throws SQLException {
        ResultSet results = setUpAndQueryDataBaseByNatId("SELECT updated_at FROM working_class_heroes WHERE natid= ?", generatedNatId);
        Assert.assertTrue(results.next());
    }

    @After("@postWithValidPayload")
    public void tearDownForAC1() throws SQLException {
        setUpDatabaseAndDeleteEntry("DELETE FROM working_class_heroes WHERE natid= ?", Constants.DEFAULT_NATID);
    }

    @After("@successfullyCreatedHero")
    public void tearDownForAC4() throws SQLException {
        setUpDatabaseAndDeleteEntry("DELETE FROM working_class_heroes WHERE natid= ?", generatedNatId);
    }

}

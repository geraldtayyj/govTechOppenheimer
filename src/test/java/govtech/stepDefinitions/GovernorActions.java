package govtech.stepDefinitions;

import govtech.CommonActions.CommonActions;
import govtech.framework.ChromeDriverSetup;
import govtech.pages.GovernorPages;
import govtech.pages.LogInPage;
import govtech.utils.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class GovernorActions extends ChromeDriverSetup {

    private String name;

    private void checkRowDataMatches(ResultSet results) throws SQLException {
        while (results.next()) {
            Date birthDate = results.getDate("birth_date");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String birthDateString = df.format(birthDate);

            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%s']", results.getString("natid")))).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%s']", results.getString("name")))).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%s']", results.getString("gender")))).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%s']", birthDateString))).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%.2f']", results.getDouble("salary")))).isDisplayed());
            Assert.assertTrue(driver.findElement(By.xpath(String.format("//*[text()='%.2f']", results.getDouble("tax_paid")))).isDisplayed());
        }
    }

    @Before("@GovernorFeatures")
    public void setUp() {
        setUpChromeDriver();
    }

    @Given("I am logged in as the Governor")
    public void i_am_logged_in_as_the_governor() {
        driver.get(Constants.BASE_URL + LogInPage.logInEndPoint);
        LogInPage.userNameInput(driver).sendKeys("gov");
        LogInPage.passwordInput(driver).sendKeys("pwd");
        LogInPage.submitButton(driver).click();
    }

    @When("I click on the List ALL!! button")
    public void i_click_on_the_list_all_button() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GovernorPages.listAllBtnXpath)));
        GovernorPages.listAllBtn(driver).click();
    }

    @Then("A table appears with the headers as below")
    public void a_table_appears_with_the_headers_as_below(io.cucumber.datatable.DataTable dataTable) {
        wait.until(ExpectedConditions.invisibilityOf(GovernorPages.searchAllProcessingTable(driver)));
        List<String> headers = dataTable.asLists().get(0);

        for (int i = 0; i < headers.size(); i++) {
            Assert.assertTrue(driver.findElement(By.xpath("//th[text()='" + headers.get(i) + "']")).isDisplayed());
        }

    }

    @Then("The total number of entries matches the total in the {string} database")
    public void the_total_number_of_entries_matches_the_total_in_the_database(String string) throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT COUNT(*) FROM working_class_heroes");
        String totalCountFromDb = "";
        while (results.next()) {
            totalCountFromDb = results.getString(1);
        }
        String totalResults = GovernorPages.searchAllTableInfo(driver).getText();
        String totalEntries = totalResults.substring(totalResults.indexOf("of ") + 3, totalResults.indexOf(" entries"));
        Assert.assertEquals(totalCountFromDb, totalEntries);
    }

    @When("I search for natid {string}")
    public void i_search_for_natid(String string) {
        GovernorPages.searchAllTableInput(driver).sendKeys("natid-2");
        wait.until(ExpectedConditions.invisibilityOf(GovernorPages.searchAllProcessingTable(driver)));
    }

    @Then("I see the results for natid {string}")
    public void i_see_the_results_for_natid(String natId) throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBaseByNatId("SELECT * FROM working_class_heroes WHERE natid= ?", natId);
        checkRowDataMatches(results);
    }

    @When("I search for name with {string}")
    public void i_search_for_name_with_natid(String natId) throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBaseByNatId("SELECT * FROM working_class_heroes WHERE natid= ?", natId);
        while (results.next()) {
            name = results.getString("name");
        }
        GovernorPages.searchAllTableInput(driver).sendKeys(name);
        wait.until(ExpectedConditions.invisibilityOf(GovernorPages.searchAllProcessingTable(driver)));

    }

    @Then("I see the results for name with {string}")
    public void i_see_the_results_for_name_with_(String natId) throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT * FROM working_class_heroes WHERE name= '" + name + "'");
        checkRowDataMatches(results);
    }


    @After("@GovernorFeatures")
    public void tearDown() {
        driver.quit();
    }


}

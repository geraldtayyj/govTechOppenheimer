package govtech.stepDefinitions;

import govtech.CommonActions.CommonActions;
import govtech.framework.ChromeDriverSetup;
import govtech.pages.ClerkPages;
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

import java.sql.*;

public class ClerkActions extends ChromeDriverSetup {

    @Before("@ClerkFeatures")
    public void setUp() {
        setUpChromeDriver();
    }

    @Given("I am logged in as the Clerk")
    public void i_am_logged_in_as_the_clerk() {
        driver.get(Constants.BASE_URL+ LogInPage.logInEndPoint);
        LogInPage.userNameInput(driver).sendKeys("clerk");
        LogInPage.passwordInput(driver).sendKeys("pwd");
        LogInPage.submitButton(driver).click();
    }
    @Given("I am able to navigate to \\/clerk\\/upload-csv page")
    public void i_am_able_to_navigate_to_clerk_upload_csv_page() {
        ClerkPages.addAHeroButton(driver).click();
       ClerkPages.uploadCsvFileButton(driver).click();
    }

    @When("I upload a csv file {string} with below data")
    public void i_upload_a_csv_file_with_below_data(String csvFile, io.cucumber.datatable.DataTable dataTable) {
        String filePath =  System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\" + csvFile;
        ClerkPages.chooseFileInput(driver).sendKeys(filePath);

    }
    @When("I create working class heroes by csv")
    public void i_create_working_class_heroes_by_csv() {
        ClerkPages.createButton(driver).click();
    }

    @Then("I should see a notification {string}")
    public void i_should_see_a_notification(String notification) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ClerkPages.notificationTitleXpath(notification))));
        Assert.assertTrue(ClerkPages.notificationTitle(notification,driver).isDisplayed());
    }
    @Then("All the records I added are persisted into the database table WORKING_CLASS_HEROES")
    public void all_the_records_i_added_are_persisted_into_the_database_table_working_class_heroes() throws SQLException {
        String query = "SELECT updated_at FROM working_class_heroes WHERE natid= ?";
        ResultSet results = CommonActions.setUpAndQueryDataBaseByNatId(query,"natid-999");
        Assert.assertTrue(results.next());

        results = CommonActions.setUpAndQueryDataBaseByNatId(query,"natid-1000");
        Assert.assertTrue(results.next());
    }

    @Then("I should see error message {string}")
    public void i_should_see_error_message(String errorMsg) {
        Assert.assertTrue(ClerkPages.errorMessage(errorMsg, driver).isDisplayed());
    }
    @Then("All valid records I added are persisted into the database table WORKING_CLASS_HEROES")
    public void all_valid_records_i_added_are_persisted_into_the_database_table_working_class_heroes() throws SQLException {
        String query = "SELECT updated_at FROM working_class_heroes WHERE natid= ?";
        ResultSet results = CommonActions.setUpAndQueryDataBaseByNatId(query,"natid-1999");
        Assert.assertTrue(results.next());

    }
    @Then("All invalid records I added are not persisted into the database table WORKING_CLASS_HEROES")
    public void all_invalid_records_i_added_are_not_persisted_into_the_database_table_working_class_heroes() throws SQLException {String query = "SELECT updated_at FROM working_class_heroes WHERE natid= ?";
        ResultSet results = CommonActions.setUpAndQueryDataBaseByNatId(query,"anatid-2000");
        Assert.assertFalse(results.next());
    }

    @After("@heroesAddedUsingWorkingHeroesCsv")
    public void tearDownWorkingHeroesCsv() throws SQLException {
        String query = "DELETE FROM working_class_heroes WHERE natid= ?";
        CommonActions.setUpDatabaseAndDeleteEntry(query, "natid-999");
        CommonActions.setUpDatabaseAndDeleteEntry(query, "natid-1000");
    }

    @After("@heroesAddedUsingInvalidWorkingHeroesCsv")
    public void tearDownInvalidWorkingHeroesCsv() throws SQLException {
        String query = "DELETE FROM working_class_heroes WHERE natid= ?";
        CommonActions.setUpDatabaseAndDeleteEntry(query, "natid-1999");
        CommonActions.setUpDatabaseAndDeleteEntry(query, "anatid-2000");
    }

    @After("@ClerkFeatures")
    public void tearDown() {
        driver.quit();
    }

}

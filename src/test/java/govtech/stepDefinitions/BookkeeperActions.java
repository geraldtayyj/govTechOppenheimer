package govtech.stepDefinitions;

import com.opencsv.CSVReader;
import govtech.CommonActions.CommonActions;
import govtech.framework.ChromeDriverSetup;
import govtech.pages.BookkeeperPages;
import govtech.pages.LogInPage;
import govtech.utils.Constants;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookkeeperActions extends ChromeDriverSetup {
    private List<String[]> csvList;
    private String countInTxtFile;
    private final List<String> natIds = new ArrayList<String>();
    private ResultSet allRows;
    private int fileCount;

    @Given("I am logged in as the Book Keeper")
    public void i_am_logged_in_as_the_book_keeper() {
        setUpChromeDriver();
        driver.get(Constants.BASE_URL + LogInPage.logInEndPoint);
        LogInPage.userNameInput(driver).sendKeys("bk");
        LogInPage.passwordInput(driver).sendKeys("pwd");
        LogInPage.submitButton(driver).click();
    }

    @When("I click on the Generate Tax Relief File button")
    public void i_click_on_the_generate_tax_relief_file_button() {
        BookkeeperPages.taxReliefBtn(driver).click();
    }

    @Then("A file {string} is created")
    public void a_file_is_created(String fileName) throws InterruptedException {
        Thread.sleep(5000);
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName;
        File f = new File(filePath);
        Assert.assertTrue(f.exists());
    }

    @When("A file {string} exists")
    public void a_file_exists(String fileName) {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName;
        File f = new File(filePath);
        Assert.assertTrue(f.exists());
    }

    @When("I read the file {string}")
    public void i_read_the_file(String fileName) throws Exception {
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\" + fileName));
        csvList = reader.readAll();
        countInTxtFile = csvList.get(csvList.size() - 1)[0];
        reader.close();
    }

    @Then("Each line in {string} is in the format <natid>, <tax relief amount>,")
    public void each_line_in_is_in_the_format_natid_tax_relief_amount(String fileName) throws Exception {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT natid FROM working_class_heroes");

        while (results.next()) {
            natIds.add(results.getString("natid"));
        }
        for (int i = 0; i < csvList.size() - 1; i++) {
            String natid = csvList.get(i)[0];
            Double taxRelief = Double.parseDouble(csvList.get(i)[1]);
            Assert.assertTrue(natIds.contains(natid));
            Assert.assertTrue(taxRelief >= 0);
        }

    }

    @Then("Footer indicates total number of records written to file")
    public void footer_indicates_total_number_of_records_written_to_file() {
        Assert.assertEquals(String.valueOf(natIds.size()), countInTxtFile);
    }

    @Given("There are no records in the database table working_class_heroes")
    public void there_are_no_records_in_the_database_table_working_class_heroes() throws SQLException {
        allRows = CommonActions.setUpAndQueryDataBase("SELECT * FROM working_class_heroes");
        CommonActions.setUpDatabaseAndDelete("DELETE FROM working_class_heroes");
    }

    @Given("There exists n counts of files in the FILE database table")
    public void there_exists_n_counts_of_files_in_the_file_database_table() throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT COUNT(*) FROM file");
        while (results.next()) {
            fileCount = results.getInt(1);
        }
    }

    @Then("There exists n+{int} counts of files in the FILE database table")
    public void there_exists_n_counts_of_files_in_the_file_database_table(Integer int1) throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT COUNT(*) FROM file");
        int newFileCount = 0;
        while (results.next()) {
            newFileCount = results.getInt(1);
        }

        Assert.assertEquals(fileCount + int1, newFileCount);
    }

    @Then("Last record contains file status, total count of records and FILE_TYPE: TAX_RELIEF")
    public void last_record_contains_file_status_total_count_of_records_and_file_type_tax_relief() throws SQLException {
        ResultSet results = CommonActions.setUpAndQueryDataBase("SELECT * FROM file ORDER BY id DESC LIMIT 1");
        ArrayList<String> fileStatus = new ArrayList<>(Arrays.asList("PROCESSING", "COMPLETED", "ERROR"));
        while (results.next()) {
            Assert.assertEquals("TAX_RELIEF", results.getString("file_type"));
            Assert.assertTrue(fileStatus.contains(results.getString("file_status")));
            Assert.assertEquals(countInTxtFile, results.getString("total_count"));
        }
    }

    @After("@noRecordsInTable")
    public void addBackToTable() throws SQLException {
        ResultSetMetaData meta = allRows.getMetaData();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            columns.add(meta.getColumnName(i));
        }
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/testdb?user=user&password=pwd");
        String insertion = "INSERT INTO working_class_heroes" + " (" + columns.stream().collect(Collectors.joining(", ")) + ") VALUES (" + columns.stream().map(c -> "?").collect(Collectors.joining(", ")) + ")";
        PreparedStatement stmt = connection.prepareStatement(insertion);

        while (allRows.next()) {
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                stmt.setObject(i, allRows.getObject(i));
            }
            stmt.addBatch();
        }
        stmt.executeBatch();

        connection.close();

    }

    @After("@UITest")
    public void tearDown() {
        driver.close();
    }

    @After("@deleteTaxReliefFile")
    public void deleteTaxReliefFile() {
        String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "taxrelief.txt";
        File f = new File(filePath);
        f.delete();
    }

}

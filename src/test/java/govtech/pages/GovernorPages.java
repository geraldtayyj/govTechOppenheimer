package govtech.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GovernorPages {

    public static final String listAllBtnXpath = "//button[text()='List ALL!!']";
    public static final String searchAllTableProcessingId = "search-all-table_processing";
    public static final String searchAllTableInfoId = "search-all-table_info";
    public static final String searchAllTableInputXpath = "//input[@placeholder='Enter name or natid here...']";

    public static final WebElement listAllBtn(WebDriver driver) {
        return driver.findElement(By.xpath(listAllBtnXpath));
    }

    public static final WebElement searchAllProcessingTable(WebDriver driver) {
        return driver.findElement(By.id(searchAllTableProcessingId));
    }

    public static final WebElement searchAllTableInfo(WebDriver driver) {
        return driver.findElement(By.id(searchAllTableInfoId));
    }

    public static final WebElement searchAllTableInput(WebDriver driver) {
        return driver.findElement(By.xpath(searchAllTableInputXpath));
    }
}

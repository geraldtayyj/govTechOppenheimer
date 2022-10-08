package govtech.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClerkPages {
    // clerk/dashboard
    public static final String addAHeroId = "dropdownMenuButton2";
    public static final String uploadCsvFileLinkText = "Upload a csv file";
    // clerk/upload-csv
    public static final String chooseFileInputId = "upload-csv-file";
    public static final String createButtonXpath = "//button[text()='Create']";

    public static final WebElement addAHeroButton(WebDriver driver) {
        return driver.findElement(By.id(addAHeroId));
    }

    public static final WebElement uploadCsvFileButton(WebDriver driver) {
        return driver.findElement(By.linkText(uploadCsvFileLinkText));
    }

    public static final WebElement chooseFileInput(WebDriver driver) {
        return driver.findElement(By.id(chooseFileInputId));
    }

    public static final WebElement createButton(WebDriver driver) {
        return driver.findElement(By.xpath(createButtonXpath));
    }

    public static final String notificationTitleXpath(String notificationMessage) {
        return "//h3[text()='" + notificationMessage + "']";
    }

    public static final WebElement notificationTitle(String notificationMessage, WebDriver driver) {
        return driver.findElement(By.xpath(notificationTitleXpath(notificationMessage)));
    }

    public static final String errorMessageXpath(String errorMsg) {
        return "//p[text()='" + errorMsg + "']";
    }

    public static final WebElement errorMessage(String errorMsg, WebDriver driver) {
        return driver.findElement(By.xpath(errorMessageXpath(errorMsg)));
    }

}

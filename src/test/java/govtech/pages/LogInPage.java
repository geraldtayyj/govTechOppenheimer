package govtech.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogInPage {
    public static final String logInEndPoint = "/login";
    public static final String userNameId = "username-in";
    public static final String passwordId = "password-in";
    public static final String submitButtonXpath = "//input[@type='submit']";
    public static final WebElement userNameInput(WebDriver driver) {
        return driver.findElement(By.id(LogInPage.userNameId));
    }

    public static final WebElement passwordInput(WebDriver driver) {
        return driver.findElement(By.id(LogInPage.passwordId));
    }

    public static final WebElement submitButton(WebDriver driver) {
        return driver.findElement(By.xpath(LogInPage.submitButtonXpath));
    }
}

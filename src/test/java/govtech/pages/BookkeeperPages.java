package govtech.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookkeeperPages {
    public static final String taxReliefBtnId = "tax_relief_btn";
    public static final WebElement taxReliefBtn(WebDriver driver) {
        return driver.findElement(By.id(taxReliefBtnId));
    }
}

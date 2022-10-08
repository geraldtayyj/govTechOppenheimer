package govtech.framework;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChromeDriverSetup {

    protected ChromeDriver driver;
    protected WebDriverWait wait;

    protected void setUpChromeDriver() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }
}

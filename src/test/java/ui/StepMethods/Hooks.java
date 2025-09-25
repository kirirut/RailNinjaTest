package ui.StepMethods;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Hooks {

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Driver.driver = new ChromeDriver();
        Driver.driver.manage().window().maximize();
        Driver.wait = new WebDriverWait(Driver.driver, Duration.ofSeconds(20));
    }

    @After
    public void tearDown() {
        if (Driver.driver != null) {
            Driver.driver.quit();
        }
    }
}

package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class TimetablePO {

    public TimetablePO() {
        switchToNewTab();
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//button[.//span[normalize-space()='Continue'] and .//span[normalize-space()='to next step']]")
    public WebElement continueButton;

    private void switchToNewTab() {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(driver -> !driver.getCurrentUrl().equals("data:,"));
        System.out.println("Switched to tab URL: " + driver.getCurrentUrl());
    }



    public void selectFirstTrain() {
        List<WebElement> buttons = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(., 'Select Seats')]")));
        WebElement firstButton = wait.until(ExpectedConditions.elementToBeClickable(buttons.get(0)));
        firstButton.click();
    }
    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }
}

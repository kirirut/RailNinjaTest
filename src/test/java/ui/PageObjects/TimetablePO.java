package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class TimetablePO {

    private static final Logger log = LoggerFactory.getLogger(TimetablePO.class);

    @FindBy(xpath = "//button[.//span[normalize-space()='Continue'] and .//span[normalize-space()='to next step']]")
    public WebElement continueButton;

    public TimetablePO() {
        switchToNewTab();
        PageFactory.initElements(driver, this);
    }

    private void switchToNewTab() {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(driver -> !driver.getCurrentUrl().equals("data:,"));
        log.info("Переключились на новую вкладку с URL: {}", driver.getCurrentUrl());
    }

    public void selectFirstTrain() {
        log.info("Выбираем первый поезд из списка");
        List<WebElement> buttons = wait.until(ExpectedConditions
                .visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(., 'Select Seats')]")));
        WebElement firstButton = wait.until(ExpectedConditions.elementToBeClickable(buttons.get(0)));
        firstButton.click();
        log.debug("Нажата кнопка 'Select Seats' для первого поезда");
    }

    public void pressContinueButton() {
        log.info("Нажимаем кнопку Continue для перехода к следующему шагу");
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        log.debug("Кнопка Continue нажата");
    }
}

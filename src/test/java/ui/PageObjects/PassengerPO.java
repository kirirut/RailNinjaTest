package ui.PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class PassengerPO extends BasePO {

    private static final Logger log = LoggerFactory.getLogger(PassengerPO.class);

    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_full_name")
    public WebElement fullNameInput;

    @FindBy(xpath = "//div[contains(@class,'sc-cf59c1f-0')]//span[1]")
    public WebElement passengerName;

    // Email
    @FindBy(id = "checkout-passengers-form_clientDetails_user_email")
    public WebElement emailInput;

    // Confirm Email
    @FindBy(id = "checkout-passengers-form_clientDetails_confirm_user_email")
    public WebElement confirmEmailInput;

    // Пол
    @FindBy(css = "input[type='radio'][value='female']")
    public WebElement femaleRadioButton;

    @FindBy(css = "input[type='radio'][value='male']")
    public WebElement maleRadioButton;

    // Паспорт
    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_id_number")
    public WebElement passportNumInput;

    // Дата рождения (3 dropdown'а)
    @FindBy(id = "rc_select_7")
    public WebElement birthDaySelect;

    @FindBy(id = "rc_select_8")
    public WebElement birthMonthSelect;

    @FindBy(id = "rc_select_9")
    public WebElement birthYearSelect;


    @FindBy(xpath = "//button[span[text()='Clear']]")
    private WebElement clearButton;

    @FindBy(xpath = "//span[contains(text(),'Email for receiving e-tickets')]")
    private WebElement emailConfirmationMessage;



    @FindBy(css = "svg[viewBox='0 0 24 24']")
    private WebElement errorIcon;

    public void enterFullName(String fullName) {
        log.info("Вводим имя пассажира: {}", fullName);
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fullNameInput));
        input.clear();
        input.sendKeys(fullName);
    }
    public void verifyPassengerName(String expectedName) {
        log.info("Проверяем имя пассажира: {}", expectedName);

        WebElement nameElement = wait.until(ExpectedConditions.visibilityOf(passengerName));
        String actualName = nameElement.getText().trim();

        if (!actualName.equals(expectedName)) {
            log.error("Имя не совпадает! Ожидалось '{}', но на странице отображается '{}'", expectedName, actualName);
            throw new AssertionError("Имя пассажира не совпадает");
        }
        log.debug("Имя пассажира корректно отображается: {}", actualName);
    }
    public void clickContinue() {
        By continueButtonLocator = By.xpath("//button[@data-variant='primary' and @type='submit' and .//span[text()='Continue']]");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement continueButton = wait.until(ExpectedConditions.presenceOfElementLocated(continueButtonLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", continueButton);
        continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueButtonLocator));

        try {
            continueButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueButton);
        }
    }


    public boolean isErrorIconDisplayed() {
        try {
            return errorIcon.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void enterEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
        input.clear();
        input.sendKeys(email);
    }

    public void confirmEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(confirmEmailInput));
        input.clear();
        input.sendKeys(email);
    }

    public boolean isEmailConfirmationDisplayed() {
        try {
            // ждём появления блока подтверждения
            wait.withTimeout(Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(emailConfirmationMessage));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }





}

package ui.PageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

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

    @FindBy(className = "ant-form-item-explain-error")
    private WebElement emailErrorMessage;



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
        confirmEmailInput.sendKeys(email);
    }

    public void confirmEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(confirmEmailInput));
        input.clear();
        input.sendKeys(email);
        input.sendKeys(Keys.ENTER);
    }

    public boolean isEmailConfirmationDisplayed() {
        try {
            wait.withTimeout(Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOf(emailConfirmationMessage));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isEmailValidationErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOf(emailErrorMessage));
            String errorText = errorElement.getText().trim();
            boolean isErrorPresent = errorText.equals("Please enter valid email address");
            if (isErrorPresent) {
                log.info("Ошибка валидации email обнаружена: {}", errorText);
            } else {
                log.debug("Текст ошибки не совпадает: ожидалось 'Please enter valid email address', получено '{}'", errorText);
            }
            return isErrorPresent;
        } catch (TimeoutException | NoSuchElementException e) {
            log.debug("Ошибка валидации email не обнаружена");
            return false;
        }
    }

    public void selectMaleGender() {
        WebElement maleSpan = driver.findElement(By.xpath("//span[text()='Male']"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});", maleSpan);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(maleSpan));
        maleSpan.click();

        log.debug("Выбран пол: Мужской");
    }
    public void selectFirstCountry() {
        log.info("Выбираем первую страну из списка");

        WebElement countryInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[id*='citizenship']")));
        countryInput.click();

        countryInput.sendKeys("");

        WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option")));
        firstOption.click();

        log.debug("Выбрана первая страна из списка");

    }

    public void enterPassportNumber(String passportNumber) {
        log.info("Вводим номер паспорта: {}", passportNumber);
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(passportNumInput));
        input.clear();
        input.sendKeys(passportNumber);
        log.debug("Номер паспорта успешно введен: {}", passportNumber);
    }
    public void selectDateOfBirth() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            WebElement dobContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("checkout-passengers-form_passengersCategories_adult_0_dob")
            ));
            List<WebElement> selectors = dobContainer.findElements(By.cssSelector(".ant-select-selector"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", selectors.get(0));
            selectors.get(0).click();
            WebElement firstDay = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option:first-child")));
            firstDay.click();
            Thread.sleep(1000);
            selectors.get(1).click();
            WebElement firstMonth = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option:first-child")));
            firstMonth.click();
            Thread.sleep(1000);
            selectors.get(2).click();
            WebElement firstYear = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option:first-child")));
            firstYear.click();
            Thread.sleep(1000);

            log.debug("Дата рождения выбрана (первые доступные значения)");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void verifyNextStepIsVisible() {
        WebElement paymentNotice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("span.sc-73484146-2.iGnbyc")
        ));

        if (!paymentNotice.isDisplayed()) {
            throw new AssertionError("Следующий шаг/страница не отображается: сообщение о выборе способа оплаты не найдено");
        }

        log.debug("Следующий шаг формы виден: сообщение о выборе способа оплаты");
    }
}












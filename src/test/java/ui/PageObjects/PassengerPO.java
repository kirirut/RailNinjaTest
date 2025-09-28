package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ui.StepMethods.Driver.wait;

public class PassengerPO extends BasePO {

    private static final Logger log = LoggerFactory.getLogger(PassengerPO.class);

    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_full_name")
    public WebElement fullNameInput;

    @FindBy(xpath = "//div[contains(@class,'sc-cf59c1f-0')]//span[1]")
    public WebElement passengerName;

    @FindBy(id = "checkout-passengers-form_clientDetails_user_email")
    public WebElement emailInput;

    @FindBy(css = "input[type='radio'][value='female']")
    public WebElement femaleRadioButton;

    @FindBy(css = "input[type='radio'][value='male']")
    public WebElement maleRadioButton;

    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_id_number")
    public WebElement passportNumInput;

    @FindBy(xpath = "//button[span[text()='Clear']]")
    private WebElement clearButton;








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


}

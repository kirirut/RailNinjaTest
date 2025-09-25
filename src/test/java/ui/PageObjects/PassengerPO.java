package ui.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class PassengerPO extends BasePO {

    private static final Logger log = LoggerFactory.getLogger(PassengerPO.class);

    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_full_name")
    public WebElement fullNameInput;

    public void enterFullName(String fullName) {
        log.info("Вводим имя пассажира: {}", fullName);
        wait.until(ExpectedConditions.visibilityOf(fullNameInput));
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName);
        log.debug("Имя пассажира '{}' введено в поле", fullName);
    }
}

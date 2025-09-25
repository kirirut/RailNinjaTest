package ui.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class PassengerPO extends BasePO {

    @FindBy(id = "checkout-passengers-form_passengersCategories_adult_0_full_name")
    public WebElement fullNameInput;

    public void enterFullName(String fullName) {
        wait.until(ExpectedConditions.visibilityOf(fullNameInput));
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName);
    }

}

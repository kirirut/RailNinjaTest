package ui.StepMethods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ui.PageObjects.LandingPO;
import ui.PageObjects.PassengerPO;
import ui.PageObjects.TimetablePO;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Бронирование билетов")
@Feature("Изменение данных пассажира")
public class ChangeAdultPassengerSteps {

    LandingPO landingPO;
    TimetablePO timetablePO;
    PassengerPO passengerPO;





    @Given("I'm on Rail Ninja web page")
    @Step("Открываю главную страницу Rail Ninja")
    @Description("Пользователь открывает веб-сайт Rail Ninja")
    public void iMOnRailwayNinjaWebPage() {
        landingPO = new LandingPO();
        landingPO.openRailwayNinjaWebPage();
    }

    @And("I accept cookies")
    @Step("Принимаю все cookies")
    public void iAcceptCookies() {
        landingPO.acceptAllCookies();
    }

    @And("I choose route")
    @Step("Выбираю маршрут: Mecca -> Medina")
    public void iChooseRoute() {
        landingPO.enterMecca();
        landingPO.enterMedina();
    }

    @And("I choose date")
    @Step("Выбираю дату поездки")
    public void iChooseDate() {
        landingPO.selectDate();
    }

    @And("I click Search trains")
    @Step("Нажимаю кнопку 'Search trains'")
    public void iSearchTrains() {
        landingPO.clickSearchTrainsButton();
    }

    @And("I choose first train")
    @Step("Выбираю первый поезд и продолжаю")
    public void iChooseFirstTrain() {
        timetablePO = new TimetablePO();
        timetablePO.selectFirstTrain();
        timetablePO.pressContinueButton();
    }

    @When("I change Adult passenger to {string}")
    @Step("Меняю данные взрослого пассажира на {fullName}")
    @Story("Изменение данных пассажира")
    public void changeAdultPassengerTo(String fullName) {

        passengerPO = new PassengerPO();
        passengerPO.enterFullName(fullName);
        passengerPO.verifyPassengerName(fullName);

    }

    @When("I click continue")
    public void iClickContinue() {

        passengerPO = new PassengerPO();
    passengerPO.clickContinue();

    }

    @Then("I should see an error message indicating the form is empty")
    public void iShouldSeeAnErrorMessageIndicatingTheFormIsEmpty() {
        PassengerPO passengerPage = new PassengerPO(); // создаём объект страницы
        boolean isErrorVisible = passengerPage.isErrorIconDisplayed();
        if (!isErrorVisible) {
            throw new AssertionError("Error icon is not displayed, but it was expected!");
        }
    }

    @When("I fill email and confirm it")
    public void iFillAllRequiredPassengerFormFieldsWithValidData() {
        passengerPO = new PassengerPO();

        passengerPO.enterEmail("testuser@example.com");
        passengerPO.confirmEmail("testuser@example.com");

    }

    @Then("I should see email confirmation message")
    public void iShouldSeeEmailConfirmationMessage() {
        boolean isDisplayed = passengerPO.isEmailConfirmationDisplayed();
        assertTrue(isDisplayed, "Email confirmation message is not displayed");
    }


    @When("I fill email")
    public void iFillEmail() {
        passengerPO = new PassengerPO();
        passengerPO.enterEmail("1234");
    }

    @Then("I should see email validation message")
    public void iShouldSeeEmailValidationMessage() {
         passengerPO = new PassengerPO();
        boolean isErrorDisplayed = passengerPO.isEmailValidationErrorDisplayed();

        if (!isErrorDisplayed) {
            throw new AssertionError("Email validation message 'Please enter valid email address' is not displayed");
        }
    }
}

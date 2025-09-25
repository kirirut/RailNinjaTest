package ui.StepMethods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ui.PageObjects.LandingPO;
import ui.PageObjects.PassengerPO;
import ui.PageObjects.TimetablePO;

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
    }
}

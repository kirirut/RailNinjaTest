package ui.StepMethods;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import ui.PageObjects.LandingPO;
import ui.PageObjects.PassengerPO;
import ui.PageObjects.TimetablePO;


public class ChangeAdultPassengerSteps {
    LandingPO landingPO;


    @Before
    public void setUp() {
        landingPO = new LandingPO();

    }

    @Given("I'm on Rail Ninja web page")
    public void iMOnRailwayNinjaWebPage(){
    landingPO.openRailwayNinjaWebPage();
    }

    @And("I accept cookies")
    public void iAcceptCookies()  {
        landingPO.acceptAllCookies();
    }

    @And("I choose route")
    public void iChooseRoute()  {
        landingPO.enterMecca();
        landingPO.enterMedina();
    }
    @And("I choose date")
    public void iChooseDate()  {
        landingPO.selectDate();
    }

    @And("I click Search trains")
    public void iSearchTrains()  {
        landingPO.clickSearchTrainsButton();
    }
    @And("I choose first train")
    public void iChooseFirstTrain()  {
        TimetablePO timetablePO = new TimetablePO();
        timetablePO.selectFirstTrain();
    }

    @And("I click continue")
    public void iClickContinue() {
        TimetablePO timetablePO = new TimetablePO();
        timetablePO.pressContinueButton();
    }
    @When("I change Adult passenger to {string}")
    public void changeAdultPassengerTo(String display_name) {
        PassengerPO passengerPO = new PassengerPO();
        passengerPO.enterFullName(display_name);
    }

}

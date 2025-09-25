package ui.StepMethods;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import ui.PageObjects.LandingPO;


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
        //landingPO.clickCalendarIcon();
        //landingPO.clickNextMonthButton();
        landingPO.selectDate();
    }






}

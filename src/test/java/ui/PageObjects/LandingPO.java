package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class LandingPO {
    public LandingPO() {
        PageFactory.initElements(driver, this);
    }

    private final String url = "https://rail.ninja/";

    @FindBy(css = "button.cf2Lf6.cf8Oa")
    public WebElement acceptAllCookiesButton;

    @FindBy(how = How.CSS, using = "input[aria-owns='departure_station_list']")
    public WebElement departureStationInput;

    @FindBy(how = How.CSS, using = "input[aria-owns='arrival_station_list']")
    public WebElement arrivalStationInput;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'kqKZPS')]")
    public WebElement calendarIcon;

    @FindBy(how = How.XPATH, using = "//button[contains(@class, 'next')]")
    public WebElement nextMonthButton;



    public void openRailwayNinjaWebPage() {
        driver.get(url);
        driver.manage().window().maximize();
    }
    public void acceptAllCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptAllCookiesButton)).click();
    }
    public void enterMecca() {
        wait.until(ExpectedConditions.visibilityOf(departureStationInput)).clear();
        departureStationInput.sendKeys("Mecca");
    }
    public void enterMedina() {
        wait.until(ExpectedConditions.visibilityOf(arrivalStationInput)).clear();
        arrivalStationInput.sendKeys("Medina");
    }

    public void clickCalendarIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(calendarIcon)).click();
    }

    public void clickNextMonthButton() {
        wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).click();
    }
    public void selectDate() {
        clickCalendarIcon();
        while (true) {
            WebElement yearBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("button.ant-picker-year-btn")));
            WebElement monthBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("button.ant-picker-month-btn")));
            String year = yearBtn.getText().trim();
            String month = monthBtn.getText().trim();
            if (year.equals("2025") && month.equals("November")) {
                break;
            }

            clickNextMonthButton();
        }

        List<WebElement> days = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("div.ant-picker-cell-inner")));
        for (WebElement day : days) {
            if (day.getText().trim().equals("5")) {
                wait.until(ExpectedConditions.elementToBeClickable(day)).click();
                break;
            }
        }
    }




}
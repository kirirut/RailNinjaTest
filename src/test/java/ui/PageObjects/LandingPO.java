package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class LandingPO extends BasePO {

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

    @FindBy(css = "button[form='search-form-rn-modern'][type='submit']")
    public WebElement SearchTrainButton;




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

        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".ant-select-item-option")));
        suggestion.click();
    }

    public void enterMedina() {
        // Кликаем в поле и очищаем
        WebElement arrival = wait.until(ExpectedConditions.elementToBeClickable(arrivalStationInput));
        arrival.click();
        arrival.clear();
        arrival.sendKeys("Medina");

        // Ждём появления дропдауна
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden)")));

        // Ждём появления хотя бы одной подсказки в дропдауне
        List<WebElement> suggestions = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector(".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option")));

        // Берём первую подсказку
        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(suggestions.get(0)));

        // Кликаем через JS (чтобы исключить перехват клика другими элементами)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstSuggestion);
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
    public void clickSearchTrainsButton() {
    wait.until(ExpectedConditions.visibilityOf(SearchTrainButton)).click();
    }
}
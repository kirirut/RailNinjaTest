package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class LandingPO extends BasePO {

    private static final Logger log = LoggerFactory.getLogger(LandingPO.class);

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
    public WebElement searchTrainButton;


    public void openRailwayNinjaWebPage() {
        log.info("Открываем Rail Ninja: {}", url);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public void acceptAllCookies() {
        log.info("Принимаем cookies");
        wait.until(ExpectedConditions.elementToBeClickable(acceptAllCookiesButton)).click();
    }

    public void enterMecca() {
        log.info("Вводим станцию отправления: Mecca");
        wait.until(ExpectedConditions.visibilityOf(departureStationInput)).clear();
        departureStationInput.sendKeys("Mecca");

        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".ant-select-item-option")));
        suggestion.click();
        log.debug("Станция отправления выбрана");
    }

    public void enterMedina() {
        log.info("Вводим станцию прибытия: Medina");
        WebElement arrival = wait.until(ExpectedConditions.elementToBeClickable(arrivalStationInput));
        arrival.click();
        arrival.clear();
        arrival.sendKeys("Medina");

        List<WebElement> suggestions = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.cssSelector(
                        ".ant-select-dropdown:not(.ant-select-dropdown-hidden) .ant-select-item-option")));

        WebElement firstSuggestion = wait.until(ExpectedConditions.elementToBeClickable(suggestions.get(0)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstSuggestion);

        log.debug("Станция прибытия выбрана");
    }

    public void clickCalendarIcon() {
        log.info("Открываем календарь выбора даты");
        wait.until(ExpectedConditions.elementToBeClickable(calendarIcon)).click();
    }

    public void clickNextMonthButton() {
        log.debug("Переключаем календарь на следующий месяц");
        wait.until(ExpectedConditions.elementToBeClickable(nextMonthButton)).click();
    }

    public void selectDate() {
        log.info("Выбираем дату: 5 ноября 2025");

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
                log.debug("Дата выбрана успешно");
                return;
            }
        }
        log.error("Не удалось выбрать дату: 5 ноября 2025");
    }

    public void clickSearchTrainsButton() {
        log.info("Нажимаем кнопку поиска поездов");
        wait.until(ExpectedConditions.elementToBeClickable(searchTrainButton)).click();
    }
}

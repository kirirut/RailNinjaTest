package ui.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static ui.StepMethods.Driver.driver;
import static ui.StepMethods.Driver.wait;

public class TimetablePO {

    private static final Logger log = LoggerFactory.getLogger(TimetablePO.class);

    @FindBy(xpath = "//button[.//span[normalize-space()='Continue'] and .//span[contains(text(),'next step')]]")
    private List<WebElement> continueButtonVariant1;

    @FindBy(xpath = "//button[@data-variant='primary']//span[normalize-space()='Continue']")
    private List<WebElement> continueButtonVariant2;

    public TimetablePO() {
        switchToNewTab();
        PageFactory.initElements(driver, this);
    }

    private void switchToNewTab() {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        wait.until(d -> !driver.getCurrentUrl().equals("data:,"));
        log.info("Переключились на новую вкладку с URL: {}", driver.getCurrentUrl());
    }

    //https://rail.ninja/v9/trains/order/timetable
    public void selectTrainByIndex(int index) {
        log.info("Выбираем поезд с индексом {}", index);

        List<WebElement> buttons;
        try {
            // Пробуем найти вариант без v9
            buttons = wait.until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(By.xpath("//button[contains(., 'Select Seats')]")));
        } catch (TimeoutException e) {
            // Если не нашли — значит это версия v9
            buttons = wait.until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'sc-c172bf25-0')]")));
        }

        if (buttons.isEmpty()) {
            log.error("Список поездов пуст. Невозможно выбрать поезд.");
            throw new RuntimeException("No trains available to select");
        }

        if (index >= buttons.size()) {
            log.error("Индекс {} превышает количество доступных поездов ({})", index, buttons.size());
            throw new IllegalArgumentException("Train index out of bounds");
        }

        WebElement selectedButton = wait.until(ExpectedConditions.elementToBeClickable(buttons.get(index)));
        selectedButton.click();
        log.debug("Выбран поезд с индексом {}", index);
    }


    public void selectFirstTrain() {
        selectTrainByIndex(0);
    }
    public void pressContinueButton() {
        log.info("Нажимаем кнопку Continue для перехода к следующему шагу");

        WebElement btn;
        if (!continueButtonVariant1.isEmpty()) {
            btn = wait.until(ExpectedConditions.elementToBeClickable(continueButtonVariant1.get(0)));
        } else if (!continueButtonVariant2.isEmpty()) {
            btn = wait.until(ExpectedConditions.elementToBeClickable(continueButtonVariant2.get(0)));
        } else {
            throw new RuntimeException("Кнопка Continue не найдена ни по одному локатору");
        }

        btn.click();
        log.debug("Кнопка Continue нажата");
    }

}

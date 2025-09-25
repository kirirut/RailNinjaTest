package ui.PageObjects;

import org.openqa.selenium.support.PageFactory;
import ui.StepMethods.Driver;

public abstract class BasePO {
    protected BasePO() {
        PageFactory.initElements(Driver.driver, this);
    }
}

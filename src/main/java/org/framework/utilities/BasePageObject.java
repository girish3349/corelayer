package org.framework.utilities;


import org.framework.ConfigProvider.ConfigProvider;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.framework.utilities.AssertionLibrary.Screenshot;

public class BasePageObject {

    private final JavascriptExecutor javascriptExecutor;
    private FluentWait<WebDriver> fluentWait;
    private WebDriver driver;
    private static final String SET_INPUT = "Set Input";
    private static final int DEFAULT_IMPLICIT_WAIT = 0;
    private static final String SET_INPUT_COMMAND = "arguments[0].value='%s':";
    private static final String JS_DISPLAY_COMMAND = "arguments[0].style.display='block'";
    private static final String CLICK_COMMAND = "arguments[0].click();";
    private static final String BEFORE_CLICK = "Before Click";
    private static final String CLICK_ACTION = "Click Action";
    private static final String CLICK = "Click: ";
    private static final Logger logger = Logger.getLogger(BasePageObject.class.getName());
    Alert alert;
    Actions action;

    public BasePageObject(WebDriver webDriver) {
        driver = webDriver;
        Duration pollingInterval = Duration.ofMillis(ConfigProvider.getAsInt("POLLING_INTERVAL"));
        Duration fulentWaitDuration = Duration.ofSeconds(ConfigProvider.getAsInt("FLUENT_WAIT"));
        fluentWait = new FluentWait<>(driver).withTimeout(fulentWaitDuration).pollingEvery(pollingInterval)
                .ignoring(StaleElementReferenceException.class).ignoring(ElementNotInteractableException.class)
                .ignoring(NoSuchElementException.class);
        javascriptExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        action = new Actions(driver);
    }

    public BasePageObject() {
        javascriptExecutor = null;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void get(String url) {
        driver.get(url);
        Screenshots.addStepWithScreenshotInReport(driver, "Application launched <a href=\"" + url + "\">" + url + "</a>");
    }

    public void navigateTo(String url) {
        driver.navigate().to(url);
        Screenshots.addStepWithScreenshotInReport(driver, "Application launched <a href=\"" + url + "\">" + url + "</a>");
    }

    protected void setImplicitWait(int duration) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(duration));
    }

    protected List<WebElement> getElements(String locator) {
        return fluentWait.until((ExpectedCondition<List<WebElement>>) driver1 -> driver.findElements(By.xpath(locator)));
    }

    protected List<WebElement> getElements(By by) {
        return fluentWait.until((ExpectedCondition<List<WebElement>>) driver1 -> driver.findElements(by));
    }

    protected WebElement getElement(String locator) {
        return fluentWait.until((ExpectedCondition<WebElement>) driver1 -> driver.findElement(By.xpath(locator)));
    }

    protected WebElement getElement(By by) {
        return fluentWait.until((ExpectedCondition<WebElement>) driver1 -> driver.findElement(by));
    }

    protected boolean isElementOnPage(String locator) {
        setImplicitWait(DEFAULT_IMPLICIT_WAIT);
        boolean flag = !getElements(locator).isEmpty();
        setImplicitWait(ConfigProvider.getAsInt("IMPLICIT_WAIT"));
        return flag;
    }

    protected boolean isElementOnPage(By by) {
        setImplicitWait(DEFAULT_IMPLICIT_WAIT);
        boolean flag = !getElements(by).isEmpty();
        setImplicitWait(ConfigProvider.getAsInt("IMPLICIT_WAIT"));
        return flag;
    }

    protected boolean isEnabled(String locator) {
        List<WebElement> elementList = getElements(locator);
        if (!elementList.isEmpty()) {
            return elementList.get(0).isEnabled();
        } else {
            return false;
        }
    }

    protected boolean isEnabled(By by) {
        List<WebElement> elementList = getElements(by);
        if (!elementList.isEmpty()) {
            return elementList.get(0).isEnabled();
        } else {
            return false;
        }
    }

    protected boolean isDisplayed(String locator) {
        List<WebElement> elementList = getElements(locator);
        if (!elementList.isEmpty()) {
            return elementList.get(0).isDisplayed();
        } else {
            return false;
        }
    }

    protected boolean isDisplayed(By by) {
        List<WebElement> elementList = getElements(by);
        if (!elementList.isEmpty()) {
            return elementList.get(0).isDisplayed();
        } else {
            return false;
        }
    }

    protected boolean isSelected(String locator) {
        List<WebElement> elementList = getElements(locator);
        if ((!elementList.isEmpty())) {
            return elementList.get(0).isSelected();
        } else {
            return false;
        }
    }

    protected boolean isSelected(By by) {
        List<WebElement> elementList = getElements(by);
        if ((!elementList.isEmpty())) {
            return elementList.get(0).isSelected();
        } else {
            return false;
        }
    }

    protected int getElementsSize(String locator) {
        if (isElementOnPage(locator)) {
            return getElements(locator).size();
        } else {
            return 0;
        }
    }

    protected int getElementsSize(By by) {
        if (isElementOnPage(by)) {
            return getElements(by).size();
        } else {
            return 0;
        }
    }

    protected void setInputValue(String locator, String value, boolean clearInput) {
        WebElement element = getElement(locator);
        if (clearInput) {
            element.clear();
        }
        element.sendKeys(value);
        Screenshots.addStepInReport(SET_INPUT + value);
    }

    protected void setInputValue(String locator, String value, boolean clearInput, Screenshot screenshot) {
        WebElement element = getElement(locator);
        if (clearInput) {
            element.clear();
        }
        element.sendKeys(value);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT + value);
        }
        Screenshots.addStepInReport(SET_INPUT + value);
    }

    protected void setInputValue(By by, String value, boolean clearInput) {
        WebElement element = getElement(by);
        if (clearInput) {
            element.clear();
        }
        element.sendKeys(value);
        Screenshots.addStepInReport(SET_INPUT + value);
    }

    protected void setInputValue(By by, String value, boolean clearInput, Screenshot screenshot) {
        WebElement element = getElement(by);
        if (clearInput) {
            element.clear();
            element.clear();
        }
        element.sendKeys(value);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addFailureStepWithScreenshotInReport(driver, SET_INPUT + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT + value);
        }
    }

    protected void setInputValue(String locator, String value) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
        Screenshots.addStepInReport(SET_INPUT + value);
    }

    protected void setInputValue(String locator, String value, Screenshot screenshot) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT + value);
        }
    }

    protected void setInputValue(By by, String value) {
        WebElement element = getElement(by);
        element.clear();
        element.sendKeys(value);
        Screenshots.addStepInReport(SET_INPUT + value);
    }

    protected void setInputValue(By by, String value, Screenshot screenshot) {
        WebElement element = getElement(by);
        element.clear();
        element.sendKeys(value);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT + value);
        }
    }

    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param locator    Locator of the element
     * @param value      Value to set in the Text box
     * @param clearInput Condition to check if the existing value in the text box is cleared
     */
    protected void setInputValueJS(String locator, String value, boolean clearInput) {
        WebElement element = getElement(locator);
        if (clearInput) {
            element.clear();
        }
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
    }

    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param locator    Locator of the element
     * @param value      Value to set in the Text box
     * @param clearInput Condition to check if the existing value in the text box is cleared
     */
    protected void setInputValueJS(String locator, String value, boolean clearInput, Screenshot screenshot) {
        WebElement element = getElement(locator);
        if (clearInput) {
            element.clear();
        }
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT_COMMAND + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
        }
    }


    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param by         Locator of the element
     * @param value      Value to set in the Text box
     * @param clearInput Condition to check if the existing value in the text box is cleared
     */
    protected void setInputValueJS(By by, String value, boolean clearInput) {
        WebElement element = getElement(by);
        if (clearInput) {
            element.clear();
        }
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
    }

    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param by         Locator of the element
     * @param value      Value to set in the Text box
     * @param clearInput Condition to check if the existing value in the text box is cleared
     */
    protected void setInputValueJS(By by, String value, boolean clearInput, Screenshot screenshot) {
        WebElement element = getElement(by);
        if (clearInput) {
            element.clear();
        }
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT_COMMAND + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
        }
    }


    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param locator Locator of the element
     * @param value   Value to set in the Text box
     */
    protected void setInputValueJS(String locator, String value) {
        WebElement element = getElement(locator);
        element.clear();
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
    }

    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param locator Locator of the element
     * @param value   Value to set in the Text box
     */
    protected void setInputValueJS(String locator, String value, Screenshot screenshot) {
        WebElement element = getElement(locator);
        element.clear();

        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT_COMMAND + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
        }
    }


    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param by    Locator of the element
     * @param value Value to set in the Text box
     */
    protected void setInputValueJS(By by, String value) {
        WebElement element = getElement(by);
        element.clear();

        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
    }

    /**
     * This method sets input value using javascriptExecutor
     * Also provides feature of clearing the text box before setting the value
     *
     * @param by    Locator of the element
     * @param value Value to set in the Text box
     */
    protected void setInputValueJS(By by, String value, Screenshot screenshot) {
        WebElement element = getElement(by);
        element.clear();
        javascriptExecutor.executeScript(String.format(SET_INPUT_COMMAND, value), element);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT_COMMAND + value);
        } else {
            Screenshots.addStepInReport(SET_INPUT_COMMAND + value);
        }
    }

    protected void clearElement(String locator) {
        getElements(locator).clear();
    }

    protected void clearElement(By by) {
        getElement(by).clear();
    }

    protected String getText(String locator) {
        return getElement(locator).getText();
    }

    protected String getText(By by) {
        return getElement(by).getText();
    }

    protected String getAttribute(String locator, String attribute) {
        return getElement(locator).getAttribute(attribute);
    }

    protected String getAttribute(By by, String attribute) {
        return getElement(by).getAttribute(attribute);
    }

    protected String getCssValue(String locator, String attribute) {
        return getElement(locator).getCssValue(attribute);
    }

    protected String getCssValue(By by, String attribute) {
        return getElement(by).getCssValue(attribute);
    }

    protected void clickElementJS(String locator) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        javascriptExecutor.executeScript(CLICK_COMMAND, getElement(locator));
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void clickElementJS(By by) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        javascriptExecutor.executeScript(CLICK_COMMAND, getElement(by));
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void clickElementJS(String locator, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        javascriptExecutor.executeScript(CLICK_COMMAND, getElement(locator));
        Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
    }

    protected void clickElementJS(By by, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        javascriptExecutor.executeScript(CLICK_COMMAND, getElement(by));
        Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
    }

    protected void makeElementVisibleAndClick(String locator) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        WebElement element = getElement(locator);
        javascriptExecutor.executeScript(JS_DISPLAY_COMMAND, element);
        javascriptExecutor.executeScript(CLICK_COMMAND, element);
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void makeElementVisibleAndClick(By by) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        WebElement element = getElement(by);
        javascriptExecutor.executeScript(JS_DISPLAY_COMMAND, element);
        javascriptExecutor.executeScript(CLICK_COMMAND, element);
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void makeElementVisibleAndClick(String locator, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        WebElement element = getElement(locator);
        javascriptExecutor.executeScript(JS_DISPLAY_COMMAND, element);
        javascriptExecutor.executeScript(CLICK_COMMAND, element);
        Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
    }

    protected void makeElementVisibleAndClick(By by, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        WebElement element = getElement(by);
        javascriptExecutor.executeScript(JS_DISPLAY_COMMAND, element);
        javascriptExecutor.executeScript(CLICK_COMMAND, element);
        Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
    }

    protected void clickElement(String locator) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        getElement(locator).click();
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void clickElement(String locator, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK + description);
        getElement(locator).click();
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION + description);
    }

    protected void clickElement(String locator, String description, Screenshot screenshot) {
        getElement(locator).click();
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
        } else {
            Screenshots.addStepInReport(CLICK + description);
        }
    }

    protected void clickElement(By by) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK);
        getElement(by).click();
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION);
    }

    protected void clickElement(By by, String description) {
        Screenshots.addStepWithScreenshotInReport(driver, BEFORE_CLICK + description);
        getElement(by).click();
        Screenshots.addStepWithScreenshotInReport(driver, CLICK_ACTION + description);
    }

    protected void clickElement(By by, String description, Screenshot screenshot) {
        getElement(by).click();
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, CLICK + description);
        } else {
            Screenshots.addStepInReport(CLICK + description);
        }
    }

    protected void shiftFocusAway(String locator) {
        getElement(locator).sendKeys(Keys.TAB);
    }

    protected void shiftFocusAway(By by) {
        getElement(by).sendKeys(Keys.TAB);
    }

    protected String getPageSource() {
        return driver.getPageSource();
    }

    protected boolean closeWindow() {
        return closeWindow(driver.getWindowHandle());
    }

    protected boolean closeWindow(String windowsId) {
        boolean ret = false;
        String before = null;
        String current;

        final Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            current = handle;
            if (current.equalsIgnoreCase(windowsId)) {
                try {
                    driver.switchTo().window(handle);
                    driver.close();
                    if (before != null) {
                        driver.switchTo().window(before);
                    }
                    ret = true;
                    break;
                } catch (Exception e) {
                    logger.info(e.toString());
                }
            } else {
                before = current;
            }
        }
        return ret;
    }

    protected void scrollIntoView(String locator) {
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", locator);
        Screenshots.addStepWithScreenshotInReport(driver, "Scroll Page: ");
    }

    protected String switchToWindow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        final String currentWindow = driver.getWindowHandle();

        try {
            wait.until((ExpectedCondition<Boolean>) d -> {
                assert d != null;
                return d.getWindowHandles().size() != 1;

            });
        } catch (TimeoutException t) {
            return driver.getWindowHandle();
        }

        final Set<String> handles = driver.getWindowHandles();
        handles.remove(currentWindow);
        String newTab = handles.iterator().next();
        driver.switchTo().window(newTab);
        return newTab;
    }

    public String switchWindow(String node) {
        String win = driver.getWindowHandle();
        if (node == null || node.isEmpty()) {
            Screenshots.addFailureStepWithScreenshotInReport(driver
                    , "A windows node id must be passed or node = 'root' or node = 'child' to switchWindow(node)");
        } else if (node.trim().equalsIgnoreCase("root") || node.trim().equalsIgnoreCase("parent")) {
            // keyword to refer to parent

            driver.switchTo().window(win);
            return win;
        } else if (node.trim().equalsIgnoreCase("child")) {
            return switchToWindow();
        }
        if (!isWindowExists(node))
            Screenshots.addFailureStepWithScreenshotInReport(driver,
                    "Windows id=" + node + " is not a valid window in current session");
        driver.switchTo().window(node);
        return node;
    }

    public boolean isWindowExists(String windowsId) {
        boolean found = false;
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (handle.trim().equalsIgnoreCase(windowsId)) {
                found = true;
                break;
            }
        }
        return found;
    }

    protected void setPassword(By by, String encryptedString) {
        WebElement element = getElement(by);
        element.clear();
        element.sendKeys(encryptedString);
        Screenshots.addStepWithScreenshotInReport(driver, SET_INPUT + encryptedString);
    }

    public void dropDownValue(String locator, String value) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(value);
    }

    public void dropDownValue(String locator, String value, Screenshot screenshot) {
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(value);
        if (screenshot.equals(Screenshot.REQUIRED)) {
            Screenshots.addStepWithScreenshotInReport(driver, "Selected Value from Dropdown : " + locator);
            logger.info("Dropdown value selected.");
        }
    }

    public void setExplicitWaitVisible(String locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void setExplicitWaitVisible(By by, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void setExplicitWaitClickable(String locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }

    public void setExplicitWaitClickable(By by, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void setExplicitWaitPresence(String locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void setExplicitWaitPresence(By by, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void setExplicitWaitSelected(String locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeSelected(By.xpath(locator)));
    }

    public void setExplicitWaitSelected(By by, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeSelected(by));
    }

    public void setExplicitWaitTextPresent(String locator, long seconds, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(locator), text));
    }

    public void setExplicitWaitSelected(By by, long seconds, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    public void setExplicitWaitAbsent(String locator, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
    }

    public void setExplicitWaitAbsent(By by, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Frame Methods
     */

    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }

    public void switchToFrame(String name) {
        driver.switchTo().frame(name);
    }

    public void switchToFrame(WebElement locator) {
        driver.switchTo().frame(locator);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToParentIFrame() {
        driver.switchTo().parentFrame();
    }

    public void acceptAlert() {
        alert = driver.switchTo().alert();
        alert.accept();
    }

    public void dismissAlert() {
        alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public String getAlertText() {
        alert = driver.switchTo().alert();
        return alert.getText();
    }

    public void sentAlertText(String textInput) {
        alert = driver.switchTo().alert();
        alert.sendKeys(textInput);
    }

    /**
     * Performs click and hold action on the last active element
     */
    public void clickAndHold() {
        action.clickAndHold().build().perform();
    }

    /**
     * Performs click and hold action on the UI element
     *
     * @param locator WebElement locator
     */
    public void clickAndHold(WebElement locator) {
        action.clickAndHold(locator).build().perform();
    }

    /**
     * Releases the hold element
     */
    public void release() {
        action.release().build().perform();
    }

    public void release(WebElement locator) {
        action.release(locator).build().perform();
    }

    public void rightClick() {
        action.contextClick().build().perform();
    }

    public void rightClick(WebElement locator) {
        action.contextClick(locator).build().perform();
    }

    public void doubleClick() {
        action.doubleClick().build().perform();
    }

    public void doubleClick(WebElement locator) {
        action.doubleClick(locator).build().perform();
    }

    public void dragAndDropElement(WebElement source, WebElement target) {
        action.dragAndDrop(source, target).build().perform();
    }

    public void dragAndDropElement(WebElement source, int xOffset, int yOffset) {
        action.dragAndDropBy(source, xOffset, yOffset).build().perform();
    }

    public void moveToElement(WebElement locator) {
        action.moveToElement(locator).build().perform();
    }

    public void moveToElement(WebElement locator, int xOffset, int yOffset) {
        action.moveToElement(locator, xOffset, yOffset).build().perform();
    }

    public void moveToOffset(int offsetX, int offsetY) {
        action.moveByOffset(offsetX, offsetY).build().perform();
    }

    public void pageRefresh() {
        driver.navigate().refresh();
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public void pageBack() {
        driver.navigate().back();
    }

    public void pageForward() {
        driver.navigate().forward();
    }

    public void clearExeDrivers() {
        if (System.getProperty("os.name").contains("Windows")) {
            if (ConfigProvider.getAsString("browser").equalsIgnoreCase("chrome")) {
                executeOnCMD("taskkill /F /IM chromedriver.exe");
                logger.info(" Chrome driver process killed which is running in background.");
            } else if (ConfigProvider.getAsString("browser").equalsIgnoreCase("edge")) {
                executeOnCMD("taskkill /F /IM msedgedriver.exe");
                logger.info("Edge driver process killed which is running in background.");
            }

        }
    }

    public void executeOnCMD(String command) {
        if(System.getProperty("os.name").contains("Windows")) {
            try{
                Runtime.getRuntime().exec(command);
                logger.info(command +" executed successfully." );
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}

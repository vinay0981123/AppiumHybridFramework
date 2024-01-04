package framework.generics;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import static ncs.appbase.RealDevice.*;

public class Generic {

    public static void scrollDown(float startX_percent, float endX_percent, float startY_percent, float endY_percent) {
        Dimension dimensions = driver.manage().window().getSize();
        int startX = (int) (dimensions.getWidth() / startX_percent);
        int startY = (int) (dimensions.getHeight() * startY_percent);
        int endY = (int) (dimensions.getHeight() * endY_percent);

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(startX,startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(startX, endY))
                .release().perform();
    }

    public static void scrollViaScrollableElement() {
        MobileElement scrollableElement = (MobileElement) driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"));

        scrollableElement.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(1)"));
    }

    public static void sendPageUpKey() throws Exception {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_UP).perform();
    }

    public static void sendPageDownKey() throws Exception {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
    }

//    public void scrollToElement(MobileElement element) throws Exception {
//        new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(element);
//    }

    public static void jsPageUp(String direction) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", direction);
        js.executeScript("mobile: scroll", scrollObject);
    }



    public static boolean isElementPresent(AndroidDriver driver, String elementXpath) {

        try {
            WebElement element = driver.findElement(By.xpath(""+elementXpath+""));

            if (element.isDisplayed()) {
                System.out.println("Element is present and visible.");
            } else {
                System.out.println("Element is present but not visible.");
            }
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Element is not present on the page.");
            return false;
        }
    }


    public static void waitUntilElementVisible(By ele) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
    }

    /**
     * Get the text value of last WebElement in a list
     */
    public static String lastElementOfList(String lastElementXpath) {
        WebElement lastElement = null;
        List<WebElement> elements = driver.findElements(By.xpath(""+lastElementXpath+""));

        int lastIndex = elements.size() - 1;
        if (lastIndex >= 0)
            lastElement = elements.get(lastIndex);

//        else
//            System.out.println("The list is empty.");

        return lastElement.getText();
    }


    /**
     * Get the WebElement value of last WebElement in a list
     */
    public static WebElement lastWebElementOfList(String lastWebElementXpath) {
        WebElement lastElement = null;
        List<WebElement> elements = driver.findElements(By.xpath(""+lastWebElementXpath+""));

        int lastIndex = elements.size() - 1;
        if (lastIndex >= 0)
            lastElement = elements.get(lastIndex);

//        else
//            System.out.println("The list is empty.");

        return lastElement;
    }



    public static void horizontalSwipe(WebElement elementToSwipe) {

        TouchActions touchActions = new TouchActions(driver);

        // Perform a horizontal swipe
        int startX = elementToSwipe.getLocation().getX();
        int endX = startX - 200;
        int y = elementToSwipe.getLocation().getY();
//        touchActions.down(startX, y).move(endX, y).release().perform();

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(" + endX + ", 0);");


        int width = elementToSwipe.getSize().getWidth();
//
//        // Create an Actions object
//        Actions actions = new Actions(driver);
//
        // Perform a horizontal swipe from right to left
        touchActions.clickAndHold(elementToSwipe)
                .moveByOffset(-width / 2, 0) // Adjust the distance as needed
                .release()
                .perform();


    }





    public static void initialiseExtentReports() {
        ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("AllTests.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter_all);
    }

    public static void generateExtentReports() { extentReports.flush(); }

    public static void checkStatus(Method m, ITestResult result, AndroidDriver driver) throws Exception {
        if(result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = null;
            screenshotPath = captureScreenshot(result.getTestContext().getName()+ "_" +result.getMethod()+".jpg",driver);
            extentTest.addScreenCaptureFromPath(screenshotPath);
            extentTest.fail(result.getThrowable());
        } else if(result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(m.getName() + " is passed");
        }
    }


    public static String captureScreenshot(String fileName, AndroidDriver driver) {

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("./Screenshots/"+ fileName);
        try {
            FileUtils.copyFile(sourceFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Screenshot saved successfully");
        return destFile.getAbsolutePath();
    }








}

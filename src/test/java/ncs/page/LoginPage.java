package ncs.page;

import framework.generics.Generic;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static ncs.appbase.RealDevice.extentTest;

public class LoginPage {

    AndroidDriver driver;

    public LoginPage(AndroidDriver driver) { this.driver = driver;}

    By ele = new By.ByXPath("//android.view.ViewGroup[@content-desc='Update Profile']");

    public void signInBtn() {
        driver.findElement(MobileBy.AccessibilityId("SIGN IN")).click();
    }

    public void typeID(String login_email) {
        driver.findElement(By.xpath("(//android.widget.ScrollView//android.view.ViewGroup/android.widget.EditText)[1]")).sendKeys(login_email);
    }

    public void typePswd(String pswd) {
        driver.findElement(By.xpath("(//android.widget.ScrollView//android.view.ViewGroup/android.widget.EditText)[2]")).sendKeys(pswd);
    }

    public String clickSignIn() {
        driver.findElement(MobileBy.AccessibilityId("SIGN IN")).click();
        return driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Create Team']/android.widget.TextView")).getText();
    }

    public String clickLogout() throws Exception{

        driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=''])[1]")).click();
        Generic.waitUntilElementVisible(By.xpath("//android.view.ViewGroup[@content-desc='Update Profile']"));
        Generic.scrollDown(3f,0f,0.8f,0.5f);
//        Generic.sendPageUpKey();
//        Generic.jsPageUp("down");
        driver.findElement(MobileBy.AccessibilityId("LOGOUT")).click();
        return driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'BASIC SCOREKEEPING FOR BASEBALL')]")).getText();

    }
}

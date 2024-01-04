package ncs.tests;

import framework.generics.Generic;
import io.appium.java_client.MobileBy;
import ncs.appbase.RealDevice;
import ncs.page.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static framework.configurations.Configuration.appConfig;

public class LoginTest extends RealDevice {

    LoginPage loginPage;
    String login_email = appConfig.getProperty("login.email");
    String signup_email = appConfig.getProperty("signup.email");
    String pswd = appConfig.getProperty("user.pswd");

    @BeforeClass
    public void setup() {
        loginPage = new LoginPage(driver);
    }

    @BeforeMethod
    public void checkAppStatus() throws Exception {

        if(Generic.isElementPresent(driver,"//android.view.ViewGroup[@content-desc='Create Team']/android.widget.TextView"))
            logoutApp();

        driver.resetApp();
        loginPage.signInBtn();

    }

    /**
     *
     */







    /**
     * Positive LogIn test
     *
     * @throws Exception
     */
    @Test
    public void login_Test1() {

        extentTest.info("logging in application with right credentials");
        loginPage.typeID(login_email);
        loginPage.typePswd(pswd);
        Assert.assertEquals(loginPage.clickSignIn(),"Create Team");

    }

    /**
     * Negative username test
     */
    @Test
    public void login_Test2() {

        extentTest.info("logging in application with wrong username and right password");
        loginPage.typeID("george@yopmail.com");
        loginPage.typePswd(pswd);
        driver.findElement(MobileBy.AccessibilityId("SIGN IN")).click();

        Assert.assertTrue(Generic.isElementPresent(driver,"//android.widget.FrameLayout//android.widget.TextView[contains(@text,'incorrect email or password')]"));
        driver.findElement(By.id("android:id/button1")).click();

    }

//
//
//    /**
//     * Negative Password test
//     */
//    @Test
//    public void login_Test3() {
//
//        extentTest.info("logging in application with right username and wrong password");
//        loginPage.typeID(emailID);
//        loginPage.typePswd("user@123");
//        driver.findElement(By.id("com.teamcommunication:id/btnLogin")).click();
//
//        Assert.assertTrue(Generic.isElementPresent(driver,"//android.widget.TextView[@text='Login']"));
//
//    }
//
//    /**
//     * Blank userName and Right Password
//     */
//    @Test
//    public void login_Test4() {
//
//        extentTest.info("logging in application with blank username and right password");
//        loginPage.typeID("");
//        loginPage.typePswd(pswd);
//        driver.findElement(By.id("com.teamcommunication:id/btnLogin")).click();
//
//        Assert.assertTrue(Generic.isElementPresent(driver,"//android.widget.TextView[@text='Login']"));
//
//    }
//
//    /**
//     * Right UserName and Blank Password
//     */
//    @Test
//    public void login_Test5() {
//
//        extentTest.info("logging in application with right username and blank password");
//        loginPage.typeID(emailID);
//        loginPage.typePswd("");
//        driver.findElement(By.id("com.teamcommunication:id/btnLogin")).click();
//
//        Assert.assertTrue(Generic.isElementPresent(driver,"//android.widget.TextView[@text='Login']"));
//
//    }
//
    /**
     * Blank UserName and Blank Password
     */
    @Test(priority = 0)
    public void login_Test6() {

        extentTest.info("logging in application with blank username and blank password");
        loginPage.typeID("");
        loginPage.typePswd("");
        driver.findElement(MobileBy.AccessibilityId("SIGN IN")).click();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(Generic.isElementPresent(driver,"//android.widget.TextView[contains(@text,'Please enter Email ID.')]"));
        softAssert.assertTrue(Generic.isElementPresent(driver,"//android.widget.TextView[contains(@text,'Please enter password')]"));
        softAssert.assertAll();


    }

    public void logoutApp() throws Exception{
        extentTest.info("logout from application");
//        loginPage.clickLogout();
//        System.out.println(loginPage.clickLogout());
        Assert.assertEquals(loginPage.clickLogout(),"BASIC SCOREKEEPING FOR BASEBALL");
    }


}

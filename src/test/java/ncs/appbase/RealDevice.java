package ncs.appbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import framework.configurations.Configuration;
import framework.generics.Generic;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class RealDevice {

    public static AndroidDriver driver;
    public static String screenshotsSubFolderName;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;

    @BeforeTest(groups = {"Smoke","Regression","Sanity"})
    public void launchApp(ITestContext context) throws Exception {

        DesiredCapabilities dc = new DesiredCapabilities();
//        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
//        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);

        dc.setCapability("app","path_to_apk/xyz_01.apk");

        dc.setCapability("ignoreHiddenApiPolicyError",true);

        dc.setCapability("noReset", true);
        dc.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);

        driver = new AndroidDriver<>(Configuration.getRemoteGridURL(), dc);
        Thread.sleep(10000);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        extentTest = extentReports.createTest(context.getName());


    }


//    @AfterTest(groups = {"Smoke","Regression","Sanity"})
    public void tearDown() {
        driver.quit();
    }


    @BeforeSuite
    public void startReports() {
        Generic.initialiseExtentReports();
    }

    @AfterSuite
    public void endReports() { Generic.generateExtentReports(); }

    @AfterMethod
    public void findStatus(Method m, ITestResult result) throws Exception {
        Generic.checkStatus(m,result,driver);
    }









}

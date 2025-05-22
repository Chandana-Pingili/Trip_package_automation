package TripPackageAutomation;

import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Implementation {
    private static WebDriver driver;
    private String baseUrl = "https://www.yatra.com/";
    private static String homePageHandle, offerPageHandle;
    public String offerPageTitle = "Domestic Flights Offers | Deals on Domestic Flight Booking | Yatra.com";
    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeClass
    public  void getDriver() throws IOException {
    	//ExcelUtil.writeToExcel();
        driver = DriverSetup.getDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        homePageHandle = driver.getWindowHandle();
        ReportManager.setup();
        extent = ReportManager.getExtent();
        test = ReportManager.createTest("Trip Package Automation Tests");
        String ss=ScreenshotUtil.captureScreenshot(driver,"HomePage");
        test.addScreenCaptureFromPath(ss, "HomePage");
    }

    @Test(priority = 1)
    public void clickOffers() {
        test.log(Status.INFO, "Clicking on View all offers");
        driver.findElement(By.xpath("//div[text()='View all offers']")).click();
        Set<String> handles = driver.getWindowHandles();
        for (String s : handles) {
            if (!s.equals(homePageHandle)) {
                offerPageHandle = s;
                driver.switchTo().window(s);
                String ss2=ScreenshotUtil.captureScreenshot(driver,"Offers Page");
                test.addScreenCaptureFromPath(ss2, "Offers Page");
                test.log(Status.PASS, "Switched to offer page");
            }
        }
    }

    @Test(priority=1,dependsOnMethods = { "clickOffers" })
    public void verifyOfferPageTitle() {
        test.log(Status.INFO, "Verifying offer page title");
        String title = driver.getTitle();
        test.log(Status.INFO, "Offer page title: " + title);
        try {
        Assert.assertEquals(title, offerPageTitle, "Offers Page title is not as per requirement");
        test.log(Status.PASS, "Offer page title verified");
        }
        catch(AssertionError e)
        {
        	test.log(Status.FAIL, "Offer Page title is not as per requirement");
        	Assert.fail();
        }
    }

    @Test(priority=2,dependsOnMethods = { "clickOffers" })
    public void verifyBannerVisibility() {
        test.log(Status.INFO, "Verifying banner visibility");
        boolean isDisplayed = driver.findElement(By.xpath("//*[@class='wfull bxs']")).isDisplayed();
        Assert.assertTrue(isDisplayed);
        test.log(Status.PASS, "Banner is visible");
        String ss=ScreenshotUtil.captureScreenshot(driver,"BannerVisibility");
        test.addScreenCaptureFromPath(ss, "BannerVisibility");
    }

    @Test(priority=3,dependsOnMethods = { "clickOffers" })
    public void verifyBannerText() {
        test.log(Status.INFO, "Verifying banner text");
        String bannerText = driver.findElement(By.xpath("//*[@class='wfull bxs']")).getText();
        String actualText = "Great Offers & Amazing Deals";
        Assert.assertEquals(bannerText, actualText, "Offers page banner text is not as per requirement");
        test.log(Status.PASS, "Banner text verified");
    }

    @Test(priority=4,dependsOnMethods = { "clickOffers" })
    public void navigateHolidaysTab() {
        test.log(Status.INFO, "Navigating to Holidays tab");
        WebElement element = driver.findElement(By.xpath("//a[text()='Holidays']"));
        element.click();
        WebElement holidays = driver.findElement(By.xpath("//a[text()='Holidays']"));
        holidays.click();
        goToForward(driver);
        String ss=ScreenshotUtil.captureScreenshot(driver,"HolidaysTab");
        test.addScreenCaptureFromPath(ss, "HolidaysTab");
        test.log(Status.PASS, "Navigated to Holidays tab");
    }

    @Test(dependsOnMethods = { "navigateHolidaysTab" })
    public void clickHolidayPackages() {
        test.log(Status.INFO, "Clicking on all holiday packages");
        List<WebElement> packages = driver.findElements(By.xpath("//span[@class='view-btn flR anim']"));
        for (WebElement element : packages) {
            element.click();
        }
        test.log(Status.PASS, "Clicked on all holiday packages");
    }

    @Test(dependsOnMethods = { "clickHolidayPackages" })
    public void printPackagesWithPrices() throws IOException {
        test.log(Status.INFO, "Printing packages with prices");
        Set<String> actualWindowHandles = driver.getWindowHandles();
        int j=1;
        int k=0;
        for (String s : actualWindowHandles) {
            driver.switchTo().window(s);
            
            try {
            	WebElement ele=driver.findElement(By.xpath("//table[@class='rnd-pck']/tbody/tr/td[1]"));
                List<WebElement> packageNames = driver.findElements(By.xpath("//table[@class='rnd-pck']/tbody/tr/td[1]"));
                List<WebElement> prices = driver.findElements(By.xpath("//table[@class='rnd-pck']/tbody/tr/td[2]/span"));
                k=ExcelUtil.writeToExcel(packageNames, prices, j++, k, driver);
//                test.log(Status.INFO, "Package Page title: " + driver.getTitle());
//                for (int i = 0; i < prices.size(); i++) {
//                	System.out.println(packageNames.get(i).getText() + " " + prices.get(i).getText());
//                    test.log(Status.INFO, packageNames.get(i).getText() + " " + prices.get(i).getText());
//                }
//                String ss=ScreenshotUtil.captureScreenshot(driver,("HolidayPackages"+j));
//                test.addScreenCaptureFromPath(ss, "HolidayPackages"+j);
//                j++;
            }
            catch(NoSuchElementException e)
            {
            	//System.out.println("Skipped");
            	continue;
            }
        }
        driver.switchTo().window(offerPageHandle);
        test.log(Status.PASS, "Printed packages with prices");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        ReportManager.flush();
    }

    public static void goToPrevious(WebDriver driver) {
        driver.navigate().back();
    }

    public static void goToForward(WebDriver driver) {
        driver.navigate().forward();
    }

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }
}
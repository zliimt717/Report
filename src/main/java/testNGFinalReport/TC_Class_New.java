package testNGFinalReport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

@Listeners(Guru99Reporter.class)
public class TC_Class_New {
    public static final WebDriver driver=new FirefoxDriver();
    String launchPageHeading="//h2[text()='Guru99 Bank']";
    final String userName_element="//input[@name='uid']",
     password_element="//input[@name='password']",
    signIn_element="//input[@name='btnLogin']";

    final String userName_value="mngr484060",password_value="jUgudYh";
    final String mangerID="//td[contains(text(),'Manger Id')]";
    final String newCustomer="//a[@href='addcustomerpage.php']";
    final String fundTransfer="//a[@href='FundTransInput.php']";

    /**
     * This test case will initialize the webDriver
     */
    @Test(priority = 0,description = "LaunchURL")
    public void tc01LaunchURL(){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://www.demo.guru99.com/V4/");
    }

    /**
     * This test case presence of Heading on Login Page
     */
    @Test(priority = 1,description = "VerifyLaunchPage")
    public void tc02VerifyLaunchPage(){
        Assert.assertTrue(driver.findElement(By.xpath(launchPageHeading)).isDisplayed(),
                "Home Page heading is not displayed");
        System.out.println("Home Page heading is displayed");
    }

    /**
     * This test case will enter Username, password and will then click on signIn button
     */
    @Test(priority = 2,description = "EnterCredentials")
    public void tc03EnterCredentials() throws InterruptedException {
        driver.findElement(By.xpath(userName_element)).sendKeys(userName_value);
        driver.findElement(By.xpath(password_element)).sendKeys(password_value);
        Thread.sleep(5000);
        driver.findElement(By.xpath(signIn_element)).click();
    }

    /**
     * This test case will verify manager's ID presence on Dashboard
     */
    @Test(priority = 3,description = "VerifyLoggedInPage")
    public void tc04VerifyLoggedInPage(){
        Assert.assertTrue(driver.findElement(By.xpath(mangerID)).isDisplayed(),
                "Manager ID label is not displayed");
        System.out.println("Manager Id label is displayed");
    }
    /**
     * This test case will check the presence of presence of New customer link
     * And FundTransfer link in Left pannel
     */
    @Test(priority = 4,description = "VerifyHyperlinks")
    public void tc05VerifyHyperlinks(){
        Assert.assertTrue(driver.findElement(By.xpath(newCustomer)).isEnabled(),
                "New customer hyperlink is not displayed");
        System.out.println("New customer hyperlink is displayed");
        Assert.assertTrue(driver.findElement(By.xpath(fundTransfer)).isEnabled(),
                "Fund Transfer hyperlink is not displayed");
        System.out.println("Fund Transfer hyperlink is displayed");
    }
    @AfterTest
    public void terminateBrowser(){
        driver.quit();
    }
}

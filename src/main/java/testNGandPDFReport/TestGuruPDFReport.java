package testNGandPDFReport;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(ReportPDFListener.class)
public class TestGuruPDFReport extends BaseClass{
    WebDriver driver;

    // Testcase failed so screen shot generate
    @Test
    public void testPDFReportOne(){
        driver=BaseClass.getDriver();
        driver.get("https://www.google.com/");
        Assert.assertTrue(false);
    }

    // Testcase failed so screen shot generate
    @Test
    public void testPDFReportTwo(){
        driver=BaseClass.getDriver();
        driver.get("http:/guru99.com");
        Assert.assertTrue(false);
    }

    // Testcase pass so screen shot generate
    @Test
    public void testPDFReportThree(){
        driver=BaseClass.getDriver();
        driver.get("http://demo.guru99.com/");
        Assert.assertTrue(true);
    }
}

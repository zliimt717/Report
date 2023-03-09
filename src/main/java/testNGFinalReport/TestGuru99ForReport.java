package testNGFinalReport;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(value = Guru99Reporter.class)
public class TestGuru99ForReport {
    @Test(priority = 0, description = "testReportOne")
    public void testReporterOne(){
        Assert.assertTrue(true);
    }
    @Test(priority = 1, description = "testReportTwo")
    public void testReporterTwo(){
        Assert.assertTrue(false);
    }

}

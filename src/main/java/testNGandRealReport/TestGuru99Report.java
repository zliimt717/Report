package testNGandRealReport;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(RealGuru99TimeReport.class)
public class TestGuru99Report {

    @Test
    public void testRealReportOne(){
        Assert.assertTrue(true);
    }

    @Test
    public void testRealReportTwo(){
        Assert.assertTrue(false);
    }



}


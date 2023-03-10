package testNGScreenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ScreenCutGenerate {

    @Test
    public void testScreenShot() throws IOException {
        WebDriver driver=new FirefoxDriver();
        driver.get("http://demo.guru99.com/v4/");
        this.takeSnapShot(driver,"C:\\Users\\xin.gu\\SpringPractices\\testNGandPDF\\test.png");

    }

    private void takeSnapShot(WebDriver driver, String fileWithPath) throws IOException {
        // Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot=((TakesScreenshot) driver);
        // Call getScreenshotAs method to create image file
        File ScrshotFile=scrShot.getScreenshotAs(OutputType.FILE);
        // Move image file to new destination
        File DestFile=new File(fileWithPath);
        // Copy file at destination
        FileUtils.copyFile(ScrshotFile,DestFile);
    }

}

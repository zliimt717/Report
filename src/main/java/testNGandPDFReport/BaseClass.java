package testNGandPDFReport;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class BaseClass {
    static WebDriver driver;
    public static WebDriver getDriver(){
        if(driver==null){
            driver=new FirefoxDriver();
        }
        return driver;
    }
    /**
     * This function will take screenshot
     * @param webdriver
     * @param fileWithPath
     * @throw Exception
     */
    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        // Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot=((TakesScreenshot) webdriver);
        // Call getScreenshotAs method to create image file
        File ScrFile=scrShot.getScreenshotAs(OutputType.FILE);
        // Move image file to new destination
        File DestFile=new File(fileWithPath);
        // Copy file at destination
        FileUtils.copyFile(ScrFile,DestFile);

    }

}

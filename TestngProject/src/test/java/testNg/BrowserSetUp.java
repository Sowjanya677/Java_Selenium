package testNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class BrowserSetUp {
    protected static WebDriver driver;


    @BeforeMethod
    @Parameters("browser")
    public void browserInt(String browser) {
       // System.out.println("Opening Facebook...");
        // Code to open Facebook in a web browser would go here
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            System.out.println("Invalid browser name: " + browser);

        }
        driver.manage().window().maximize();
        }


    @AfterMethod
    public void tearDown() throws IOException {

       takeScreenshot();
        driver.quit();
        System.out.println("Browser closed");
    }


    public void takeScreenshot() throws IOException {
//        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//        // Code to save the screenshot file would go here
//        src.canWrite("")

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.toString().replace(":", "-");
        TakesScreenshot ts=(TakesScreenshot) driver;
        File src=ts.getScreenshotAs(OutputType .FILE);
        File dest=new File("C:\\Users\\Yashwanth\\IdeaProjects\\TestngProject\\src\\Screenshot\\test_"+timestamp+".png");
        System.out.println("Screenshot taken successfully");
        FileHandler.copy(src,dest);

    }



}



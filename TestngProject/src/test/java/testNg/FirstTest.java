package testNg;


import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.testng.Assert.*;

public class FirstTest extends BrowserSetUp {

    @Test(priority = 2)

    public void openGoogle() {

        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        String expectedTitle="Google";
        String ActualTile=driver.getTitle();
        assertEquals(ActualTile,expectedTitle,"matching");


    }

    @Test(dependsOnMethods = "openGoogle",priority = 1)
    public void openFacebook() {

        driver.get("https://www.facebook.com");
        System.out.println(driver.getTitle());

    }

}





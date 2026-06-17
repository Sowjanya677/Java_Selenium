package testNg;

import static testNg.BrowserSetUp.driver;

public class TestCase1 {

    public void openGoogle() {

        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());


}

    public static void main(String[] args) {
        TestCase1 obj=new TestCase1();
        obj.openGoogle();


    }


}

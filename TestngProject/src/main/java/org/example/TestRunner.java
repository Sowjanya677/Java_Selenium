package org.example;

import org.testng.TestNG;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        TestNG testng = new TestNG();

        // Add the testng.xml file
        List<String> suites = new ArrayList<>();
        suites.add("testng.xml");
        testng.setTestSuites(suites);

        // Run the tests
        testng.run();
    }
}


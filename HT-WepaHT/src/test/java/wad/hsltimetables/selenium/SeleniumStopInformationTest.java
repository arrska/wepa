package wad.hsltimetables.selenium;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class SeleniumStopInformationTest {
    private WebDriver driver;
    private String baseAddress;
    
    private String port;
    
    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        this.baseAddress = "http://localhost:" + port +"/app/";
    }
    
    //stop name shown nicely 
    @Test
    public void stopNameAndCodeShown() {
        driver.get(baseAddress + "stop/1284123");
        
        assertTrue("stop name should be shown", driver.getPageSource().contains("Patola"));
        assertTrue("stop code should be shown", driver.getPageSource().contains("(3122)"));
    }
    
    //stop departures (max 10), no negative minutes
    @Test
    public void stopDeparturesShown() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement depTable = driver.findElement(By.className("deptable"));
        assertNotNull("departure table not found", depTable);
        List<WebElement> departures = depTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        
        assertTrue("zero to 10 departures should be shown", departures.size() >= 0 && departures.size() <=10);
        
        for (WebElement d : departures) {
            WebElement lastColumn = d.findElements(By.tagName("td")).get(5);
            Integer minutesToDeparture = Integer.parseInt(lastColumn.getText());
            assertTrue("minutes to departure should be zero or more and less than 360",  minutesToDeparture >= 0 && minutesToDeparture <= 360);
        }
    }
    
    //linelist
    @Test
    public void stopLinesShown() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement lineTable = driver.findElement(By.className("linetable"));
        assertNotNull("line table not found", lineTable);
    }
    
    //linelinks are valid
    @Test
    public void stopLineLinksAreValid() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement lineTable = driver.findElement(By.className("linetable"));
        List<WebElement> links = lineTable.findElements(By.tagName("a"));
        
        List<String> hrefs = new ArrayList<String>();
        for (WebElement link : links) {
            hrefs.add(link.getAttribute("href"));
        }
        
        for (String url : hrefs) {
            driver.get(url);
            assertTrue(driver.getTitle().contains("line info"));
            assertTrue(driver.getPageSource().contains("Line info"));
        }
    }
}

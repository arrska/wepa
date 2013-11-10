package wad.hsltimetables.selenium;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class SeleniumLineInfoTest {
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
    public void lineNameAndCodeShown() {
        driver.get(baseAddress + "line/1064  2/");
        
        assertTrue("stop starting stop should be shown", driver.getPageSource().contains("Rautatientori"));
        assertTrue("stop end stop should be shown", driver.getPageSource().contains("Itä-Pakila"));
        assertTrue("stop code should be shown", driver.getPageSource().contains("(64)"));
    }
    
    //stop departures (max 10), no negative minutes
    @Test
    public void lineStopListTest() {
        driver.get(baseAddress + "line/1064  2/");
        
        WebElement lineTable = driver.findElement(By.tagName("table"));
        assertNotNull("linetable should exist", lineTable);
        
        List<WebElement> rows = lineTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        
        assertTrue("Line should have stops", rows.size() > 0);
        
        List<String> hrefs = new ArrayList<String>();
        for (WebElement row : rows) {
            WebElement link = row.findElement(By.tagName("a"));
            hrefs.add(link.getAttribute("href"));
        }
        
        for (String url : hrefs) {
            driver.get(url);
            assertTrue("page behind stop link should be stopinfo page", driver.getTitle().contains("stop information"));
            assertTrue("page behind stop link should be stopinfo page", driver.getPageSource().contains("Stop info"));
            assertTrue("page behind stop link should include the line", driver.getPageSource().contains("64"));
        }
    }
}

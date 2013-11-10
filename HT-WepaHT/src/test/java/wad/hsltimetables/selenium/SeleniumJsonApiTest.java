package wad.hsltimetables.selenium;

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

public class SeleniumJsonApiTest {
    private WebDriver driver;
    private String baseAddress;
    
    private String port;
    
    private String username;
    private String password;
    private String apikey;
    
    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        this.baseAddress = "http://localhost:" + port +"/app/";
        Random r = new Random();
        
        this.username = "testuser" + (r.nextInt(1000));
        this.password = "pa55wrd";
        
        //create user
        
        driver.get(baseAddress + "register");
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys(username);
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        passwordField.sendKeys(password);
        
        WebElement loginForm = driver.findElement(By.name("f"));
        loginForm.submit();
        
        driver.get(baseAddress + "user/" + username);
        this.apikey = driver.findElement(By.id("apikey")).getText();
        //driver.get(baseAddress + "j_spring_security_logout");
    }
    
    //api stop list empty
    @Test
    public void emptyStopList() {
        driver.get(baseAddress + "api/stops?apikey=" + this.apikey);
        
        assertEquals("stoplist should be empty", "[]", driver.getPageSource().trim());
    }
    //api stop list one stop
    @Test
    public void populatedStopListWithOne() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement favBut = driver.findElement(By.tagName("button"));
        
        favBut.click();
        
        driver.get(baseAddress + "api/stops?apikey=" + this.apikey);
        
        assertNotEquals("stoplist shouldn't be empty", "[]", driver.getPageSource().trim());
        assertTrue("should include the stopcode", driver.getPageSource().contains("\"code\":1284123"));
        assertTrue("should include the stopname", driver.getPageSource().contains("\"name\":\"Patola\""));
        assertTrue("should include the stopaddress", driver.getPageSource().contains("\"address\":\"Siltavoudintie\""));
        assertTrue("should include the shortcode", driver.getPageSource().contains("\"code_short\":\"3122\""));
    }
    
    //api stop info right
    @Test
    public void stopInfoTest() {
        driver.get(baseAddress + "api/stop/1284123?apikey=" + this.apikey);
        
        assertTrue("departures must be found", driver.getPageSource().contains("\"departures\":"));
        assertTrue("lines must be found", driver.getPageSource().contains("\"lines\":"));
    }
    
    //api stop info wrong
    @Test
    public void stopInfoWrongCodeTest() {
        driver.get(baseAddress + "api/stop/9999?apikey=" + this.apikey);
        
        assertEquals("departures must be found", "null", driver.getPageSource());
    }
    
    //api line info ok
    @Test
    public void lineInfoTest() {
        driver.get(baseAddress + "api/line/1064N 2/?apikey=" + this.apikey);
        
        assertTrue("linecode must be found", driver.getPageSource().contains("\"code\":\"1064N 2\""));
        assertTrue("stopinfo not found", driver.getPageSource().contains("\"line_stops\":"));
    }
    
    //api line info ok
    
}

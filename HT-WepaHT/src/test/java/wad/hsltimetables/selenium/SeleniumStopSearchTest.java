package wad.hsltimetables.selenium;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class SeleniumStopSearchTest {
    private WebDriver driver;
    private String baseAddress;
    
    private String port;
    
    private String username;
    private String password;
    
    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        this.baseAddress = "http://localhost:" + port +"/app/";
        
        Random r = new Random();
        this.username = "testuser" + (r.nextInt(1000));
        System.out.println(username);
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
        //this.driver = new HtmlUnitDriver();
    }
    
    //search stops, nothing found
    
    @Test
    public void noStopsFoundWithSearch() {
        driver.get(baseAddress + "stop/search");
        String query = "findnothing";
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + query, driver.getCurrentUrl());
        assertEquals("query field should have the qyuery", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue("no results -message should be shown", driver.getPageSource().contains("No results for \"" + query + "\""));
    }
    
    //search stops, empty string
    @Test
    public void stopSearchWithEmptyString() {
        driver.get(baseAddress + "stop/search");
        String query = "";
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + query, driver.getCurrentUrl());
        assertEquals("query field should have be empty after empty search", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue("invalid length -message should be shown", driver.getPageSource().contains("length must be between"));
    }
    
    //search stops, invalid string
    @Test
    public void stopSearchWithTooShortString() {
        driver.get(baseAddress + "stop/search");
        String query = "as";
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + query, driver.getCurrentUrl());
        assertEquals("query field should have the previous query", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue("invalid length -message should be shown", driver.getPageSource().contains("length must be between"));
    }
    
    @Test
    public void stopSearchWithTooLongString() throws UnsupportedEncodingException {
        driver.get(baseAddress + "stop/search");
        String query = "asdsad sad lkfjk ldfclkads ulkgjsklfjaskjd kasj dlaskjdkasjdwoq lkdjfklasj flkfjlasfjas klksjdkasjdlaur iojfkslajkdl";
        String encoded = URLEncoder.encode(query, "UTF-8");
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + encoded, driver.getCurrentUrl());
        assertEquals("query field should have the previous query", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue("invalid length -message should be shown", driver.getPageSource().contains("length must be between"));
    }
    
    @Test
    public void stopSearchWithInvalidCharacters() throws UnsupportedEncodingException {
        driver.get(baseAddress + "stop/search");
        String query = "\"> just hacking <\"";
        String encoded = URLEncoder.encode(query, "UTF-8");
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + encoded, driver.getCurrentUrl());
        assertEquals("query field should have the previous query", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue("invalid length -message should be shown", driver.getPageSource().contains("Only alphanumeric characters and some special characters"));
    }
    
    //search stops, valid string
    @Test
    public void normalStopSearchWorks() {
        driver.get(baseAddress + "stop/search");
        String query = "Patola";
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        assertEquals("wrong url", baseAddress + "stop/search?query=" + query, driver.getCurrentUrl());
        assertEquals("query field should have the previous query", query, driver.findElement(By.name("query")).getAttribute("value"));
        assertTrue(driver.getPageSource().contains("Search results for \"" + query + "\""));
        
        
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> links = table.findElements(By.linkText(query));
        assertTrue("table of results should be shown", links.size()>1);
    }
    
    //search stops, links work
    @Test
    public void normalStopSearchLinksWork() {
        driver.get(baseAddress + "stop/search");
        String query = "Patola";
        
        WebElement text = driver.findElement(By.name("query"));
        text.sendKeys(query);
        
        text.submit();
        
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> links = table.findElements(By.linkText(query));
        String linkurl = links.get(0).getAttribute("href");
        links.get(0).click();
        assertEquals("list links should work", linkurl, driver.getCurrentUrl());
        assertTrue(driver.getPageSource().contains(query));
        assertTrue(driver.getPageSource().contains("Stop info"));
    }
}

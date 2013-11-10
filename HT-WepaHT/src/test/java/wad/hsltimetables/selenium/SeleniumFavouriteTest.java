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

public class SeleniumFavouriteTest {
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
    }
    
    
    //favourite button exists
    @Test
    public void favouriteButtonFoundTest() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement favBut = driver.findElement(By.tagName("button"));
        assertNotNull("favourite button not found", favBut);
        assertEquals("favbutton text is wrong", "favourite", favBut.getText());
    }
    
    //after favouriting unfavourite button exist
    @Test
    public void unfavouriteButtonFoundTest() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement favBut = driver.findElement(By.tagName("button"));
        
        favBut.click();
        driver.get(baseAddress + "stop/1284123");
        
        WebElement unfavBut = driver.findElement(By.tagName("button"));
        assertNotNull("unfavourite button not found", unfavBut);
        assertEquals("unfavbutton text is wrong", "unfavourite", unfavBut.getText());
    }
    
    
    //my stops is empty
    @Test
    public void stopListEmptyTest() {
        driver.get(baseAddress + "stop");
        
        assertTrue("no stops listed", driver.getPageSource().contains("no favourited stops"));
        //only nav ul
        assertTrue("no stop list should be found", driver.findElements(By.tagName("ul")).size() == 1);
    }
    
    //mys stops include favourited stops
    @Test
    public void stopListPopulatedAfterFavouritingTest() {
        driver.get(baseAddress + "stop/1284123");
        
        WebElement favBut = driver.findElement(By.tagName("button"));
        
        favBut.click();
        driver.get(baseAddress + "stop");
        
        WebElement stopList = driver.findElements(By.tagName("ul")).get(1);
        List<WebElement> stops = stopList.findElements(By.tagName("li"));
        
        assertFalse("no stops listed", driver.getPageSource().contains("no favourited stops"));
        assertEquals("only one stop added", 1, stops.size());
        assertEquals("right stop added", "Patola Siltavoudintie (3122)", stops.get(0).getText().trim());
    }
    
    //my stops doesnt include unfavourited stops
    @Test
    public void stopListEmptyAfterUnfavouritingTest() {
        driver.get(baseAddress + "stop/1291220");
        WebElement favBut = driver.findElement(By.tagName("button"));
        favBut.click();
        
        driver.get(baseAddress + "stop/1284123");
        favBut = driver.findElement(By.tagName("button"));
        favBut.click();
        
        driver.get(baseAddress + "stop");
        
        WebElement stopList = driver.findElement(By.id("mystops"));
        List<WebElement> stops = stopList.findElements(By.tagName("a"));
        List<String> stopUrls = new ArrayList<String>();
        
        for (WebElement stop : stops) {
            System.out.println(stop.getAttribute("href"));
            stopUrls.add(stop.getAttribute("href"));
        }
        
        //unfav all
        for (String url : stopUrls) {
            driver.get(url);
            WebElement unfavBut = driver.findElement(By.tagName("button"));
            unfavBut.click();
            
            driver.get(baseAddress + "stop");
            assertFalse("stop should've bee removed", driver.getPageSource().contains(url));
        }
        
        driver.get(baseAddress + "stop");
        assertTrue("no stops listed", driver.getPageSource().contains("no favourited stops"));
    }
}

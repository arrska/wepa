package wad.hsltimetables.selenium;

import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumUserEditingTest {
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
        //this.username = "testuser";// + (r.nextInt(200));
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
    
    @Test
    public void deleteUserWorks() {
        driver.get(baseAddress + "user/" + username);
        
        WebElement delForm = driver.findElement(By.id("delForm"));
        delForm.submit();
        
        
        assertTrue(driver.getPageSource().contains("login"));
        assertFalse(driver.getPageSource().contains("logout"));
        assertEquals(baseAddress, driver.getCurrentUrl());
        
        driver.get(baseAddress + "login");
        
        
        WebElement usernameField = driver.findElement(By.name("j_username"));
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        
        WebElement loginForm = driver.findElement(By.name("f"));
        loginForm.submit();
        
        assertTrue("errormessage should be shown", driver.getPageSource().contains("Login failed"));
        assertTrue("error reason should be shown", driver.getPageSource().contains("Bad credentials"));
        assertFalse("logout-link shouldm't be visible on first page", driver.getPageSource().contains("logout"));
        assertTrue("login-link should be visible on first page", driver.getPageSource().contains("login"));
    }
    
    @Test
    public void passWordChangeWorks() {
        String newpw ="asdFDS#4";
        
        driver.get(baseAddress + "user/" + username);
        System.out.println(driver.getCurrentUrl());
        
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        passwordField1.sendKeys(newpw);
        passwordField2.sendKeys(newpw);
        
        WebElement pwForm = driver.findElement(By.id("passwdForm"));
        pwForm.submit();
        
        
        assertFalse(driver.getPageSource().contains("login"));
        assertTrue(driver.getPageSource().contains("logout"));
        
        driver.get("http://localhost:" + port + "/j_spring_security_logout");
        
        driver.get(baseAddress + "login");
        
        WebElement usernameField = driver.findElement(By.name("j_username"));
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys(newpw);
        
        WebElement regForm = driver.findElement(By.name("f"));
        regForm.submit();
        
        assertFalse("errormessage should not be shown", driver.getPageSource().contains("Login failed"));
        assertTrue("logout-link shouldm't be visible on first page", driver.getPageSource().contains("logout"));
        assertFalse("login-link should be visible on first page", driver.getPageSource().contains("login"));
    }
}

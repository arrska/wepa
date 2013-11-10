package wad.hsltimetables.selenium;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumUserLoginLogoutTest {
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
        this.username = "testuser";
        this.password = "pa55wrd";
        
        //create user
        
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys(username);
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        this.driver = new HtmlUnitDriver();
    }
    
    @Test
    public void userSuccessfullyLogsIn() {
        driver.get(baseAddress + "login");
    
        WebElement usernameField = driver.findElement(By.name("j_username"));
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.name("f"));
        regForm.submit();
        
        assertEquals("redirection to front page", baseAddress, driver.getCurrentUrl());
        assertTrue("username should be visible on first page", driver.getPageSource().contains(username));
        assertTrue("logout-link should be visible on first page", driver.getPageSource().contains("logout"));
        assertFalse("logoin-link shouldn't be visible on first page", driver.getPageSource().contains("login"));
    }
    
    @Test
    public void loginFailsWithWrongCreditentials() {
        driver.get(baseAddress + "login");
    
        WebElement usernameField = driver.findElement(By.name("j_username"));
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys("vääräsalasana");
        
        WebElement regForm = driver.findElement(By.name("f"));
        regForm.submit();
        
        assertTrue("redirection to front page", driver.getCurrentUrl().contains((baseAddress + "login")));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("Login failed"));
        assertTrue("error reason should be shown", driver.getPageSource().contains("Bad credentials"));
        assertFalse("logout-link shouldm't be visible on first page", driver.getPageSource().contains("logout"));
        assertTrue("login-link should be visible on first page", driver.getPageSource().contains("login"));
    }
    
    @Test
    public void logoutLinkWorks(){
        driver.get(baseAddress + "login");
    
        WebElement usernameField = driver.findElement(By.name("j_username"));
        WebElement passwordField = driver.findElement(By.name("j_password"));
        
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.name("f"));
        regForm.submit();
        
        driver.get("http://localhost:" + port + "/j_spring_security_logout");
        
        assertTrue(driver.getPageSource().contains("login"));
        assertFalse(driver.getPageSource().contains("logout"));
        assertEquals(baseAddress, driver.getCurrentUrl());
    }
    
}

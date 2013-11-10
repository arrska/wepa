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

public class SeleniumUserRegistrationTest {
    private WebDriver driver;
    private String baseAddress;
    
    private String port;
    
    private String username;
    private String password;
    
    private List<String> usernames;

    public SeleniumUserRegistrationTest() {
        usernames = new ArrayList<String>();
        usernames.add("testuser1");
        usernames.add("testuser2");
        usernames.add("testuser3");
        usernames.add("testuser4");
        usernames.add("testuser5");
        usernames.add("testuser6");
        usernames.add("testuser7");
        usernames.add("testuser8");
        usernames.add("testuser9");
        usernames.add("testuser10");
    }
    
    @Before
    public void setUp() {
        this.driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        this.baseAddress = "http://localhost:" + port +"/app/";
        this.username = usernames.remove(0);
        this.password = "pa55wrd";
    }
    
    @Test
    public void testRegistrationFormAcceptsValidDataAndRedirects() {
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
        
        assertEquals("form should redirect to login", (baseAddress + "login"), driver.getCurrentUrl());
        assertTrue("message of successfulness should be shown", driver.getPageSource().contains("Registration complete, please sign in"));
        
        WebElement loginUsernameField = driver.findElement(By.name("j_username"));
        assertEquals("new users username should be already on the usernamefield", this.username, loginUsernameField.getAttribute("value"));
    }
    @Test
    public void testRegistrationFormRejectsShortUsername() {
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys("asd");
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        assertNotEquals("form should't redirect to login if short username", driver.getCurrentUrl(), (baseAddress + "login"));
        assertTrue("form should be shown again", driver.getCurrentUrl().startsWith(baseAddress + "register"));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("length must be between"));
    }
    @Test
    public void testRegistrationFormRejectsLongUsername() {
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys("aaaaaaaaaaaaaaaaaaaaaaa");
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        assertNotEquals("form should't redirect to login if long username", driver.getCurrentUrl(), (baseAddress + "login"));
        assertTrue("form should be shown again", driver.getCurrentUrl().startsWith(baseAddress + "register"));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("length must be between"));
    }
    @Test
    public void testRegistrationFormRejectsUsernameWithIllegalCharacters() {
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys("\"><h1>LOL</h1><\"");
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password);
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        assertNotEquals("form should't redirect to login if long username", driver.getCurrentUrl(), (baseAddress + "login"));
        assertTrue("form should be shown again", driver.getCurrentUrl().startsWith(baseAddress + "register"));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("Only characters"));
    }
    @Test
    public void testRegistrationFormRejectsShortPassword() {
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys(username);
        passwordField1.sendKeys("12345");
        passwordField2.sendKeys("12345");
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        assertNotEquals("form should't redirect to login if short password", driver.getCurrentUrl(), (baseAddress + "login"));
        assertTrue("form should be shown again", driver.getCurrentUrl().startsWith(baseAddress + "register"));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("length must be between"));
    }
    @Test
    public void testRegistrationFormRejectsNonMatchingPasswords() {
        driver.get(baseAddress + "register");
        System.out.println(baseAddress);
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField1 = driver.findElement(By.name("password1"));
        WebElement passwordField2 = driver.findElement(By.name("password2"));
        
        usernameField.sendKeys(username);
        passwordField1.sendKeys(password);
        passwordField2.sendKeys(password + "a");
        
        WebElement regForm = driver.findElement(By.id("regForm"));
        regForm.submit();
        
        assertNotEquals("form should't redirect to login if passwords dont match", driver.getCurrentUrl(), (baseAddress + "login"));
        assertTrue("form should be shown again", driver.getCurrentUrl().startsWith(baseAddress + "register"));
        assertTrue("errormessage should be shown", driver.getPageSource().contains("passwords didnt match"));
    }
}

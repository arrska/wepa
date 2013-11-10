package wad.hsltimetables.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControlServiceTest {
    
    @Autowired
    private UserControlService userControlService;
    
    private User testUser1;
    private User testUser2;
    private User testUser3;
    
    public UserControlServiceTest() {
    }
    
    @Before
    public void setUp() {
        testUser1 = new User();
        testUser1.setName("testuser1");
        testUser1.setPassword("paZZw00rd");
        
        testUser2 = new User();
        testUser2.setName("testuser2");
        testUser2.setPassword("DaS:)iasjh8");
        
        testUser3 = new User();
        testUser3.setName("testuser3");
        testUser3.setPassword("Sal4sNA");
        
    }
    
    @Test
    public void addingUserWithSameNameTwiceThrowsException () {
        boolean exceptionThrown = false;
        
        User u = new User();
        User u2 = new User();
        u.setName("peelo");
        u2.setName("peelo");
        
        try {
            userControlService.newUser(u);
            userControlService.newUser(u2);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        
        assertTrue("exception should be thrown if adding multiple users with same name", exceptionThrown);
    }
    
    
    @Test
    public void userCanBeFoundAfterAdding () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
        
        User u = userControlService.findUser(testUser1.getName());
        
        assertEquals("added users username doestn match the found user's one", u.getName(), testUser1.getName());
        assertEquals("added users password doestn match the found user's one", u.getPassword(), testUser1.getPassword());
    }
    
    @Test
    public void userGetsAnApiKeyWhenAdding () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
        
        User u = userControlService.findUser(testUser1.getName());
        
        assertTrue("apikey is null or empty", u.getApikey() != null && !u.getApikey().isEmpty());
    }
    
    @Test
    public void noUsersFoundFromFreshService () {
        assertNull("testuser never added, but found", userControlService.findUser(testUser1.getName()));
    }
    
    @Test
    public void noUserFoundFromFreshService () {
        assertTrue("userlist is not empty", userControlService.getUsers().isEmpty());
    }
    
    @Test
    public void userCanBeFoundByApikey () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
        User u = userControlService.findUser(testUser1.getName());
        User u2 = userControlService.findUserByApiKey(u.getApikey());
        
        assertEquals("user couldn't be found by apikey", u.getName(), u2.getName());
    }
    
    @Test
    public void noAuthenticatedUserBeforeAuthentication () {
        assertNull("authenticated user found", userControlService.getAuthenticatedUser());
        assertFalse("service claims someone is authenticated", userControlService.isAuthenticated());
    }
    
    @Test
    public void authenticatedUserFoundAfterAuthentication () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        } 
        String name = testUser1.getName();
        String password = testUser1.getPassword();
        List<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
        gas.add(new SimpleGrantedAuthority("user"));
        Authentication a = new UsernamePasswordAuthenticationToken(name, password, gas);
        
        SecurityContextHolder.getContext().setAuthentication(a);
        
        assertNotNull("authenticated user not found", userControlService.getAuthenticatedUser());
        assertEquals("wrong authenticated user found", name, userControlService.getAuthenticatedUser().getName());
        assertTrue("service claims noone is authenticated", userControlService.isAuthenticated());
    }
    
    
    @Test
    public void userCantBeFoundAfterDeleting () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
        
        userControlService.deleteUser(testUser1.getName());
        
        User u = userControlService.findUser(testUser1.getName());
        
        assertNull("deleted user shouldn't be found", u);
        
        List<User> users = userControlService.getUsers();
        assertFalse("deleted user shouldn't be listed", users.contains(u));
    }
    
    @Test
    public void passwordChangingWorks () {
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
        
        User u = userControlService.findUser(testUser1.getName());
        userControlService.changePassword(u, "Buga1a");
        u = userControlService.findUser(testUser1.getName());
        
        assertEquals("user password wasnt changed", "Buga1a", u.getPassword());
    }
}

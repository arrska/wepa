package wad.hsltimetables.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApiAuthenticationServiceTest {
    @Autowired
    private UserControlService userControlService;
    @Autowired
    private ApiAuthenticationService apiAuthenticationService;
    
    public ApiAuthenticationServiceTest() {
    }
    
    @Before
    public void setUp() {
        User testUser1;
        testUser1 = new User();
        testUser1.setName("testuser1");
        testUser1.setPassword("paZZw00rd");
        
        try {
            userControlService.newUser(testUser1);
        } catch (Exception ex) {
            fail("user adding threw exception");
        }
    }

    @Test
    public void testApiKeyAuthentication() {
        String apikey = userControlService.findUser("testuser1").getApikey();
        User user = null;
        try {
            user = apiAuthenticationService.authenticate(apikey);
        } catch (Exception ex) {
            fail("authentication with apikey failed");
        }
        assertEquals("apikey authenticated wrong user", "testuser1", user.getName());
    }
    
}

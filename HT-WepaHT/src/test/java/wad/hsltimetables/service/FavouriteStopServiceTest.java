package wad.hsltimetables.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.Stop;
import wad.hsltimetables.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class FavouriteStopServiceTest {
    @Autowired
    private FavouriteService<Stop> favouriteStopService;
    
    @Autowired
    private UserControlService userControlService;
    
    private User testUser1;
    private User testUser2;
    private Stop testStop1;
    private Stop testStop2;
    
    
    @Before
    public void setUp() {
        testUser1 = new User();
        testUser1.setName("testuser1");
        testUser1.setPassword("94jfjh2kaA");
        
        testUser2 = new User();
        testUser2.setName("testuser2");
        testUser2.setPassword("ffffuu");
        
        testStop1 = new Stop();
        testStop1.setCode(1);
        testStop1.setAddressFi("Testikatu 1");
        testStop1.setNameFi("Pysäkki1");
        testStop1.setShortCode("0001");
        
        testStop2 = new Stop();
        testStop2.setCode(2);
        testStop2.setAddressFi("Testikatu 2");
        testStop2.setNameFi("Pysäkki2");
        testStop2.setShortCode("0002");
        
        try {
            userControlService.newUser(testUser1);
            userControlService.newUser(testUser2);
        } catch (Exception ex) {
            fail("user adding failed, because of duplicate usernames");
        }
        
    }
    
    @Test
    public void isFavouriteWithNoFavourites() {
        User testUser = userControlService.findUser("testuser1");
        
        assertFalse(favouriteStopService.isFavourite(testUser, new Stop()));
        
        assertFalse(testUser.getFavouriteStops().contains(testStop1.getCode()));
    }
    
    @Test
    public void isFavouriteWithNullArguments() {
        User testUser = userControlService.findUser("testuser1");
        
        assertFalse(favouriteStopService.isFavourite(null, testStop1));
        assertFalse(favouriteStopService.isFavourite(testUser, null));
        
        assertFalse("user's favouritelist is not initialized", favouriteStopService.isFavourite(testUser1, testStop1));
    }
    
    @Test
    public void oneFavourite() {
        User testUser = userControlService.findUser("testuser1");
        
        favouriteStopService.favourite(testUser, testStop1);
        
        assertTrue(favouriteStopService.isFavourite(testUser, testStop1));
        
        assertTrue(testUser.getFavouriteStops().contains(testStop1.getCode()));
    }
    
    @Test
    public void multipleFavourites() {
        User testUser = userControlService.findUser("testuser1");
        
        favouriteStopService.favourite(testUser, testStop1);
        favouriteStopService.favourite(testUser, testStop2);
        
        assertTrue(favouriteStopService.isFavourite(testUser, testStop1));
        assertTrue(favouriteStopService.isFavourite(testUser, testStop2));
        
        assertTrue(testUser.getFavouriteStops().contains(testStop1.getCode()));
        assertTrue(testUser.getFavouriteStops().contains(testStop2.getCode()));
    }
    
    @Test
    public void testUnfavourite() {
        User testUser = userControlService.findUser("testuser1");
        
        favouriteStopService.favourite(testUser, testStop1);
        favouriteStopService.favourite(testUser, testStop2);
        
        favouriteStopService.unfavourite(testUser, testStop2);
        
        assertTrue(favouriteStopService.isFavourite(testUser, testStop1));
        assertFalse(favouriteStopService.isFavourite(testUser, testStop2));
        
        assertTrue(testUser.getFavouriteStops().contains(testStop1.getCode()));
        assertFalse(testUser.getFavouriteStops().contains(testStop2.getCode()));
    }
}

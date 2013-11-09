package wad.hsltimetables.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import wad.hsltimetables.domain.Stop;
import wad.hsltimetables.domain.User;

public class FavouriteStopServiceTest {
    @Autowired
    private FavouriteService<Stop> favouriteStopService;
    
    @Autowired
    private UserControlService userControlService;
    
    @Autowired
    private StopService stopService;
    
    private User testUser;
    
    public FavouriteStopServiceTest() {
    }
    
    @Before
    public void setUp() {
        testUser = new User();
        testUser
        userControlService.newUser(null)
    }

    @Test
    public void testSomeMethod() {
        favouriteStopService.
    }
    
}

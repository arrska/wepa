package wad.hsltimetables.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.Departure;
import wad.hsltimetables.domain.Stop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StopServiceTest {
    @Autowired
    StopService stopService;
    
    public StopServiceTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void stopGettingWorks() {
        Integer stopcode = 1220207;
        Stop stop = stopService.getStop(stopcode);
        
        assertNotNull("stop wasn't found", stop);
        assertNotNull("stop should have lines", stop.getLines());
        assertTrue("stop should have lines", stop.getLines().size() > 0);
        
        assertNotNull("departure's line should be set", stop.getDepartures().get(0).getLine());
        
        for (Departure dep : stop.getDepartures()) {
            assertTrue("only departures of stop's lines are valid", stop.getLines().contains(dep.getLine()));
        }
    }

    @Test
    public void stopGettingWithWrongCode() {
        Integer stopcode = 123654;
        Stop stop = stopService.getStop(stopcode);
        
        assertNull("stop wasn't found", stop);
    }
    
    @Test
    public void stopsGettingWorks() {
        List<Integer> stopcodes = new ArrayList<Integer>();
        stopcodes.add(1220207);
        stopcodes.add(1284115);
        stopcodes.add(1342198);
        stopcodes.add(1121124);
        
        List<Stop> stops = stopService.getStops(stopcodes);
        
        for (Stop stop : stops) {
            assertNotNull("stop wasn't found", stop);
            assertNotNull("stop should have lines", stop.getLines());
            assertTrue("stop should have lines", stop.getLines().size() > 0);

            assertNotNull("departure's line should be set", stop.getDepartures().get(0).getLine());

            for (Departure dep : stop.getDepartures()) {
                assertTrue("only departures of stop's lines are valid", stop.getLines().contains(dep.getLine()));
            }
        }
        
    }
    @Test
    public void stopsGettingWithNullList() {
        List<Stop> stops = stopService.getStops(null);
        
        assertNull("stop wasn't found", stops);
    }
}

package wad.hsltimetables.service;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.Line;
import wad.hsltimetables.domain.Stop;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TimetableServiceTest {
    @Autowired
    private TimetableService timetableService;
    
    public TimetableServiceTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void stopSearchFindsRightStops() {
        String query = "Patola";
        List<Stop> stops = timetableService.findStops(query);
        assertTrue("'"+ query + "' query should return at least one stop", stops.size()>0);
        
        for (Stop stop : stops) {
            assertTrue("every found stop should include the search term", 
                    stop.getName().toLowerCase().contains(query.toLowerCase()) ||
                    stop.getAddress().toLowerCase().contains(query.toLowerCase()) ||
                    stop.getShortCode().toLowerCase().contains(query.toLowerCase()) ||
                    stop.getCode().toString().contains(query.toLowerCase())
            );
            
            assertNotNull("stop code must be set", stop.getCode());
            assertNotNull("stop shortcode must be set", stop.getShortCode());
            assertNotNull("stop name must be set", stop.getName());
            assertNotNull("stop address must be set", stop.getAddress());
            assertNotNull("stop lineinfos must be set", stop.getLineInfos());
            assertTrue("stop should have lineinfos", stop.getLineInfos().size() > 0);
            assertNotNull("stop departures must be set", stop.getDepartures());
            assertTrue("stop should have departures", stop.getDepartures().size() > 0);
        }
    }
    
    @Test
    public void stopSearchReturnsEmptyListIfNothingFound() {
        String query = "Kemijärven taksitolppa";
        List<Stop> stops = timetableService.findStops(query);
        assertNull("'"+ query + "' query should return null", stops);
    }
    
    @Test
    public void stopCachingReducesSearchTime() {
        String query = "Patola";
        List<Stop> stops1 = timetableService.findStops(query);
        
        //rerun
        Long starttime = System.currentTimeMillis();
        List<Stop> stops2 = timetableService.findStops(query);
        Long stoptime = System.currentTimeMillis();
        
        assertTrue("second query took too long", stoptime-starttime<100);
    }
    
    @Test
    public void getStopReturnsRightStop() {
        Integer stopcode = 1220207;
        Stop stop = timetableService.getStop(stopcode);
        
        assertNotNull("stop should have been found", stop);
        assertEquals("got the wrong stop", stopcode, stop.getCode());
    }
    @Test
    public void getStopReturnsNullIfNothingFound() {
        Integer stopcode = 1324567;
        Stop stop = timetableService.getStop(stopcode);
        
        assertNull("stop shouldn't have been found", stop);
    }
    
    @Test
    public void stopCachingReducesGettingTime() {
        Integer stopcode = 1220207;
        Stop stop1 = timetableService.getStop(stopcode);
        
        //rerun
        Long starttime = System.currentTimeMillis();
        Stop stop2 = timetableService.getStop(stopcode);
        Long stoptime = System.currentTimeMillis();
        
        assertTrue("second getting took too long", stoptime-starttime<100);
    }
    
    @Test
    public void getLineReturnsRightStop() {
        String linecode = "4615  1";
        Line line = timetableService.getLine(linecode);
        
        assertNotNull("line should have been found", line);
        assertEquals("got the wrong line", linecode, line.getCode());
    }
    
    @Test
    public void getLineReturnsNullIfNothingFound() {
        String linecode = "1234561";
        Line line = timetableService.getLine(linecode);
        
        assertNull("line should't have been found", line);
    }
    
    @Test
    public void lineCachingReducesGettingTime() {
        String linecode = "4615  1";
        Line line1 = timetableService.getLine(linecode);
        
        //rerun
        Long starttime = System.currentTimeMillis();
        Line line2 = timetableService.getLine(linecode);
        Long stoptime = System.currentTimeMillis();
        
        assertTrue("second line getting took too long", stoptime-starttime<100);
    }
    
}

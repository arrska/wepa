package wad.hsltimetables.domain;

import wad.hsltimetables.domain.Departure;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepartureTest {
    private Departure departure;
    
    public DepartureTest() {
    }
    
    @Before
    public void setUp() {
        departure = new Departure();
    }

    @Test
    public void testSetTimeAddsLeadingZero() {
        departure.setTime("735");
        assertEquals("", "0735", departure.getTime());
    }
    @Test
    public void testGetDatetimeWorksWithProperInput() {
        departure.setTime("1402");
        departure.setDate("20130909");
        
        Date date = departure.getDatetime();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        assertNotNull("returned date is null", date);
        assertEquals("wrong year returned", 2013, cal.get(Calendar.YEAR));
        assertEquals("wrong month returned", (9-1), cal.get(Calendar.MONTH)); //month indexes from 0
        assertEquals("wrong day of month returned", 9, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals("wrong hour returned", 14, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals("wrong minute returned", 2, cal.get(Calendar.MINUTE));
    }
    
    @Test
    public void testGetDatetimeReturnsNullWithInvalidInput() {
        departure.setTime("lolo");
        departure.setDate("20131309");
        
        Date date = departure.getDatetime();
        assertNull("returned date is not null", date);
    }
    
    @Test
    public void testGetMinutesUntilWorksWithProperInput() {
        Date now = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(now);
        String hhmm = new SimpleDateFormat("HHmm").format(now);
        String yyyymmdd = new SimpleDateFormat("yyyyMMdd").format(now);
        
        departure.setTime(hhmm);
        departure.setDate(yyyymmdd);
        
        Integer mins = departure.getMinutesUntil();
        
        assertEquals("from now to now is 0 minutes", new Integer(0), mins);
    }
}

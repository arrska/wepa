package wad.hsltimetables.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.hsltimetables.domain.Line;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LineServiceTest {
    @Autowired
    LineService lineService;
    
    public LineServiceTest() {
    }
    
    @Before
    public void setUp() {
    }
    @Test
    public void getLineReturnsRightStop() {
        String linecode = "4615  1";
        Line line = lineService.getLine(linecode);
        
        assertNotNull("line should have been found", line);
        assertEquals("got the wrong line", linecode, line.getCode());
    }
    
    @Test
    public void getLineReturnsNullIfNothingFound() {
        String linecode = "1234561";
        Line line = lineService.getLine(linecode);
        
        assertNull("line should't have been found", line);
    }
    
}

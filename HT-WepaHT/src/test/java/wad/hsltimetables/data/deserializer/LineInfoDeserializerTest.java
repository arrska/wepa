package wad.hsltimetables.data.deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import wad.hsltimetables.domain.LineInfo;

public class LineInfoDeserializerTest {
    private ObjectMapper objectMapper;
    
    public LineInfoDeserializerTest() {
    }
    
    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule lineInfoDeserializationModule = new SimpleModule();
        lineInfoDeserializationModule.addDeserializer(List.class, new LineInfoDeserializer());
        objectMapper.registerModule(lineInfoDeserializationModule);
    }

    @Test
    public void testDeserializationWithOneLineInfo() {
        try {
            List<LineInfo> infolist;
            String testString = "[\"1054  1:Espoo\"]";
            
            infolist = objectMapper.readValue(testString, new TypeReference<List<LineInfo>>() {});
            
            assertNotNull("object should not be null", infolist);
            assertEquals("list should only have one item", 1, infolist.size());
            assertEquals("lineinfo's line code is wrong", "1054  1", infolist.get(0).getLineCode());
            assertEquals("lineinfo's destination is wrong", "Espoo", infolist.get(0).getDestination());
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
        }
    }
    
    @Test
    public void testDeserializationWithMultipleLineInfos() {
        try {
            List<LineInfo> infolist;
            String testString = "[\"1054  1:Espoo\",\"1064  1:Rautatieasema\",\"1054  2:Vantaa\"]";
            
            infolist = objectMapper.readValue(testString, new TypeReference<List<LineInfo>>() {});
            
            assertNotNull("object should not be null", infolist);
            assertEquals("list should have three items", 3, infolist.size());
            
            assertEquals("last lineinfo's line code is wrong", "1054  2", infolist.get(2).getLineCode());
            assertEquals("last lineinfo's destination is wrong", "Vantaa", infolist.get(2).getDestination());
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
        }
    }
    
    @Test
    public void testDeserializationWithEmptyString() {
        boolean et = false;
        List<LineInfo> infolist = null;
        
        try {
            String testString = "\"\"";
            infolist = objectMapper.readValue(testString, new TypeReference<List<LineInfo>>() {});
        } catch (Exception ex) {
            et = true;
        }
        
        assertTrue("some exception was thrown", !et);
        
        assertNotNull("list of lineinfos is null", infolist);
        assertTrue("list is not empty", infolist.isEmpty());
    }
    
    @Test
    public void testDeserializationWithNullValue() {
        try {
            List<LineInfo> infolist;
            String testString = "null";
            infolist = objectMapper.readValue(testString, new TypeReference<List<LineInfo>>() {});
            
            assertNull("object should be null", infolist);
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
        }
    }
    
}

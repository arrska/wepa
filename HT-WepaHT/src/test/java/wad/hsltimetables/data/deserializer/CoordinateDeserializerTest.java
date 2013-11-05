package wad.hsltimetables.data.deserializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import wad.hsltimetables.domain.Coordinates;

public class CoordinateDeserializerTest {
    private ObjectMapper objectMapper;
    
    public CoordinateDeserializerTest() {
    }
    
    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule coordinateDeserializationModule = new SimpleModule();
        coordinateDeserializationModule.addDeserializer(Coordinates.class, new CoordinateDeserializer());
        objectMapper.registerModule(coordinateDeserializationModule);
    }

    @Test
    public void testDeserializationWithProperJson() {
        try {
            String testString = "\"24.12345,60.54321\"";
            Coordinates c = objectMapper.readValue(testString, new TypeReference<Coordinates>() {});
            
            assertEquals("latitude deserialization failed", 60.54321, c.getLatitude(), 0.0);
            assertEquals("longtitude deserialization failed", 24.12345, c.getLongtitude(), 0.0);
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
        }
    }
    @Test
    public void testDeserializationWithEmptyString() {
        boolean nfe = false;
        
        try {
            String testString = "\"\"";
            Coordinates c = objectMapper.readValue(testString, new TypeReference<Coordinates>() {});
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
            
        } catch (NumberFormatException ex) {
            nfe = true;
        }
        
        if (!nfe)
            assertTrue("no exception was thrown", nfe);
    }
    
    @Test
    public void testDeserializationWithNullValue() {
        try {
            String testString = "null";
            Coordinates c = objectMapper.readValue(testString, new TypeReference<Coordinates>() {});
            
            assertNull("object should be null", c);
        } catch (IOException ex) {
            assertTrue("deserialization threw an IOException", false);
        }
    }
    
}

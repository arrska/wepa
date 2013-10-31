package wad.template.data.deseriaizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import wad.template.domain.Coordinates;

public class CoordinateDeserializer extends JsonDeserializer<Coordinates> {

    @Override
    public Coordinates deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Coordinates coords = new Coordinates();
        
        String[] wgsCoords = jp.getText().split(",");
        
        coords.setLongtitude(Double.parseDouble(wgsCoords[0]));
        coords.setLatitude(Double.parseDouble(wgsCoords[1]));
        
        return coords;
    }

}

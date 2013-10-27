package wad.template.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wad.template.domain.Line;

public class LineDeserializer extends JsonDeserializer<List<Line>> {
    public LineDeserializer() {
        super();
    }
    
    @Override
    public List<Line> deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        List<Line> lines = new ArrayList<Line>();
        
        String textData;
        textData = jp.nextTextValue();

        while (textData != null) {
            Line line = new Line();
            String[] data = textData.split(":");
            line.setLinecode(data[0]);
            line.setDestination(data[1]);
            lines.add(line);
            textData = jp.nextTextValue();
        }
        
        return lines;
    }
    
}

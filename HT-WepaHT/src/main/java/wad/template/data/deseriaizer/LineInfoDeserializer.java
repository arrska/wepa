package wad.template.data.deseriaizer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import wad.template.domain.LineInfo;

public class LineInfoDeserializer extends JsonDeserializer<List<LineInfo>> {
    public LineInfoDeserializer() {
        super();
    }
    
    @Override
    public List<LineInfo> deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        List<LineInfo> lines = new ArrayList<LineInfo>();
        
        String textData;
        textData = jp.nextTextValue();

        while (textData != null) {
            LineInfo line = new LineInfo();
            String[] data = textData.split(":");
            line.setLineCode(data[0]);
            line.setDestination(data[1]);
            lines.add(line);
            textData = jp.nextTextValue();
        }
        
        return lines;
    }
    
}

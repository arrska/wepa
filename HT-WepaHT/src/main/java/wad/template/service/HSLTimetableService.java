package wad.template.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wad.template.domain.Line;
import wad.template.domain.Stop;

@Service
public class HSLTimetableService implements TimetableService {

    private final String apiUrl = "http://api.reittiopas.fi/hsl/prod/?user=%1$s&pass=%2$s&request=%3$s&format=json";
    
    @Value("${hslapi.token}")
    private String token;
    
    @Value("${hslapi.pass}")
    private String tokenPass;
    
    private ObjectMapper HSLStopMapper;
    
    @PostConstruct
    private void init() {
        if (token == null) token = "";
        if (tokenPass == null) tokenPass = "";
        HSLStopMapper = new ObjectMapper();
    }
    
    private String stopSearchQuery (String query, Date time, Integer time_limit, Integer dep_limit) {
        String params =   
                  "&code=%1$s"
                + "&date=%2$tY%2$tm%2$td"
                + "&time=%2$tH%2$tM"
                + "&time_limit=%3$d"
                + "&dep_limit=%4$d";
        String queryString;
        
        queryString = 
                String.format(apiUrl, token, tokenPass, "stop") +
                String.format(params, query, time, time_limit, dep_limit);
        
        return queryString;
    }
    
    
    @Override
    public List<Stop> getStops(String query) {
        URL requestUrl;
        List<Stop> stops;
        
        try {
            requestUrl = new URL(stopSearchQuery(query, new Date(), 360, 10));
            stops = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Stop>>() {});
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting stops from HSL api! :(", e);
            return null;
        }
        return stops;
    }

    @Override
    public Stop getStop(Integer stopCode) {
        return getStops(stopCode.toString()).get(0);
    }

    @Override
    public Line getLine(Integer lineCode) {
        String queryString;
        
        queryString = 
                String.format(apiUrl, token, tokenPass, "line") +
                String.format("&query=%1$d", lineCode);
        
        URL requestUrl;
        Line line;
        
        try {
            requestUrl = new URL(queryString);
            List<Line> lines = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Line>>() {});
            line = lines.get(0);
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting line from HSL api! :(", e);
            return null;
        }
        return line;
    }
    
}

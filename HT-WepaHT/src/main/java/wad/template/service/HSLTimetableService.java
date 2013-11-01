package wad.template.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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
    
    public static String TransportType(Integer transportTypeId) {
        if (transportTypeId == 1) {
            return "bus";
        } else if (transportTypeId == 2) {
            return "tram";
        } else if (transportTypeId == 3) {
            return "bus";
        } else if (transportTypeId == 4) {
            return "bus";
        } else if (transportTypeId == 5) {
            return "bus";
        } else if (transportTypeId == 6) {
            return "metro";
        } else if (transportTypeId == 7) {
            return "ferry";
        } else if (transportTypeId == 8) {
            return "u-bus";
        } else if (transportTypeId == 12) {
            return "train";
        } else if (transportTypeId == 21) {
            return "serviceline";
        } else if (transportTypeId == 22) {
            return "bus";
        } else if (transportTypeId == 23) {
            return "serviceline";
        } else if (transportTypeId == 24) {
            return "serviceline";
        } else if (transportTypeId == 25) {
            return "bus";
        } else if (transportTypeId == 36) {
            return "bus";
        } else if (transportTypeId == 39) {
            return "bus";
        }
        
        return "unknown";
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
    @Cacheable(value ="stops")
    public Stop getStop(Integer stopCode) {
        Logger.getLogger(HSLTimetableService.class.getName()).log(Level.INFO, ("getting single stop indo of stop " + stopCode + " from HSLapi"));
        return findStops(stopCode.toString()).get(0);
    }
    
    @Cacheable(value ="stops")
    @Override
    public List<Stop> findStops(String query) {
        URL requestUrl;
        List<Stop> stops;
        
        try {
            requestUrl = new URL(stopSearchQuery(query, new Date(), 360, 10));
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.INFO, ("searching for stops with query " + query + " from HSLapi"));
            stops = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Stop>>() {});
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting stops from HSL api! :(", e);
            return null;
        }
        
        return stops;
    }
    
    @Override
    @Cacheable(value ="lines")
    public Line getLine(String lineCode) {
        String queryString;
        String encodedQuery;
        
        try {
            encodedQuery = URLEncoder.encode(lineCode, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "epic no UTF-8 exception when urlencoding", ex);
            return null;
        }
        
        queryString = 
                String.format(apiUrl, token, tokenPass, "lines") +
                String.format("&query=%1$s", encodedQuery);
        
        URL requestUrl;
        List<Line> lines;
        
        try {
            requestUrl = new URL(queryString);
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.INFO, ("getting line info of line " + lineCode + " from HSLapi"));
            lines = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Line>>() {});
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting line data from HSL api! :(", e);
            return null;
        }
        
        return lines.get(0);
    }
}

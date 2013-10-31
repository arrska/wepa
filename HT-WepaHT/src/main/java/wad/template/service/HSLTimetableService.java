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
import org.springframework.stereotype.Service;
import wad.template.domain.Departure;
import wad.template.domain.Line;
import wad.template.domain.LineInfo;
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
    public List<Stop> getStops(String query, boolean withLines) {
        URL requestUrl;
        List<Stop> stops;
        
        try {
            requestUrl = new URL(stopSearchQuery(query, new Date(), 360, 10));
            stops = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Stop>>() {});
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting stops from HSL api! :(", e);
            return null;
        }
        
        if (withLines) {
            for (Stop stop : stops) {
                stop.setLines(getLines(stop.getLineInfos()));
                
                for (Departure departure : stop.getDepartures()) {
                    departure.setLine(this.getLine(departure.getLineCode()));
                }
            }
        }
        
        return stops;
    }

    @Override
    public Stop getStop(Integer stopCode, boolean withLines) {
        return getStops(stopCode.toString(), withLines).get(0);
    }

    @Override
    public List<Line> getLines(String query) {
        String queryString;
        String encodedQuery;
        
        try {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "epic no UTF-8 exception when urlencoding", ex);
            return null;
        }
        
        queryString = 
                String.format(apiUrl, token, tokenPass, "lines") +
                String.format("&query=%1$s", encodedQuery);
        System.out.println(queryString);
        URL requestUrl;
        List<Line> lines;
        
        try {
            requestUrl = new URL(queryString);
            lines = HSLStopMapper.readValue(requestUrl, new TypeReference<List<Line>>() {});
        } catch (IOException e) {
            Logger.getLogger(HSLTimetableService.class.getName()).log(Level.SEVERE, "Error when getting line data from HSL api! :(", e);
            return null;
        }
        return lines;
    }
    
    @Override
    public Line getLine(String lineCode) {
        return getLines(lineCode).get(0);
    }
    
    @Override
    public Line getLine(LineInfo lineInfo) {
        return getLine(lineInfo.getLineCode());
    }

    @Override
    public List<Line> getLines(List<LineInfo> lineinfos) {
        List<Line> lines;
        String lineQuery = "";
        
        for (LineInfo lineinfo : lineinfos) {
            lineQuery += lineinfo.getLineCode() + "|";
        }
        
        lineQuery = lineQuery.substring(0, lineQuery.length()-1);
        
        lines = getLines(lineQuery);
        
        return lines;
    }
}

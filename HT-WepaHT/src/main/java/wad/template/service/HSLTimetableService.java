package wad.template.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wad.template.domain.Line;
import wad.template.domain.LineList;
import wad.template.domain.Stop;
import wad.template.domain.StopList;

@Service
public class HSLTimetableService implements TimetableService {
    private final String apiUrl = "http://api.reittiopas.fi/hsl/prod/?user=%1$s&pass=%2$s&request=%3$s&format=json";
    
    @Value("${hslapi.token}")
    private String token;
    
    @Value("${hslapi.pass}")
    private String tokenPass;
    
    //private ObjectMapper HSLStopMapper;
    private RestTemplate restTemplate;
    
    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mjhmc = new MappingJackson2HttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.TEXT_PLAIN);
        mjhmc.setSupportedMediaTypes(mediaTypes);
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        
        messageConverters.add(mjhmc);
        restTemplate.setMessageConverters(messageConverters);
        
        if (token == null) token = "";
        if (tokenPass == null) tokenPass = "";
        //HSLStopMapper = new ObjectMapper();
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
    
    private String stopSearchUrl (String query, Date time, Integer time_limit, Integer dep_limit) {
            String paramFormat =
                    "&code=%1$s"
                    + "&date=%2$tY%2$tm%2$td"
                    + "&time=%2$tH%2$tM"
                    + "&time_limit=%3$d"
                    + "&dep_limit=%4$d";
            String queryURL;
            
            queryURL =
                    String.format(apiUrl, token, tokenPass, "stop") +
                    String.format(paramFormat, query, time, time_limit, dep_limit);
            
            return queryURL;
    }
    
    private String lineGetUrl (String query) {
            String paramFormat = "&query=%1$s";
            String queryURL;
            
            queryURL =
                    String.format(apiUrl, token, tokenPass, "lines") +
                    String.format(paramFormat, query);
            
            return queryURL;
    }
    
    @Override
    @Cacheable(value ="stops")
    public Stop getStop(Integer stopCode) {
        return findStops(stopCode.toString()).get(0);
    }
    
    @Cacheable(value ="stops")
    @Override
    public List<Stop> findStops(String query) {
        System.out.println("getting stops with query \"" + query + "\" from HSLapi");
        String searchUrl = stopSearchUrl(query, new Date(), 360, 10);
        List<Stop> stops = restTemplate.getForObject(searchUrl, StopList.class);
        return stops;
    }
    
    @Override
    @Cacheable(value ="lines")
    public Line getLine(String lineCode) {
        System.out.println("getting line info of line " + lineCode + " from HSLapi");
        String url = lineGetUrl(lineCode);
        
        List<Line> lines = restTemplate.getForObject(url, LineList.class);
        if (lines == null)
            return null;
        return lines.get(0);
    }
}

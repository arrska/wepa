package wad.hsltimetables.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.hsltimetables.data.jsonview.JsonViews;
import wad.hsltimetables.domain.Line;
import wad.hsltimetables.domain.Stop;
import wad.hsltimetables.domain.User;
import wad.hsltimetables.service.ApiAuthenticationService;
import wad.hsltimetables.service.LineService;
import wad.hsltimetables.service.StopService;

@Controller
@RequestMapping(value = "api/")
public class JsonAPIController {
    @Autowired
    private StopService stopService;
    
    @Autowired
    private LineService lineService;
    
    @Autowired
    private ApiAuthenticationService apiAuthenticationService;
    
    private ObjectMapper objectMapper;
    
    @PostConstruct
    private void init() {
        objectMapper = new ObjectMapper();
    }
    
    @RequestMapping(value = "stops", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String myStops(@RequestParam(value = "apikey") String apiKey) throws Exception {
        ObjectWriter objectWriter = objectMapper.writerWithView(JsonViews.DefaultStopView.class);
        
        User user = apiAuthenticationService.authenticate(apiKey);
        
        List<Integer> stopcodes = user.getFavouriteStops();
        List<Stop> stops = stopService.getStops(stopcodes);
        
        return objectWriter.writeValueAsString(stops);
    }
    
    @RequestMapping(value = "stop/{stopCode}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String stopInfo(@RequestParam(value = "apikey") String apiKey, @PathVariable(value = "stopCode") Integer stopCode) throws Exception {
        apiAuthenticationService.authenticate(apiKey);
        ObjectWriter objectWriter = objectMapper.writerWithView(JsonViews.DefaultStopView.DetailedStopView.class);
        
        Stop stop = stopService.getStop(stopCode);
        
        return objectWriter.writeValueAsString(stop);
    }
    
    @RequestMapping(value = "line/{lineCode}", method=RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String lineInfo(@RequestParam(value = "apikey") String apiKey, @PathVariable(value = "lineCode") String lineCode) throws Exception {
        apiAuthenticationService.authenticate(apiKey);
        ObjectWriter objectWriter = objectMapper.writerWithView(JsonViews.DefaultLineView.DetailedLineView.class);
        
        Line line = lineService.getLine(lineCode);
        
        return objectWriter.writeValueAsString(line);
    }
}

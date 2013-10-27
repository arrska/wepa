package wad.template.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.template.domain.Stop;
import wad.template.service.TimetableService;

@Controller
public class DefaultController {
    
    @Autowired
    private TimetableService timetableService;
    
    @Value("${gmapi.apikey}")
    private String googleMapsAPIKey;
    
    @RequestMapping("*")
    public String handleDefault() {
        return "menu";
    }
//    
//    @ResponseBody
//    @RequestMapping("test")
//    public String testPage() {
//        List<Stop> stops = timetableService.getStops("Patola");
//        String s = "";
//        System.out.println(stops);
//        if (stops == null) {
//            System.out.println("yes its null :(");
//        }
//        
//        for (Stop stop : stops) {
//            s += stop.getAddress() + "<br>";
//        }
//        return s;
//    }
    
    @RequestMapping("stop/search")
    public String testStopinfo(@RequestParam(value = "q", required = false) String query, Model model) {
        if (query != null) {
            List<Stop> stops = timetableService.getStops(query);
            model.addAttribute("stops", stops);
            model.addAttribute("query", query);
        }
        
        return "stopsearch";
    }
    
    @RequestMapping("stop/{stopcode}")
    public String showStop(@PathVariable(value = "stopcode") String stopCode, Model model) {
        Stop stop = timetableService.getStop(stopCode);
        model.addAttribute("stop", stop);
        model.addAttribute("gmapikey", googleMapsAPIKey);
        //model.addAttribute("deps", stop.getDepartures().size());
        
        return "stopinfo";
    }
}

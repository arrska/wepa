package wad.template.controller;

import wad.template.data.QueryValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.template.domain.Coordinates;
import wad.template.domain.SiteUser;
import wad.template.domain.Stop;
import wad.template.service.FavouriteService;
import wad.template.service.TimetableService;
import wad.template.service.UserControlService;

@Controller
public class StopController {
    @Autowired
    private TimetableService timetableService;
    
    @Autowired
    private UserControlService userControlService;
    
    @Autowired
    private FavouriteService<Stop> favService;
    
    @Autowired
    private QueryValidator queryValidator;
    
    @Value("${gmapi.apikey}")
    private String googleMapsAPIKey;
    
    @RequestMapping(value="stop/search", method=RequestMethod.GET)
    public String stopSearch(@RequestParam(value = "q", required = false) String query, Model model) {
        if (query != null) {
            model.addAttribute("query", query);
            if (!queryValidator.validate(query)) {
                model.addAttribute("error", true);

                return "stopsearch";
            }

            model.addAttribute("error", false);
            List<Stop> stops = timetableService.getStops(query);
            model.addAttribute("stops", stops);
        }
        return "stop/stopsearch";
    }
    
    @RequestMapping(value="stop/{stopcode}", method=RequestMethod.GET)
    public String stopInfo(@PathVariable(value = "stopcode") Integer stopCode, Model model) {
        Stop stop = timetableService.getStop(stopCode);
        
        boolean favourite = favService.isFavourite(userControlService.getAuthenticatedUser(), timetableService.getStop(stopCode));
        
        model.addAttribute("stop", stop);
        model.addAttribute("favourite", favourite);
        model.addAttribute("gmapikey", googleMapsAPIKey);
        
        return "stop/stopinfo";
    }
    
    @RequestMapping(value="stop", method=RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String myStops(Model model) {
        model.addAttribute("stops", userControlService.getAuthenticatedUser().getFavouriteStops());
        
        return "stop/stoplist";
    }
    
    @RequestMapping(value="stop/{stopcode}/coordinates", method=RequestMethod.GET)
    @ResponseBody
    public Coordinates stopCoordinates(@PathVariable(value = "stopcode") Integer stopCode) {
        return timetableService.getStop(stopCode).getCoordinates();
    }
    
    @RequestMapping(value = "stop/{stopcode}/favourite", method=RequestMethod.POST)
    public String addFavourite(@PathVariable(value = "stopcode") Integer stopcode) {
        Stop stop = timetableService.getStop(stopcode);
        SiteUser user = userControlService.getAuthenticatedUser();
        favService.favourite(user, stop);
        return "redirect:/app/stop";
    }
    
    @RequestMapping(value = "stop/{stopcode}/unfavourite", method=RequestMethod.POST)
    public String removeFavourite(@PathVariable(value = "stopcode") Integer stopcode) {
        Stop stop = timetableService.getStop(stopcode);
        SiteUser user = userControlService.getAuthenticatedUser();
        favService.unfavourite(user, stop);
        return "redirect:/app/stop";
    }
}

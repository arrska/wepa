package wad.template.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.template.data.StopSearchFormObject;
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
    
    @Value("${gmapi.apikey}")
    private String googleMapsAPIKey;
    
    @RequestMapping(value="stop/search", method=RequestMethod.GET)
    public String stopSearch(
            @Valid @ModelAttribute(value = "searchForm") StopSearchFormObject searchForm,
            BindingResult bindingResult,
            Model model) {
        
        if (bindingResult.hasErrors()) {
            return "stop/stopsearch";
        }
        
        List<Stop> stops = timetableService.getStops(searchForm.getQuery(), true);
        model.addAttribute("stops", stops);
        model.addAttribute("query", searchForm.getQuery());
        return "stop/stopsearch";
    }
    
    @RequestMapping(value="stop/{stopcode}", method=RequestMethod.GET)
    public String stopInfo(@PathVariable(value = "stopcode") Integer stopCode, Model model) {
        Stop stop = timetableService.getStop(stopCode, true);
        
        boolean favourite = favService.isFavourite(userControlService.getAuthenticatedUser(), stop);
        
        model.addAttribute("stop", stop);
        model.addAttribute("favourite", favourite);
        model.addAttribute("gmapikey", googleMapsAPIKey);
        
        return "stop/stopinfo";
    }
    
    @RequestMapping(value="stop", method=RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String myStops(Model model) {
        try {
            List<Stop> stops = userControlService.getAuthenticatedUser().getFavouriteStops();
            model.addAttribute("stops", stops);
        } catch (Exception e) {}
        
        return "stop/stoplist";
    }
    
    @RequestMapping(value="stop/{stopcode}/coordinates", method=RequestMethod.GET)
    @ResponseBody
    public Coordinates stopCoordinates(@PathVariable(value = "stopcode") Integer stopCode) {
        return timetableService.getStop(stopCode, false).getCoordinates();
    }
    
    @RequestMapping(value = "stop/{stopcode}/favourite", method=RequestMethod.POST)
    public String addFavourite(@PathVariable(value = "stopcode") Integer stopcode) {
        Stop stop = timetableService.getStop(stopcode, false);
        SiteUser user = userControlService.getAuthenticatedUser();
        favService.favourite(user, stop);
        return "redirect:/app/stop";
    }
    
    @RequestMapping(value = "stop/{stopcode}/unfavourite", method=RequestMethod.POST)
    public String removeFavourite(@PathVariable(value = "stopcode") Integer stopcode) {
        Stop stop = timetableService.getStop(stopcode, false);
        SiteUser user = userControlService.getAuthenticatedUser();
        favService.unfavourite(user, stop);
        return "redirect:/app/stop";
    }
}

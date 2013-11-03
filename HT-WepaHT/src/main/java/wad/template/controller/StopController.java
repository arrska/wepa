package wad.template.controller;

import java.util.ArrayList;
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
import wad.template.data.formobject.StopSearchFormObject;
import wad.template.domain.User;
import wad.template.domain.Stop;
import wad.template.service.FavouriteService;
import wad.template.service.StopService;
import wad.template.service.UserControlService;

@Controller
public class StopController {
    @Autowired
    private StopService stopService;
    
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
        
        if (bindingResult.hasErrors() || searchForm.getQuery() == null) {
            return "stop/stopsearch";
        }
        
        List<Stop> stops = stopService.searchStop(searchForm.getQuery());
        model.addAttribute("stops", stops);
        model.addAttribute("query", searchForm.getQuery());
        return "stop/stopsearch";
    }
    
    @RequestMapping(value="stop/{stopcode}", method=RequestMethod.GET)
    public String stopInfo(@PathVariable(value = "stopcode") Integer stopCode, Model model) {
        Stop stop = stopService.getStop(stopCode);
        
        boolean favourite = favService.isFavourite(userControlService.getAuthenticatedUser(), stop);
        
        model.addAttribute("stop", stop);
        model.addAttribute("favourite", favourite);
        model.addAttribute("gmapikey", googleMapsAPIKey);
        
        return "stop/stopinfo";
    }
    
    @RequestMapping(value="stop", method=RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String myStops(Model model) {
            User user = userControlService.getAuthenticatedUser();
            List<Integer> stopcodes = user.getFavouriteStops();

            List<Stop> stops = stopService.getStops(stopcodes);
            
            model.addAttribute("stops", stops);
        
        return "stop/stoplist";
    }
    
    @RequestMapping(value = "stop/{stopCode}/favourite", method=RequestMethod.POST)
    public String addFavourite(@PathVariable(value = "stopCode") Integer stopCode) {
        Stop stop = stopService.getStop(stopCode);
        User user = userControlService.getAuthenticatedUser();
        favService.favourite(user, stop);
        return "redirect:/app/stop";
    }
    
    @RequestMapping(value = "stop/{stopCode}/unfavourite", method=RequestMethod.POST)
    public String removeFavourite(@PathVariable(value = "stopCode") Integer stopCode) {
        Stop stop = stopService.getStop(stopCode);
        User user = userControlService.getAuthenticatedUser();
        favService.unfavourite(user, stop);
        return "redirect:/app/stop";
    }
}

package wad.template.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.template.domain.SiteUser;
import wad.template.domain.Stop;
import wad.template.service.ApiAuthenticationService;
import wad.template.service.FavouriteService;
import wad.template.service.TimetableService;

@Controller
@RequestMapping(value = "api/")
public class JsonAPIController {
    @Autowired
    TimetableService timetableService;
    
    @Autowired
    FavouriteService<Stop> favouriteService;
    
    @Autowired
    ApiAuthenticationService apiAuthenticationService;
    
    @RequestMapping(value = "stops", method=RequestMethod.GET)
    @ResponseBody
    public List<Stop> myStops(@RequestParam(value = "apikey") String apiKey) throws Exception {
        SiteUser user = apiAuthenticationService.authenticate(apiKey);
        return user.getFavouriteStops();
    }
    
    @RequestMapping(value = "stop/{stopCode}", method=RequestMethod.GET)
    @ResponseBody
    public Stop stopInfo(@RequestParam(value = "apikey") String apiKey, @PathVariable(value = "stopCode") String stopCode) throws Exception {
        apiAuthenticationService.authenticate(apiKey);
        return timetableService.getStop(Integer.parseInt(stopCode), true);
    }
}

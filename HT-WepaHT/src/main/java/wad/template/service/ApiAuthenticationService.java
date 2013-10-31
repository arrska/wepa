package wad.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.template.domain.SiteUser;

@Service
public class ApiAuthenticationService {
    @Autowired
    UserControlService userControlService;
    
    public SiteUser authenticate(String apikey) throws Exception {
        SiteUser user = userControlService.findUserByApiKey(apikey);
        if (user == null)
            throw new Exception("apikey authentication failed");
        return user;
    }
}

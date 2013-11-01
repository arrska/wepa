package wad.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.template.domain.SiteUser;

@Service
public class ApiAuthenticationService {
    @Autowired
    UserControlService userControlService;
    
    @Transactional(readOnly = true)
    public SiteUser authenticate(String apikey) throws Exception {
        SiteUser user = userControlService.findUserByApiKey(apikey);
        if (user == null)
            throw new Exception("apikey authentication failed");
        return user;
    }
}

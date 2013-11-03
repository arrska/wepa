package wad.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.template.domain.User;

@Service
public class ApiAuthenticationService {
    @Autowired
    UserControlService userControlService;
    
    @Transactional(readOnly = true)
    public User authenticate(String apikey) throws Exception {
        User user = userControlService.findUserByApiKey(apikey);
        if (user == null)
            throw new Exception("apikey authentication failed");
        return user;
    }
}

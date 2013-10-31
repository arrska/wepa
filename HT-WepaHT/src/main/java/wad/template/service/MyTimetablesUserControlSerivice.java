package wad.template.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.template.domain.SiteUser;
import wad.template.repository.UserRepository;

@Service
public class MyTimetablesUserControlSerivice implements UserControlService {
    @Autowired
    private UserRepository userRepo;
    
    @Override
    @Transactional(readOnly = false)
    public SiteUser newUser(SiteUser user) throws Exception {
        if (userRepo.findOne(user.getName()) != null) {
            throw new Exception("Username taken!");
        }
        
        user.setApikey(UUID.randomUUID().toString());
        
        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteUser findUser(String username) {
        return userRepo.findOne(username);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(String username) {
        SecurityContextHolder.clearContext();
        userRepo.delete(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteUser> getUsers() {
        return (List<SiteUser>) userRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SiteUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return this.findUser(auth.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    @Override
    public void changePassword(SiteUser authenticatedUser, String password1) {
        authenticatedUser.setPassword(password1);
        userRepo.save(authenticatedUser);
    }

    @Override
    public SiteUser findUserByApiKey(String apikey) {
        return userRepo.findOneByApikey(apikey);
    }
}

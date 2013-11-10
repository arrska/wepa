package wad.hsltimetables.service;

import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.hsltimetables.domain.User;
import wad.hsltimetables.repository.UserRepository;

@Service
public class MyTimetablesUserControlService implements UserControlService {
    @Autowired
    private UserRepository userRepo;
    
    
    @PostConstruct
    private void init() {
//        User u = new User();
//        u.setName("aaro");
//        u.setApikey("apikk");
//        u.setPassword("passus");
//        
//        userRepo.save(u);
    }
    
    @Override
    @Transactional(readOnly = false)
    public User newUser(User user) throws Exception {
        if (userRepo.findOne(user.getName()) != null) {
            throw new Exception("Username taken!");
        }
        
        String apikey;
        do {
            apikey = UUID.randomUUID().toString();
        } while (userRepo.findOneByApikey(apikey) != null);
        user.setApikey(apikey);
        
        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUser(String username) {
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
    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        return this.findUser(auth.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAuthenticated() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null) return false;
        return a.isAuthenticated();
    }

    @Override
    @Transactional(readOnly = false)
    public void changePassword(User authenticatedUser, String password) {
        authenticatedUser.setPassword(password);
        userRepo.save(authenticatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserByApiKey(String apikey) {
        return userRepo.findOneByApikey(apikey);
    }
}

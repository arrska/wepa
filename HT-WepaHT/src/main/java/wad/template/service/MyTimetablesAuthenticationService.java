package wad.template.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.template.domain.SiteUser;
import wad.template.repository.UserRepository;

@Service
public class MyTimetablesAuthenticationService implements AuthenticationProvider {
    @Autowired
    UserRepository userRepo;
    
    @Override
    @Transactional(readOnly = true)
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String username = a.getName();
        String password = a.getCredentials().toString();
        
        SiteUser user = userRepo.findOne(username);
        if (user != null && user.getPassword().equals(password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            
            grantedAuths.add(new SimpleGrantedAuthority("user"));
            
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }
        
        throw new AuthenticationCredentialsNotFoundException("Incorrect information entered.");
    }

    @Override
    public boolean supports(Class authType) {
        return authType.equals(UsernamePasswordAuthenticationToken.class);
    }
}

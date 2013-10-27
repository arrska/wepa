package wad.template.service;

import java.util.List;
import wad.template.domain.SiteUser;

public interface UserControlService {
    public SiteUser newUser(SiteUser user) throws Exception;
    public SiteUser getUser(String username);
    public SiteUser getAuthenticatedUser();
    public List<SiteUser> getUsers();
    public void deleteUser(String username);
    public boolean isAuthenticated();
}

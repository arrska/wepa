package wad.hsltimetables.service;

import java.util.List;
import wad.hsltimetables.domain.User;

public interface UserControlService {
    public User newUser(User user) throws Exception;
    public User findUser(String username);
    public User findUserByApiKey(String apikey);
    public User getAuthenticatedUser();
    public List<User> getUsers();
    public void deleteUser(String username);
    public boolean isAuthenticated();

    public void changePassword(User authenticatedUser, String password1);
}

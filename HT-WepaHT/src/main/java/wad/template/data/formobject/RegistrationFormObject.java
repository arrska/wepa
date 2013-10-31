package wad.template.data.formobject;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import wad.template.domain.SiteUser;

public class RegistrationFormObject {
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "Only characters a-z and digits 0-9")
    @Length(min = 4, max = 20)
    private String username;
    
    @Length(min = 6, max = 50)
    private String password1;
    
    @Length(min = 6, max = 50)
    private String password2;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public SiteUser makeUser() {
        SiteUser user = new SiteUser();
        user.setName(username);
        user.setPassword(password1);
        
        return user;
    }
}

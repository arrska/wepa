package wad.hsltimetables.data.formobject;

import org.hibernate.validator.constraints.Length;

public class PasswdFormObject {
    @Length(min = 6, max = 50)
    private String password1;
    
    @Length(min = 6, max = 50)
    private String password2;
    
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
}

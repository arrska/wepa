package wad.template.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import wad.template.data.RegistrationFormObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.template.domain.SiteUser;
import wad.template.service.UserControlService;

@Controller
public class UserController {
    @Autowired
    private UserControlService userControlService;
    
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registrationHandler(
            @Valid @ModelAttribute(value = "regForm") RegistrationFormObject regForm, 
            BindingResult bindingResult) {
        
        if (!regForm.getPassword1().equals(regForm.getPassword2())) {
            bindingResult.addError(new FieldError("regform", "password1", "passwords didnt match"));
        }
        
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        SiteUser user = regForm.makeUser();
        
        try {
            userControlService.newUser(user);
        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, "cant add user!", ex);
            bindingResult.addError(new FieldError("regform", "username", ex.getMessage()));
            return "register";
        }
        
        return "redirect:login";
    }
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistration (@ModelAttribute(value = "regForm") RegistrationFormObject regForm, Model model) {
        model.addAttribute("users", userControlService.getUsers());
        return "register";
    }
    
    @RequestMapping(value="login", method = RequestMethod.GET)
    public String loginView(Model model, @RequestParam(value = "fail", required = false) String fail) {
        model.addAttribute("error", (fail != null));
        return "login";
    }
    
    @RequestMapping(value="user/{username}", method = RequestMethod.GET)
    public String userInfo(Model model, @PathVariable(value = "username") String username) {
        model.addAttribute("user", userControlService.getUser(username));
        return "user";
    }
    
    @RequestMapping(value="user/{username}", method = RequestMethod.POST)
    public String userModify(
            Model model, 
            @PathVariable(value = "username") String username, 
            @RequestParam(value = "delete", required = false) String delete) {
        
        if (delete != null) {
            userControlService.deleteUser(username);
        }
        
        return "redirect:/app/";
    }
}

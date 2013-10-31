package wad.template.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import wad.template.data.formobject.PasswdFormObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.template.data.formobject.RegistrationFormObject;
import wad.template.domain.SiteUser;
import wad.template.service.UserControlService;

@Controller
public class UserController {
    @Autowired
    private UserControlService userControlService;
    
    @PreAuthorize("!isAuthenticated()")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registrationHandler(
            @Valid @ModelAttribute(value = "regForm") RegistrationFormObject regForm, 
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (!regForm.getPassword1().equals(regForm.getPassword2())) {
            bindingResult.addError(new FieldError("regForm", "password1", "passwords didnt match"));
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
        redirectAttributes.addFlashAttribute("message", "Registration complete, please sign in");
        redirectAttributes.addFlashAttribute("username", user.getName());
        return "redirect:login";
    }
    
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistration (@ModelAttribute(value = "regForm") RegistrationFormObject regForm, Model model) {
        return "register";
    }
    
    @RequestMapping(value="login", method = RequestMethod.GET)
    public String loginView(Model model, @RequestParam(value = "fail", required = false) String fail) {
        model.addAttribute("error", (fail != null));
        return "login";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="user/{username}", method = RequestMethod.GET)
    public String userInfo(
            @ModelAttribute(value = "passwdForm") PasswdFormObject passwdForm, 
            Model model, 
            @PathVariable(value = "username") String username) {
        model.addAttribute("apikey", userControlService.getAuthenticatedUser().getApikey());
        return "user";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="user/{username}", method = RequestMethod.PUT)
    public String userPasswd(
            @Valid @ModelAttribute(value = "passwdForm") PasswdFormObject passwdForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("apikey", userControlService.getAuthenticatedUser().getApikey());
                
        if (!passwdForm.getPassword1().equals(passwdForm.getPassword2())) {
            bindingResult.addError(new FieldError("passwdForm", "password1", "passwords didnt match"));
        }
        
        if (bindingResult.hasErrors()) {
            return "user";
        }
        userControlService.changePassword(userControlService.getAuthenticatedUser(), passwdForm.getPassword1());
        
        redirectAttributes.addFlashAttribute("message", "Password changed");
        return "redirect:/app/";
    }
    
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="user/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable(value = "username") String username, RedirectAttributes redirectAttributes) {
        SiteUser authenticatedUser = userControlService.getAuthenticatedUser();
        
        if (username.equals(authenticatedUser.getName())) {
            userControlService.deleteUser(username);
            redirectAttributes.addFlashAttribute("message", "user deleted forever");
        }
        
        return "redirect:/app/";
    }
}

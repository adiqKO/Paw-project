package pl.test.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.test.model.User;
import pl.test.model.UserSpecific;
import pl.test.service.UserService;

import java.security.Principal;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult bindResult) {
        if(bindResult.hasErrors())
            return "registerForm";
        else {
            boolean check = userService.addWithDefaultRole(user);
            if(check) {
                return "redirect:/register?success=true";
            }else {
                return "redirect:/register?success=false";
            }
        }
    }

    @GetMapping("/users")
    public String showUsers(Model model){
            model.addAttribute("users", userService.findAll());
            return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id){
       userService.deleteUser(id);
       return "redirect:/users?success";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id, @RequestParam("sourceText") String sourceText){
        userService.updateUser(id,sourceText);
        return "redirect:/users?success";
    }

    @GetMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id){
        return "update";
    }

    @GetMapping("/users/show/{id}")
    public String showUser(@PathVariable("id") long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userSpecific", user.getUserSpecific() == null ? new UserSpecific() : user.getUserSpecific());
        return "userInfo";
    }

    @GetMapping("/account")
    public String showAccount(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userSpecific", user.getUserSpecific() == null ? new UserSpecific() : user.getUserSpecific());
        return "account";
    }

    @PostMapping("/account/update")
    public String showAccount(@ModelAttribute @Valid UserSpecific userSpecific,
                              BindingResult bindResult,Principal principal){
        User user = userService.findByEmail(principal.getName());
        if(bindResult.hasErrors())
            return "redirect:/account?error";
        else {

            userService.updateUserSpecific(user.getId(),userSpecific);
            return "redirect:/account?success";
        }

    }

}
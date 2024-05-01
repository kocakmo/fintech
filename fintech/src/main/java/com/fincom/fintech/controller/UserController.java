package com.fincom.fintech.controller;

import com.fincom.fintech.exception.UserNotAcceptableException;
import com.fincom.fintech.model.User;
import com.fincom.fintech.service.EmailServiceImpl;
import com.fincom.fintech.service.UserService;
import com.fincom.fintech.service.UserServiceImpl;
import org.hibernate.annotations.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private final EmailServiceImpl emailService;

    public UserController(UserServiceImpl userServiceImpl, EmailServiceImpl emailService) {
        this.userService = userServiceImpl;
        this.emailService = emailService;
    }

    @GetMapping("/addnew")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "newuser";
    }


    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user)  {
        if(!user.isValidNationalIdNumber())
            throw new UserNotAcceptableException("Entered National Id Number is Not Valid");
        if(!user.isUserAdult())
            throw new UserNotAcceptableException("Entered Birth Date is not acceptable");

        userService.addUser(user);
        String message = "Welcome to our application! We're thrilled to have you join us on this journey.";
        StringBuilder sb = new StringBuilder("Dear " + user.getFirstName() + " "+ user.getLastName()+","+"\n\n"+message);
        emailService.sendSimpleEmail(user.getEmail(),"Welcome Aboard",sb.toString());
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "redirect:/";
    }

}

package com.bank.controller;

import com.bank.dao.CheckingDao;
import com.bank.dao.SavingsDao;
import com.bank.entities.Checking;
import com.bank.entities.Savings;
import com.bank.service.UserService;
import com.bank.dto.UserDto;
import com.bank.entities.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {
    private UserService userService;

    private CheckingDao checkingDao;

    private SavingsDao savingsDao;


    public AuthController(UserService userService, CheckingDao checkingDao, SavingsDao savingsDao) {
        this.userService = userService;
        this.checkingDao = checkingDao;
        this.savingsDao = savingsDao;
    }

   /* @GetMapping("index")
    public String home(){
        return "index";
    }*/

    @GetMapping("/")
    public String firstPage() {
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);

        //Create new checking account for user
        int newBalance = (int)(Math.random() * 1000 + 100);
        Checking checkingAccount = new Checking();
        checkingAccount.setCheckingBalance(newBalance);
        checkingDao.addChecking(checkingAccount);

        int checkingID = checkingAccount.getCheckingAccountId();
        checkingAccount.setUserId(checkingID);

        //Create new savings account for user
        int newBalance2 = (int)(Math.random() * 1000 + 100);
        Savings savingsAccount = new Savings();
        savingsAccount.setSavingsBalance(newBalance2);
        savingsDao.addSavings(savingsAccount);

        int savingID = savingsAccount.getSavingsAccountId();
        savingsAccount.setUserId(savingID);

        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}

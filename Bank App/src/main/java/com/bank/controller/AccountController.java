package com.bank.controller;

import com.bank.dao.CheckingDao;
import com.bank.dao.SavingsDao;
import com.bank.dao.transactionHistoryDao;
import com.bank.dto.UserDto;
import com.bank.entities.Checking;
import com.bank.entities.Savings;
import com.bank.entities.User;
import com.bank.entities.transactionHistory;
import com.bank.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private CheckingDao checkingDao;
    @Autowired
    private SavingsDao savingsDao;
    @Autowired
    private transactionHistoryDao transactionDao;


    @GetMapping("index")
    public String displayCheckingAccount(Model model) {

        //Retrieves the details of the user that is currently logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println(auth.getName());
        //System.out.println(userService.findByEmail(auth.getName()));
        //System.out.println(convertEntityToDto(userService.findByEmail(auth.getName())));

        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        System.out.println(USER.getUserId());
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));
        //System.out.println(checkingAccount);
        model.addAttribute("checkingAccount", checkingAccount);

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));
        //System.out.println(savingsAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        List<transactionHistory> transactions = transactionDao.getLatestTransactions(USERID);
        model.addAttribute("transactions", transactions);

        return "index";
    }

    @GetMapping("transferChecking")
    public String transferCheckingAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));
        model.addAttribute("checkingAccount", checkingAccount);

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));
        model.addAttribute("savingsAccount", savingsAccount);

        return "transferChecking";
    }

    @PostMapping("transferChecking")
    public String performTransferCheckingFunds(HttpServletRequest request, Model model) {
        String funds = request.getParameter("transferFunds");
        double transferFunds = Double.parseDouble(funds);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));

        double currentBalanceCHECKING = checkingAccount.getCheckingBalance();
        double newBalanceCHECKING = currentBalanceCHECKING + transferFunds;

        double currentBalanceSAVINGS = savingsAccount.getSavingsBalance();
        double newBalanceSavings = currentBalanceSAVINGS - transferFunds;

        checkingAccount.setCheckingBalance(newBalanceCHECKING);
        checkingDao.updateCheckingBalance(checkingAccount);
        model.addAttribute("checkingAccount", checkingAccount);

        savingsAccount.setSavingsBalance(newBalanceSavings);
        savingsDao.updateSavingsBalance(savingsAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        transactionHistory newTransaction = new transactionHistory();
        newTransaction.setNewCheckingBalance(newBalanceCHECKING);
        newTransaction.setNewSavingsBalance(newBalanceSavings);
        newTransaction.setTransferValue(transferFunds);
        newTransaction.setUserId(USERID);

        transactionDao.addTransaction(newTransaction);

        return "redirect:/index";
    }

    @GetMapping("transferSavings")
    public String transferSavingsAccount(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));
        model.addAttribute("checkingAccount", checkingAccount);

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));
        model.addAttribute("savingsAccount", savingsAccount);

        return "transferSavings";
    }

    @PostMapping("transferSavings")
    public String performTransferSavingsFunds(HttpServletRequest request, Model model) {
        String funds = request.getParameter("transferFunds");
        double transferFunds = Double.parseDouble(funds);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));

        double currentBalanceCHECKING = checkingAccount.getCheckingBalance();
        double newBalanceCHECKING = currentBalanceCHECKING - transferFunds;

        double currentBalanceSAVINGS = savingsAccount.getSavingsBalance();
        double newBalanceSavings = currentBalanceSAVINGS + transferFunds;

        checkingAccount.setCheckingBalance(newBalanceCHECKING);
        checkingDao.updateCheckingBalance(checkingAccount);
        model.addAttribute("checkingAccount", checkingAccount);

        savingsAccount.setSavingsBalance(newBalanceSavings);
        savingsDao.updateSavingsBalance(savingsAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        transactionHistory newTransaction = new transactionHistory();
        newTransaction.setNewCheckingBalance(newBalanceCHECKING);
        newTransaction.setNewSavingsBalance(newBalanceSavings);
        newTransaction.setTransferValue(transferFunds);
        newTransaction.setUserId(USERID);

        transactionDao.addTransaction(newTransaction);

        return "redirect:/index";
    }

    @GetMapping("transactionHistory")
    public String displayTransactionHistory(Model model) {

        //Retrieves the details of the user that is currently logged in
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println(auth.getName());
        //System.out.println(userService.findByEmail(auth.getName()));
        //System.out.println(convertEntityToDto(userService.findByEmail(auth.getName())));

        User USER = userService.findByEmail(auth.getName());

        String userName = USER.getName();
        model.addAttribute("username", userName);
        System.out.println(USER.getUserId());
        Long USERID = USER.getUserId();

        //Finds checking account matching the currently signed-in user
        Checking checkingAccount = checkingDao.getCheckingAccountById(Math.toIntExact(USERID));
        //System.out.println(checkingAccount);
        model.addAttribute("checkingAccount", checkingAccount);

        //Finds savings account matching the currently signed-in user
        Savings savingsAccount = savingsDao.getSavingsAccountById(Math.toIntExact(USERID));
        //System.out.println(savingsAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        List<transactionHistory> transactions = transactionDao.getTransactionsById(USERID);
        model.addAttribute("transactions", transactions);

        return "transactionHistory";
    }


}

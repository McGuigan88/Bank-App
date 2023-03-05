package com.bank.dao;

import com.bank.dto.UserDto;
import com.bank.entities.Checking;
import com.bank.entities.User;
import com.bank.repository.UserRepository;
import com.bank.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CheckingDaoDBTest {
    @Autowired
    CheckingDao checkingDao;

    private UserServiceImpl userService;

    public CheckingDaoDBTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddAndGetChecking() {
        Checking checkingAccount = new Checking();
        checkingAccount.setCheckingBalance(200);
        checkingAccount.setUserId(1);

        checkingAccount = checkingDao.addCheckingAccount(checkingAccount);

        Checking fromDao = checkingDao.getCheckingAccountById(checkingAccount.getCheckingAccountId());
        assertEquals(checkingAccount, fromDao);
    }

    @Test
    public void testUpdateChecking() {
        Checking checkingAccount = new Checking();
        checkingAccount.setCheckingBalance(150);
        checkingAccount.setUserId(1);
        checkingAccount = checkingDao.addCheckingAccount(checkingAccount);

        Checking fromDao = checkingDao.getCheckingAccountById(checkingAccount.getCheckingAccountId());
        assertEquals(checkingAccount, fromDao);

        checkingAccount.setCheckingBalance(1600);

        assertNotEquals(checkingAccount, fromDao);

        checkingDao.updateCheckingBalance(checkingAccount);
        fromDao = checkingDao.getCheckingAccountById(checkingAccount.getCheckingAccountId());
        assertEquals(checkingAccount, fromDao);
    }

}

package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.entities.Savings;
import com.bank.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class SavingsDaoDBTest {
    @Autowired
    SavingsDao savingsDao;

    private UserRepository userRepository;

    public SavingsDaoDBTest() {
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
    public void testAddAndGetSavings() {
        Savings savingsAccount = new Savings();
        savingsAccount.setSavingsBalance(200);

        savingsAccount = savingsDao.addSavingsAccount(savingsAccount);

        Savings fromDao = savingsDao.getSavingsAccountById(savingsAccount.getSavingsAccountId());
        assertEquals(savingsAccount, fromDao);
    }

    @Test
    public void testUpdateSavings() {

        Savings savingsAccount = new Savings();
        savingsAccount.setSavingsBalance(150);
        savingsAccount = savingsDao.addSavingsAccount(savingsAccount);
        savingsAccount.setUserId(1);

        Savings fromDao = savingsDao.getSavingsAccountById(savingsAccount.getSavingsAccountId());
        assertEquals(savingsAccount, fromDao);

        savingsAccount.setSavingsBalance(1600);

        assertNotEquals(savingsAccount, fromDao);

        savingsDao.updateSavingsBalance(savingsAccount);
        fromDao = savingsDao.getSavingsAccountById(savingsAccount.getSavingsAccountId());
        assertEquals(savingsAccount, fromDao);
    }
}

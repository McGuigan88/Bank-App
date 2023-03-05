package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.entities.Savings;

public interface SavingsDao {
    Savings getSavingsAccountById(int id);

    Savings addSavings(Savings savings);

    Savings addSavingsAccount(Savings savings);

    void updateSavingsBalance(Savings savings);
}

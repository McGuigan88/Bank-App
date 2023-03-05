package com.bank.dao;

import com.bank.entities.Checking;

import java.util.List;

public interface CheckingDao {
    Checking getCheckingAccountById(int id);
    Checking addChecking(Checking checking);

    Checking addCheckingAccount(Checking checking);
    List<Checking> getAllCheckingAccounts();
    void updateCheckingBalance(Checking checking);
}

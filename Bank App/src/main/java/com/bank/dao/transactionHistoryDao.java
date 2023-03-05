package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.entities.transactionHistory;

import java.util.List;

public interface transactionHistoryDao {
    List<transactionHistory> getTransactionsById(long id);

    transactionHistory getTransaction(long id);

    List<transactionHistory> getLatestTransactions(long id);
    transactionHistory addTransaction(transactionHistory transaction);
    List<transactionHistory> getAllTransactions();

}

package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.entities.transactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class transactionHistoryDaoDB implements transactionHistoryDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<transactionHistory> getTransactionsById(long id) {
        try {
            final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactionHistory WHERE userId = ?";
            List<transactionHistory> transactions= jdbc.query(SELECT_TRANSACTION_BY_ID , new TransactionMapper(), id);
            return transactions;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public transactionHistory getTransaction(long id) {
        try {
            final String SELECT_TRANSACTION_BY_ID = "SELECT * FROM transactionHistory WHERE userId = ?";
            transactionHistory transaction = jdbc.queryForObject(SELECT_TRANSACTION_BY_ID , new TransactionMapper(), id);
            return transaction;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<transactionHistory> getLatestTransactions(long id) {
        try {
            final String SELECT_LATEST_TRANSACTION = "SELECT * FROM transactionHistory WHERE userId = ? ORDER BY transactionId DESC LIMIT 5";
            List<transactionHistory> transactions= jdbc.query(SELECT_LATEST_TRANSACTION , new TransactionMapper(), id);
            return transactions;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public transactionHistory addTransaction(transactionHistory transaction) {
        final String INSERT_TRANSACTION = "INSERT INTO transactionHistory(newCheckingBalance, newSavingsBalance, transferValue, userId)"
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_TRANSACTION,
                transaction.getNewCheckingBalance(),
                transaction.getNewSavingsBalance(),
                transaction.getTransferValue(),
                transaction.getUserId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        transaction.setTransactionId(newId);

        return transaction;
    }

    @Override
    public List<transactionHistory> getAllTransactions() {
        final String SELECT_ALL_TRANSACTIONS = "SELECT * FROM transactionHistory";
        List<transactionHistory> transactions = jdbc.query(SELECT_ALL_TRANSACTIONS, new TransactionMapper());
        return transactions;
    }

    public static final class TransactionMapper implements RowMapper<transactionHistory> {

        @Override
        public transactionHistory mapRow(ResultSet rs, int index) throws SQLException {
            transactionHistory transaction = new transactionHistory();
            transaction.setTransactionId(rs.getInt("transactionId"));
            transaction.setNewCheckingBalance(rs.getDouble("newCheckingBalance"));
            transaction.setNewSavingsBalance(rs.getDouble("newSavingsBalance"));
            transaction.setTransferValue(rs.getDouble("transferValue"));
            transaction.setUserId(rs.getInt("userId"));
            return transaction;
        }
    }
}

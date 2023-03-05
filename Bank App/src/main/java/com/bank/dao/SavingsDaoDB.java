package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.entities.Savings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SavingsDaoDB implements SavingsDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Savings getSavingsAccountById(int id) {
        try {
            final String SELECT_SAVINGS_ACCOUNT_BY_ID = "SELECT * FROM savingsAccount WHERE savingsAccountId = ?";
            Savings savings= jdbc.queryForObject(SELECT_SAVINGS_ACCOUNT_BY_ID , new SavingsMapper(), id);
            return savings;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Savings addSavings(Savings savings) {
        final String INSERT_SAVINGS_ACCOUNT = "INSERT INTO savingsAccount(savingsBalance)"
                + "VALUES(?)";
        jdbc.update(INSERT_SAVINGS_ACCOUNT,
                savings.getSavingsBalance());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        savings.setSavingsAccountId(newId);
        savings.setUserId(newId);

        //Update savings account record with appropriate userId
        insertUserID(savings);
        return savings;
    }

    @Override
    public Savings addSavingsAccount(Savings savings) {
        final String INSERT_SAVINGS_ACCOUNT = "INSERT INTO savingsAccount(savingsBalance)"
                + "VALUES(?)";
        jdbc.update(INSERT_SAVINGS_ACCOUNT,
                savings.getSavingsBalance());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        savings.setSavingsAccountId(newId);
        savings.setUserId(newId);

        return savings;
    }

    private void insertUserID(Savings savings) {
        final String UPDATE_USER_ID = "UPDATE savingsAccount SET savingsBalance = ?, "
                + "userId = ? WHERE savingsAccountId = ?";
        jdbc.update(UPDATE_USER_ID,
                savings.getSavingsBalance(),
                savings.getUserId(),
                savings.getSavingsAccountId());
    }

    @Override
    public void updateSavingsBalance(Savings savings) {
        final String UPDATE_SAVINGS = "UPDATE savingsAccount SET savingsBalance = ? WHERE savingsAccountId = ?";
        jdbc.update(UPDATE_SAVINGS,
                savings.getSavingsBalance(),
                savings.getSavingsAccountId());
    }

    public static final class SavingsMapper implements RowMapper<Savings> {

        @Override
        public Savings mapRow(ResultSet rs, int index) throws SQLException {
            Savings savings = new Savings();
            savings.setSavingsAccountId(rs.getInt("savingsAccountId"));
            savings.setSavingsBalance(rs.getDouble("savingsBalance"));
            savings.setUserId(rs.getInt("userId"));
            return savings;
        }
    }
}

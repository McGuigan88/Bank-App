package com.bank.dao;

import com.bank.entities.Checking;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CheckingDaoDB implements CheckingDao{

    @Autowired
    JdbcTemplate jdbc;

    private UserRepository userRepository;

    @Override
    public Checking getCheckingAccountById(int id) {
        try {
            final String SELECT_CHECKING_ACCOUNT_BY_ID = "SELECT * FROM checkingAccount WHERE checkingAccountId = ?";
            Checking checking= jdbc.queryForObject(SELECT_CHECKING_ACCOUNT_BY_ID , new CheckingMapper(), id);
            return checking;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Checking addChecking(Checking checking) {
        final String INSERT_CHECKING_ACCOUNT = "INSERT INTO checkingAccount(checkingBalance)"
                + "VALUES(?)";
        jdbc.update(INSERT_CHECKING_ACCOUNT,
                checking.getCheckingBalance());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        checking.setCheckingAccountId(newId);
        checking.setUserId(newId);

        //Update checking account record with appropriate userId
        insertUserID(checking);
        return checking;
    }

    @Override
    public Checking addCheckingAccount(Checking checking) {
        final String INSERT_CHECKING_ACCOUNT = "INSERT INTO checkingAccount(checkingBalance)"
                + "VALUES(?)";
        jdbc.update(INSERT_CHECKING_ACCOUNT,
                checking.getCheckingBalance());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        checking.setCheckingAccountId(newId);
        checking.setUserId(newId);

        //Update checking account record with appropriate userId
        return checking;
    }

    private void insertUserID(Checking checking) {
        final String UPDATE_USER_ID = "UPDATE checkingAccount SET checkingBalance = ?, "
                + "userId = ? WHERE checkingAccountId = ?";
        jdbc.update(UPDATE_USER_ID,
                    checking.getCheckingBalance(),
                    checking.getUserId(),
                    checking.getCheckingAccountId());
    }
    @Override
    public void updateCheckingBalance(Checking checking) {
        final String UPDATE_CHECKING = "UPDATE checkingAccount SET checkingBalance = ? WHERE checkingAccountId = ?";
        jdbc.update(UPDATE_CHECKING,
                checking.getCheckingBalance(),
                checking.getCheckingAccountId());
    }

    @Override
    public List<Checking> getAllCheckingAccounts() {
        final String SELECT_ALL_CHECKING_ACCOUNTS = "SELECT * FROM checkingAccount";
        List<Checking> checkingAccounts = jdbc.query(SELECT_ALL_CHECKING_ACCOUNTS, new CheckingMapper());
        return checkingAccounts;
    }
    public static final class CheckingMapper implements RowMapper<Checking> {

        @Override
        public Checking mapRow(ResultSet rs, int index) throws SQLException {
            Checking checking = new Checking();
            checking.setCheckingAccountId(rs.getInt("checkingAccountId"));
            checking.setCheckingBalance(rs.getDouble("checkingBalance"));
            checking.setUserId(rs.getInt("userId"));
            return checking;
        }
    }
}

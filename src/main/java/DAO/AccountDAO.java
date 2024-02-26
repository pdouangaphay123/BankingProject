package DAO;

import Model.Account;
import Utility.DTO.AmountDepositWithdraw;
import Utility.DTO.AmountTransferTransaction;

import java.sql.*;

public class AccountDAO {

    // CRUD methods should be here, easier for springboot convert. create, read, update and delete db stuff shou be here!!
    // need to make a transaction query so it will track the transactions: withdraw, deposit!!
    // retrieve values of current object/user in the session

    public Account checkBalance(int userId, Account account) {
        try (Connection conn = Utility.Connection.getConnection()) {

            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id =?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                //account.setAccountId(rs.getInt(1));
                account.setBalance(rs.getDouble(2));
                //account.setUserId(rs.getInt(3));
            }

            return account;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account updateDeposit(int userId, double amount, Account account) {

        try (Connection conn = Utility.Connection.getConnection()) {

            PreparedStatement ps;

            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            else {
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id"));

            }

            account.setDeposit(amount);

            ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getUserId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public Account updateWithdraw(int userId, double amount, Account account) {

        try (Connection conn = Utility.Connection.getConnection()) {

            PreparedStatement ps;

            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;
            else {
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id"));

            }

            if (account.getBalance() < amount) return null;
            else {
                account.setWithdraw(amount);

                ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
                ps.setDouble(1, account.getBalance());
                ps.setInt(2, account.getUserId());
                ps.executeUpdate();

                return account;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account createAccount(int userId) { // used to initialize Account class when customer registered

        try (Connection conn = Utility.Connection.getConnection()) {
            Account account = new Account();
            PreparedStatement ps;
            ps = conn.prepareStatement("INSERT INTO accounts(balance, user_id)" +
                    "VALUES (?, ?);");
            ps.setDouble(1, 0.00);
            ps.setInt(2, userId);
            ps.executeUpdate();

            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id=?");
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                account.setAccountId(resultSet.getInt("account_id"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setUserId(resultSet.getInt("user_id"));
            }

            return account;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public Account transferBetweenAccounts(int userId, AmountTransferTransaction transfer, Account sendingAccount) {
//        try (Connection conn = Utility.Connection.getConnection()) {
//
//            Account receivingAccount = new Account();
//            PreparedStatement ps;
//
//            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id =?");
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                sendingAccount.setAccountId(rs.getInt(1));
//                sendingAccount.setBalance(rs.getDouble(2));
//                sendingAccount.setUserId(rs.getInt(3));
//
//            }
//
//            ps = conn.prepareStatement("SELECT * FROM accounts WHERE user_id =?");
//            ps.setInt(1, transfer.getTransferId());
//            rs = ps.executeQuery();
//            while (rs.next()) {
//                receivingAccount.setAccountId(rs.getInt(1));
//                receivingAccount.setBalance(rs.getDouble(2));
//                receivingAccount.setUserId(rs.getInt(3));
//
//            }
//
//            if (sendingAccount.getBalance() < transfer.getAmount()) return null;
//            else {
//                sendingAccount.setWithdraw(transfer.getAmount());
//
//                ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
//                ps.setDouble(1, sendingAccount.getBalance());
//                ps.setInt(2, userId);
//                ps.executeUpdate();
//
//                receivingAccount.setDeposit(transfer.getAmount());
//
//                ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
//                ps.setDouble(1, receivingAccount.getBalance());
//                ps.setInt(2, transfer.getTransferId());
//                ps.executeUpdate();
//
//                return sendingAccount;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
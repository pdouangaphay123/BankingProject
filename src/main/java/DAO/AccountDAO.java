package DAO;

import Model.User;

import java.sql.*;
import java.util.Scanner;

public class AccountDAO {

    // CRUD methods should be here, easier for springboot convert. create, read, update and delete db stuff shou be here!!
    public static User sessionUserObject(User validUser) {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users INNER JOIN accounts ON users.user_id = accounts.user_id " +
                    "WHERE users.user_id =?;");
            ps.setInt(1, validUser.getUserId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                validUser.setUserId(rs.getInt(1));
                validUser.setCustomerName(rs.getString(2));
                validUser.setEmail(rs.getString(3));
                validUser.setAccountId(rs.getInt(5));
                validUser.setBalance(rs.getDouble(6));
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return validUser;
    }

    public static void checkBalance(User currentUser) {
        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps;
            ResultSet rs;

            ps = conn.prepareStatement("SELECT balance FROM accounts WHERE user_id =?");
            ps.setInt(1, currentUser.getUserId());
            rs = ps.executeQuery();
            while(rs.next())
                currentUser.setBalance(rs.getDouble(1));
            System.out.println("balance: $" + String.format("%.2f", currentUser.getBalance()));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // update deposit to db
    public static void updateDeposit(User currentUser, Scanner input) {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps;

            System.out.println("Enter the amount to deposit: ");
            double amountDeposit = input.nextDouble();
            currentUser.setDeposit(amountDeposit);
            double newBalance = currentUser.getBalance();
            ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
            ps.setDouble(1, newBalance);
            ps.setInt(2, currentUser.getUserId());
            ps.executeUpdate();
            checkBalance(currentUser);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // update withdraw to db
    public static void updateWithdraw(User currentUser, Scanner input) {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps;

            System.out.println("Enter amount to withdraw: $");
            double amountWithdraw = input.nextDouble();
            currentUser.setWithdraw(amountWithdraw);
            double newBalance = currentUser.getBalance();
            ps = conn.prepareStatement("UPDATE accounts SET balance =? WHERE user_id =?;");
            ps.setDouble(1, newBalance);
            ps.setInt(2, currentUser.getUserId());
            ps.executeUpdate();
            checkBalance(currentUser);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // change password
    public static void changePassword(User validUser) {

        try(Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps;

            System.out.println("Enter new password: ");
            Scanner inputPassword = new Scanner(System.in);
            String newPassword = inputPassword.nextLine();
            ps = conn.prepareStatement("UPDATE users SET password =? WHERE user_id =?;");
            ps.setString(1, newPassword);
            ps.setInt(2, validUser.getUserId());
            ps.executeUpdate();
            System.out.println("Successfully changed the password");
    } catch (SQLException e){
        e.printStackTrace();
        }
    }
}

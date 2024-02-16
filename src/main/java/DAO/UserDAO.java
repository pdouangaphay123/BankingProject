package DAO;

import Model.User;
import java.sql.*;

public class UserDAO {

    // CRUD methods should be here easier to covert to springboot. create, read, update and delete db stuff shou be here!!
    // function to check for all user info
    public static boolean isEmailExist(String validEmail, User currentUser) {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps;
            ResultSet rs;

            ps = conn.prepareStatement("SELECT email FROM users;");
            rs = ps.executeQuery();
            while (rs.next()) {
                String rsEmail = rs.getString(1);
                if (validEmail.equals(rsEmail)) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // create user and account in db
    public static void createUserAccount (String validEmail, String validPassword, String customerName, User newUser) {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement createUser = conn.prepareStatement("INSERT INTO users(customer_name, email, password)" +
                    "VALUES ('" + newUser.getCustomerName() + "', '" + newUser.getEmail() + "', '" + newUser.getPassword() + "');");
            createUser.executeUpdate();
            PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM users WHERE email =?;");
            ps.setString(1, validEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int currentId = rs.getInt(1);
                newUser.setUserId(currentId);
            }
            System.out.println(newUser.getUserId());
            PreparedStatement createAccount = conn.prepareStatement("INSERT INTO accounts(balance, user_id) " +
                    "VALUES (" + 0.00 + ", " + newUser.getUserId() + ");");
            createAccount.executeUpdate();
            System.out.println("\nSuccessfully made the account!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validEmailPassword (String validEmail, String validPassword) throws SQLException {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email =? AND password =?;");
            ps.setString(1, validEmail);
            ps.setString(2, validPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
                //currentUser.setEmail(rs.getString(3));
                //currentUser.setUserId(rs.getInt(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static User setIdEmail (User currentUser, String validEmail, String validPassword) throws SQLException {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email =? AND password =?;");
            ps.setString(1, validEmail);
            ps.setString(2, validPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                currentUser.setEmail(rs.getString(3));
                currentUser.setUserId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return currentUser;
    }
}

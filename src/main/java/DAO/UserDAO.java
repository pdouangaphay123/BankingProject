package DAO;

import Model.User;
import java.sql.*;

public class UserDAO {

    // CRUD methods should be here easier to covert to springboot. create, read, update and delete db stuff shou be here!!
    // function to check for all user info


    private User convertSqlIntoUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setUserId(resultSet.getInt(1));
        user.setCustomerName(resultSet.getString(2));
        user.setEmail(resultSet.getString(3));
        user.setPassword(resultSet.getString(4));

        return user;
    }
    public User getUserByEmail(String email) throws SQLException {
        try (Connection conn = Utility.Connection.getConnection()) {
            String sql = "SELECT * FROM users WHERE email =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);


            try (ResultSet resultSet = preparedStatement.executeQuery()) { // Execute the query
                if (resultSet.next()) {
                    return convertSqlIntoUser(resultSet);
                } else {
                    return null; // No user found with the given email
                }
            }
        }
    }

    public boolean getUserById(int userId) throws SQLException {
        try (Connection conn = Utility.Connection.getConnection()) {
            String sql = "SELECT * FROM users WHERE users_id =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) { // Execute the query
                if (resultSet.next()) {
                    return true;
                } else {
                    return false; // No user found
                }
            }
        }
    }
    // create user and account in db
    public User createUser(User newUser) {

        try (Connection conn = Utility.Connection.getConnection()){

            String sql = "INSERT INTO users(customer_name, email, password) values (?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, newUser.getCustomerName());
            preparedStatement.setString(2, newUser.getEmail());
            preparedStatement.setString(3, newUser.getPassword());

            int checkinsert = preparedStatement.executeUpdate();

            if(checkinsert == 0){
                throw new RuntimeException("User was not added to database");
            }
            else{
                String sql2 = "SELECT * FROM users";
                preparedStatement = conn.prepareStatement(sql2);
                ResultSet resultSet = preparedStatement.executeQuery();

                while(resultSet.next()){
                    newUser.setUserId(resultSet.getInt("user_id"));
                }

                return newUser;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean validLogin(User currentUser) throws SQLException {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email =? AND password =?;");
            ps.setString(1, currentUser.getEmail());
            ps.setString(2, currentUser.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkEmailDB (String email) throws SQLException {

        try (Connection conn = Utility.Connection.getConnection()){

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email =?;");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return false;
            else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

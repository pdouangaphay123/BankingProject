package Service;

import Controller.UserController;
import DAO.AccountDAO;
import DAO.UserDAO;
import Model.Account;
import Model.User;
import Utility.DTO.LoginCreds;
import io.javalin.http.Context;

import java.sql.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices {

    UserDAO userDAO;
    AccountDAO accountDAO = new AccountDAO();
    public UserServices(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(User user, Context context) throws SQLException {
        if (user.getEmail() != null && user.getPassword() != null){
            if(!validateEmail(user.getEmail(), context)) { // check email requirements
                return null;
            }
            if(userDAO.checkEmailDB(user.getEmail())){
                context.status(400);
                context.json("Email already exists");
                return null;
            }
            if(!validatePassword(user.getPassword(), context)) { // password requirements
                return null;
            }
            if (userDAO.getUserByEmail(user.getEmail()) == null) {

               user = userDAO.createUser(user); // register user
               Account account = accountDAO.createAccount(user.getUserId()); // initialize account for user


               return user;
            } else {
                return null;
            }
        } return null;
    }

    public User loginUser(LoginCreds loginCreds) throws SQLException {
        User loginUser = userDAO.getUserByEmail(loginCreds.getEmail()); // check in db if email exist
        if (Objects.equals(loginUser.getPassword(), loginCreds.getPassword())){ // check if password matches
            return loginUser;
        }
        return null;
    }

                                /* VALIDATION EMAIL AND PW REQUIREMENTS*/
    // email validation regex pattern accepts from 6 to 16 email prefix chars A-z,a-z,0-9,_,. only
    // after the @ symbol accepts from range 2 to 8 chars a-z only, and after . accepts only a-z chars range 2 to 6.
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Za-z0-9._]{6,16}@[a-z]{2,8}.[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmailId(String emailId) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailId);
        return matcher.find();
    }
    public static boolean validateEmail(String email, Context context) {

        if (email == null) { // validates for if null email
            context.status(400);
            context.json("Email cannot be empty!");
            return false;
        }

        if (!validateEmailId(email)) {  // validates if email address is invalid
            context.status(400);
            context.json("Invalid email address!");
            return false;
        }

        return true;
    }

    public static boolean validatePassword(String password, Context context){

        if (password == null){ // validates for if null password
            context.status(400);
            context.json("Password cannot be empty!");
            return false;
        }

        if (password.length() < 5) { // password must be at least 5 characters
            context.status(400);
            context.json("Password must be at least 5 characters!");
            return false;
        } else if (!Pattern.matches("[^ ]*", password)) { // password cannot contain space
            context.status(400);
            context.json("Password cannot contain space!");
            return false;
        } else if (!password.matches(".*\\d.*")) { // password must have at least 1 digit
            context.status(400);
            context.json("Password must have at least 1 digit!");
            return false;
        }

        return true;
    }
}
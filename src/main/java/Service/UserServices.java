package Service;

import Model.User;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static DAO.UserDAO.*;
import static Service.AccountServices.logIn;

public class UserServices {

    // function that enrolls customers by using conditional if statement and puts key and value in Hashmap
    public static void isEnrolling(User currentUser, Scanner input) throws SQLException {

        boolean validating = false;
        String validEmail = "";

        while (!validating) {

            boolean status = false;

            while (!status) {

                System.out.println("\nEmail requirements:\n" +
                        "  - Email prefix range 6-16 characters accepted chararacters are A-z,a-z,0-9,_,.\n" +
                        "  - after the @ symbol accepts character range 2-8 a-z charaters only\n" +
                        "  - after . accepts only a-z characters range 2-6 and email cannot be empty");

                System.out.println("Enter email: ");
                validEmail = input.nextLine();

                validating = isEmailExist(validEmail, currentUser);

                if (validating) {
                    validating = (validateEmail(validEmail, currentUser));
                    if (validating) {
                        status = true;
                    } else {
                        status = false;
                    }
                } else {
                    System.out.println("Email already exists, try a different email!");
                }
            }
        }
        input.reset(); // clear from locale scope

        validating = false;
        do if (!validating) {

            System.out.println("\nPassword requirements:\n" +
                    "  - Password must be at least 5 characters\n" +
                    "  - must contain at least 1 digit and cannot contain space\n" +
                    "  - and cannot be empty");
            System.out.println("Enter password: ");
            String validPassword = input.nextLine();

            validating = (validatePassword(validPassword));

            if (!validating) System.out.println("Try again!\n"); //condition to check if email already exists.
            else {
                Scanner inputName = new Scanner(System.in);
                System.out.println("Enter customer name: ");
                String customerName = inputName.nextLine();

                currentUser = new User(validEmail, validPassword, customerName); // create the user from the @overload method in User class 3 args
                createUserAccount(validEmail, validPassword, customerName, currentUser);
            }
        } while (!validating);
        input.reset();
    }

    // function used to login
    public static void isLogIn(User currentUser, Scanner input) throws SQLException {

        boolean status = false;

        while (!status) {

            System.out.println("Enter email: ");
            String validEmail = input.nextLine();
            input.reset();
            System.out.println("Enter password: ");
            String validPassword = input.nextLine();
            input.reset();

            try {
                status = validEmailPassword(validEmail, validPassword);
                if (status) {
                    currentUser = setIdEmail(currentUser, validEmail, validPassword);
                } else {
                    System.out.println("Invalid email or password!");
                }
            } catch (SQLException e) {
                System.out.println(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        logIn(currentUser); // valid email and password
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
    public static boolean validateEmail(String email, User currentUser) {

        if (email == null) { // validates for if null email
            System.out.println("Email cannot be empty!");
            return false;
        }

        if (!validateEmailId(email)) {  // validates if email address is invalid
            System.out.println("Invalid email address!");
            return false;
        }

        return true;
    }

    public static boolean validatePassword(String password){

        if (password == null){ // validates for if null password
            System.out.println("Password cannot be empty!");
            return false;
        }

        if (password.length() < 5) { // password must be at least 5 characters
            System.out.println("Password must be at least 5 characters!");
            return false;
        } else if (!Pattern.matches("[^ ]*", password)) { // password cannot contain space
            System.out.println("Password cannot contain space!");
            return false;
        } else if (!password.matches(".*\\d.*")) { // password must have at least 1 digit
            System.out.println("Password must have at least 1 digit!");
            return false;
        }

        return true;
    }
}
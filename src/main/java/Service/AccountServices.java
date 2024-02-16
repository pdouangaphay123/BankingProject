package Service;

import Model.User;

import java.sql.*;
import java.util.Scanner;

import static DAO.AccountDAO.*;

public class AccountServices {

    // function for a verified account to access bank account services
    public static void logIn(User validUser) {

        validUser = sessionUserObject(validUser); // pass db info to current user by create object, then assigning it from db
        boolean status = false;

        do {
            try {
                System.out.println("Welcome " + validUser.getCustomerName() + ".");
                System.out.println("Enter: 1) check balance, 2) deposit, 3) withdraw, " +
                        "4) Account information, 5) Change password 6) logout");

                Scanner input = new Scanner(System.in);
                input.reset();
                String choice = input.nextLine();

                int num = Integer.parseInt(choice);

                switch (num) {
                    case 1: // check balance
                        checkBalance(validUser);
                        break;
                    case 2: // deposit
                        updateDeposit(validUser, input);
                        break;
                    case 3: // withdraw
                        updateWithdraw(validUser, input);
                        break;
                    case 4: // account info
                       System.out.println("Account id: " + validUser.getAccountId() +
                               "\nEmail: " + validUser.getEmail() +
                               "\nAccount holder name: " + validUser.getCustomerName());
                       break;
                    case 5: // change password
                        changePassword(validUser);
                        break;
                    case 6: // logout
                        status = true;
                        System.out.println("logged out successfully");
                        break;
                    default:
                       System.out.println("try again");
                       break;
                    }
            } catch (NumberFormatException n) {
                System.out.println("Please enter a valid number, Try again");
            }
        } while (!status);
    }
}

/*This is a simple banking cli app.
- user input cli
- functions
  * register feature
     - enroll multiple customer objects
     - customer objects stored in hashmap
  * login feature
     - verifies validity of customer objects from stored hashmap
     - transaction features
        * check current balance
        * deposit option with updated balance
        * withdraw option with updated balance and no over-drafting warning
        * logout option with message*/

package com.java.banking;

import Model.User;

import static Service.UserServices.*;

import java.sql.*;
import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {

        User currentUser = new User();
        boolean status = false;
        Scanner input = new Scanner(System.in);

        while (!status) { // enroll customers or quit to continue to log in

            try {

                System.out.println("Welcome to FirstNations bank\n");
                System.out.println("Our options are:\n" +
                        "1 - To enroll\n" +
                        "2 - To login\n" +
                        "3 - Quit program");

                String choice = input.nextLine();
                int num = Integer.parseInt(choice);

                switch (num){

                    case 1:
                        isEnrolling(currentUser, input); // enroll
                        break;
                    case 2:
                        isLogIn(currentUser, input); // login
                        break;
                    case 3:
                        status = true; // quit program
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid option! Try again.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
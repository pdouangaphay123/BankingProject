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

import Controller.AccountController;
import Controller.UserController;
import DAO.AccountDAO;
import DAO.UserDAO;
import Model.User;
import Service.AccountServices;
import Service.UserServices;
import io.javalin.Javalin;


import java.sql.*;
import java.util.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) {

       Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                    it.exposeHeader("Authorization");
                });
            });
        }).start(8080);



        UserDAO userDAO = new UserDAO();
        AccountDAO accountDAO = new AccountDAO();

        UserServices userServices = new UserServices(userDAO);
        AccountServices accountServices = new AccountServices(accountDAO);


        UserController userController = new UserController(app, userServices, accountServices);
        AccountController accountController = new AccountController(app, accountServices);

        userController.userEndpoint(app);
        accountController.accountEndpoint(app);

    }
}
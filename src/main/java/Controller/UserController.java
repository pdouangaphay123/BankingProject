package Controller;

import Model.User;
import Service.AccountServices;
import Service.UserServices;
import Utility.DTO.LoginCreds;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserController {

    private static User sessionUser;
    private final UserServices userServices;
    Javalin app;
    public UserController(Javalin app, UserServices userServices, AccountServices accountServices) {
        this.app = app;
        this.userServices = userServices;
    }

    public static int getSessionUserId(){

        return sessionUser.getUserId();
    }

    public void userEndpoint(Javalin app) {
        app.post("register", this::registerHandler);
        app.post("login", this::loginHandler);
        app.post("logout", this::logoutHandler);
    }

    private void registerHandler(Context context) throws JsonProcessingException, SQLException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(context.body(), User.class);

        if(sessionUser == null){
            sessionUser = userServices.registerUser(user, context);
            if(sessionUser != null) {
                context.status(200);
                context.json(user);
                sessionUser = null;
                context.json("Successfully created the account");
            }
        }
    }
    private void loginHandler(Context context) throws JsonProcessingException, SQLException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        LoginCreds loginCreds = mapper.readValue(context.body(), LoginCreds.class);

        User user = userServices.loginUser(loginCreds);

        sessionUser = user;

        if(sessionUser != null) {
            context.status(200);
            context.json(user);
            context.json("Login successfully");
        }
        else{
            context.status(400);
            context.json("Incorrect email or password");
        }
    }

    private void logoutHandler(Context context) throws JsonProcessingException, NullPointerException {


        //System.out.println("There is a user logged on: " + sessionUser.getUserId());

        if(sessionUser != null){
            sessionUser = null;
            context.status(200);
            context.json("Logout successfully");
        } else{
            context.status(400);
            context.json("There is no sessionUser logged on to log out");
        }
    }
}

package Model;

import java.util.*;

public class User extends Account {

    private int userId;
    private String email;
    private String password;
    private String customerName;
    static public HashMap<String, User> customerMap = new HashMap<String, User>();

    //make constructor all args and also getter and setters
    public User(){

    }
    public User(String email, String password, String customerName){

        this.email = email;
        this.password = password;
        this.customerName = customerName;
        //customerMap.put(email, this);
    }

    public User(int userId, String email, String customerName){

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.customerName = customerName;
        //customerMap.put(email, this);
    }

    public User(int accountId, double balance) {
        super(accountId, balance);
    }

    // getters
    public int getUserId(){

        return userId;
    }

    public String getEmail(){

        return this.email;
    }

    public String getPassword(){

        return this.password;
    }

    public String getCustomerName(){

        return customerName;
    }

    // setters
    public void setPassword(String password){

        this.password = password;
    }

    public void setUserId(int userId){

        this.userId = userId;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public void setCustomerName(String customerName){

        this.customerName = customerName;
    }
}

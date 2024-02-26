package Model;

public class User {

    private int userId;
    private String email;
    private String password;
    private String customerName;

    //make constructor all args and also getter and setters
    public User(String email, String password, String customerName){
        this.email = email;
        this.password = password;
        this.customerName = customerName;
    }

    public User(){
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.customerName = customerName;
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

    public int getUserId(){

        return this.userId;
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

package Utility.DTO;

public class LoginCreds {

    private String email;
    private String password;

    public String getEmail(){

        return this.email;
    }

    public String getPassword(){

        return this.password;
    }

    //
    public String setEmail(String email){

        this.email = email;
        return email;
    }
    public String setPassword(String password){

        this.password = password;
        return password;
    }
}

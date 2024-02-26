package Model;

public class Account {

    private int accountId;
    private int userId;
    private double balance;

    public int getAccountId(){

        return this.accountId;
    }

    public double getBalance(){

        return this.balance;
    }

    public int getUserId(){

        return this.userId;
    }

    // setters
    public void setDeposit(double amount){

        this.balance += amount;
    }

    public void setWithdraw(double amount) {

        if (amount <= this.balance)
            this.balance -= amount;
    }

    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }

    public void setBalance(double balance) {

        this.balance = balance;
    }

    public void setUserId(int userId) {

        this.userId = userId;
    }
}




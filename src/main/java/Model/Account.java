package Model;

import java.util.*;

public class Account {

    private int accountId;
    private double balance;

    public Account(int accountId, double balance) {

        this.accountId = accountId;
        this.balance = 0;
    }

    public Account() {
    }

    //make constructor all args and also getter and setters
    /*public Account(int accountId, int balance){

        this.accountId = accountId;
        this.balance = balance;

    }*/

    public int getAccountId(){

        return this.accountId;
    }

    public double getBalance(){

        return this.balance;
    }

    // setters
    public void setDeposit(double amount){ // initial deposit

        this.balance += amount;

    }

    public void setWithdraw(double amount) {

        if (amount <= this.balance)
            this.balance -= amount;

        else System.out.println("Insufficient funds to withdraw, try again.");
    }

    public void setAccountId(int accountId) {

        this.accountId = accountId;
    }

    public void setBalance(double balance) {

        this.balance = balance;
    }
}




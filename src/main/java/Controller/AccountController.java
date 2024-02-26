package Controller;

import Model.Account;
import Service.AccountServices;
import Utility.DTO.AmountDepositWithdraw;
import Utility.DTO.AmountTransferTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;

public class AccountController {

    private Account sessionAccount;
    private final AccountServices accountServices;
    Javalin app;
    public AccountController(Javalin app, AccountServices accountServices) {
        this.app = app;
        this.accountServices = accountServices;
    }

    public void accountEndpoint(Javalin app) {
        app.post("deposit", this::depositHandler);
        app.post("withdraw", this::withdrawHandler);
        app.get("balance", this::balanceHandler);
        app.post("transfer", this::transferHandler);
        }

    private void balanceHandler(Context context) throws JsonProcessingException, IOException, SQLException, NullPointerException {
        //ObjectMapper mapper = new ObjectMapper();
        //AmountDepositWithdraw amount = mapper.readValue(context.body(), AmountDepositWithdraw.class);

        Account account = new Account();
        try {
            sessionAccount = accountServices.checkBalance(UserController.getSessionUserId(), account);
            if (sessionAccount == null) {
                context.status(400);
            } else {
                context.status(200);
                context.json(sessionAccount.getBalance());
            }
        } catch (NullPointerException e){
            context.status(400);
            context.json("need be logged in to use the services");
            e.printStackTrace();
        }
    }

    private void depositHandler(Context context) throws JsonProcessingException, SQLException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        AmountDepositWithdraw amount = mapper.readValue(context.body(), AmountDepositWithdraw.class);

        Account account = new Account();

        try{
            sessionAccount = accountServices.accountDeposit(UserController.getSessionUserId(), amount, account);
            if (sessionAccount == null) {
                context.status(400);
                context.json("need be logged in to use the services");
            } else {
                context.status(200);
                context.json(sessionAccount);
                context.json("Successfully deposited into account");
            }
        } catch(NullPointerException e){
            context.status(400);
            context.json("need be logged in to use the services");
            e.printStackTrace();
        }
    }
    private void withdrawHandler(Context context) throws JsonProcessingException, SQLException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        AmountDepositWithdraw amount = mapper.readValue(context.body(), AmountDepositWithdraw.class);

        Account account = new Account();

        try {
            sessionAccount = accountServices.accountWithdraw(UserController.getSessionUserId(), amount, account, context);

            if (sessionAccount == null) {
                context.status(400);
                //context.json("need be logged in to use the services");
            } else {
                context.status(200);
                context.json(account);
                context.json("Successfully withdraw into account");
            }
        } catch (NullPointerException e){
            context.status(400);
            context.json("need be logged in to use the services");
            e.printStackTrace();
        }
    }

    private void transferHandler(Context context) throws JsonProcessingException, SQLException, NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        AmountTransferTransaction transfer = mapper.readValue(context.body(), AmountTransferTransaction.class);
        //AmountDepositWithdraw amount = new AmountDepositWithdraw();
        //amount.setAmount(transfer.getAmount());
        Account account = new Account();
        try {
            sessionAccount = accountServices.accountTransfer(UserController.getSessionUserId(), transfer, account, context);
            if (sessionAccount == null) {
                context.status(400);
                //context.json("need be logged in to use the services");
            } else {
                context.status(200);
                context.json(account);
                context.json("Successfully transferred to " + transfer.getTransferId());
            }
        } catch(NullPointerException e){
            context.status(400);
            context.json("need be logged in to use the services");
            e.printStackTrace();
        }
    }
}

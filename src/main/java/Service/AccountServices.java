package Service;

import DAO.AccountDAO;
import DAO.UserDAO;
import Model.Account;
import Utility.DTO.AmountDepositWithdraw;
import Utility.DTO.AmountTransferTransaction;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.Optional;

public class AccountServices {

    AccountDAO accountDAO;
    UserDAO userDAO;

    public AccountServices(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account checkBalance(int userId, Account account) {
        account = accountDAO.checkBalance(userId, account);
        if (account == null) return null;
        return account;

    }

    public Account accountDeposit(int userId, AmountDepositWithdraw transferor, Account account) {
        account = accountDAO.updateDeposit(userId, transferor.getAmount(), account);
        if (account == null) return null;
        return account;
    }

    public Account accountWithdraw(int userId, AmountDepositWithdraw transferor, Account account, Context context) {
        account = accountDAO.updateWithdraw(userId, transferor.getAmount(), account);
        if (account == null) {
            context.status(400);
            context.json("Insufficient funds");
            return null;
        }

        return account;
    }

    public Account accountTransfer(int userId, AmountTransferTransaction transferee, Account sendingAccount, Context context) throws SQLException {
        AmountDepositWithdraw transferor = new AmountDepositWithdraw();
        transferor.setAmount(transferee.getAmount());
        Account receivingAccount = new Account();

        sendingAccount = accountDAO.updateWithdraw(userId, transferor.getAmount(), sendingAccount);
        if(sendingAccount == null){
            context.status(400);
            context.json("Insufficient funds to transfer");
            return null;
        }
        else {
                receivingAccount = accountDAO.updateDeposit(transferee.getTransferId(), transferee.getAmount(), receivingAccount);
                return sendingAccount;
        }
    }
}
//    public Account accountTransfer(int userId, AmountTransferTransaction transferee, Account sendingAccount, Context context) {
//        sendingAccount = accountDAO.transferBetweenAccounts(userId, transferee, sendingAccount);
//        if(sendingAccount == null) {
//            context.status(400);
//            context.json("Insufficient funds to transfer");
//            return null;
//        }
//
//        return sendingAccount;
//    }


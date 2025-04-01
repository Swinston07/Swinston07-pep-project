package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        return accountDAO.addAccount(account);
    }

    public Account getAccountById(int id){
        return accountDAO.getAccountById(id);
    }

    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }
}

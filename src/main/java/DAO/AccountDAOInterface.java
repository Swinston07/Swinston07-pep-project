package DAO;
import Model.Account;
import java.util.List;

public interface AccountDAOInterface {
    Account addAccount(Account account);
    Account getAccountById(int id);
    Account getAccountByUsername(String username);
    List<Account> getAllAccounts();
}

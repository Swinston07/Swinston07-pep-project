package DAO;
import Model.Account;
import java.util.List;

public interface AccountDAOInterface {
    void addAccount(Account account);
    Account getAccountById(int id);
    Account getAccountByUsername(String username);
    List<Account> getAllAccounts();
}

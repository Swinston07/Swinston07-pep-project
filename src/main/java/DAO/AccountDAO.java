package DAO;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO implements AccountDAOInterface {
    @Override
    public Account addAccount(Account account){
        String sql = "INSERT INTO account (username, password) VALUES(?, ?)";

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                int id = rs.getInt(1);
                return new Account(id, account.getUsername(), account.getPassword());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getAccountById(int id){
        String sql = "Select * FROM account WHERE account_id = ?";
        Account account;

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                return account;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getAccountByUsername(String username){
        String sql = "SELECT * FROM account WHERE username = ?";
        Account account;

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                account = new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );

                return account;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> getAllAccounts(){
        String sql = "SELECT * FROM account";
        List<Account> accounts = new ArrayList<>();

        try(Connection conn = ConnectionUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                accounts.add(
                    new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")
                    )
                );
            }

            return accounts;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

package se.leotest.manager;

import java.util.ArrayList;
import java.util.List;
import se.leotest.db.entities.AccountEntity;
import se.leotest.db.query.AccountCRUD;

/**
 * Utf�r kontorelaterade operationer
 * 
 * Mellanlager mellan REST-tj�nsterna och Persistenslagret
 * 
 * Borde inte jobba med Entitetsobjekt alls men nu g�r den det
 * f�r att spara tid
 * 
 * @author Andreas
 */
public class AccountManager {
    
    /**
     * Skapar upp ett AccountEntity-objekt utifr�n ett anv�ndarnamn och en summa
     * 
     * @param name
     * @param amount 
     */
    public void createAccount(String username, int amount) {
        AccountCRUD crud = new AccountCRUD();
        crud.saveAccount(new AccountEntity(username, amount));
    }
    
    /**
     * Returnerar kontot f�r en viss user
     * 
     * @param username
     * @return AccountEntity
     */
    public AccountEntity getAccount(String username) {
        AccountEntity account = new AccountCRUD().getAccount(username);
        
        return account;
    }
    
    /**
     * Returnerar samtliga anv�ndares anv�ndarnamn
     * 
     * @return List<String>
     */
    public static List<String> getAccountUsersAsStrings() {
        List<AccountEntity> accounts = new AccountCRUD().getAccounts();
        
        List<String> userNames = new ArrayList<String>();
        for (AccountEntity account : accounts) {
            userNames.add(account.getName());
        }
        
        return userNames;
    }
    
    /**
     * Kollar om anv�ndarnamnet �r upptaget
     * 
     * Potentiell concurrency-bugg som jag inte hinner l�sa! 
     * G�r vi en l�sning kommer man inte "r�ka"
     * se att ett anv�ndarnamn �r ledigt samtidigt 
     * som n�gon annan skapar den.
     * En l�sning ger en prestandaf�rs�mring.
     * Man skapar dock inte anv�ndarkonton s� ofta s� 
     * i det h�r fallet kanske det �r godtagbart
     * 
     * @param username String
     * @return Boolean
     */
    public static Boolean doesUserExist(String username) {
        AccountEntity account = new AccountCRUD().getAccount(username);
        
        return null != account ? true : false;
    }
}

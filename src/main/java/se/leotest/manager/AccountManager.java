package se.leotest.manager;

import se.leotest.db.entities.AccountEntity;
import se.leotest.db.query.AccountCRUD;

/**
 * Utför kontorelaterade operationer
 * 
 * Mellanlager mellan REST-tjänsterna och Persistenslagret
 * 
 * Borde inte jobba med Entitetsobjekt alls men nu gör den det
 * för att spara tid
 * 
 * @author Andreas
 */
public class AccountManager {
    
    /**
     * Skapar upp ett AccountEntity-objekt utifrån ett användarnamn och en summa
     * 
     * @param name
     * @param amount 
     */
    public void createAccount(String username, int amount) {
        AccountCRUD crud = new AccountCRUD();
        crud.saveAccount(new AccountEntity(username, amount));
    }
    
    /**
     * Returnerar kontot för en viss user
     * 
     * @param username
     * @return AccountEntity
     */
    public AccountEntity getAccount(String username) {
        AccountEntity account = new AccountCRUD().getAccount(username);
        
        return account;
    }
    
    
    
    /**
     * Kollar om användarnamnet är upptaget
     * 
     * Potentiell concurrency-bugg som jag inte hinner lösa! 
     * Gör vi en låsning kommer man inte "råka"
     * se att ett användarnamn är ledigt samtidigt 
     * som någon annan skapar den.
     * En låsning ger en prestandaförsämring.
     * Man skapar dock inte användarkonton så ofta så 
     * i det här fallet kanske det är godtagbart
     * 
     * @param username String
     * @return Boolean
     */
    public Boolean doesUserExist(String username) {
        AccountEntity account = new AccountCRUD().getAccount(username);
        
        return null != account ? true : false;
    }
}

package se.leotest.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import se.leotest.db.entities.AccountEntity;
import se.leotest.db.entities.TransactionEntity;
import se.leotest.db.query.AccountCRUD;
import se.leotest.db.query.TransactionCRUD;

/**
 * Utf�r administrat�rsrelaterade operationer
 * 
 * Mellanlager mellan REST-tj�nsterna och Persistenslagret
 * 
 * Borde inte jobba med Entitetsobjekt alls men nu g�r den det
 * f�r att spara tid
 * 
 * @author Andreas
 */
public class AdminManager {
    
    /**
     * Returnerar samtliga anv�ndares anv�ndarnamn
     * 
     * @return List<String>
     */
    public static List<String> getAccountUsersAsStrings() {
        List<AccountEntity> accounts = new AccountCRUD().getAccounts();
        
        List<String> userNames = accounts.stream().map(sc -> sc.getName()).collect(Collectors.toList());
        
        return userNames;
    }
    
    /**
     * Returnerar samtliga transaktioner
     * 
     * @return List<String>
     */
    public static List<String> getTransactionsAsStrings() {
        List<TransactionEntity> transactions = new TransactionCRUD().getTransactions();
        
        List<String> transactionsAsString = transactions.stream().map(sc -> "{"+sc.toString()+"}").collect(Collectors.toList());
   
        return transactionsAsString;
    }
}

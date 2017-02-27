package se.leotest.manager;

import java.util.ArrayList;
import java.util.List;
import se.leotest.db.entities.TransactionEntity;
import se.leotest.db.query.TransactionCRUD;
import se.leotest.exception.TransactionException;

/**
 * Utför transaktionsrelaterade operationer
 * 
 * Mellanlager mellan REST-tjänsterna och Persistenslagret
 * 
 * Borde inte jobba med Entitetsobjekt alls men nu gör den det
 * för att spara tid
 * 
 * @author Andreas
 */
public class TransactionManager {
    
    /**
     * Anropar persistenslagret för att utföra en DEBIT-transaktion
     * 
     * @param username String
     * @param transid String
     * @param amount int
     * @throws TransactionException 
     */
    public void doDebitTransaction(String username, String transid, int amount) throws TransactionException {
        TransactionCRUD transCrud = new TransactionCRUD();

        transCrud.doDebitTransaction(username, transid, amount);
    }
    
    /**
     * Anropar persistenslagret för att utföra en CREDIT-transaktion
     * 
     * @param username String
     * @param transid String
     * @param amount int
     */
    public void doCreditTransaction(String username, String transid, int amount) {
        TransactionCRUD transCrud = new TransactionCRUD();
        
        transCrud.doCreditTransaction(username, transid, amount);
    }
    
    /**
     * Kontrollerar om ett transaktionsid är upptaget
     * 
     * Potentiell concurrency-bugg som jag inte hinner lösa! 
     * Gör vi en låsning kommer man inte "råka"
     * se att ett transaktionsid är ledigt samtidigt 
     * som någon annan skapar det.
     * En låsning ger en prestandaförsämring.
     * Man skapar dock inte användarkonton så ofta så 
     * i det här fallet kanske det är godtagbart
     * 
     * @param transid String
     * @return Boolean
     */
    public static Boolean doesTransactionExist(String transid) {
        List<TransactionEntity> transactions = new TransactionCRUD().getTransactions();
        
        for(TransactionEntity trans : transactions) {
            if (transid.equals(trans.getTransactionid()))
                return true;
        }
        
        return false;
    }
    
    /**
     * Returnerar samtliga transaktioner
     * 
     * @return List<String>
     */
    public static List<String> getTransactionsAsStrings() {
        List<TransactionEntity> transactions = new TransactionCRUD().getTransactions();
        
        List<String> transactionsAsString = new ArrayList<String>();
        for (TransactionEntity trans : transactions) {
            transactionsAsString.add("{" + trans.toString() + "}");
        }
        
        return transactionsAsString;
    }
}

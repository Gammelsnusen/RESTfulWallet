package se.leotest.manager;

import java.util.ArrayList;
import java.util.List;
import se.leotest.db.entities.TransactionEntity;
import se.leotest.db.query.TransactionCRUD;
import se.leotest.exception.TransactionException;

/**
 * Utf�r transaktionsrelaterade operationer
 * 
 * Mellanlager mellan REST-tj�nsterna och Persistenslagret
 * 
 * Borde inte jobba med Entitetsobjekt alls men nu g�r den det
 * f�r att spara tid
 * 
 * @author Andreas
 */
public class TransactionManager {
    
    /**
     * Anropar persistenslagret f�r att utf�ra en DEBIT-transaktion
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
     * Anropar persistenslagret f�r att utf�ra en CREDIT-transaktion
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
     * Kontrollerar om ett transaktionsid �r upptaget
     * 
     * Potentiell concurrency-bugg som jag inte hinner l�sa! 
     * G�r vi en l�sning kommer man inte "r�ka"
     * se att ett transaktionsid �r ledigt samtidigt 
     * som n�gon annan skapar det.
     * En l�sning ger en prestandaf�rs�mring.
     * Man skapar dock inte anv�ndarkonton s� ofta s� 
     * i det h�r fallet kanske det �r godtagbart
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

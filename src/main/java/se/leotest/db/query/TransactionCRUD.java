package se.leotest.db.query;

import java.util.List;
import org.hibernate.Session;
import se.leotest.db.entities.AccountEntity;
import se.leotest.db.entities.TransactionEntity;
import se.leotest.exception.TransactionException;
import se.leotest.transaction.TransactionType;

/**
 * Utför CRUD-operationer för transaktioner mot databasen
 * 
 * @author Andreas
 */
public class TransactionCRUD {
    
    /**
     * Debiterar en summa från ett konto
     * samt registrerar transaktionen
     * 
     * @param username String
     * @param transid String
     * @param amount int
     * @throws TransactionException 
     */
    public void doDebitTransaction(String username, String transid, int amount) throws TransactionException {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        AccountEntity accEntity = new AccountCRUD().getAccount(username);
        
        int balance = accEntity.getBalance();
        
        if (balance - amount < 0)
            throw new TransactionException("Not enough balance, amount was: " + amount + " and Balance is: " + balance);
        
        balance -= amount;
        accEntity.setBalance(balance);
        
        TransactionEntity transEntity = new TransactionEntity(accEntity, amount, transid, TransactionType.DEBIT);
        
        session.saveOrUpdate(accEntity);
        session.save(transEntity);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    /**
     * Lägger till en summa till ett konto
     * samt registrerar transaktionen
     * 
     * @param username String
     * @param transid String
     * @param amount int
     */
    public void doCreditTransaction(String username, String transid, int amount) {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        AccountEntity accEntity = new AccountCRUD().getAccount(username);
        
        int balance = accEntity.getBalance();
        
        balance += amount;
        accEntity.setBalance(balance);
        
        TransactionEntity transEntity = new TransactionEntity(accEntity, amount, transid, TransactionType.CREDIT);
        
        session.saveOrUpdate(accEntity);
        session.save(transEntity);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    /**
     * Returnerar en lista över alla transaktioner
     * 
     * @return List<TransactionEntity>
     */
    public List<TransactionEntity> getTransactions() {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        List<TransactionEntity> list = session.createCriteria(TransactionEntity.class).list();
        
        session.close();
        
        return list;
    }
}

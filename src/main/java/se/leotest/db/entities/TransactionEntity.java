package se.leotest.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import se.leotest.transaction.TransactionType;

/**
 * TransactionEntity-klassen representerar en Debit eller Credit-transaktion 
 * som entitet i DB
 * 
 * Innehåller minimalt med data pga. att uppgiften inte kräver mer
 * 
 * En transaktion tillhör ett visst konto - AccountEntity
 * 
 * @author Andreas
 * 
 */
@Entity
@Table(name="transaction")
public class TransactionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int amount;

    private TransactionType type;
    
    @Column(unique = true)
    private String transactionid;
    
    @ManyToOne
    @JoinColumn
    private AccountEntity account;
    
    public TransactionEntity() { }
    
    public TransactionEntity(AccountEntity account, int amount, String transactionid, TransactionType type) {
        this.account = account;
        this.amount = amount;
        this.transactionid = transactionid;
        this.type = type;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public String getTransactionid() {
        return transactionid;
    }
    
    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
    
    public void setAccountEntity(AccountEntity account) {
        this.account = account;
    }
    
    public AccountEntity getAccountEntity() {
        return account;
    }
    
    @Override
    public String toString() {
        String transaction = "Transaction: " + transactionid + 
                    ", User: " + account.getName() + 
                    ", Type: " + type + 
                    ", Amount: " + amount;
        
        return transaction;
    }
}

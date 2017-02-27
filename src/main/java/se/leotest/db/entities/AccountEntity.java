package se.leotest.db.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * AccountEntity-klassen representerar ett "användarkonto"
 * som entitet i DB
 * 
 * Innehåller minimalt med data pga. att uppgiften inte kräver mer
 * 
 * Ett Account kan innehålla flera Debit/Credit-transaktioner - TransactionEntity
 * 
 * @author Andreas
 * 
 */
@Entity
@Table(name="account")
public class AccountEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Account_ID", unique = true, nullable = false)
    private Long accountid;
    
    @Column(unique = true)
    private String name;
    
    private int balance;
    
    @OneToMany(targetEntity=TransactionEntity.class, mappedBy="account",cascade=CascadeType.PERSIST)
    private List<TransactionEntity> trans = new ArrayList<TransactionEntity>();
    
    public AccountEntity() { }
    
    public AccountEntity(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
    
    public Long getAccountId() {
        return accountid;
    }
    
    public void setAccountId(Long accountId) {
        this.accountid = accountId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<TransactionEntity> getTrans() {
        return trans;
    }
    
    public void setTrans(List<TransactionEntity> trans) {
        this.trans = trans;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}

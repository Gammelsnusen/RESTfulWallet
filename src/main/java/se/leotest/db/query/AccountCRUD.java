package se.leotest.db.query;


import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import se.leotest.db.entities.AccountEntity;

/**
 *
 * Utför CRUD-operationer för Account mot databasen
 * 
 * @author Andreas
 */
public class AccountCRUD {
    
    /**
     * Sparar ett AccountEntity-objekt till databasen
     * 
     * @param account AccountEntity
     */
    public void saveAccount(AccountEntity account) {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        session.save(account);
        
        session.getTransaction().commit();
        
        session.close();
    }
    
    /**
     * Hämtar ett unikt AccountEntity från DB
     * 
     * @param name String - Username
     * @return 
     */
    public AccountEntity getAccount(String name) {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        Criteria criteria = session.createCriteria(AccountEntity.class);
        AccountEntity account = (AccountEntity) criteria.add(Restrictions.eq("name", name)).setMaxResults(1).uniqueResult();
        
        session.close();
        
        return account;
    }
    
    /**
     * Returnerar en lista på alla AccountEntity-objekt i DB
     * 
     * @return 
     */
    public List<AccountEntity> getAccounts() {
        Session session = DBConnect.getSession();
        session.beginTransaction();
        
        List<AccountEntity> list = session.createCriteria(AccountEntity.class).list();
        
        session.close();
        
        return list;
    }
}

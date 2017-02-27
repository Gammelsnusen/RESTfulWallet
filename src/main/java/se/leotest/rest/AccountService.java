package se.leotest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.hibernate.exception.ConstraintViolationException;
import se.leotest.manager.AccountManager;


/**
 * Resttj�nst f�r Konto-relaterade operationer
 * 
 * @author Andreas
 */
@Path("/account")
public class AccountService {
    
    
    /**
     * Skapar ett konto med ett Username
     * �r sn�ll och sk�nker 100kr till nya anv�ndare
     * 
     * Antar lite fult att ConstraintViolation alltid inneb�r att anv�ndarnamnet
     * �r upptaget. Borde snyggas till, f�nga felet tidigare.
     * 
     * @param username String
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Produces("text/plain")
    @Path("/register/{username}")
    public Response registerAccount(@PathParam("username")  String username) {
        try {
            new AccountManager().createAccount(username, 100);
        } catch ( ConstraintViolationException e ) {
            return Response.status(500).entity("User " + username + " already exists").build();
        }
        return Response.status(200).entity("Account created for user: " + username).build();
    }
    
    /**
     * Returnerar anv�ndarens aktuella saldo
     * 
     * @param username String
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Produces("text/plain")
    @Path("/balance/{username}")
    public Response getBalance(@PathParam("username") String username) {
        AccountManager accManager = new AccountManager();
        if (accManager.doesUserExist(username)) {
            int balance = accManager.getAccount(username).getBalance();
            return Response.status(200).entity("Account balance is: " + balance).build();
        } else {
            return Response.status(200).entity("Unknown user").build();
        }
        
    }
}

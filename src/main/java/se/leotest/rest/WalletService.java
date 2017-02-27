package se.leotest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import se.leotest.manager.AccountManager;
import se.leotest.exception.TransactionException;
import se.leotest.manager.TransactionManager;

/**
 * Resttjänst för "plånbok"
 * 
 * @author Andreas
 */
@Path("/transaction")
public class WalletService {

    /**
     * Debiterar ett konto en viss summa
     * 
     * @param username String
     * @param transid String
     * @param amount in
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Produces("text/plain")
    @Path("/debit/{username}/{transid}/{amount}")
    public Response getDebit(
            @PathParam("username")  String username,
            @PathParam("transid")  String transid,
            @PathParam("amount")   int amount) {
        
        if (!AccountManager.doesUserExist(username))
            return Response.status(200).entity("Unknown username: " + username).build();
        if (amount < 0) 
            return Response.status(200).entity("Amount was " + amount + ", needs to be greater or equal to 0").build();
        if (TransactionManager.doesTransactionExist(transid))
            return Response.status(200).entity("TransactionID " + transid + " already exists").build();
        
        try {
            new TransactionManager().doDebitTransaction(username, transid, amount);
        } catch (TransactionException e) {
            return Response.status(500).entity(e.toString()).build();
        }
        String output = "Wallet for user: " + username + " was debited " + amount + ", transactionid: " + transid;
        
        return Response.status(200).entity(output).build();
    }
    
    /**
     * Krediterar ett konto en viss summa
     * 
     * @param username String
     * @param transid String
     * @param amount int
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Path("/credit/{username}/{transid}/{amount}")
    public Response setCredit(
            @PathParam("username")  String username,
            @PathParam("transid")  String transid,
            @PathParam("amount")   int amount) {
        
       if (!AccountManager.doesUserExist(username))
            return Response.status(200).entity("Unknown username: " + username).build();
        if (amount < 0) 
            return Response.status(200).entity("Amount was " + amount + ", needs to be greater or equal to 0").build();
        if (TransactionManager.doesTransactionExist(transid))
            return Response.status(200).entity("TransactionID " + transid + " already exists").build();
        
        new TransactionManager().doCreditTransaction(username, transid, amount);

        String output = "Wallet for user: " + username + " was credited " + amount + ", transactionid: " + transid;
        
        return Response.status(200).entity(output).build();
    }
}

package se.leotest.rest;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import se.leotest.manager.AdminManager;

/**
 * Administrerande tj�nster f�r att lista olika saker fr�n DB
 * Skulle kunna kompletteras med en del andra operationer som
 * Ta bort/editera konton och dylikt
 * 
 * Os�krade i det h�r fallet, men borde nog s�kras upp p� n�got s�tt
 * i "verkligheten"
 * 
 * @author Andreas
 */
@Path("/admin")
public class AdminService {
    
    /**
     * Returnerar en lista p� alla registrerade anv�ndare.
     * 
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Produces("text/plain")
    @Path("/users")
    public Response getAllUsers() {
        List<String> userNames = AdminManager.getAccountUsersAsStrings();
        
        String response = "";
        for (String name : userNames) {
            response += name + ",";
        }
        
        return Response.status(200).entity(response).build();
    }
    
    /**
     * Returnerar alla transaktioner som en str�ng
     * 
     * @return text/plain - Skulle helst ge JSON men pga. tidsbrist blev det text
     */
    @GET
    @Produces("text/plain")
    @Path("/transactions")
    public Response getTransactions() {
        List<String> transactions = AdminManager.getTransactionsAsStrings();
        
        String response = "";
        for (String transaction : transactions) {
            response += transaction + ",";
        }
        
        return Response.status(200).entity(response).build();
    }
}

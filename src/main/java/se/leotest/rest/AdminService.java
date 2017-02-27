package se.leotest.rest;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import se.leotest.manager.AdminManager;

/**
 * Administrerande tjänster för att lista olika saker från DB
 * Skulle kunna kompletteras med en del andra operationer som
 * Ta bort/editera konton och dylikt
 * 
 * Osäkrade i det här fallet, men borde nog säkras upp på något sätt
 * i "verkligheten"
 * 
 * @author Andreas
 */
@Path("/admin")
public class AdminService {
    
    /**
     * Returnerar en lista på alla registrerade användare.
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
     * Returnerar alla transaktioner som en sträng
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

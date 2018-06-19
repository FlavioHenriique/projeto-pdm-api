package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.SolicitacaoDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SolicitacaoResource {
    
    Gson gson = new Gson();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarSolicitacao(String json){
        
        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

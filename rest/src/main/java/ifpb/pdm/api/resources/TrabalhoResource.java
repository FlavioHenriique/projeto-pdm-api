package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.TrabalhoDAO;
import ifpb.pdm.api.model.Trabalho;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("trabalho")
public class TrabalhoResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(String json) {

        Gson gson = new Gson();
        Trabalho trabalho = gson.fromJson(json, Trabalho.class);
        
        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            if (dao.cadastrar(trabalho)) {
                return Response.status(Status.CREATED).build();
            }

        } catch (SQLException ex) {
            
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Status.BAD_REQUEST).build();
    }
}

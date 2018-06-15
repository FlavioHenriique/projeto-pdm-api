package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.UsuarioDAO;
import ifpb.pdm.api.model.Usuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

@Path("usuario")
public class UsuarioResource {

    @GET
    @Path("/{email}")
    public Response buscarUsuario(@PathParam("email") String email) {

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario user = dao.buscar(email);
            if (user != null) {
                return Response.ok().entity(user.toString()).build();
            } else {
                return Response.ok().entity("Não encontrado").build();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(String json) {
        JSONObject obj = new JSONObject(json);
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.login(obj.getString("email").toString(),
                    obj.getString("senha").toString())) {
                    return "cadastrado";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "nao cadastrado";
    }

}

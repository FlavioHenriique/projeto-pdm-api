package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.UsuarioDAO;
import ifpb.pdm.api.model.Usuario;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

@Path("usuario")
public class UsuarioResource {

    Gson gson = new Gson();

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuario(@PathParam("email") String email) {

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario user = dao.buscar(email);
            if (user != null) {
                return Response.ok().entity(gson.toJson(user)).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        JSONObject obj = new JSONObject(json);
        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.login(obj.getString("email").toString(),
                    obj.getString("senha").toString())) {
                Usuario user = dao.buscar(obj.getString("email").toString());

                return Response.status(Status.ACCEPTED)
                        .entity(gson.toJson(user))
                        .build();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Path("cadastro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response cadastrar(String json) {

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario user = gson.fromJson(json, Usuario.class);
            System.out.println(user.toString());
            if (dao.buscar(user.getEmail()) == null) {
                if (dao.salvar(user)) {
                    return Response.status(Status.CREATED).build();

                }
            } else {
                return Response.status(Status.FORBIDDEN).build();
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return Response.ok().entity(null).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarUsuario(String json) {

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario user = dao.atualizar(gson.fromJson(json, Usuario.class));

            return Response.status(Status.OK).entity(gson.toJson(user)).build();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();

        }

        return Response.status(Status.FORBIDDEN).build();
    }

}

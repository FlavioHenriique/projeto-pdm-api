package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.TrabalhoDAO;
import ifpb.pdm.api.model.Trabalho;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("trabalho")
public class TrabalhoResource {

    private Gson gson = new Gson();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(String json) {

        Trabalho trabalho = gson.fromJson(json, Trabalho.class);

        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            if (dao.cadastrar(trabalho)) {
                return Response.status(Status.CREATED).build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response meusTrabalhos(@PathParam("email") String email) {

        try {
            TrabalhoDAO dao = new TrabalhoDAO();

            return Response.ok().entity(gson.toJson(dao.meusTrabalhos(email)))
                    .build();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return Response.ok().build();
    }

    @GET
    @Path("/busca/{campo}/{valor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorCampo(@PathParam("campo") String campo,
            @PathParam("valor") String valor) {

        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            if (campo.equals("categoria")) {

                return Response.ok().entity(gson.toJson(
                        dao.buscaPorCategoria(valor))).build();

            } else {

                return Response.ok().entity(gson.toJson(
                        dao.buscaPorCidade(valor))).build();

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @DELETE
    @Path("/{codTrabalho}")
    public Response deletarTrabalho(@PathParam("codTrabalho") int trabalho) {

        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            dao.deletar(trabalho);

            return Response.ok().build();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.CONFLICT).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response atualizarTrabalho(String json) {

        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            Trabalho t = dao.atualizar(gson.fromJson(json,Trabalho.class));
            
            return Response.status(Status.OK).entity(gson.toJson(t)).build();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.NOT_FOUND).build();
    }
}

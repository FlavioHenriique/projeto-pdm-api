package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.TrabalhoDAO;
import ifpb.pdm.api.model.Trabalho;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
                dao.fecharConexao();
                return Response.status(Status.CREATED).build();
            }
        } catch (SQLException | ClassNotFoundException 
                | NoSuchAlgorithmException ex) {
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
            List<Trabalho> lista = dao.meusTrabalhos(email);
            dao.fecharConexao();
            System.out.println(lista.size());
            return Response.ok().entity(gson.toJson(lista))
                    .build();
        } catch (SQLException | ClassNotFoundException 
                | NoSuchAlgorithmException ex) {
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
                
                List<Trabalho> lista = dao.buscaPorCategoria(valor);
                dao.fecharConexao();
                return Response.ok().entity(gson.toJson(lista)).build();

            } else {

                return Response.ok().entity(gson.toJson(
                        dao.buscaPorCidade(valor))).build();

            }
        } catch (SQLException | ClassNotFoundException 
                | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return Response.status(Status.FORBIDDEN).build();
    }

    @DELETE
    @Path("/{codTrabalho}")
    public Response deletarTrabalho(@PathParam("codTrabalho") int trabalho) {

        try {
            TrabalhoDAO dao = new TrabalhoDAO();
            dao.deletar(trabalho);
            dao.fecharConexao();
            return Response.ok().build();

        } catch (SQLException | ClassNotFoundException 
                | NoSuchAlgorithmException ex) {
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
            dao.fecharConexao();
            return Response.status(Status.OK).entity(gson.toJson(t)).build();

        } catch (SQLException | ClassNotFoundException 
                | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.NOT_FOUND).build();
    }
}

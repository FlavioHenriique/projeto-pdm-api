package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.NotificacaoDAO;
import ifpb.pdm.api.dao.SolicitacaoDAO;
import ifpb.pdm.api.model.Notificacao;
import ifpb.pdm.api.model.Trabalho;
import ifpb.pdm.api.model.Usuario;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
import org.json.JSONObject;

@Path("solicitacao")
public class SolicitacaoResource {

    private Gson gson = new Gson();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarSolicitacao(String json) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            JSONObject jo = new JSONObject(json);
            if (dao.salvar(jo.getInt("trabalho"), jo.getString("email"))) {
                dao.fecharConexao();
                return Response.status(Status.CREATED).build();

            } else {

                return Response.status(Status.FORBIDDEN).build();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Status.BAD_REQUEST)
                .build();
    }

    @GET
    @Path("/{trabalho}")
    public Response buscarSolicitacoesTrabalho(@PathParam("trabalho") int trabalho) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            List<Usuario> solicitantes = dao.buscarSolicitacoes(trabalho);
            dao.fecharConexao();
            return Response.status(Status.FOUND).entity(gson.toJson(solicitantes))
                    .build();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();

        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aceitarSolicitacao(String json) {
        SolicitacaoDAO dao = new SolicitacaoDAO();
        JSONObject dados = new JSONObject(json);

        try {

            dao.aceitarSolicitacao(dados.getString("emailUsuario"),
                    dados.getInt("codTrabalho"));
            dao.fecharConexao();
            return Response.status(Status.OK).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{emailUsuario}/{codTrabalho}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recusarSolicitacao(@PathParam("emailUsuario") String emailUsuario,
            @PathParam("codTrabalho") int codTrabalho) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            dao.recusarSolicitacao(emailUsuario, codTrabalho);
            dao.fecharConexao();
            return Response.ok().build();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return Response.status(Status.CONFLICT).build();
    }

    @GET
    @Path("/busca/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minhasSolicitacoes(@PathParam("email") String email) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            List<Trabalho> lista = dao.minhasSolicitacoes(email);
            dao.fecharConexao();
            return Response.status(Status.OK).entity(gson.toJson(lista)).build();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/notificacoes/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minhasNotificacoes(@PathParam("email") String email) {

        NotificacaoDAO dao = new NotificacaoDAO();
        return Response.ok().entity(dao.minhasNotificacoes(email)).build();

    }
}

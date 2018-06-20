package ifpb.pdm.api.resources;

import com.google.gson.Gson;
import ifpb.pdm.api.dao.SolicitacaoDAO;
import ifpb.pdm.api.model.Usuario;
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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;

@Path("solicitacao")
public class SolicitacaoResource {

    Gson gson = new Gson();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvarSolicitacao(String json) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            JSONObject jo = new JSONObject(json);
            if (dao.salvar(jo.getInt("trabalho"), jo.getString("email"))) {

                return Response.status(Status.CREATED).build();

            } else {

                return Response.status(Status.FORBIDDEN).build();

            }
        } catch (SQLException ex) {

            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(Status.BAD_REQUEST).build();
    }

    @GET
    @Path("/{trabalho}")
    public Response buscarSolicitacoesTrabalho(@PathParam("trabalho") int trabalho) {

        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            List<Usuario> solicitantes = dao.buscarSolicitacoes(trabalho);

            return Response.status(Status.FOUND).entity(gson.toJson(solicitantes))
                    .build();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        return Response.status(Status.BAD_REQUEST).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aceitarSolicitacao(String json) {

        JSONObject dados = new JSONObject(json);
        
        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            dao.aceitarSolicitacao(dados.getString("emailUsuario"),
                    dados.getInt("codTrabalho"));

            return Response.status(Status.OK).build();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SolicitacaoResource.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return Response.status(Status.BAD_REQUEST).build();
    }

    @POST
    @Path("recusar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recusarSolicitacao(String json) {

        JSONObject dados = new JSONObject(json);
        
        try {
            SolicitacaoDAO dao = new SolicitacaoDAO();
            dao.recusarSolicitacao(dados.getString("emailusuario"),
                    dados.getInt("codtrabalho"));

            return Response.ok().build();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        return Response.status(Status.USE_PROXY).build();
    }

}

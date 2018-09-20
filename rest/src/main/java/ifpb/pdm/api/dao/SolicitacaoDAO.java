package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Trabalho;
import ifpb.pdm.api.model.Usuario;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

public class SolicitacaoDAO {

    private Connection conn;
    private UsuarioDAO dao;
    private TrabalhoDAO daoTrabalho;

    public SolicitacaoDAO() {

        try {
            conn = Conexao.getConnection();
            daoTrabalho = new TrabalhoDAO();
            dao = new UsuarioDAO();
        } catch (SQLException | ClassNotFoundException
                | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    public boolean salvar(int trabalho, String email) throws SQLException,
            ClassNotFoundException, NoSuchAlgorithmException {

        String sql = "INSERT INTO solicita_trabalho (emailUsuario, codTrabalho,"
                + " estado) VALUES (?,?,?) ; "
                + "INSERT INTO Notificacao (texto,codtrabalho,emailsolicitante, emaildono) "
                + "VALUES (?,?,?,?);";
        TrabalhoDAO dao = new TrabalhoDAO();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, trabalho);
        stmt.setString(3, "pendente");
        stmt.setString(4, email + " solicitou um trabalho seu!");
        stmt.setInt(5, trabalho);
        stmt.setString(6, email);
        stmt.setString(7, dao.buscarTrabalho(trabalho).getContratante().getEmail());
        stmt.execute();
        stmt.close();

        return true;
    }

    public List<Usuario> buscarSolicitacoes(int trabalho) throws SQLException,
            ClassNotFoundException {

        String sql = "SELECT emailUsuario from solicita_trabalho"
                + " WHERE codTrabalho = ? AND estado ilike 'pendente';";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, trabalho);
        ResultSet rs = stmt.executeQuery();

        List<Usuario> lista = new ArrayList<>();

        while (rs.next()) {
            Usuario u = dao.buscar(rs.getString("emailUsuario"));
            lista.add(u);
        }

        return lista;
    }

    public void aceitarSolicitacao(String email, int trabalho) throws SQLException {

        String sql = "UPDATE solicita_trabalho set estado = 'aceita' WHERE"
                + " emailUsuario = ? AND codTrabalho = ? ;"
                + "UPDATE trabalho set contratado = ? WHERE codigo = ?;"
                + "INSERT INTO NOTIFICACAO (texto,codtrabalho,emailsolicitante,emaildono) "
                + "VALUES (?,?,?, ?);";

        try {
            TrabalhoDAO tdao = new TrabalhoDAO();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2, trabalho);
            stmt.setString(3, email);
            stmt.setInt(4, trabalho);
            stmt.setString(5, "Sua solicitação foi aceita!");
            stmt.setInt(6, trabalho);
            stmt.setString(7, email);
            stmt.setString(8, tdao.buscarTrabalho(trabalho).getContratante().getEmail());
            stmt.execute();
            stmt.close();

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    public void recusarSolicitacao(String email, int trabalho) throws SQLException {

        String sql = "DELETE FROM Solicita_trabalho WHERE emailUsuario = ? "
                + " AND codTrabalho = ? ; "
                + "INSERT INTO NOTIFICACAO (texto,codtrabalho,emailsolicitante, emaildono) "
                + "values (?,?,?, ?)";

        TrabalhoDAO tdao;
        try {
            tdao = new TrabalhoDAO();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2, trabalho);
            stmt.setString(3, "Sua solicitação foi recusada...");
            stmt.setInt(4, trabalho);
            stmt.setString(5, email);
            stmt.setString(6, tdao.buscarTrabalho(trabalho).getContratante().getEmail());
            stmt.execute();
            stmt.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

    }

    public List<Trabalho> minhasSolicitacoes(String email) throws SQLException,
            ClassNotFoundException {

        List<Trabalho> lista = new ArrayList<>();
        String sql = "SELECT codTrabalho FROM solicita_trabalho WHERE "
                + "emailUsuario = ? ;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Trabalho t = daoTrabalho.buscarTrabalho(rs.getInt("codTrabalho"));

            lista.add(t);
        }

        return lista;
    }

    private void abrirConexao() {
        /* try {
            if (conn == null) {
                conn = Conexao.getConnection();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void fecharConexao() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

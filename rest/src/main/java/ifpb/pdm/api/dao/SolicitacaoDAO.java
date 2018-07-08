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

public class SolicitacaoDAO {

    private Connection conn;
    private UsuarioDAO dao;
    private TrabalhoDAO daoTrabalho;

    public SolicitacaoDAO() throws SQLException, ClassNotFoundException,
            NoSuchAlgorithmException {
        conn = Conexao.getConnection();
        daoTrabalho = new TrabalhoDAO();
        dao = new UsuarioDAO();
    }

    public boolean salvar(int trabalho, String email) throws SQLException {

        String sql = "INSERT INTO solicita_trabalho (emailUsuario, codTrabalho,"
                + " estado) VALUES (?,?,?) ;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(2, trabalho);
        stmt.setString(1, email);
        stmt.setString(3, "pendente");
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
                + "UPDATE trabalho set contratado = ? WHERE codigo = ?;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, trabalho);
        stmt.setString(3, email);
        stmt.setInt(4, trabalho);

        stmt.execute();
        stmt.close();

    }

    public void recusarSolicitacao(String email, int trabalho) throws SQLException {

        String sql = "DELETE FROM Solicita_trabalho WHERE emailUsuario = ? "
                + " AND codTrabalho = ? ;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, trabalho);

        stmt.execute();
        stmt.close();

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

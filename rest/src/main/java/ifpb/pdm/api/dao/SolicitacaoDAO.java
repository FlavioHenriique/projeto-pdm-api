package ifpb.pdm.api.dao;

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

    public SolicitacaoDAO() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    public boolean salvar(int trabalho, String email) throws SQLException {

        abrirConexao();

        String sql = "INSERT INTO solicita_trabalho (emailUsuario, codTrabalho,"
                + " estado) VALUES (?,?,?) ;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(2, trabalho);
        stmt.setString(1, email);
        stmt.setString(3, "pendente");
        stmt.execute();
        stmt.close();
        fecharConexao();

        return true;
    }

    public List<Usuario> buscarSolicitacoes(int trabalho) throws SQLException,
            ClassNotFoundException {
        abrirConexao();

        String sql = "SELECT emailUsuario from solicita_trabalho"
                + " WHERE codTrabalho = ? AND estado ilike 'pendente';";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, trabalho);
        ResultSet rs = stmt.executeQuery();

        List<Usuario> lista = new ArrayList<>();
        UsuarioDAO dao;
        try {
            dao = new UsuarioDAO();

            while (rs.next()) {
                Usuario u = dao.buscar(rs.getString("emailUsuario"));
                lista.add(u);
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        fecharConexao();

        return lista;
    }

    public void aceitarSolicitacao(String email, int trabalho) throws SQLException {

        abrirConexao();

        String sql = "UPDATE solicita_trabalho set estado = 'aceita' WHERE"
                + " emailUsuario = ? AND codTrabalho = ? ;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, trabalho);

        stmt.execute();
        stmt.close();
        fecharConexao();
    }

    public void recusarSolicitacao(String email, int trabalho) throws SQLException {

        abrirConexao();

        String sql = "DELETE FROM Solicita_trabalho WHERE emailUsuario = ? "
                + " AND codTrabalho = ? ;";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, trabalho);

        stmt.execute();
        stmt.close();
        fecharConexao();
    }

    private void abrirConexao() {
        try {
            if (conn == null) {
                conn = Conexao.getConnection();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SolicitacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fecharConexao() {

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

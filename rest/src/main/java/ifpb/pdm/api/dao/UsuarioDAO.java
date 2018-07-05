package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private Connection conn;
    private MessageDigest md;

    public UsuarioDAO() throws SQLException, ClassNotFoundException,
            NoSuchAlgorithmException {
        conn = Conexao.getConnection();
        md = MessageDigest.getInstance("MD5");
    }

    public boolean salvar(Usuario u) throws SQLException {

        abrirConexao();

        String sql = "INSERT INTO Usuario (nome,email,senha,cidade,estado)"
                + " VALUES (?,?,?,?,?)";

        md.update(u.getSenha().getBytes(), 0, u.getSenha().length());

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getEmail());
        stmt.setString(3, new BigInteger(1, md.digest()).toString());
        stmt.setString(4, u.getCidade());
        stmt.setString(5, u.getEstado());

        stmt.execute();
        stmt.close();
        fecharConexao();

        return true;

    }

    public Usuario buscar(String email) throws SQLException {

        abrirConexao();

        String sql = "SELECT * FROM Usuario WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Usuario u = new Usuario();
            u.setCidade(rs.getString("cidade"));
            u.setEmail(rs.getString("email"));
            u.setEstado(rs.getString("estado"));
            u.setNome(rs.getString("nome"));
            u.setSenha(rs.getString("senha"));
            stmt.close();

            return u;
        }
        stmt.close();
        fecharConexao();

        return null;
    }

    public boolean login(String email, String senha) throws SQLException {

        abrirConexao();

        md.update(senha.getBytes(), 0, senha.length());
        String senhaMD5 = new BigInteger(1, md.digest()).toString();

        String sql = "SELECT * FROM Usuario WHERE  email = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senhaMD5);

        if (stmt.executeQuery().next()) {
            stmt.close();
            return true;
        }
        stmt.close();
        fecharConexao();

        return false;
    }

    public Usuario atualizar(Usuario u) throws SQLException {

        abrirConexao();

        md.update(u.getSenha().getBytes(), 0, u.getSenha().length());

        String sql = "UPDATE Usuario set nome =?, senha = ?, cidade = ?,"
                + " estado = ? WHERE email = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, u.getNome());
        stmt.setString(2, new BigInteger(1, md.digest()).toString());
        stmt.setString(3, u.getCidade());
        stmt.setString(4, u.getEstado());
        stmt.setString(5, u.getEmail());

        stmt.execute();
        stmt.close();
        fecharConexao();

        return buscar(u.getEmail());
    }

    private void abrirConexao() {
        try {

            conn = Conexao.getConnection();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrabalhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fecharConexao() {

        try {
            if (conn != null & !conn.isClosed()) {
                conn.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

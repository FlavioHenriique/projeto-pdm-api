package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    public boolean salvar(Usuario u) throws SQLException {

        String sql = "INSERT INTO Usuario (nome,email,senha,cidade,estado)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getEmail());
        stmt.setString(3, u.getSenha());
        stmt.setString(4, u.getCidade());
        stmt.setString(5, u.getEstado());

        stmt.execute();
        stmt.close();

        return true;

    }

    public Usuario buscar(String email) throws SQLException {

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
        return null;
    }

    public boolean login(String email, String senha) throws SQLException {

        String sql = "SELECT * FROM Usuario WHERE  email = ? AND senha = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, senha);
        if (stmt.executeQuery().next()) {
            stmt.close();
            return true;
        }
        stmt.close();
        return false;
    }
    
    public Usuario atualizar(Usuario u) throws SQLException{
        
        String sql = "UPDATE Usuario set nome =?, senha = ?, cidade = ?,"
                + " estado = ? WHERE email = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, u.getNome());
        stmt.setString(2, u.getSenha());
        stmt.setString(3, u.getCidade());
        stmt.setString(4, u.getEstado());
        stmt.setString(5, u.getEmail());
        
        stmt.execute();
        stmt.close();
        
        return buscar(u.getEmail());
    }
}

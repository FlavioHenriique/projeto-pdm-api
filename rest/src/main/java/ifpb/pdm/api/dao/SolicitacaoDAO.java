package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoDAO {

    private Connection conn;

    public SolicitacaoDAO() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
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
                + " WHERE codTrabalho = ? ;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, trabalho);
        ResultSet rs = stmt.executeQuery();
        
        List<Usuario> lista = new ArrayList<>();
        UsuarioDAO dao = new UsuarioDAO();
        while(rs.next()){
            Usuario u = dao.buscar(rs.getString("emailUsuario"));
            lista.add(u);
        }
        
        return lista;
    }

}

package ifpb.pdm.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SolicitacaoDAO {

    private Connection conn;
    
    public SolicitacaoDAO() throws SQLException, ClassNotFoundException{
        conn = Conexao.getConnection();
    }
    
    public boolean salvar(int trabalho, String email) throws SQLException{
        
        String sql = "INSERT INTO Solicitacao (emailUsuario, codTrabalho)"
                + " VALUES (?,?) ;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(2, trabalho);
        stmt.setString(1, email);
        
        stmt.execute();
        stmt.close();
        return true;
    }
}

package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Trabalho;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TrabalhoDAO {

    private Connection conn;

    public TrabalhoDAO() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    public boolean cadastrar(Trabalho t) throws SQLException {

        String sql = "INSERT INTO Trabalho (titulo,estado,cidade,valor,horario,"
                + "data,descricao,contratante) values "
                + "(?,?,?,?,?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        stmt.setString(1, t.getTitulo());
        stmt.setString(2, t.getEstado());
        stmt.setString(3, t.getCidade());
        stmt.setFloat(4, t.getValor());
        stmt.setString(5, t.getHorario());
        stmt.setDate(6, Date.valueOf(t.getData()));
        stmt.setString(7, t.getDescricao());
        stmt.setString(8, t.getContratante().getEmail());
        
        stmt.execute();
        stmt.close();

        return true;

    }

}

package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Trabalho;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrabalhoDAO {

    private Connection conn;

    public TrabalhoDAO() throws SQLException, ClassNotFoundException {
        conn = Conexao.getConnection();
    }

    public boolean cadastrar(Trabalho t) throws SQLException {

        String sql = "INSERT INTO Trabalho (titulo,estado,cidade,valor,horario,"
                + "data,descricao,contratante,categoria) values "
                + "(?,?,?,?,?,?,?,?,?);";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, t.getTitulo());
        stmt.setString(2, t.getEstado());
        stmt.setString(3, t.getCidade());
        stmt.setFloat(4, t.getValor());
        stmt.setString(5, t.getHorario());
        stmt.setDate(6, Date.valueOf(t.getData()));
        stmt.setString(7, t.getDescricao());
        stmt.setString(8, t.getContratante().getEmail());
        stmt.setString(9, t.getCategoria());
        stmt.execute();
        stmt.close();

        return true;

    }

    public List<Trabalho> meusTrabalhos(String email) throws SQLException,
            ClassNotFoundException {

        String sql = "SELECT codigo FROM Trabalho WHERE contratante = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        List<Trabalho> lista = new ArrayList<>();

        while (rs.next()) {
            Trabalho t = buscarTrabalho(rs.getInt("codigo"));
            lista.add(t);
        }

        return lista;
    }

    public Trabalho buscarTrabalho(int codigo) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM TRABALHO WHERE CODIGO =? ;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, codigo);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            UsuarioDAO dao = new UsuarioDAO();
            Trabalho t = new Trabalho();
            t.setCidade(rs.getString("cidade"));
            t.setCodigo(rs.getInt("codigo"));
            t.setContratado(dao.buscar(rs.getString("contratado")));
            t.setContratante(dao.buscar(rs.getString("contratante")));
            t.setData(rs.getDate("data").toString());
            t.setDescricao(rs.getString("descricao"));
            t.setCategoria(rs.getString("categoria"));
            t.setEstado(rs.getString("estado"));
            t.setHorario(rs.getString("horario"));
            t.setTitulo(rs.getString("titulo"));
            t.setValor(rs.getFloat("valor"));

            rs.close();
            stmt.close();
            return t;

        }
        return null;

    }

    public List<Trabalho> buscaPorCategoria(String categoria) throws SQLException,
            ClassNotFoundException {

        String sql = "SELECT * FROM Trabalho WHERE categoria = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, categoria);
        ResultSet rs = stmt.executeQuery();

        List<Trabalho> lista = new ArrayList<>();

        while (rs.next()) {
            Trabalho trabalho = buscarTrabalho(rs.getInt("codigo"));
            lista.add(trabalho);
        }
        return lista;
    }

    public List<Trabalho> buscaPorCidade(String cidade) throws SQLException,
            ClassNotFoundException {

        String sql = "SELECT * FROM Trabalho WHERE cidade = ?;";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cidade);
        ResultSet rs = stmt.executeQuery();

        List<Trabalho> lista = new ArrayList<>();

        while (rs.next()) {
            Trabalho trabalho = buscarTrabalho(rs.getInt("codigo"));
            lista.add(trabalho);
        }
        return lista;
    }
    
    
    public void deletar(int trabalho) throws SQLException{
        
        String sql = "DELETE FROM Trabalho WHERE codigo = ?;";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, trabalho);
        stmt.execute();
        stmt.close();
    }
}

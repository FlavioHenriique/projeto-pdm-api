package ifpb.pdm.api.dao;

import ifpb.pdm.api.model.Notificacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificacaoDAO {

    private Connection conn;

    public NotificacaoDAO() {
        try {
            conn = Conexao.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public List<Notificacao> minhasNotificacoes(String email) {

        String sql = "SELECT * FROM NOTIFICACAO WHERE emailDono = ? ;";
        List<Notificacao> notificacoes = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notificacoes.add(
                    new Notificacao(
                        rs.getString("texto"), 
                        rs.getInt("codtrabalho")
                    )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return notificacoes;
    }
}

package ifpb.pdm.api.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String url = "jdbc:postgresql://ec2-54-243-235-153.compute-1.amazonaws.com:5432/d97ic377a2cvph?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String password = "1e1a33499f118bb58765b90abfa9531c085989826d61af0f46b558a75d1c6721";
    private static final String user = "vmgwpjrxuzqnjl";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, user, password);
    }
}

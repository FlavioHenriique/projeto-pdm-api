package ifpb.pdm.api.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {

    public static void main(String[] args) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Scanner scan = new Scanner(System.in);
            System.out.println("digite");
            int opcao = scan.nextInt();
            
            while(opcao != 1){
                System.out.println(dao.buscar("flavio@gmail.com"));
                opcao = scan.nextInt();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

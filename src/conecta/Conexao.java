
package conecta;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Rogério
 */
public class Conexao {
    
     public static Connection conector() {
        java.sql.Connection conexao = null;
        //Carrega o driver
        String driver = "com.mysql.cj.jdbc.Driver";
        //Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/porto_seco";
        String user = "root";
        String password = "";     
        //Conectando com o banco de dados
        try {
            Class.forName(driver);
            conexao =  DriverManager.getConnection(url, user, password);
            return conexao;
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
     
}

package principal.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    String driver = "org.postgresql.Driver";
    final String url = "jdbc:postgresql://localhost:5432/cadastropessoa";
    final String usuario = "postgres";
    final String senha = "root";
    Connection conexao;

    public Connection conexao() throws ClassNotFoundException {

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexao realizada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro de SQL ERRO: " + ex.getMessage());
        }
        return conexao;

    }

}

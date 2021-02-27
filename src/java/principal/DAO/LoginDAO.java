package principal.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import principal.Usuario;

/**
 * Classe para crud de professores
 *
 * @author rodri
 */
public class LoginDAO {
    
    public void salvar(Usuario usuario) throws ClassNotFoundException {
        ConexaoBD cx = new ConexaoBD();
        Connection conexao = cx.conexao();
        PreparedStatement insereSt = null;
        String sql = "insert into login(usuario, senha) values(?,MD5(?));";
        try {
            insereSt = conexao.prepareStatement(sql);
            insereSt.setString(1, usuario.getUsuario());
            insereSt.setString(2, usuario.getSenha());

            insereSt.executeUpdate();

            System.out.println("Usuario cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao incluir usuario. Mensagem: "
                    + e.getMessage());
        } finally {
            try {
                insereSt.close();
                conexao.close();
            } catch (Throwable e) {
                System.out
                        .println("Erro ao fechar operações de inserção. Mensagem: "
                                + e.getMessage());
            }
        }
    }

    public void logar(Usuario usuario) throws ClassNotFoundException {
        ConexaoBD conexao = new ConexaoBD();
        Connection conn = conexao.conexao();
        String SQL = "SELECT * FROM login WHERE usuario = ? AND senha = ?;";
        PreparedStatement loginSt = null;
        ResultSet rs = null;

        boolean status = false;

        try {
            System.out.println("Usuario DAO: " + usuario.getUsuario());
            System.out.println("Senha DAO: " + usuario.getSenha());
            loginSt = conn.prepareStatement(SQL);
            loginSt.setString(1, usuario.getUsuario());
            loginSt.setString(2, usuario.getSenha());

            rs = loginSt.executeQuery();

            if (rs.next()) {
                status = true;
            }

        } catch (SQLException ex) {
            System.out.println("Erro de SQL: " + ex.getMessage());
        } finally {
            try {
                rs.close();
                loginSt.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao encerrar a conexão: " + ex.getMessage());
            }

        }
    }
    
    public boolean loga(Usuario usuario) throws ClassNotFoundException {
        ConexaoBD conexao = new ConexaoBD();
        Connection conn = conexao.conexao();
        String SQL = "SELECT * FROM login WHERE usuario = ? AND senha = ?;";
        PreparedStatement loginSt = null;
        ResultSet rs = null;

        boolean status = false;

        try {
            loginSt = conn.prepareStatement(SQL);
            loginSt.setString(1, usuario.getUsuario());
            loginSt.setString(2, usuario.getSenha());

            rs = loginSt.executeQuery();

            if (rs.next()) {
                status = true;
            }

        } catch (SQLException ex) {
            System.out.println("Erro de SQL: " + ex.getMessage());
        } finally {
            try {
                rs.close();
                loginSt.close();
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao encerrar a conexão: " + ex.getMessage());
            }

        }return status;
    }

}

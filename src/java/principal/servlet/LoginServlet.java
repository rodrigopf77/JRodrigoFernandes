package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.Criptografia;
import principal.Pessoa;
import principal.Criptografia;

/**
 *
 * @author rodri
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    
    public boolean statusSessao = true;
    
    public HttpSession sessao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Status: " + statusSessao);

        try {

            PrintWriter out = response.getWriter();

            // Sair,
            if (request.getParameter("msg") != null) {
                if (request.getParameter("msg").equals("logoff")) {
                    HttpSession sessao = request.getSession();
                    sessao.invalidate();
                    out.println("<span style='color: red'>Deslogado com sucesso.!</span>");
                    response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet");
                }
            }

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Autenticação</h1>");
            out.println("<hr/>");

            if (request.getParameter("msg") != null) {
                if (request.getParameter("msg").equals("error")) {
                    out.println("<span style='color: red'>Login e/ou senhas incorreto!</span>");
                }
            }

            out.println("<form method='POST'>");
            out.println("Login:<br> <input type='text' name='txtNome' >");
            out.println("<br>");
            out.println("Senha:<br> <input type='password' name='txtSenha'>");
            out.println("<br>");
            out.println("<br>");
            out.println("<input type='submit' value='Logar'>");
            out.println("</form>");
            out.println("<br>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String nome = request.getParameter("txtNome");
        String senha = Criptografia.criptografar(request.getParameter("txtSenha"));

        if (nome.trim().length() < 4) {

            out.println("Preencha o campo nome!");

        } else if (senha.trim().length() < 4) {
            out.println("Informe sua senha");
        } else {

            try {
                Class.forName("org.postgresql.Driver");

                try {
                    Connection conn = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/cadastropessoa", "postgres", "root");

                    String SQL = "SELECT * FROM pessoa WHERE nome = ? and senha = ?";

                    PreparedStatement pstm = conn.prepareStatement(SQL);

                    pstm.setString(1, nome);
                    pstm.setString(2, senha);
                    /*21232F297A57A5A743894A0E4A801FC3*/

                    ResultSet rs = pstm.executeQuery();

                    if (rs.next()) {
                        System.out.println("Senha capturada: " + senha);
                        System.out.println("Senha banco: " + rs.getString("senha"));
                        if (nome.equals(rs.getString("nome"))) {
                            if (senha.equals(rs.getString("senha"))) {
                                statusSessao = true;
                                System.out.println("Status: " + statusSessao);
                                pstm.close();
                                conn.close();
                                sessao = request.getSession();
                                sessao.setAttribute("login", nome);
                                sessao.setAttribute("senha", senha);
                                sessao.setAttribute("info", request.getRemoteAddr());
                                response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/ListarClientes");
                            } else {
                                
                                pstm.close();
                                conn.close();
                                System.out.println("usuario nao existe");
                                response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet?msg=error");

                            }

                        }

                    }

                } catch (SQLException e) {
                    System.out.println("Erro: " + e.getMessage());
                    out.println("Problema no banco de dados: " + e.getMessage());
                }

            } catch (ClassNotFoundException ex) {
                out.println("Problema ao carregar o driver de conexão!");
            }

        }
    }
}

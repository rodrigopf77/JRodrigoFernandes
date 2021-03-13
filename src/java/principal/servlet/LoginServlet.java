package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.Criptografia;
import principal.DAO.LoginDAO;
import principal.Usuario;

/**
 *
 * @author rodri
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            out.println("Login:<br> <input type='text' name='txtUsuario' >");
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

        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getParameter("txtUsuario"));
        usuario.setSenha(Criptografia.criptografar(request.getParameter("txtSenha")));

        if (usuario.getUsuario().trim().length() < 4) {

            out.println("Preencha o campo usuário!");

        } else if (usuario.getSenha().trim().length() < 4) {
            out.println("Informe sua senha");
        } else {

            try {

                LoginDAO dao = new LoginDAO();

                if (dao.loga(usuario)) {

                    HttpSession sessao = request.getSession();
                    sessao.setAttribute("login", usuario);
                    sessao.setAttribute("info", request.getRemoteAddr());
                    response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/ListarClientes");

                } else {
                    response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet?msg=error");
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

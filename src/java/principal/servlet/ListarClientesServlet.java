package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.seguranca.Autorizacao;

/**
 *
 * @author rodri
 */
@WebServlet(name = "ListarClientesServlet", urlPatterns = {"/ListarClientes"})
public class ListarClientesServlet extends HttpServlet {

    LoginServlet lg = new LoginServlet();
    public boolean status = lg.statusSessao;
    
    FilterChain chain;
    
    Autorizacao au = new Autorizacao();
    
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Status: " + lg.statusSessao);

        PrintWriter out = response.getWriter();

        HttpSession sessao = request.getSession();

//        chain.doFilter(request, response);
        if (sessao.getAttribute("login") == null) {
            response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet");
        }

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cadastropessoa", "postgres", "root");

            // Deleta o registro
            // Quando exisitir uma QUERY String ID
            if (request.getParameter("idPessoa") != null) {
                int ID = Integer.parseInt(request.getParameter("idPessoa"));
                String SQLDelete = "DELETE FROM pessoa WHERE idPessoa = ?";
                PreparedStatement pstm = conn.prepareStatement(SQLDelete);
                pstm.setInt(1, ID);
                pstm.execute();
            }

            String SQL = "SELECT * FROM pessoa ORDER BY nome";

            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(SQL);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Pessoas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Pessoas</h1>");
            out.println("<hr>");
            out.println("<form method='POST'>");
            out.println("<table width='100%'>");
            out.println("<tr bgcolor='#32CD32'>");
            out.println("<td>ID</td>");
            out.println("<td>Nome</td>");
            out.println("<td>Sexo</td>");
            out.println("<td>E-mail</td>");
            out.println("<td>Data de Nascimento</td>");
            out.println("<td>Naturalidade</td>");
            out.println("<td>Nacionalidade</td>");
            out.println("<td>Data Registro</td>");
            out.println("<td>Data Atualização</td>");
            out.println("<td>CPF</td>");
            out.println("<td>Editar</td>");
            out.println("<td>Apagar</td>");
            out.println("</tr>");
            while (rs.next()) {

                String dNascimento = sdf.format(rs.getDate("dtNascimento"));
                String dRegistro = sdf.format(rs.getDate("dataRegistro"));
                String dAtualizacao = sdf.format(rs.getDate("dataAtualizacao"));

                out.println("<tr>");
                out.println("<td>" + rs.getInt("idPessoa") + "</td>");
                out.println("<td>" + rs.getString("nome") + "</td>");
                out.println("<td>" + rs.getString("sexo") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + dNascimento + "</td>");
                out.println("<td>" + rs.getString("naturalidade") + "</td>");
                out.println("<td>" + rs.getString("nacionalidade") + "</td>");
                out.println("<td>" + dRegistro + "</td>");
                out.println("<td>" + dAtualizacao + "</td>");
                out.println("<td>" + rs.getString("cpf") + "</td>");
                out.println("<td><a href='http://localhost:8080/JavaRodrigoFernandes/EditarClientes?idPessoa=" + rs.getInt("idPessoa") + "'>[EDITAR]</a></td>");
                out.println("<td><a href='http://localhost:8080/JavaRodrigoFernandes/ListarClientes?idPessoa=" + rs.getInt("idPessoa") + "'>[APAGAR]</a></td>");
                out.println("</tr>");
            }
            status = false;
            System.out.println("Status: " + lg.statusSessao);
            out.println("</table>");
            out.println("<hr>");
            out.println("</body>");
            out.println("</html>");
            out.println("<td><a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/CadastrarPessoa'>[CADASTRAR]</a></td>");
            out.println("   |   ");

            out.println("<input type='submit' value='sair'>");
        } catch (SQLException ex) {
            Logger.getLogger(ListarClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListarClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        lg.statusSessao = false;
        System.out.println("Status: " + lg.statusSessao);
        
        au.status = false;
        
        au.doFilter(request, response, chain);
        response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet");
    }

}

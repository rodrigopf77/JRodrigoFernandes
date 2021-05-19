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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rodri
 */
@WebServlet(name = "ListarClientesServlet", urlPatterns = {"/ListarClientes"})
public class ListarClientesServlet extends HttpServlet {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    HttpSession sessao = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        this.sessao = request.getSession();

        if (this.sessao.getAttribute("login") == null) {
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

            String SQL = "SELECT * FROM pessoa";

            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery(SQL);

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Pessoas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1 text-align='center' >Lista de Pessoas</h1>");
            out.println("<table width='100%'>");
            out.println("<tr bgcolor='#32CD32'>");
            out.println("<td style= 'text-align: center' ><strong>ID</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Nome</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Usuario</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Sexo</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>E-mail</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Data de Nascimento</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Naturalidade</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Nacionalidade</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Data de Registro</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Data de Atualização</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>CPF</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Editar</strong></td>");
            out.println("<td style= 'text-align: center' ><strong>Apagar</strong></td>");
            out.println("</tr>");
            
            while (rs.next()) {

                String dNascimento = sdf.format(rs.getDate("dtNascimento"));
                String dRegistro = sdf.format(rs.getDate("dataRegistro"));
                String dAtualizacao = sdf.format(rs.getDate("dataAtualizacao"));

                out.println("<tr>");
                out.println("<td>" + rs.getInt("idPessoa") + "</td>");
                out.println("<td>" + rs.getString("nome") + "</td>");
                out.println("<td>" + rs.getString("usuario") + "</td>");
                out.println("<td>" + rs.getString("sexo") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td style= 'text-align: center' >" + dNascimento + "</td>");
                out.println("<td>" + rs.getString("naturalidade") + "</td>");
                out.println("<td>" + rs.getString("nacionalidade") + "</td>");
                out.println("<td style= 'text-align: center' >" + dRegistro + "</td>");
                out.println("<td style= 'text-align: center' >" + dAtualizacao + "</td>");
                out.println("<td>" + rs.getString("cpf") + "</td>");
                out.println("<td><a href='http://localhost:8080/JavaRodrigoFernandes/EditarClientes?idPessoa=" + rs.getInt("idPessoa") + "'>[EDITAR]</a></td>");
                out.println("<td><a href='http://localhost:8080/JavaRodrigoFernandes/ListarClientes?idPessoa=" + rs.getInt("idPessoa") + "'>[APAGAR]</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<hr>");
            out.println("</body>");
            out.println("</html>");
            out.println("<td><a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/CadastrarPessoa'>[CADASTRAR]</a></td>");
            out.println("   |   ");
            out.println("<a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/LoginServlet?msg=logoff'>[SAIR]</a>");
        } catch (SQLException ex) {
            Logger.getLogger(ListarClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListarClientesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rodri
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/IndexServlet"})
public class IndexServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Api de Cadastro de Pessoas</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Cadastro de Pessoas</h1>");
			out.println("<hr/>");
			out.println("<a href='http://localhost:8080/Chamados/NovoChamado'>Novo Chamado</a>");
			out.println("<br>");
			out.println("<a href='http://localhost:8080/Chamados/ListarChamados'>Listar Chamados</a>");
			out.println("<br>");
			out.println("<a href='/Logoff'>Sair</a>");
			out.println("</body>");
			out.println("</html>");
		} catch (IOException e) {
			
		}

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}

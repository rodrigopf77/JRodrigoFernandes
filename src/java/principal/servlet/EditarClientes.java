package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.DAO.PessoaDAO;
import principal.Pessoa;

/**
 *
 * @author rodri
 */
@WebServlet(name = "EditarClientes", urlPatterns = {"/EditarClientes"})
public class EditarClientes extends HttpServlet {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        Pessoa p = null;
        
        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("login") == null) {
            response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet");
        }

        try {

            if (request.getParameter("idPessoa") != null) {
                int ID = Integer.parseInt(request.getParameter("idPessoa"));

                PessoaDAO dao = new PessoaDAO();
                p = dao.buscaPessoa(ID);

                String dNascimento = sdf.format(p.getDtNascimento());

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Editar Pessoa</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Atualize suas Informações</h1>");
                out.println("<hr/>");
                out.println("<form method='POST'>");
                out.println("Código:<br> <input type='text' name='txtID' readonly='readonly' value='" + p.getIdPessoa() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("Nome:<br> <input type='text' name='txtNome' value='" + p.getNome() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("Sexo:<br> <input type='text' name='txtSexo' value='" + p.getSexo() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("E-mail.:<br> <input type='email' name='txtEmail'  value='" + p.getEmail() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("Data de Nascimento:<br> <input type='text' name='txtData'  value='" + dNascimento + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("Naturalidade:<br> <input type='text' name='txtNaturalidade'  value='" + p.getNaturalidade() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("Nacionalidade:<br> <input type='text' name='txtNacionalidade'  value='" + p.getNacionalidade() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("CPF:<br> <input type='text' name='txtCpf'  value='" + p.getCpf() + "'>");
                out.println("<br>");
                out.println("<br>");
                out.println("<input type='submit' value='Salvar'>");
                out.println("</form>");
                out.println("<a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/ListarClientes'>[MOSTRAR PESSOAS]</a>");
                out.println("   |   ");
                out.println("</body>");
                out.println("</html>");
                out.println("<a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/LoginServlet?msg=logoffs'>[SAIR]</a>");

            } else {
                out.println("Este registro não existe!");
            }

        } catch (ClassNotFoundException ex) {
            out.println("Problema ao carregar o driver de conexão!");
        } catch (ParseException ex) {
            Logger.getLogger(EditarClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        int codigo = Integer.parseInt(request.getParameter("txtID"));
        String nome = request.getParameter("txtNome");
        String sexo = request.getParameter("txtSexo");
        String email = request.getParameter("txtEmail");
        String data = request.getParameter("txtData");
        String naturalidade = request.getParameter("txtNaturalidade");
        String nacionalidade = request.getParameter("txtNacionalidade");
        String cpf = request.getParameter("txtCpf");

        if (nome.trim().length() < 4) {
            out.println("Preencha o campo nome!");

        } else if (data.trim().length() < 9) {
            out.println("Preencha a data no formato 00/00/0000!");

        } else if (cpf.trim().length() < 11) {
            out.println("Preencha um CPF válido, com no mínimo 11 digítos (Apenas números)!");

        } else if (email.trim().length() < 7) {
            System.out.println("Preencha um e-mail válido!");

        } else {
            try {

                Pessoa p = new Pessoa();
                p.atualizar(codigo, nome, sexo, email, data, naturalidade, nacionalidade, cpf);

                System.out.println("Dados Atualizados com sucesso!");
                response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/ListarClientes");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CadastrarPessoa.class.getName()).log(Level.SEVERE, null, ex);

            } catch (SQLException ex) {
                Logger.getLogger(EditarClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}

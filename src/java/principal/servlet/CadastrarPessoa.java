package principal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.Pessoa;

@WebServlet(name = "CadastrarPessoa", urlPatterns = {"/CadastrarPessoa"})
public class CadastrarPessoa extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();

        if (sessao.getAttribute("login") == null) {
            response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/LoginServlet");
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cadastro</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Cadastrar Pessoa</h1>");
            out.println("<hr/>");
            out.println("<form method='POST'>");
            out.println("Nome:<br> <input type='text' name='txtNome'><br><br>");
            out.println("Usuario:<br> <input type='text' name='txtUsuario'>");
            out.println("<br><br>Senha:<br> <input type='password' name='txtSenha'>");
            out.println("<br><br>Sexo:<br>  <input type='text' name='txtSexo'>");
            out.println("<br><br>E-mail:<br> <input type='email' name='txtEmail'>");
            out.println("<br><br>Data de Nascimento:<br> <input type='text' name='txtData'></textarea>");
            out.println("<br><br>Naturalidade:<br> <input type='text' name='txtNaturalidade'></textarea>");
            out.println("<br><br>Nacionalidade:<br> <input type='text' name='txtNacionalidade'></textarea>");
            out.println("<br><br>CPF:<br> <input type='txt' name='txtCpf'></textarea>");
            out.println("<br><br>");
            out.println("<input type='submit' value='Cadastrar'>");
            out.println("</form>");
//            out.println("<br>");
            out.println("<a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/ListarClientes'>[MOSTRAR PESSOAS]</a>");
            out.println("   |   ");
            out.println("</body>");
            out.println("</html>");
            out.println("<a style='color: red' href='http://localhost:8080/JavaRodrigoFernandes/LoginServlet?msg=logoff'>[SAIR]</a>");
        } catch (IOException e) {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        String nome = request.getParameter("txtNome");
        String usuario = request.getParameter("txtUsuario");
        String senha = request.getParameter("txtSenha");
        String sexo = request.getParameter("txtSexo");
        String email = request.getParameter("txtEmail");
        String dataNascimento = request.getParameter("txtData");
        String naturalidade = request.getParameter("txtNaturalidade");
        String nacionalidade = request.getParameter("txtNacionalidade");
        String cpf = request.getParameter("txtCpf");

        if (nome.trim().length() < 4) {
            out.println("Preencha o campo nome!");

        } else if (dataNascimento.trim().length() < 9) {
            out.println("Preencha a data no formato 00/00/0000!");

        } else if (cpf.trim().length() < 11) {
            out.println("Preencha um CPF válido, com no mínimo 11 digítos (Apenas números)!");

        } else if (email.trim().length() < 7) {
            System.out.println("Preencha um e-mail válido!");

        } else {
            try {

                Pessoa p = new Pessoa(nome, usuario, senha, sexo, email, dataNascimento, naturalidade, nacionalidade, cpf);
                System.out.println("Registro inserido com sucesso!");
                response.sendRedirect("http://localhost:8080/JavaRodrigoFernandes/ListarClientes");
                out.println("Registro inserido com sucesso!");

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CadastrarPessoa.class.getName()).log(Level.SEVERE, null, ex);

            } catch (SQLException ex) {
                Logger.getLogger(CadastrarPessoa.class.getName()).log(Level.SEVERE, null, ex);

            }
        }

        out.println(dataNascimento);

    }
}

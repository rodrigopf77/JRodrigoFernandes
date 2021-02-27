package principal.seguranca;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import principal.servlet.LoginServlet;

/**
 *
 * @author rodri
 */
@WebFilter("/*")
public class Autorizacao implements Filter {
    
    LoginServlet lg = new LoginServlet();
//    public boolean status = lg.statusSessao;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        System.out.println("AutorizacaoFilter");

//        HttpSession sessao = request.getSession();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String paramAcao = request.getParameter("acao");

        HttpSession sessao = request.getSession();
        //Exemplo
        boolean verificaSessao = sessao != null;
//        boolean usuarioNaoEstaLogado = (sessao.getAttribute("usuarioLogado") == null);
//        boolean loggedIn = sessao != null && sessao.getAttribute("login") != null;

        if (verificaSessao /*&& status*/) {
            chain.doFilter(request, response);
//            response.sendRedirect("/ListarClientes");
            
        }else{
            response.sendRedirect("/LoginServlet");
        }

//        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}

package principal;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import principal.DAO.PessoaDAO;

/**
 *
 * @author rodri
 */
public class Programa {
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Scanner sc = new Scanner(System.in);
        
        Pessoa p = new Pessoa();
        
        PessoaDAO dao = new PessoaDAO();
        
        System.out.println("Informe o código: ");
        dao.listar();
        
        
    }
    
}

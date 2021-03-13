package principal;

import java.sql.SQLException;
import java.text.ParseException;
import principal.DAO.LoginDAO;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import principal.DAO.PessoaDAO;

public class Programa {

    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        //Criar usuário para manipular valores
        Pessoa pessoa = new Pessoa();
        
        System.out.println("Nome: ");
        pessoa.setNome(sc.nextLine());
        
        System.out.println("Usuario: ");
        pessoa.setUsuario(sc.nextLine());
        
        System.out.println("Senha: ");
        pessoa.setSenha(Criptografia.criptografar(sc.nextLine()));
        System.out.println("Exibir senha criptografada: " + pessoa.getSenha());
        
        pessoa.setSexo("Masculino");
        
        pessoa.setEmail("adm@adm.com");
        
        pessoa.setdNascimento(pessoa.converterData("09/05/1976"));
        
        pessoa.setNaturalidade("Rio de Janeiro");
        
        pessoa.setNacionalidade("Brasileiro");
        
        pessoa.setCpf("00000000000");
        
        
        PessoaDAO salvar = new PessoaDAO();
        try {
            salvar.salvarRegistro(pessoa);
            
            
//        System.out.println("Usuario cadastrado: " + usuario.getUsuario());
//        System.out.println("Senha cadastrada: " + usuario.getSenha());
//        System.out.println("Senha criptografada: " + Criptografia.criptografar(usuario.getSenha()));
//        
//        System.out.println("senha: " + usuario.getSenha());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        

    }
    
}

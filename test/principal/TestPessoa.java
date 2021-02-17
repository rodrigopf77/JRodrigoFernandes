package principal;

import java.sql.SQLException;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class TestPessoa {
    
    /*
        Esse teste se a conversão da data foi realizada corretamente para
        a inserção no banco de dados
    */
    @Test
    public void validaData() throws ClassNotFoundException, SQLException{
        //Preparando ambiente de teste
        Pessoa p = new Pessoa();
                
        //Execução
        LocalDate lDate = p.converterData("09/05/1976");
        
        //Validação
        Assert.assertEquals("1976-05-09", lDate);
        
    }
    
    /*
        Esse teste valida se o campo nome foi preenchido com no mínimo 7
        Caracteres.
    */
    @Test
    public void validaNome() throws ClassNotFoundException, SQLException{
        //Preparando ambiente de teste
        Pessoa p = new Pessoa();
                
        //Execução
        p.setNome("Maria Fernandes");
        
        //Validação
        Assert.assertTrue(p.getNome().trim().length() >= 7);
        
    }
    
}

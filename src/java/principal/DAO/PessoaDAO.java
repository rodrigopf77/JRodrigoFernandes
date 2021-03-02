package principal.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import principal.Pessoa;

public class PessoaDAO {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    public void salvarRegistro(Pessoa p) throws ClassNotFoundException, SQLException, ParseException {

        ConexaoBD conexao = new ConexaoBD();
        Connection conex = conexao.conexao();
        PreparedStatement insereSt = null;

        LocalDate lDate = LocalDate.now();

        String sql = "INSERT INTO pessoa(nome, senha, sexo, email, dtnascimento, naturalidade, nacionalidade, cpf, dataRegistro, dataAtualizacao)"
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            
            System.out.println("Nome: " + p.getNome());
            System.out.println("Nome: " + p.getSexo());
            
            System.out.println("Nome: " + p.getEmail());
            System.out.println("Nome: " + p.getdNascimento());
            System.out.println("Nome: " + p.getNaturalidade());
            System.out.println("Nome: " + p.getNacionalidade());
            System.out.println("Nome: " + p.getCpf());
            
            insereSt = conex.prepareStatement(sql);
            insereSt.setString(1, p.getNome());
            insereSt.setString(2, p.getSenha());
            insereSt.setString(3, p.getSexo());
            insereSt.setString(4, p.getEmail());
            insereSt.setDate(5, Date.valueOf(p.getdNascimento()));
            insereSt.setString(6, p.getNaturalidade());
            insereSt.setString(7, p.getNacionalidade());
            insereSt.setString(8, p.getCpf());
            insereSt.setDate(9, Date.valueOf(lDate));
            insereSt.setDate(10, Date.valueOf(lDate));

            insereSt.executeUpdate();
            System.out.println("Pessoa inserida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir registro. Mensagem: " + e.getMessage());
        } finally {
            try {
                insereSt.close();
                conex.close();
                System.out.println("Conexão encerrada com sucesso!");
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de inserção. Mensagem: "
                        + e.getMessage());
            }
        }

    }

    public void atualizar(Pessoa p) throws ClassNotFoundException {
        ConexaoBD conexao = new ConexaoBD();
        Connection conex = conexao.conexao();
        PreparedStatement atualizaSt = null;

        LocalDate lDate = LocalDate.now();

        String sql = "update pessoa set nome = ?, sexo = ?, email = ?, dtNascimento = ?, naturalidade = ?,"
                + "nacionalidade = ?, cpf = ?, dataAtualizacao = ?"
                + "where idpessoa = ?";
        
        System.out.println("Código: " + p.getIdPessoa());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Nascimento: " + p.getdNascimento());

        try {
            atualizaSt = conex.prepareStatement(sql);
            atualizaSt.setString(1, p.getNome());
            atualizaSt.setString(2, p.getSexo());
            atualizaSt.setString(3, p.getEmail());
            atualizaSt.setDate(4, Date.valueOf(p.getdNascimento()));
            atualizaSt.setString(5, p.getNaturalidade());
            atualizaSt.setString(6, p.getNacionalidade());
            atualizaSt.setString(7, p.getCpf());
            atualizaSt.setDate(8, Date.valueOf(lDate));
            atualizaSt.setInt(9, p.getIdPessoa());

            atualizaSt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados. Mensagem: " + e.getMessage());
        } finally {
            try {
                atualizaSt.close();
                conex.close();
            } catch (Throwable e) {
                System.out
                        .println("Erro ao fechar operações de atualização. Mensagem: "
                                + e.getMessage());
            }
        }
    }

    public List<Pessoa> listar() throws ClassNotFoundException, SQLException, ParseException {

        ConexaoBD conexao = new ConexaoBD();
        Connection conex = conexao.conexao();
        Statement consulta = null;
        ResultSet resultado = null;

        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        Pessoa pessoa = null;
//        Pessoa p = new Pessoa();

        String sql = "select * from pessoa";
        try {
            consulta = conex.createStatement();
            resultado = consulta.executeQuery(sql);
            while (resultado.next()) {

//                pessoa = new Pessoa();

                pessoa.setIdPessoa((resultado.getInt("idPessoa")));
                pessoa.setSexo(resultado.getString("sexo"));
                pessoa.setEmail(resultado.getString("email"));

                pessoa.setDtNascimento((resultado.getDate("dtNascimento")));
                pessoa.setSexo(resultado.getString("naturalidade"));
                pessoa.setNacionalidade(resultado.getString("nacionalidade"));

                pessoa.setCpf(resultado.getString("cpf"));
                pessoa.setDataCadastro(resultado.getDate("dataRegistro"));
                pessoa.setDataAtualizacao(resultado.getDate("dataAtualização"));

                pessoas.add(pessoa);

            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar registro. Mensagem: "
                    + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conex.close();
            } catch (Throwable e) {
                System.out
                        .println("Erro ao fechar operações de consulta. Mensagem: "
                                + e.getMessage());
            }
        }

        return pessoas;
    }

    public Pessoa buscaPessoa(int id) throws ClassNotFoundException, ParseException {
        ConexaoBD conexao = new ConexaoBD();
        Connection conex = conexao.conexao();
        PreparedStatement consulta = null;
        ResultSet resultado = null;
        Pessoa pessoa = null;

        String sql = "select * from pessoa where idPessoa = ?";

        try {
            consulta = conex.prepareStatement(sql);
            consulta.setInt(1, id);
            resultado = consulta.executeQuery();

            if (resultado.next()) {
                pessoa = new Pessoa();
                System.out.println("teste");
                pessoa.setIdPessoa(resultado.getInt("idPessoa"));
                System.out.println(pessoa.getIdPessoa());
                
                pessoa.setNome(resultado.getString("nome"));
                System.out.println(pessoa.getNome());
                
                pessoa.setSexo(resultado.getString("sexo"));
                System.out.println(pessoa.getSexo());
                
                pessoa.setEmail(resultado.getString("email"));
                System.out.println(pessoa.getEmail());
                
                pessoa.setDtNascimento(resultado.getDate("dtNascimento"));
                System.out.println(pessoa.getDtNascimento());
                
                pessoa.setNaturalidade(resultado.getString("naturalidade"));
                System.out.println(pessoa.getNaturalidade());
                
                pessoa.setNacionalidade(resultado.getString("nacionalidade"));
                System.out.println(pessoa.getNacionalidade());
                
                pessoa.setCpf(resultado.getString("cpf"));
                System.out.println(pessoa.getCpf());
                

            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pessoa. Mensagem: "
                    + e.getMessage());
        } finally {
            try {
                consulta.close();
                resultado.close();
                conex.close();
            } catch (Throwable e) {
                System.out.println("Erro ao fechar operações de consulta. Mensagem: "
                        + e.getMessage());
            }
        }
        return pessoa;
    }

    public void excluir(int id) throws ClassNotFoundException {
        ConexaoBD conexao = new ConexaoBD();
        Connection conex = conexao.conexao();
        PreparedStatement excluiSt = null;

        String sql = "delete from pessoa where idPessoa = ?";

        try {
            excluiSt = conex.prepareStatement(sql);
            excluiSt.setInt(1, id);
            excluiSt.executeUpdate();

            System.out.println("Pessoa excluida com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir registro. Mensagem: "
                    + e.getMessage());
        } finally {
            try {
                excluiSt.close();
                conex.close();
            } catch (Throwable e) {
                System.out
                        .println("Erro ao fechar operações de exclusão. Mensagem: "
                                + e.getMessage());
            }
        }
    }

}
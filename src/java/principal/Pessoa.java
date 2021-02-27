package principal;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import principal.DAO.PessoaDAO;


public class Pessoa {
    
    private int idPessoa;
    private String nome;
    private String usuario;
    private String senha;
    private String sexo;
    private String email;
    private LocalDate dNascimento;
    private Date dtNascimento;
    private Date dataCadastro;
    private Date dataAtualizacao;
    private String data; 
    private String naturalidade;
    private String nacionalidade;
    private String cpf;
    private PessoaDAO dao;
    
    Scanner sc = new Scanner(System.in);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Pessoa() {
    }
    
    public Pessoa(String nome, String senha, String sexo, String email, String data, String naturalidade, String nacionalidade, String cpf) throws ClassNotFoundException, SQLException {
        this.nome = nome;
        this.senha = Criptografia.criptografar(senha);
        this.sexo = sexo;
        this.email = email;
        
        this.dNascimento = this.converterData(data);
        
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.cpf = cpf;
        
        PessoaDAO dao = new PessoaDAO();
        
        try {
            dao.salvarRegistro(this);
        } catch (ParseException ex) {
            Logger.getLogger(Pessoa.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
    }
    
    public LocalDate converterData(String data){
        
        String[] separandoData = data.split("/");
        return LocalDate.of(Integer.parseInt(separandoData[2]), Integer.parseInt(separandoData[1]), Integer.parseInt(separandoData[0]));
        
    }
    
    public  void atualizar(int idPessoa, String nome, String sexo, String email, String data, String naturalidade, String nacionalidade, String cpf) throws ClassNotFoundException, SQLException{
        
        this.idPessoa = idPessoa;
        this.nome = nome;
        this.sexo = sexo;
        this.email = email;
        
        String[] separandoData = data.split("/");
        this.dNascimento = LocalDate.of(Integer.parseInt(separandoData[2]), Integer.parseInt(separandoData[1]), Integer.parseInt(separandoData[0]));
        
        
        this.naturalidade = naturalidade;
        this.nacionalidade = nacionalidade;
        this.cpf = cpf;
        
        PessoaDAO dao = new PessoaDAO();
        
        dao.atualizar(this);
                
    }

    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getdNascimento() {
        return dNascimento;
    }

    public void setdNascimento(LocalDate dNascimento) {
        this.dNascimento = dNascimento;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    
    
}

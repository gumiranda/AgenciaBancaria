package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente implements Serializable {

    private int cdCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private LocalDate data_de_nascimento;
    private Funcionario funcionario;
    
    public Cliente(){
    }
    
    public Cliente(int cdCliente, String nome, String cpf) {
        this.cdCliente = cdCliente;
        this.nome = nome;
        this.cpf = cpf;
    }
     public Cliente(int cdCliente, String nome, String cpf,String endereco,String cidade,LocalDate data_de_nascimento,String estado) {
        this.cdCliente = cdCliente;
        this.nome = nome;
        this.cpf = cpf;
         this.cidade = cidade;
        this.data_de_nascimento = data_de_nascimento;
        this.estado = estado;
    }

    public int getCdCliente() {
        return cdCliente;
    }

    public void setCdCliente(int cdCliente) {
        this.cdCliente = cdCliente;
    }

    public String getNome() {
        return nome;
    }
    public String getCidade() {
        return cidade;
    }public String getEndereco() {
        return endereco;
    }public String getEstado() {
        return estado;
    }
    public LocalDate getData_de_nascimento() {
        return data_de_nascimento;
    }
  public void setCidade(String nome) {
        this.cidade = nome;
    }  public void setEstado(String nome) {
        this.estado = nome;
    }  public void setData_de_nascimento(LocalDate nome) {
        this.data_de_nascimento = nome;
    }  public void setEndereco(String nome) {
        this.endereco = nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    @Override
    public String toString() {
        return this.nome;
    }
    
}

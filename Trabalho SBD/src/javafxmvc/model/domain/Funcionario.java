package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Funcionario implements Serializable {

    private int cdFuncionario;
    private int tempo_de_servico;
    private String nome;
    private LocalDate data_de_admissao;
    private String telefone;
    private Agencia agencia;
    private Funcionario supervisor;
    public Funcionario getSupervisor(){
        return supervisor;
    }
    public void setSupervisor(Funcionario f){
        supervisor = f;
    }
    public Agencia getAgencia(){
        return agencia;
    }
    public void setAgencia(Agencia a){
        agencia = a;
    }
    
    public Funcionario(){
    }

    public Funcionario(Agencia agencia,int cdFuncionario, String nome,int tempo_de_servico,LocalDate data_de_admissao,String telefone) {
       this.agencia = agencia;
        this.cdFuncionario = cdFuncionario;
        this.nome= nome;
        this.tempo_de_servico = tempo_de_servico;
        this.telefone= telefone;
        this.data_de_admissao = data_de_admissao;
    }

    public int getCdFuncionario() {
        return cdFuncionario;
    }

    public void setCdFuncionario(int cdFuncionario) {
        this.cdFuncionario = cdFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
        public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String nome) {
        this.telefone = nome;
    }    public int getTempoServico() {
        return tempo_de_servico;
    }
 public LocalDate getData() {
        return data_de_admissao;
    }

    public void setData(LocalDate data) {
        this.data_de_admissao = data;
    }
    public void setTempoServico(int nome) {
        this.tempo_de_servico = nome;
    }
    @Override
    public String toString() {
        return this.nome;
    }
    
}

package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ContaCorrente implements Serializable {

    private int cdConta;
    private double saldo;
    private LocalDate ultimo_acesso;
    private LocalDate data_de_criacao;
    private Agencia a;
    public double getSaldo(){
        return saldo;
    }
    public void setSaldo(double saldo){
        this.saldo = saldo;
    }
     public LocalDate getUltimoAcesso() {
        return ultimo_acesso;
    }

    public void setUltimoAcesso(LocalDate data) {
        this.ultimo_acesso = data;
    }
     public LocalDate getDataCriacao() {
        return data_de_criacao;
    }

    public void setDataCriacao(LocalDate data) {
        this.data_de_criacao = data;
    }
    public ContaCorrente(){
    }
    public int getAgencia_codigo(){
        return this.a.getCdAgencia();
    }
    public Agencia getAgencia(){
        return a;
    }
    public void setAgencia(Agencia a){
        this.a = a;
    }

    public ContaCorrente(int cdConta,double saldo,LocalDate ultimo_a,Agencia ag,LocalDate datacriacao) {
        this.cdConta = cdConta;
        a = ag;
        data_de_criacao = datacriacao;
        this.saldo = saldo;
        ultimo_acesso = ultimo_a;
        
    }

    public int getCdConta() {
        return cdConta;
    }

    public void setCdConta(int cdConta) {
        this.cdConta = cdConta;
    }

    public int getQuantidade() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    
}

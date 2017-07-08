/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.domain;

import java.time.LocalDate;

/**
 *
 * @author Lara
 */
public class ContaPoupanca {
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
    public ContaPoupanca(){
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

    public ContaPoupanca(int cdConta,double saldo,LocalDate ultimo_a,Agencia ag,LocalDate datacriacao) {
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
}

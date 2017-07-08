/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.domain;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Lara
 */
public class Cupom implements Serializable {
    private int numCupom;
    private LocalDate validade;
    private OperacaoBancaria operacao;
    private ContaCorrente conta;
    private Agencia agencia;

    public Cupom(int numCupom, LocalDate validade, OperacaoBancaria cdOperacao, ContaCorrente conta, Agencia agencia) {
        this.numCupom = numCupom;
        this.validade = validade;
        this.operacao = cdOperacao;
        this.conta = conta;
        this.agencia = agencia;
    }
    
    public Cupom(){
        
    }
 
    public void setAgencia(Agencia agencia){
       this.agencia = agencia;
   }
   
   public Agencia getAgencia(){
       return this.agencia;
   }
   
   public void setCdAgencia(int id){
       this.agencia.setCdAgencia(id);
   }
   
   public int getCdAgencia(){
       return this.agencia.getCdAgencia();
   }
   
   
    public int getCdOperacao(){
        return this.operacao.getCdOperacaoBancaria();
    
    }
    
    public void setCdOperacao(int cd){
        this.operacao.setCdOperacaoBancaria(cd);
    }
    
    public void setCdConta(int cd){
        this.conta.setCdConta(cd);
    }
    
    public int getCdConta(){
        return this.conta.getCdConta();
    }

    public int getNumCupom() {
        return numCupom;
    }

    public void setNumCupom(int numCupom) {
        this.numCupom = numCupom;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public OperacaoBancaria getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoBancaria cdOperacao) {
        this.operacao = cdOperacao;
    }

    public ContaCorrente getConta() {
        return conta;
    }

    public void setConta(ContaCorrente conta) {
        this.conta = conta;
    }
    
    
    
    
    
}

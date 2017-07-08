/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.domain;

import java.io.Serializable;

/**
 *
 * @author Lara
 */
public class ClienteContaPoupanca implements Serializable {
private ContaPoupanca contapoupanca;
private Cliente cliente;
private Agencia agencia;
  

    public ClienteContaPoupanca(){
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
   
   
    public int getCdCliente(){
        return this.cliente.getCdCliente();
    }
    
    public int getCdConta(){
        return this.contapoupanca.getCdConta();
    }
    
  
    
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente a){
        this.cliente= a;
    }
    
    public ContaPoupanca getConta(){
        return this.contapoupanca;
    }
    public void setConta(ContaPoupanca a){
        this.contapoupanca= a;
    }
    
   public void setClienteCodigo(int id){
    this.cliente.setCdCliente(id);
}

public void setContaCodigo(int cod){
    this.contapoupanca.setCdConta(cod);
}

    public ClienteContaPoupanca(ContaPoupanca cc, Cliente c, Agencia agencia) {
      cliente = c;
      contapoupanca = cc;
      this.agencia = agencia;
    }
}

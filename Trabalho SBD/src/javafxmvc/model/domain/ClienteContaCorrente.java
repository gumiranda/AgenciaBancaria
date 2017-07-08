package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ClienteContaCorrente implements Serializable {
private ContaCorrente contacorrente;
private Cliente cliente;
private Agencia agencia;
private int codigoCliente;
private String cpfCliente;
private int codigoConta;

    public ClienteContaCorrente(ContaCorrente contacorrente, Cliente cliente, Agencia agencia) {
        this.contacorrente = contacorrente;
        this.cliente = cliente;
        this.agencia = agencia;
    }
    
    public ClienteContaCorrente(){
        
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
   
    
public int getContaCodigo(){
    return contacorrente.getCdConta();
}

public int getClienteCodigo(){
    return cliente.getCdCliente();
}

public void setClienteCodigo(int id){
    this.cliente.setCdCliente(id);
}
public void setContaCodigo(int cod){
    this.contacorrente.setCdConta(cod);
    this.codigoConta = cod;
}
public void setClienteCPF(String cod){
    this.cliente.setCpf(cod);
    this.cpfCliente = cod;
}

    public ContaCorrente getContacorrente() {
        return contacorrente;
    }

    public void setContacorrente(ContaCorrente contacorrente) {
        this.contacorrente = contacorrente;
    }
    
    public int getCdConta(){
        return this.contacorrente.getCdConta();
    }

    public int getCdCliente(){
        return this.cliente.getCdCliente();
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
  

   

  
    
}

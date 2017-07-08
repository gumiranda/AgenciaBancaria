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
public class ClienteEmprestimo implements Serializable {
    private Emprestimo emprestimo;
    private Cliente cliente;

    public ClienteEmprestimo(Emprestimo emprestimo, Cliente cliente) {
        this.emprestimo = emprestimo;
        this.cliente = cliente;
    }
    
    public ClienteEmprestimo(){
        
    }


   
    public int getCdEmprestimo() {
        return this.emprestimo.getCdEmprestimo();
    }
    
    public Emprestimo getEmprestimo(){
        return this.emprestimo;
    }
    
    public void setEmprestimo(Emprestimo emprestimo){
        this.emprestimo = emprestimo;
    }
    
    public void setCdEmprestimo(int cd){
        this.emprestimo.setCdEmprestimo(cd);
    }


    public Cliente getCliente() {
        return this.cliente;
    }
    
    public int getCdCliente(){
        return this.cliente.getCdCliente();
    }
    
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public void setCdCliente(int cd) {
        this.cliente.setCdCliente(cd);
    }
    
    
}

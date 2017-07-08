/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.model.domain;

import java.io.Serializable;

public class Emprestimo implements Serializable {
   private int cdEmprestimo;
   private Agencia agencia;
   private double valor;
   private int qdeParcelas;

    public Emprestimo(int cdEmprestimo,Agencia agencia, double valor, int qdeParcelas) {
        this.cdEmprestimo = cdEmprestimo;
        this.valor = valor;
        this.qdeParcelas = qdeParcelas;
        this.agencia = agencia;
    }
   
    public Emprestimo(){
        
    }
    
    public void setAgencia(Agencia a){
        this.agencia = a;
    }
    
    public Agencia getAgencia(){
        return this.agencia;
    }
    
    public void setcdAgencia(int cd){
        this.agencia.setCdAgencia(cd);
    }
    
    public int getcdAgencia(){
        return this.agencia.getCdAgencia();
    }
   

    public int getCdEmprestimo() {
        return cdEmprestimo;
    }

    public void setCdEmprestimo(int cdEmprestimo) {
        this.cdEmprestimo = cdEmprestimo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQdeParcelas() {
        return qdeParcelas;
    }

    public void setQdeParcelas(int qdeParcelas) {
        this.qdeParcelas = qdeParcelas;
    }
   
}

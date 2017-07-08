package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class OperacaoBancaria implements Serializable {

    private int cdOperacaoBancaria;
    private LocalDate hora;
    private LocalDate data_operacao;
    private ContaCorrente a;
    private Agencia agencia;
    private String tipo,descricao;

     public LocalDate getHora() {
        return hora;
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
   
    public void setHora(LocalDate data) {
        this.hora = data;
    }
     public LocalDate getDataOperacao() {
        return data_operacao;
    }

    public void setDataOperacao(LocalDate data) {
        this.data_operacao = data;
    }
    public OperacaoBancaria(){
    }
    public int getConta_codigo(){
        return this.a.getCdConta();
    }
    public ContaCorrente getConta(){
        return a;
    }
    public void setConta(ContaCorrente a){
        this.a = a;
    }

    public OperacaoBancaria(String tipo,String descricao,int cdOperacaoBancaria,LocalDate ultimo_a,Agencia agencia, ContaCorrente ag,LocalDate datacriacao) {
        this.cdOperacaoBancaria = cdOperacaoBancaria;
        a = ag;
        data_operacao = datacriacao;
        hora = ultimo_a;
        this.tipo = tipo;
        this.descricao = descricao;
        this.agencia = agencia;
    }

    public int getCdOperacaoBancaria() {
        return cdOperacaoBancaria;
    }

    public void setCdOperacaoBancaria(int cdOperacaoBancaria) {
        this.cdOperacaoBancaria = cdOperacaoBancaria;
    }
public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
  public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
private double valor;
    public void setValor(double valor) {
this.valor = valor;
    }
    public double getValor()
    {
        return valor;
    }   
}

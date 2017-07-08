package javafxmvc.model.domain;

import java.io.Serializable;

public class Agencia implements Serializable {

    private int cdAgencia;
    private String nome;
    private String estado;
    private String municipio;
    public Agencia(){
    }

    public Agencia(int cdAgencia, String nome,String municipio,String estado) {
        this.cdAgencia = cdAgencia;
        this.nome= nome;
        this.estado = estado; this.municipio = municipio;
    }

    public int getCdAgencia() {
        return cdAgencia;
    }

    public void setCdAgencia(int cdAgencia) {
        this.cdAgencia = cdAgencia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
        public String getEstado() {
        return estado;
    }

    public void setEstado(String nome) {
        this.estado = nome;
    }    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String nome) {
        this.municipio = nome;
    }
    @Override
    public String toString() {
        return this.nome;
    }
    
}

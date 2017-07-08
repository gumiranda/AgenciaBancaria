package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Dependente implements Serializable {
private Funcionario funcionario;
  private String nome;
  public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public Dependente(){
    }
    public int getFuncionario_codigo(){
        return this.funcionario.getCdFuncionario();
    }
    public Funcionario getFuncionario(){
        return funcionario;
    }
    public void setFuncionario(Funcionario a){
        this.funcionario= a;
    }

    public Dependente(String nome, Funcionario c) {
      funcionario = c;
      this.nome = nome;
    }

  
    
}

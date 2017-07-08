/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.AgenciaDAO;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Agencia;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ClienteEmprestimo;
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.Emprestimo;

/**
 *
 * @author Lara
 */


public class FXMLAnchorPaneCadastroEmprestimosDialogController implements Initializable {

    @FXML
    private Label labelCpfCliente;
    @FXML
    private Label labelNumAgencia;
    @FXML
    private Label labelValorEmprestimo;
     @FXML
    private Label labelQdeParcelas;
    @FXML
    private TextField textfieldCpfCliente;
    @FXML
    private TextField textFieldValor;
    @FXML
    private ComboBox comboBoxAgencia;
    @FXML
    private TextField textFieldQdeParcelas;
    @FXML
    private Button botaoCriar;
    @FXML
    private Button botaoCancelar;
    
     private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Emprestimo emprestimo;
    private ClienteEmprestimo clienteemp;
    private Cliente cliente;
    private ClienteDAO clienteDAO;
     private List<Agencia> listAgencias;
    private ObservableList<Agencia> observableListAgencias;
   private AgenciaDAO agenciaDAO;
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    
   
    public void initialize(URL url, ResourceBundle rb) {
        agenciaDAO.setConnection(connection);
        carregarComboBoxAgencia();
    }    
    
      public void carregarComboBoxAgencia() {
        listAgencias = agenciaDAO.listar();
        observableListAgencias = FXCollections.observableArrayList(listAgencias);
        comboBoxAgencia.setItems(observableListAgencias);
    }

         public void handleButtonCancelar() {
        dialogStage.close();
    }
         
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

  public Emprestimo getEmprestimo(){
      return this.emprestimo;
  }
  
  public ClienteEmprestimo getClienteEmprestimo(){
      return this.clienteemp;
    
  }
  
  public void setEmprestimo(Emprestimo emprestimo){
      this.emprestimo = emprestimo;
  }
  
   public void setClienteEmprestimo(ClienteEmprestimo cemprestimo){
      clienteemp = cemprestimo;
  }
  
  @FXML
  public void handleBotaoCriar(){
       if (validarEntradaDeDados()) {
            cliente.setCpf(textfieldCpfCliente.getText());
            emprestimo.setAgencia((Agencia) comboBoxAgencia.getSelectionModel().getSelectedItem());
            emprestimo.setValor(Double.parseDouble(textFieldValor.getText()));
            emprestimo.setQdeParcelas(Integer.parseInt(textFieldQdeParcelas.getText()));
            clienteemp.setCliente(clienteDAO.buscarPorCpf(cliente));
            clienteemp.setEmprestimo(emprestimo);
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
  }
  
  
    
   private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textfieldCpfCliente.getText() == null || textfieldCpfCliente.getText().length() == 0) {
            errorMessage += "Cpf inválido!\n";
        }
        
      
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
}


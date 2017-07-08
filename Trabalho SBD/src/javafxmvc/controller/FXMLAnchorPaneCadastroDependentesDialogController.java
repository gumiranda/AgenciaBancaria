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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.Dependente;
import javafxmvc.model.domain.Funcionario;

/**
 *
 * @author root
 */
public class FXMLAnchorPaneCadastroDependentesDialogController {

    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;
    private ComboBox comboBoxFuncionario;
    private Button botaoCadastrar;
    private Button botaoCancelar;
    private TextField textFieldNomeDependente;
    private Dependente dependente;
     private boolean buttonConfirmarClicked = false;
     private Stage dialogStage;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void initialize(URL url, ResourceBundle rb) {

        funcionarioDAO.setConnection(connection);
        carregarComboBoxFuncionarios();

        // TODO
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

    public void carregarComboBoxFuncionarios() {
        listFuncionarios = funcionarioDAO.listar();
        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
        comboBoxFuncionario.setItems(observableListFuncionarios);
    }
    
    public void setDependente(Dependente dependente){
        this.dependente = dependente;
        this.textFieldNomeDependente.setText(dependente.getNome());
       
    }
    
    public void handleBotaoCadastrar(){
         if (validarEntradaDeDados()) {
         Funcionario funcionario = new Funcionario();
          if (comboBoxFuncionario.getSelectionModel().getSelectedItem() != null) {
            funcionario = (Funcionario) comboBoxFuncionario.getSelectionModel().getSelectedItem();
            dependente.setFuncionario(funcionario);
         }
          dependente.setNome(textFieldNomeDependente.getText());
            buttonConfirmarClicked = true;
            dialogStage.close();
         }
    }
    
     public void handleButtonCancelar() {
        dialogStage.close();
    }
    
      //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldNomeDependente.getText() == null || textFieldNomeDependente.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
      
        if (comboBoxFuncionario.getSelectionModel().getSelectedItem() == null) {
                         errorMessage += "Funcionario não selecionada!\n";

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

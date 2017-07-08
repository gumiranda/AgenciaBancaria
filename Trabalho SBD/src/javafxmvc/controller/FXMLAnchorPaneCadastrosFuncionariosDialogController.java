package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.AgenciaDAO;
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Agencia;
import javafxmvc.model.domain.Funcionario;
public class FXMLAnchorPaneCadastrosFuncionariosDialogController implements Initializable {

    @FXML
    private ComboBox comboBoxFuncionarioAgencia;
    @FXML
    private DatePicker datePickerFuncionarioData;
    @FXML
    private ComboBox comboBoxFuncionarioFuncionario;
    
    @FXML
    private TextField textFieldFuncionarioNome;

    
    @FXML
    private TextField textFieldFuncionarioValor;
    @FXML
    private TextField textFieldFuncionarioTelefone;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Funcionario funcionario;
  private List<Agencia> listAgencias;
    private List<Funcionario> listFuncionarios;
    private ObservableList<Agencia> observableListAgencias;
    private ObservableList<Funcionario> observableListFuncionarios;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgenciaDAO agenciaDAO = new AgenciaDAO();
    private final FuncionarioDAO supervisorDAO = new FuncionarioDAO();
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        agenciaDAO.setConnection(connection);
        supervisorDAO.setConnection(connection);
        carregarComboBoxAgencias();
        carregarComboBoxFuncionarios();
        // TODO
    }
    public void carregarComboBoxAgencias() {
        listAgencias = agenciaDAO.listar();
        observableListAgencias = FXCollections.observableArrayList(listAgencias);
        comboBoxFuncionarioAgencia.setItems(observableListAgencias);
    }

    public void carregarComboBoxFuncionarios() {
        listFuncionarios = supervisorDAO.listar();
        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
        comboBoxFuncionarioFuncionario.setItems(observableListFuncionarios);
      
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        this.textFieldFuncionarioNome.setText(funcionario.getNome());
        this.textFieldFuncionarioTelefone.setText(funcionario.getTelefone());
        this.textFieldFuncionarioValor.setText(Double.toString(funcionario.getValor()));
       
    }

 

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
        Funcionario supervisor = new Funcionario();
        Funcionario funcionario = new Funcionario();
        if (comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem() != null) {
          Agencia agencia2 = (Agencia) comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem();
            funcionario.setAgencia(agencia2);
             if (comboBoxFuncionarioFuncionario.getSelectionModel().getSelectedItem() != null) {
            supervisor = (Funcionario) comboBoxFuncionarioFuncionario.getSelectionModel().getSelectedItem();
           funcionario.setSupervisor(supervisor);
         }
        }        
            funcionario.setNome(textFieldFuncionarioNome.getText());
            funcionario.setTelefone(textFieldFuncionarioTelefone.getText());
            funcionario.setData(datePickerFuncionarioData.getValue());
               
            funcionario.setValor(Double.parseDouble(textFieldFuncionarioValor.getText()));
            buttonConfirmarClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldFuncionarioNome.getText() == null || textFieldFuncionarioNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldFuncionarioTelefone.getText() == null || textFieldFuncionarioTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }
        if (textFieldFuncionarioValor.getText() == null || textFieldFuncionarioValor.getText().length() == 0) {
            errorMessage += "Valor inválido!\n";
        }
            if (datePickerFuncionarioData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        /*     if (comboBoxFuncionarioFuncionario.getSelectionModel().getSelectedItem() == null) {
                         errorMessage += "Conta corrente não selecionada!\n";

         }
             if (comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem() == null) {
                         errorMessage += "Agencia não selecionado!\n";

         }
*/
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
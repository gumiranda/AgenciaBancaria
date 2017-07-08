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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteContaCorrenteDAO;
import javafxmvc.model.dao.ContaCorrenteDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ClienteContaCorrente;
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.OperacaoBancaria;

public class FXMLAnchorPaneCadastrosOperacaoBancariaDialog implements Initializable {

    @FXML
    private ComboBox comboBoxOperacaoBancariaCliente;
    @FXML
    private DatePicker datePickerOperacaoBancariaData;
    @FXML
    private ComboBox comboBoxOperacaoBancariaContaCorrente;
    
    @FXML
    private TextField textFieldOperacaoBancariaTipo;
    @FXML
    private TextField textFieldOperacaoBancariaValor;
    @FXML
    private TextField textFieldOperacaoBancariaDescricao;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private OperacaoBancaria operacaoBancaria;
  private List<Cliente> listClientes;
    private List<ContaCorrente> listContaCorrentes;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<ContaCorrente> observableListContaCorrentes;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteContaCorrenteDAO clienteContaCorrenteDAO = new ClienteContaCorrenteDAO();
    private final ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        clienteContaCorrenteDAO.setConnection(connection);
        contaCorrenteDAO.setConnection(connection);
        carregarComboBoxClientes();
        carregarComboBoxContaCorrentes();
        // TODO
    }
    public void carregarComboBoxClientes() {
        listClientes = clienteContaCorrenteDAO.listarCliente();
        observableListClientes = FXCollections.observableArrayList(listClientes);
        comboBoxOperacaoBancariaCliente.setItems(observableListClientes);
    }

    public void carregarComboBoxContaCorrentes() {
        listContaCorrentes = clienteContaCorrenteDAO.listarConta();
        observableListContaCorrentes = FXCollections.observableArrayList(listContaCorrentes);
        comboBoxOperacaoBancariaContaCorrente.setItems(observableListContaCorrentes);
           if (comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem() != null) {
                  Cliente cliente = (Cliente) comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem();
         listContaCorrentes = clienteContaCorrenteDAO.listarConta(cliente);
         observableListContaCorrentes = FXCollections.observableArrayList(listContaCorrentes);
        comboBoxOperacaoBancariaContaCorrente.setItems(observableListContaCorrentes);
        }
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

    public OperacaoBancaria getOperacaoBancaria() {
        return operacaoBancaria;
    }

    public void setOperacaoBancaria(OperacaoBancaria operacaoBancaria) {
        this.operacaoBancaria = operacaoBancaria;
        this.textFieldOperacaoBancariaTipo.setText(operacaoBancaria.getTipo());
        this.textFieldOperacaoBancariaDescricao.setText(operacaoBancaria.getDescricao());
        this.textFieldOperacaoBancariaValor.setText(Double.toString(operacaoBancaria.getValor()));
       
    }

 

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
        ContaCorrente contaCorrente = new ContaCorrente();
        ClienteContaCorrente ccc = new ClienteContaCorrente();
        if (comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem() != null) {
          Cliente cliente2 = (Cliente) comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem();
            ccc.setCliente(cliente2);
            ccc.setClienteCodigo(cliente2.getCdCliente());
             if (comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem() != null) {
            contaCorrente = (ContaCorrente) comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem();
           ccc.setContacorrente(contaCorrente);
           ccc.setContaCodigo(contaCorrente.getCdConta());
             operacaoBancaria.setConta(contaCorrente); 
         }
        }        
            operacaoBancaria.setTipo(textFieldOperacaoBancariaTipo.getText());
            operacaoBancaria.setDescricao(textFieldOperacaoBancariaDescricao.getText());
            operacaoBancaria.setDataOperacao(datePickerOperacaoBancariaData.getValue());
            operacaoBancaria.setValor(Double.parseDouble(textFieldOperacaoBancariaValor.getText()));
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

        if (textFieldOperacaoBancariaTipo.getText() == null || textFieldOperacaoBancariaTipo.getText().length() == 0) {
            errorMessage += "Tipo inválido!\n";
        }
        if (textFieldOperacaoBancariaDescricao.getText() == null || textFieldOperacaoBancariaDescricao.getText().length() == 0) {
            errorMessage += "Descricao inválida!\n";
        }
        if (textFieldOperacaoBancariaValor.getText() == null || textFieldOperacaoBancariaValor.getText().length() == 0) {
            errorMessage += "Valor inválido!\n";
        }
            if (datePickerOperacaoBancariaData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        /*     if (comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem() == null) {
                         errorMessage += "Conta corrente não selecionada!\n";

         }
             if (comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem() == null) {
                         errorMessage += "Cliente não selecionado!\n";

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
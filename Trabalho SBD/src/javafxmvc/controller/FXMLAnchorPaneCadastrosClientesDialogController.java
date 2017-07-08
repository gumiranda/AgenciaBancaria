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
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Agencia;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ClienteContaCorrente;
import javafxmvc.model.domain.ContaCorrente;
import javafxmvc.model.domain.Funcionario;

public class FXMLAnchorPaneCadastrosClientesDialogController implements Initializable {

    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private TextField textFieldClienteNome;
    @FXML
    private TextField textFieldClienteCPF;
    @FXML
    private TextField textFieldClienteTelefone;
        @FXML
    private TextField textFieldClienteEndereco;
    @FXML
    private TextField textFieldClienteEstado;
    @FXML
    private TextField textFieldClienteCidade;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    @FXML
    private ComboBox comboBoxFuncionarioAgencia;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private ClienteContaCorrente ccc;
    
  private List<Agencia> listAgencias;
    private ObservableList<Agencia> observableListAgencias;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgenciaDAO agenciaDAO = new AgenciaDAO();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {           
        agenciaDAO.setConnection(connection);
        carregarComboBoxAgencias();
    }
    public void carregarComboBoxAgencias() {
        listAgencias = agenciaDAO.listar();
        observableListAgencias = FXCollections.observableArrayList(listAgencias);
        comboBoxFuncionarioAgencia.setItems(observableListAgencias);
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

    public ClienteContaCorrente getClienteContaCorrente() {
        return ccc;
    }

    public void setClienteContaCorrente(ClienteContaCorrente ccc) {
        this.ccc = ccc;
         Cliente c = ccc.getCliente();
        this.textFieldClienteNome.setText(c.getNome());
        this.textFieldClienteCPF.setText(c.getCpf());
        this.textFieldClienteTelefone.setText(c.getTelefone());
        this.textFieldClienteCidade.setText(c.getCidade());
        this.textFieldClienteEstado.setText(c.getEstado());
        this.textFieldClienteEndereco.setText(c.getEndereco());
        
    }
    

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {
Cliente cliente = new Cliente();
ContaCorrente cc = new ContaCorrente();
if (comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem() != null) {
          Agencia agencia2 = (Agencia) comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem();
            ccc.setAgencia(agencia2);
            cc.setAgencia(agencia2);
}
cc.setSaldo(0);
            cliente.setNome(textFieldClienteNome.getText());
            cliente.setCpf(textFieldClienteCPF.getText());
            cliente.setTelefone(textFieldClienteTelefone.getText());
ccc.setCliente(cliente);
ccc.setContacorrente(cc);
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

        if (textFieldClienteNome.getText() == null || textFieldClienteNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldClienteCPF.getText() == null || textFieldClienteCPF.getText().length() == 0) {
            errorMessage += "CPF inválido!\n";
        }
        if (textFieldClienteTelefone.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }
     if (textFieldClienteEstado.getText() == null || textFieldClienteEstado.getText().length() == 0) {
            errorMessage += "Estado inválido!\n";
        }
     if (comboBoxFuncionarioAgencia.getSelectionModel().getSelectedItem() == null) {
              errorMessage += "Agencia inválida!\n";

}
        if (textFieldClienteCidade.getText() == null || textFieldClienteCidade.getText().length() == 0) {
            errorMessage += "Cidade inválido!\n";
        }
        if (textFieldClienteEndereco.getText() == null || textFieldClienteEndereco.getText().length() == 0) {
            errorMessage += "Endereco inválido!\n";
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

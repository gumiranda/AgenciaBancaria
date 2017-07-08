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

public class FXMLAnchorPaneOperacoesBancariasDialog implements Initializable {

    @FXML
    private ComboBox comboBoxOperacaoBancariaCliente;
    @FXML
    private DatePicker datePickerOperacaoBancariaData;
    @FXML
    private ComboBox comboBoxOperacaoBancariaContaCorrente;
    @FXML
    private ComboBox comboBoxTipoOperacao;
    @FXML
    private ComboBox comboBoxDescricaoOperacao;
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
    private ContaCorrente contaCorrente;
    private List<Cliente> listClientes;
    private List<ContaCorrente> listContaCorrentes;
    private ObservableList<Cliente> observableListClientes;
    private ObservableList<ContaCorrente> observableListContaCorrentes;
    private List<String> listTipos;
    private ObservableList<String> observableListTipos;
      private List<String> listDescricao;
    private ObservableList<String> observableListDescricao;


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
        carregarComboBoxTipoOperacao();
        carregarComboBoxDescricaoOperacao();
        // TODO
    }

    public void carregarComboBoxTipoOperacao() {
        listTipos.add("Credito");
        listTipos.add("Debito");

        observableListTipos = FXCollections.observableArrayList(listTipos);
        comboBoxTipoOperacao.setItems(observableListTipos);
    }
    
     public void carregarComboBoxDescricaoOperacao() {
        listDescricao.add("Deposito");
        listDescricao.add("Saque");
        listDescricao.add("Pagamento");
        observableListDescricao = FXCollections.observableArrayList(listDescricao);
        comboBoxDescricaoOperacao.setItems(observableListDescricao);
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
        boolean x;
        if (validarEntradaDeDados()) {

            ClienteContaCorrente ccc = new ClienteContaCorrente();
            if (comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem() != null) {
                Cliente cliente2 = (Cliente) comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem();
                ccc.setCliente(cliente2);
                ccc.setClienteCodigo(cliente2.getCdCliente());
                if (comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem() != null) {
                    contaCorrente = (ContaCorrente) comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem();
                    ccc.setContacorrente(contaCorrente);
                    ccc.setContaCodigo(contaCorrente.getCdConta());
                    
                    operacaoBancaria.setAgencia(contaCorrente.getAgencia());
                    
                    // realiza saque
                    if (((String) comboBoxTipoOperacao.getSelectionModel().getSelectedItem()).equals("Debito")
                            && comboBoxDescricaoOperacao.getSelectionModel().getSelectedItem().equals("Saque")) {
                        x = contaCorrente.sacar(Double.parseDouble(textFieldOperacaoBancariaValor.getText()));
                        if (x == false) { 
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro na operacao");
                            alert.setHeaderText("Saldo Insuficiente! Tente novamente...");
                            alert.setContentText("");
                            alert.show();
                            dialogStage.close();
                        }
                    }
                    
                    // realiza pagamento
                    else if (((String) comboBoxTipoOperacao.getSelectionModel().getSelectedItem()).equals("Debito")
                            && comboBoxDescricaoOperacao.getSelectionModel().getSelectedItem().equals("Pagamento")) {
                        x = contaCorrente.sacar(Double.parseDouble(textFieldOperacaoBancariaValor.getText()));
                        if (x == false) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Erro na operacao");
                            alert.setHeaderText("Saldo Insuficiente! Tente novamente...");
                            alert.setContentText("");
                            alert.show();
                            dialogStage.close();
                        } 
                        
                        // realiza deposito
                        else if (((String) comboBoxDescricaoOperacao.getSelectionModel().getSelectedItem()).equals("Deposito")) {
                            contaCorrente.depositar(Double.parseDouble(textFieldOperacaoBancariaValor.getText()));
                        }

                    }
                }
            }
            operacaoBancaria.setTipo((String) comboBoxTipoOperacao.getSelectionModel().getSelectedItem());
            operacaoBancaria.setDescricao((String) comboBoxDescricaoOperacao.getSelectionModel().getSelectedItem());
            operacaoBancaria.setDataOperacao(datePickerOperacaoBancariaData.getValue());
            operacaoBancaria.setValor(Double.parseDouble(textFieldOperacaoBancariaValor.getText()));
            operacaoBancaria.setConta(contaCorrente);
            contaCorrente.setUltimoAcesso(datePickerOperacaoBancariaData.getValue());
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

        if (comboBoxTipoOperacao.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Tipo nao selecionado!\n";
        }
        if (comboBoxDescricaoOperacao.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Descricao nao selecionada!\n";
        }
        if (textFieldOperacaoBancariaValor.getText() == null || textFieldOperacaoBancariaValor.getText().length() == 0) {
            errorMessage += "Valor inválido!\n";
        }
        if (datePickerOperacaoBancariaData.getValue() == null) {
            errorMessage += "Data inválida!\n";
        }
        if (comboBoxOperacaoBancariaContaCorrente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Conta corrente não selecionada!\n";

        }
        if (comboBoxOperacaoBancariaCliente.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Cliente não selecionado!\n";

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

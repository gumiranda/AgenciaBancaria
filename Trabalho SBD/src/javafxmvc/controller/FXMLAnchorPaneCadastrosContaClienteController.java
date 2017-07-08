/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.database.*;
import javafxmvc.model.dao.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.AgenciaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Agencia;
import javafxmvc.model.domain.*;
/**
 * FXML Controller class
 *
 * @author luizg
 */
public class FXMLAnchorPaneCadastrosContaClienteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<ClienteContaCorrente> tableContaCliente;
    @FXML
    private TableColumn<ClienteContaCorrente,Integer> tableColumnCodConta;
    @FXML
    private TableColumn<ClienteContaCorrente,String> tableColumnCpf;
    
    
    @FXML
    private Label labelNomeCliente;
    @FXML
    private Label labelCPFCliente;
    @FXML
    private Label labelTelCliente;
    @FXML
    private Label labelEndCliente;
    @FXML
    private Label labelCidadeCliente;
    @FXML
    private Label labelEstadoCliente;
    @FXML
    private Label labelCodAgencia;
    @FXML
    private Label labelCodConta;
    
    //@FXML
    //private RadioButton selCC;
    //@FXML
    //private RadioButton selP;
    
    @FXML
    private Button buttonInserir;
    @FXML
    private Label buttonAlterar;
    @FXML
    private Label buttonRemover;
    
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteContaCorrenteDAO clienteContaCorrenteDAO = new ClienteContaCorrenteDAO();
private final ClienteDAO clienteDAO = new ClienteDAO();
private final ContaCorrenteDAO ccDAO = new ContaCorrenteDAO();
    private List<ClienteContaCorrente> listContaCorrenteCliente;
    private ObservableList<ClienteContaCorrente> observableListContaCorrenteCliente;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteContaCorrenteDAO.setConnection(connection);
        carregarTableViewContaCliente();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableContaCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewClienteContaCorrente(newValue));

    } 
    
    public void carregarTableViewContaCliente() {
        tableColumnCodConta.setCellValueFactory(new PropertyValueFactory<>("codigoConta"));
        tableColumnCpf.setCellValueFactory(new PropertyValueFactory<>("cpfCliente"));

        listContaCorrenteCliente = clienteContaCorrenteDAO.listar();

        observableListContaCorrenteCliente = FXCollections.observableArrayList(listContaCorrenteCliente);
        tableContaCliente.setItems(observableListContaCorrenteCliente);
    }
    
    public void selecionarItemTableViewClienteContaCorrente(ClienteContaCorrente ccc){
        if (ccc != null) {
            labelNomeCliente.setText(String.valueOf(ccc.getCliente().getNome()));
            labelCPFCliente.setText(String.valueOf(ccc.getCliente().getCpf()));
             labelTelCliente.setText(String.valueOf(ccc.getCliente().getTelefone()));
            labelEndCliente.setText(String.valueOf(ccc.getCliente().getEndereco()));
            labelCidadeCliente.setText(String.valueOf(ccc.getCliente().getCidade()));
            labelEstadoCliente.setText(String.valueOf(ccc.getCliente().getEstado()));
            
            labelCodAgencia.setText(Integer.toString(ccc.getContacorrente().getAgencia().getCdAgencia()));
            labelCodConta.setText(Integer.toString(ccc.getCdConta()));

        } else {
            labelNomeCliente.setText("");
            labelCPFCliente.setText("");
             labelTelCliente.setText("");
            labelEndCliente.setText("");
            labelCidadeCliente.setText("");
            labelEstadoCliente.setText("");
            
            labelCodAgencia.setText("");
            labelCodConta.setText("");

        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        ClienteContaCorrente clienteconta = new ClienteContaCorrente();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosContaECliente(clienteconta);
        if (buttonConfirmarClicked) {
            clienteContaCorrenteDAO.inserir(clienteconta);
             
            ccDAO.inserir(clienteconta.getContacorrente());
            clienteDAO.inserir(clienteconta.getCliente());
            carregarTableViewContaCliente();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        ClienteContaCorrente c = tableContaCliente.getSelectionModel().getSelectedItem();
        if (c != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosContaECliente(c);
            if (buttonConfirmarClicked) {
                clienteContaCorrenteDAO.alterar(c);
                ccDAO.alterar(c.getContacorrente());
                clienteDAO.alterar(c.getCliente());
                carregarTableViewContaCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma conta na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        ClienteContaCorrente c = tableContaCliente.getSelectionModel().getSelectedItem();
        if (c != null) {
            clienteContaCorrenteDAO.remover(c);
ccDAO.remover(c.getContacorrente());
            clienteDAO.remover(c.getCliente());
            carregarTableViewContaCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma conta da tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosContaECliente(ClienteContaCorrente c) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosClientesDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosContaClienteDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de conta e cliente");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLAnchorPaneCadastrosClientesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setClienteContaCorrente(c);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
}

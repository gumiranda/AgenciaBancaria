/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.controller;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.ClienteEmprestimoDAO;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.ClienteEmprestimo;
import javafxmvc.model.domain.Emprestimo;

/**
 * FXML Controller class
 *
 * @author Lara
 */
public class FXMLAnchorPaneCadastroEmprestimosController implements Initializable {

  @FXML
    private TableView<Emprestimo> tableViewEmprestimos;
    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoCodigo;
    @FXML
    private TableColumn<Emprestimo, Double> tableColumnEmprestimoValor;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelEmprestimoCodigo;
    @FXML
    private Label labelEmprestimoValor;
@FXML
    private Label labelEmprestimoParcelas;
    @FXML
    private Label labelEmprestimoAgencia;
      @FXML
    private Label labelEmprestimoCliente;
    private List<Emprestimo> listEmprestimos;
    private ObservableList<Emprestimo> observableListEmprestimos;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final EmprestimoDAO empDAO = new EmprestimoDAO();
    private final ClienteEmprestimoDAO clienteempDAO = new ClienteEmprestimoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empDAO.setConnection(connection);
        clienteempDAO.setConnection(connection);
        clienteDAO.setConnection(connection);
        carregarTableViewEmprestimo();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewEmprestimos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEmprestimos(newValue));

    }//////////////

    public void carregarTableViewEmprestimo() {
        tableColumnEmprestimoCodigo.setCellValueFactory(new PropertyValueFactory<>("cdemprestimo"));
        tableColumnEmprestimoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        listEmprestimos = empDAO.listar();

        observableListEmprestimos = FXCollections.observableArrayList(listEmprestimos);
        tableViewEmprestimos.setItems(observableListEmprestimos);
    }
    
    public void selecionarItemTableViewEmprestimos(Emprestimo emprestimo){
        if (emprestimo != null) {
            Cliente c = new Cliente();
            ClienteEmprestimo clienteemp = new ClienteEmprestimo();
            clienteemp.setEmprestimo(emprestimo);
            clienteemp = clienteempDAO.buscarPorCodigo(clienteemp); // para obter dados do cliente
            labelEmprestimoCodigo.setText(String.valueOf(emprestimo.getCdEmprestimo()));
            labelEmprestimoValor.setText(String.valueOf(emprestimo.getValor()));
            labelEmprestimoParcelas.setText(String.valueOf(emprestimo.getQdeParcelas()));
            labelEmprestimoAgencia.setText(emprestimo.getAgencia().getNome());
            labelEmprestimoCliente.setText(clienteemp.getCliente().getNome());

        } else {
            labelEmprestimoCodigo.setText("");
            labelEmprestimoValor.setText("");
            labelEmprestimoParcelas.setText("");
            labelEmprestimoAgencia.setText("");
            labelEmprestimoCliente.setText("");

        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Emprestimo emprestimo = new Emprestimo();
        ClienteEmprestimo clienteemp = new ClienteEmprestimo();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroEmprestimosDialog(emprestimo, clienteemp);
        if (buttonConfirmarClicked) {
            empDAO.inserir(emprestimo);
            clienteempDAO.inserir(clienteemp);
            carregarTableViewEmprestimo();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Emprestimo emprestimo = tableViewEmprestimos.getSelectionModel().getSelectedItem();
        ClienteEmprestimo clienteemp = new ClienteEmprestimo();
        if (emprestimo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroEmprestimosDialog(emprestimo, clienteemp);
            if (buttonConfirmarClicked) {
                empDAO.alterar(emprestimo); // não precisa alterar clienteemp pois id do cliente e id do emp n serao alterados
                //clienteempDAO.alterar(clienteemp);
                carregarTableViewEmprestimo();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um emprestimo na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Emprestimo emprestimo = tableViewEmprestimos.getSelectionModel().getSelectedItem();
        ClienteEmprestimo clienteemp = new ClienteEmprestimo();
        if (emprestimo != null) {
            clienteemp.setEmprestimo(emprestimo);
            empDAO.remover(emprestimo); 
            clienteempDAO.remover(clienteemp);
            carregarTableViewEmprestimo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um emprestimo na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastroEmprestimosDialog(Emprestimo emprestimo, ClienteEmprestimo clienteemp) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroEmprestimosDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastroEmprestimosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Emprestimos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o agencia no Controller.
        FXMLAnchorPaneCadastroEmprestimosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmprestimo(emprestimo);
        controller.setClienteEmprestimo(clienteemp);
        

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
    
}

package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafxmvc.model.dao.ContaCorrenteDAO;
import javafxmvc.model.dao.OperacaoBancariaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.OperacaoBancaria;

public class FXMLAnchorPaneOperacoesBancariasController implements Initializable {

    @FXML
    private TableView<OperacaoBancaria> tableViewOperacaoBancarias;
    @FXML
    private TableColumn<OperacaoBancaria, String> tableColumnOperacaoBancariaTipo;
    @FXML
    private TableColumn<OperacaoBancaria, Integer> tableColumnOperacaoBancariaConta;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelOperacaoBancariaCodigo;
    @FXML
    private Label labelOperacaoBancariaTipo;
      @FXML
    private Label labelOperacaoBancariaConta;
     @FXML
    private Label labelOperacaoBancariaDescricao;
      @FXML
    private Label labelOperacaoBancariaAgencia;
       @FXML
    private Label labelOperacaoBancariaValor;

    private List<OperacaoBancaria> listOperacaoBancarias;
    private ObservableList<OperacaoBancaria> observableListOperacaoBancarias;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final OperacaoBancariaDAO operacaoBancariaDAO = new OperacaoBancariaDAO();
    private final ContaCorrenteDAO contaDAO = new ContaCorrenteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        operacaoBancariaDAO.setConnection(connection);
        carregarTableViewOperacaoBancaria();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewOperacaoBancarias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewOperacaoBancarias(newValue));

    }//////////////

    public void carregarTableViewOperacaoBancaria() {
        tableColumnOperacaoBancariaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tableColumnOperacaoBancariaConta.setCellValueFactory(new PropertyValueFactory<>("conta_id"));

        listOperacaoBancarias = operacaoBancariaDAO.listar();

        observableListOperacaoBancarias = FXCollections.observableArrayList(listOperacaoBancarias);
        tableViewOperacaoBancarias.setItems(observableListOperacaoBancarias);
    }
    
    public void selecionarItemTableViewOperacaoBancarias(OperacaoBancaria operacaoBancaria){
        if (operacaoBancaria != null) {
            labelOperacaoBancariaCodigo.setText(String.valueOf(operacaoBancaria.getCdOperacaoBancaria()));
            labelOperacaoBancariaTipo.setText(operacaoBancaria.getTipo());
            labelOperacaoBancariaConta.setText(String.valueOf(operacaoBancaria.getConta_codigo()));
            labelOperacaoBancariaDescricao.setText(operacaoBancaria.getDescricao());
            labelOperacaoBancariaAgencia.setText(operacaoBancaria.getAgencia().getNome());
            labelOperacaoBancariaValor.setText(String.valueOf(operacaoBancaria.getValor()));
        } else {
            labelOperacaoBancariaCodigo.setText("");
            labelOperacaoBancariaTipo.setText("");
            labelOperacaoBancariaConta.setText("");
            labelOperacaoBancariaDescricao.setText("");
            labelOperacaoBancariaAgencia.setText("");
            labelOperacaoBancariaValor.setText("");
        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        OperacaoBancaria operacaoBancaria = new OperacaoBancaria();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosOperacoesBancariasDialog(operacaoBancaria);
        if (buttonConfirmarClicked) {
            operacaoBancariaDAO.inserir(operacaoBancaria);
            contaDAO.alterar(operacaoBancaria.getConta()); // vai alterar o saldo e ultimo acesso no banco
            carregarTableViewOperacaoBancaria();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        OperacaoBancaria operacaoBancaria = tableViewOperacaoBancarias.getSelectionModel().getSelectedItem();
        if (operacaoBancaria != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosOperacoesBancariasDialog(operacaoBancaria);
            if (buttonConfirmarClicked) {
                operacaoBancariaDAO.alterar(operacaoBancaria);
                carregarTableViewOperacaoBancaria();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um operacaoBancaria na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        OperacaoBancaria operacaoBancaria = tableViewOperacaoBancarias.getSelectionModel().getSelectedItem();
        if (operacaoBancaria != null) {
            operacaoBancariaDAO.remover(operacaoBancaria);
            carregarTableViewOperacaoBancaria();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um operacaoBancaria na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosOperacoesBancariasDialog(OperacaoBancaria operacaoBancaria) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneOperacoesBancariasDialog.class.getResource("/javafxmvc/view/FXMLAnchorPaneOperacoesBancariasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Operacao Bancaria");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o operacaoBancaria no Controller.
        FXMLAnchorPaneOperacoesBancariasDialog controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setOperacaoBancaria(operacaoBancaria);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }



}
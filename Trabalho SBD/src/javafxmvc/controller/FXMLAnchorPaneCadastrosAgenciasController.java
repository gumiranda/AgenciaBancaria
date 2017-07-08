
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
import javafxmvc.model.dao.AgenciaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Agencia;

public class FXMLAnchorPaneCadastrosAgenciasController implements Initializable {

    @FXML
    private TableView<Agencia> tableViewAgencias;
    @FXML
    private TableColumn<Agencia, String> tableColumnAgenciaNome;
    @FXML
    private TableColumn<Agencia, Integer> tableColumnAgenciaCodigo;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelAgenciaCodigo;
    @FXML
    private Label labelAgenciaNome;
@FXML
    private Label labelAgenciaMunicipio;
    @FXML
    private Label labelAgenciaEstado;
    private List<Agencia> listAgencias;
    private ObservableList<Agencia> observableListAgencias;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AgenciaDAO agenciaDAO = new AgenciaDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agenciaDAO.setConnection(connection);
        carregarTableViewAgencia();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAgencias.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAgencias(newValue));

    }//////////////

    public void carregarTableViewAgencia() {
        tableColumnAgenciaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnAgenciaCodigo.setCellValueFactory(new PropertyValueFactory<>("cdAgencia"));

        listAgencias = agenciaDAO.listar();

        observableListAgencias = FXCollections.observableArrayList(listAgencias);
        tableViewAgencias.setItems(observableListAgencias);
    }
    
    public void selecionarItemTableViewAgencias(Agencia agencia){
        if (agencia != null) {
            labelAgenciaCodigo.setText(String.valueOf(agencia.getCdAgencia()));
            labelAgenciaNome.setText(agencia.getNome());
             labelAgenciaEstado.setText(agencia.getEstado());
            labelAgenciaMunicipio.setText(agencia.getMunicipio());

        } else {
            labelAgenciaCodigo.setText("");
            labelAgenciaNome.setText("");
            labelAgenciaMunicipio.setText("");
            labelAgenciaEstado.setText("");

        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Agencia agencia = new Agencia();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosAgenciasDialog(agencia);
        if (buttonConfirmarClicked) {
            agenciaDAO.inserir(agencia);
            carregarTableViewAgencia();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Agencia agencia = tableViewAgencias.getSelectionModel().getSelectedItem();
        if (agencia != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosAgenciasDialog(agencia);
            if (buttonConfirmarClicked) {
                agenciaDAO.alterar(agencia);
                carregarTableViewAgencia();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agencia na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Agencia agencia = tableViewAgencias.getSelectionModel().getSelectedItem();
        if (agencia != null) {
            agenciaDAO.remover(agencia);
            carregarTableViewAgencia();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um agencia na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosAgenciasDialog(Agencia agencia) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosAgenciasDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosAgenciasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Agencias");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o agencia no Controller.
        FXMLAnchorPaneCadastrosAgenciasDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAgencia(agencia);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }



}
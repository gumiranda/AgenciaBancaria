
package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Funcionario;

public class FXMLAnchorPaneCadastrosFuncionariosController implements Initializable {

    @FXML
    private TableView<Funcionario> tableViewFuncionarios;
    @FXML
    private TableColumn<Funcionario, String> tableColumnFuncionarioNome;
    @FXML
    private TableColumn<Funcionario, String> tableColumnFuncionarioCodigo;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelFuncionarioCodigo;
    @FXML
    private Label labelFuncionarioData;
    @FXML
    private Label labelFuncionarioNome;
    @FXML
    private Label labelFuncionarioAgencia;
@FXML
    private Label labelFuncionarioTelefone;
    @FXML
    private Label labelFuncionarioTempoServico;
    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO.setConnection(connection);
        carregarTableViewFuncionario();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewFuncionarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewFuncionarios(newValue));

    }//////////////

    public void carregarTableViewFuncionario() {
        tableColumnFuncionarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnFuncionarioCodigo.setCellValueFactory(new PropertyValueFactory<>("cdFuncionario"));

        listFuncionarios = funcionarioDAO.listar();

        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
        tableViewFuncionarios.setItems(observableListFuncionarios);
    }
    
    public void selecionarItemTableViewFuncionarios(Funcionario funcionario){
        if (funcionario != null) {
            labelFuncionarioCodigo.setText(String.valueOf(funcionario.getCdFuncionario()));
            labelFuncionarioAgencia.setText(String.valueOf(funcionario.getAgencia().getCdAgencia()));
            labelFuncionarioNome.setText(funcionario.getNome());
           labelFuncionarioData.setText(String.valueOf(funcionario.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        LocalDate dataAdmissao = funcionario.getData() ;
        LocalDate hoje = LocalDate.now();
Period periodo = Period.between(dataAdmissao,hoje);
    funcionario.setTempoServico(periodo.getMonths());//pode ser getDays ou getYears
             labelFuncionarioTempoServico.setText(Integer.toString((funcionario.getTempoServico())));
            labelFuncionarioTelefone.setText(funcionario.getTelefone());

        } else {
            labelFuncionarioCodigo.setText("");
            labelFuncionarioAgencia.setText("");
            labelFuncionarioNome.setText("");
            labelFuncionarioTelefone.setText("");
            labelFuncionarioTempoServico.setText("");
            labelFuncionarioData.setText("");

        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Funcionario funcionario = new Funcionario();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosFuncionariosDialog(funcionario);
        if (buttonConfirmarClicked) {
            funcionarioDAO.inserir(funcionario);
            carregarTableViewFuncionario();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Funcionario funcionario = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosFuncionariosDialog(funcionario);
            if (buttonConfirmarClicked) {
                funcionarioDAO.alterar(funcionario);
                carregarTableViewFuncionario();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um funcionario na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Funcionario funcionario = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            funcionarioDAO.remover(funcionario);
            carregarTableViewFuncionario();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um funcionario na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastrosFuncionariosDialog(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosFuncionariosDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosFuncionariosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Funcionarios");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o funcionario no Controller.
        FXMLAnchorPaneCadastrosFuncionariosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFuncionario(funcionario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }



}
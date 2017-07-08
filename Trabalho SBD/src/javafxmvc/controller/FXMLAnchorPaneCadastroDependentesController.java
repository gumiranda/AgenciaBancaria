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
import javafxmvc.model.dao.DependenteDAO;
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Dependente;
import javafxmvc.model.domain.Funcionario;

/**
 * FXML Controller class
 *
 * @author Lara
 */
public class FXMLAnchorPaneCadastroDependentesController implements Initializable {

     @FXML
    private TableView<Dependente> tableViewDependentes;
    @FXML
    private TableColumn<Dependente, String> tableColumnDependenteNome;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelDependenteNome;
    @FXML
    private Label labelDependenteFuncionario;
    private List<Dependente> listDependentes;
    private ObservableList<Dependente> observableListDependentes;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final DependenteDAO dependenteDAO = new DependenteDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dependenteDAO.setConnection(connection);
        funcionarioDAO.setConnection(connection);
        carregarTableViewDependente();

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewDependentes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewDependentes(newValue));

    }

    public void carregarTableViewDependente() {
        tableColumnDependenteNome.setCellValueFactory(new PropertyValueFactory<>("nome_do_dependente"));
        listDependentes = dependenteDAO.listar();

        observableListDependentes = FXCollections.observableArrayList(listDependentes);
        tableViewDependentes.setItems(observableListDependentes);
    }
    
    public void selecionarItemTableViewDependentes(Dependente dependente){
        if (dependente != null) {
            Funcionario f = new Funcionario();
            f.setCdFuncionario(dependente.getFuncionario_codigo());
            f = funcionarioDAO.buscar(f);
            labelDependenteNome.setText(String.valueOf(dependente.getNome()));
            labelDependenteFuncionario.setText(f.getNome());

        } else {
            labelDependenteNome.setText("");
            labelDependenteFuncionario.setText("");
        }

    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Dependente dependente = new Dependente();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroDependentesDialog(dependente);
        if (buttonConfirmarClicked) {
            dependenteDAO.inserir(dependente);
            carregarTableViewDependente();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Dependente dependente = tableViewDependentes.getSelectionModel().getSelectedItem();
        if (dependente != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastroDependentesDialog(dependente);
            if (buttonConfirmarClicked) {
                dependenteDAO.alterar(dependente);
                carregarTableViewDependente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um dependente na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Dependente dependente = tableViewDependentes.getSelectionModel().getSelectedItem();
        if (dependente != null) {
            dependenteDAO.remover(dependente);
            carregarTableViewDependente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um dependente na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLAnchorPaneCadastroDependentesDialog(Dependente dependente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastroDependentesDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastroDependentesDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Dependentes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o agencia no Controller.
        FXMLAnchorPaneCadastroDependentesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setDependente(dependente);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }
    
}

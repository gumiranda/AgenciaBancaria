package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Agencia;

public class FXMLAnchorPaneCadastrosAgenciasDialogController implements Initializable {

    @FXML
    private Label labelAgenciaNome;
    @FXML
    private Label labelAgenciaMunicipio;
    @FXML
    private TextField textFieldAgenciaNome;
    @FXML
    private TextField textFieldAgenciaMunicipio;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Agencia agencia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
        this.textFieldAgenciaNome.setText(agencia.getNome());
        this.textFieldAgenciaMunicipio.setText(agencia.getMunicipio());
    }

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {

            agencia.setNome(textFieldAgenciaNome.getText());
            agencia.setMunicipio(textFieldAgenciaMunicipio.getText());

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

        if (textFieldAgenciaNome.getText() == null || textFieldAgenciaNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldAgenciaMunicipio.getText() == null || textFieldAgenciaMunicipio.getText().length() == 0) {
            errorMessage += "Municipio inválido!\n";
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
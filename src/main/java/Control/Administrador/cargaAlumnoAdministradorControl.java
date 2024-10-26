package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ControlArchivos.manejoArchivosCarrera;

import java.util.ArrayList;

import static Path.Path.pathCarreras;

public class cargaAlumnoAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceCarrera;

    @FXML
    private Rectangle lista;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {
        txtApellido.getText();
        txtDni.getText();
        txtNombre.getText();
        choiceCarrera.getValue();
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalAdministrador,stage,"Menu Principal");

    }

    @FXML
    void setTxtApellido(ActionEvent event) {

    }

    @FXML
    void setTxtDni(ActionEvent event) {

    }

    @FXML
    void setTxtNombre(ActionEvent event) {

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<String> carreras = manejoArchivosCarrera.obtenerCampoEspecificoDeCarrera(pathCarreras, "nombre");

            choiceCarrera.getItems().addAll(carreras);

        });

    }

}

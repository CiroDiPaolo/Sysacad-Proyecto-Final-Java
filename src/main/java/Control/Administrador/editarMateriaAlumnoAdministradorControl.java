package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarMateriaAlumnoAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnVolver1;

    @FXML
    private ChoiceBox<String> choiceMateria;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtComision;

    @FXML
    private TextField txtFolio;

    @FXML
    private TextField txtPrimerParcial;

    @FXML
    private TextField txtSegundoParcial;

    @FXML
    private TextField txtTomo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.configurarMateriasAdministrador, stage, "Configurar Materia");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            choiceMateria.getItems().addAll("Materia 1", "Materia 2", "Materia 3", "Materia 4", "Materia 5");

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }

}

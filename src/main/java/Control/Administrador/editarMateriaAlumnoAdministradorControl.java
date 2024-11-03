package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    private void actualizarTextFields(String materiaSeleccionada) {
        // Aquí puedes obtener los datos de la materia seleccionada y actualizar los TextField
        // Por ejemplo:
        txtComision.setText("Comisión de " + materiaSeleccionada);
        txtFolio.setText("Folio de " + materiaSeleccionada);
        txtPrimerParcial.setText("Nota del primer parcial de " + materiaSeleccionada);
        txtSegundoParcial.setText("Nota del segundo parcial de " + materiaSeleccionada);
        txtTomo.setText("Tomo de " + materiaSeleccionada);
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            HashMap<String, String> materia = Data.getEstudiante().obtenerMaterias();

            for (Map.Entry<String, String> entry : materia.entrySet()) {
                choiceMateria.getItems().add(entry.getValue());
            }

            // Agregar listener para manejar la selección de la ChoiceBox
            choiceMateria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    actualizarTextFields(newValue);
                }
            });

            stage = (Stage) btnVolver.getScene().getWindow();
        });

    }

}

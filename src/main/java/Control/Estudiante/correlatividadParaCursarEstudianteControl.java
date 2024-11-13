package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalAlumnos;

public class correlatividadParaCursarEstudianteControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> colAnio;

    @FXML
    private TableColumn<?, ?> colCorrelatividad;

    @FXML
    private TableColumn<?, ?> colMateria;

    @FXML
    private TableColumn<?, ?> colPlan;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;


    /**
     * Metodo que se ejecuta al clickear el boton volver
     * @param event
     */
    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            Estudiante estudiante = Data.getEstudiante();

        });

    }


}

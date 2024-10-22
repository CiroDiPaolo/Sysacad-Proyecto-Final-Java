package Control.Estudiante;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import static Path.Path.*;

public class materiasDelPlanEstudianteControl {

    private Stage stage;

    @FXML
    private TableColumn<?, ?> anio;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> dic;

    @FXML
    private TableColumn<?, ?> materia;

    @FXML
    private TableColumn<?, ?> seCursa;

    @FXML
    private TableColumn<?, ?> seRinde;

    @FXML
    private Label tctMenuPrincipal;

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

}
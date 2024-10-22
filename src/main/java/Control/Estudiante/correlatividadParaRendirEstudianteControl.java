package Control.Estudiante;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalAlumnos;

public class correlatividadParaRendirEstudianteControl {

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

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

}

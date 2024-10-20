package Control.Estudiante;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalAlumnos;

public class notasEstudiantesControl {

    private Stage stage;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<?, ?> colAnio;

    @FXML
    private TableColumn<?, ?> colComision;

    @FXML
    private TableColumn<?, ?> colFinal;

    @FXML
    private TableColumn<?, ?> colMateria;

    @FXML
    private TableColumn<?, ?> colPParcial;

    @FXML
    private TableColumn<?, ?> colSParcial;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

}

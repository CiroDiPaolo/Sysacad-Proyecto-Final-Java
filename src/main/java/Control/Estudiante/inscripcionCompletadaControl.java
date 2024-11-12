package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Modelo.Turno;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import static Path.Path.menuPrincipalAlumnos;

public class inscripcionCompletadaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextArea textArea;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            String turno = (Data.getComision().getTurno() == Turno.MANIANA) ? "MAÑANA" : Data.getComision().getTurno().toString();
            textArea.setText("INSCRIPCIÓN COMPLETADA CON ÉXITO\n" + Data.getComision().getNombre() + "\n" + Data.getAux() + "\n" + Data.getComision().getDescripcion() + "\n" + "Turno: " + turno);

        });
    }


}

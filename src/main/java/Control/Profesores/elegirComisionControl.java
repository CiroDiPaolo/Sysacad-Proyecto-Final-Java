package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Modelo.Comision;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.HashSet;

public class elegirComisionControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private ChoiceBox<String> choiceComision;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalProfesores, stage, "Menu principal profesores");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            HashSet<Comision> comisiones = Data.getProfesor().obtenerComisiones(Path.pathComisiones);

            for(Comision comision : comisiones){
                choiceComision.getItems().add(comision.getNombre());
            }

        });

    }

}

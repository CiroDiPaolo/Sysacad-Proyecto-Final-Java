package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class configurarMesaExamenAdministrador {

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Button btnVolver;

    private EscenaControl escena = new EscenaControl();

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();

        escena.cambiarEscena(Path.menuPrincipalAdministrador,stage,"Menu Principal");

    }


}

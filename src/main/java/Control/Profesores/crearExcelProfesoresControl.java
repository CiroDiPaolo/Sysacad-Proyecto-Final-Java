package Control.Profesores;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalProfesores;

public class crearExcelProfesoresControl {

    private Stage stage;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Text txtBienvenida;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalProfesores, stage, "Inicio de sesion");

    }

}

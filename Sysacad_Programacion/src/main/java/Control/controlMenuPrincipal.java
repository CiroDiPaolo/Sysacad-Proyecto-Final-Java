package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import static Path.Path.*;


public class controlMenuPrincipal  {

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Button op1;

    private Stage stage;

    @FXML
    private void clicOp1(ActionEvent event) {

        stage = (Stage) op1.getScene().getWindow();

        EscenaControl escena = new EscenaControl();

        escena.cambiarEscena(verDatos,stage);

    }


}

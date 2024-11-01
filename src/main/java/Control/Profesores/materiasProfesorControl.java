package Control.Profesores;


import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.inicioSesion;
import static Path.Path.menuPrincipalProfesores;

public class materiasProfesorControl {

    private Stage stage;

    @FXML
    private TableView<?> tablaMateriasProfesor;

    @FXML
    private TableColumn<?, ?> uno;

    @FXML
    private TableColumn<?, ?> dos;

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



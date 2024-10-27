package Control.Administrador;

import Control.EscenaControl;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarAlumnoAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnEditarMaterias;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {

    }

    @FXML
    void clickBtnEditarMaterias(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        stage = (Stage) btnVolver.getScene().getWindow();
        escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage, "Menu Administrador");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            //Estudiante e = LLamada al metodo para obtener el estudiante

            //txtNombre.setText(e.getNombre());
            //txtApellido.setText(e.getApellido());
            //txtDni.setText(e.getDni());
            //txtCorreo.setText(e.getCorreo());

        });

    }

}


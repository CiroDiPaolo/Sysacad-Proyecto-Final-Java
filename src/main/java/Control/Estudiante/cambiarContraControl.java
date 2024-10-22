package Control.Estudiante;

import Control.EscenaControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import static Path.Path.menuPrincipalAlumnos;

public class cambiarContraControl {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnConfirmarCambio;

    @FXML
    private PasswordField contraActual;

    @FXML
    private PasswordField nuevaContra;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private PasswordField verificarNuevaContra;

    private Stage stage;

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

    /**
     * Metodo que se encarga de confirmar el cambio de contraseña
     * @param event
     */
    @FXML
    void confirmarCambio(ActionEvent event) {

        //llamada el metodo de cambio de contraseña y sus verificaciones

    }

    /**
     * Metodo que se encarga de obtener la contraseña actual y setea el atributo
     * @param event
     */
    @FXML
    void setContraActual(ActionEvent event) {

        contraActual.getText();

    }

    /**
     * Metodo que se encarga de obtener la nueva contraseña y setea el atributo
     * @param event
     */
    @FXML
    void setNuevaContra(ActionEvent event) {

        nuevaContra.getText();

    }

    /**
     * Metodo que se encarga de obtener la nueva contraseña y setea el atributo
     * @param event
     */
    @FXML
    void setVerificarNuevaContra(ActionEvent event) {

        verificarNuevaContra.getText();

    }

}
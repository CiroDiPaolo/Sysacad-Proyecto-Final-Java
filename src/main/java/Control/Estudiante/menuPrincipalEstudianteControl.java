package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static Path.Path.*;

public final class menuPrincipalEstudianteControl {

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Button op1;

    @FXML
    private Button btnOp1;

    @FXML
    private Button btnOp2;

    @FXML
    private Button btnOp3;

    @FXML
    private Button btnOp4;

    @FXML
    private Button btnOp5;

    @FXML
    private Button btnOp6;

    @FXML
    private Button btnOp7;

    @FXML
    private Button btnOp8;

    @FXML
    private Button btnOp9;

    @FXML
    private Text txtBienvenida;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    /**
     * Metodo que se ejecuta al clickear el boton de materias del plan y redirige a la pantalla de materias del plan
     * @param event
     */
    @FXML
    private void clickBtnOp1(ActionEvent event) {

        stage = (Stage) btnOp1.getScene().getWindow();
        escena.cambiarEscena(materiasDelPlanEstudiante,stage,"Materias del plan");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de estado academico y redirige a la pantalla de estado academico
     * @param event
     */
    @FXML
    void clickBtnOp2(ActionEvent event) {

        escena.cambiarEscena(estadoAcademico, stage, "Estado Academico");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de notas y redirige a la pantalla de notas
     * @param event
     */
    @FXML
    void clickBtnOp3(ActionEvent event) {

        escena.cambiarEscena(notasEstudiante, stage, "Notas");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de correlatividad para cursar y redirige a la pantalla de correlatividad para cursar
     * @param event
     */
    @FXML
    void clickBtnOp4(ActionEvent event) {

        escena.cambiarEscena(correlatividadCursar,stage,"Correlatividad para cursar");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de correlatividad para rendir y redirige a la pantalla de correlatividad para rendir
     * @param event
     */
    @FXML
    void clickBtnOp5(ActionEvent event) {

        escena.cambiarEscena(correlatividadRendir,stage,"Correlatividad para rendir");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de inscripcion a examenes finales y redirige a la pantalla de inscripcion a examenes finales
     * @param event
     */
    @FXML
    void clickBtnOp6(ActionEvent event) {

        escena.cambiarEscena(inscripcionExamenFinal,stage,"Inscripcion a examenes finales");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de inscripcion a cursada y redirige a la pantalla de inscripcion a cursada
     * @param event
     */
    @FXML
    void clickBtnOp7(ActionEvent event) {

        escena.cambiarEscena(inscripcionCursada,stage,"Inscripcion a cursada");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de cambiar contraseña y redirige a la pantalla de cambiar contraseña
     * @param event
     */
    @FXML
    void clickBtnOp8(ActionEvent event) {

        escena.cambiarEscena(cambiarContra,stage,"Cambiar contraseña");

    }

    /**
     * Metodo que se ejecuta al clickear el boton de salir
     * @param event
     */
    @FXML
    void clickBtnOp9(ActionEvent event) {

        escena.cambiarEscena(inicioSesion,stage,"Inicio sesion");

    }

    /**
     * Metodo que se ejecuta al inicializar la pantalla
     */
    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            stage = (Stage) txtBienvenida.getScene().getWindow();
            setTxtBienvenida();
        });

    }

    /**
     * Metodo que setea el texto de bienvenida
     */
    protected void setTxtBienvenida() {

        String legajo = Data.getLegajo();

        Estudiante estudiante = Data.getEstudiante();

        txtBienvenida.setText("Bienvenido, " + estudiante.getNombre() + " " + estudiante.getApellido());
    }

}

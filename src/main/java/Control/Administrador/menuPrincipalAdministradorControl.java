package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.*;


public final class  menuPrincipalAdministradorControl {


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
    private Label tctMenuPrincipal;

    @FXML
    private Text txtBienvenida;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnOp1(ActionEvent event) {

        escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar Materias");

    }

    @FXML
    void clickBtnOp2(ActionEvent event) {

        escena.cambiarEscena(configurarAlumnosAdministrador,stage,"Configurar Alumno");

    }

    @FXML
    void clickBtnOp3(ActionEvent event) {

        escena.cambiarEscena(congifurarProfesorAdministrador,stage,"Configurar Profesor");

    }

    @FXML
    void clickBtnOp4(ActionEvent event) {

        escena.cambiarEscena(Path.configurarMesaExamenAdministrador,stage,"Configurar Mesa de Examen");

    }

    @FXML
    void clickBtnOp5(ActionEvent event) {

    }

    @FXML
    void clickBtnOp6(ActionEvent event) {

    }

    @FXML
    void clickBtnOp7(ActionEvent event) {

    }

    @FXML
    void clickBtnOp8(ActionEvent event) {

        escena.cambiarEscena(inicioSesion, stage, "Inicio de Sesion");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            stage = (Stage) txtBienvenida.getScene().getWindow();
            setTxtBienvenida();
        });

    }

    protected void setTxtBienvenida() {

        String legajo = Data.getLegajo();

        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameAdministrador, legajo));

    }

}

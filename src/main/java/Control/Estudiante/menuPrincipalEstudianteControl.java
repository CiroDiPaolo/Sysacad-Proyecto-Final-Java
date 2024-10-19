package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.inicioSesionData;
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

    @FXML
    //Materias del Plan
    private void clickBtnOp1(ActionEvent event) {

        stage = (Stage) btnOp1.getScene().getWindow();

        EscenaControl escena = new EscenaControl();

        escena.cambiarEscena(materiasDelPlanEstudiante,stage,"Materias del plan");

    }

    @FXML
    //Estado academico
    void clickBtnOp2(ActionEvent event) {

        stage = (Stage) btnOp2.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(estadoAcademico, stage, "Estado Academico");

    }

    @FXML
    //Notas
    void clickBtnOp3(ActionEvent event) {

        stage = (Stage) btnOp3.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(notasEstudiante, stage, "Notas");

    }

    @FXML
    //Correlatividad para cursar
    void clickBtnOp4(ActionEvent event) {
    //Correlatividad para cursar
        stage = (Stage) btnOp4.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(correlatividadCursar,stage,"Correlatividad para cursar");

    }

    @FXML
    //Correlatividad para rendir
    void clickBtnOp5(ActionEvent event) {

        stage = (Stage) btnOp5.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(correlatividadRendir,stage,"Correlatividad para rendir");

    }

    @FXML
    //Inscripcion a examenes finales
    void clickBtnOp6(ActionEvent event) {

        stage = (Stage) btnOp6.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(inscripcionExamenFinal,stage,"Inscripcion a examenes finales");

    }

    @FXML
    //Inscripcion a cursada
    void clickBtnOp7(ActionEvent event) {

        stage = (Stage) btnOp7.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(inscripcionCursada,stage,"Inscripcion a cursada");

    }

    @FXML
    //Cambiar contraseña
    void clickBtnOp8(ActionEvent event) {

        stage = (Stage) btnOp8.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(cambiarContra,stage,"Cambiar contraseña");

    }

    @FXML
    //salir
    void clickBtnOp9(ActionEvent event) {

        stage = (Stage) btnOp9.getScene().getWindow();

        stage.close();

    }

    @FXML
    protected void initialize() { setTxtBienvenida(); }

    protected void setTxtBienvenida() {
        String legajo = inicioSesionData.getLegajo();
        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameAlumnos, legajo));
    }

}

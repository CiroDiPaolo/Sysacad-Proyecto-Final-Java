package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.inicioSesionControl;
import Control.InicioSesion.inicioSesionData;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    //Materias del plan
    private void clickBtnOp1(ActionEvent event) {

        try {

            // Crear una instancia de EscenaControl
            EscenaControl escena = new EscenaControl();

            // Obtener el Stage actual
            Stage stage = (Stage) btnOp1.getScene().getWindow();

            // Cambiar la escena utilizando el método de EscenaControl
            escena.cambiarEscena(estadoAcademico, stage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    //Estado academico
    void clickBtnOp2(ActionEvent event) {

        try {

            // Crear una instancia de EscenaControl
            EscenaControl escena = new EscenaControl();

            // Obtener el Stage actual
            Stage stage = (Stage) btnOp1.getScene().getWindow();

            // Cambiar la escena utilizando el método de EscenaControl
            escena.cambiarEscena(estadoAcademico, stage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    //Notas
    void clickBtnOp3(ActionEvent event) {

        try {

            // Crear una instancia de EscenaControl
            EscenaControl escena = new EscenaControl();

            // Obtener el Stage actual
            Stage stage = (Stage) btnOp1.getScene().getWindow();

            // Cambiar la escena utilizando el método de EscenaControl
            escena.cambiarEscena(notasEstudiante, stage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    //Correlatividad para cursar
    void clickBtnOp4(ActionEvent event) {

    }

    @FXML
    //Correlatividad para rendir
    void clickBtnOp5(ActionEvent event) {

    }

    @FXML
    //Inscripcion a examenes finales
    void clickBtnOp6(ActionEvent event) {

    }

    @FXML
    //Inscripcion a cursadas
    void clickBtnOp7(ActionEvent event) {

    }

    @FXML
    //Cambiar contraseña
    void clickBtnOp8(ActionEvent event) {

    }

    @FXML
    //Salir
    void clickBtnOp9(ActionEvent event) {

        try {
            // Crear una instancia de EscenaControl
            EscenaControl escena = new EscenaControl();

            // Obtener el Stage actual
            Stage stage = (Stage) btnOp9.getScene().getWindow();

            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void initialize() { setTxtBienvenida(); }

    protected void setTxtBienvenida() {
        String legajo = inicioSesionData.getLegajo();
        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameAlumnos, legajo));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

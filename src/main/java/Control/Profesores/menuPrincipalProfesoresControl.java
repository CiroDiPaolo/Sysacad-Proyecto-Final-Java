package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.ArchivoNoEncontrado;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.fileNameProfesores;
import static Path.Path.inicioSesion;

public class menuPrincipalProfesoresControl {

    @FXML
    private Button btnComisiones;

    @FXML
    private Button btnSalir;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Text txtBienvenida;

    private Stage stage;

    /**
     * Metodo que se ejecuta al inicializar la pantalla
     */
    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnSalir.getScene().getWindow();
            setTxtBienvenida();

        });

    }

    /**
     * Metodo que setea el texto de bienvenida
     */
    protected void setTxtBienvenida() {

        String legajo = Data.getLegajo();

        try {
            txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameProfesores,legajo));
        } catch (ArchivoNoEncontrado e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnSalir(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(inicioSesion, stage, "Inicio de sesion");

    }


    @FXML
    void clickBtnComisiones(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.elegirComisionProfesor, stage, "Elegir comision");

    }

}
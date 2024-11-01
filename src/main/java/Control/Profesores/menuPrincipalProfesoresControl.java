package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.inicioSesionData;
import Path.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static Path.Path.*;

public class menuPrincipalProfesoresControl {

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Button btnSalir;

    @FXML
    private Text txtBienvenida;

    @FXML
    private Button btnCrearExcel;

    @FXML
    private Button btnVerComisiones;

    @FXML
    private Button btnVerMaterias;

    @FXML
    private Button btnVerMesas;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    /**
     * Metodo que setea el texto de bienvenida
     */
    protected void setTxtBienvenida() {

        String legajo = inicioSesionData.getLegajo();

        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameProfesores,legajo));
    }

    @FXML
    void clickBtnCrearExcel(ActionEvent event) {

    }

    @FXML
    void clickBtnVerComisiones(ActionEvent event) {

        escena.cambiarEscena(comisionesProfesores,stage,"Comisiones");

    }

    @FXML
    void clickBtnVerMaterias(ActionEvent event) {

    }

    @FXML
    void clickBtnVerMesas(ActionEvent event) {

    }

    @FXML
    void clickBtnSalir(ActionEvent event) {

        stage = (Stage) btnSalir.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(inicioSesion, stage, "Inicio de sesion");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            setTxtBienvenida();
            stage = (Stage) btnSalir.getScene().getWindow();

        });

    }

}
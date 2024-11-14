package Control.Administrador;


import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static Path.Path.*;

public class busquedaCarrera2AdministradorControl {
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtIdCarrera;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnBuscar(ActionEvent event) {
        Data data = new Data();
        try {
            data.setCarrera(manejoArchivosCarrera.retornarCarrera(pathCarreras, txtIdCarrera.getText()));
            if (Data.getCarrera().isActividad()){
                escena.cambiarEscena(elegirOpcionInscripcionAdministrador, stage, "Elegir opción");
            }else{
                throw new DatosIncorrectosException("La carrera está inactiva. No se puede modificar.");
            }
        }catch (CamposVaciosException e)
        {
            e.getMessage();
        }catch (DatosIncorrectosException e)
        {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(Path.menuPrincipalAdministrador, stage, "Menu Principal");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}

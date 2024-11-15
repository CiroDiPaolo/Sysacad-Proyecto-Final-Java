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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static Path.Path.opcionEditarCarreraAdministrador;
import static Path.Path.pathCarreras;

public class busquedaCarreraAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtIdCarrera;

    private Stage stage;

    @FXML
    void clickBtnBuscar(ActionEvent event) {
        Data data = new Data();
        try{
            data.setCarrera(manejoArchivosCarrera.retornarCarrera(pathCarreras, txtIdCarrera.getText()));
            EscenaControl escena = new EscenaControl();
            escena.cambiarEscena(opcionEditarCarreraAdministrador, stage, "Configurar carrera");

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
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalAdministrador, stage, "Menu Principal");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}

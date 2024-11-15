package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.DatosIncorrectosException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static Path.Path.*;

public class opcionEditarCarreraAdministradorControl {

    @FXML
    private Button btnVolver;

    private Stage stage;

    @FXML
    void clickBtnCarrera(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
    }

    @FXML
    void clickBtnMaterias(ActionEvent event) {
        try{
            if(Data.getCarrera().isActividad())
            {
                EscenaControl escena = new EscenaControl();
                escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar materias");
            }else{
                throw new DatosIncorrectosException("La carrera no está activa. No se puede modificar");
            }
        }catch (DatosIncorrectosException e)
        {
            e.getMessage();
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(busquedaCarreraAdministrador, stage, "Configurar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}

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

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCarrera(ActionEvent event) {
        escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
    }

    @FXML
    void clickBtnMaterias(ActionEvent event) {
        try{
            if(Data.getCarrera().isActividad())
            {
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
        escena.cambiarEscena(busquedaCarreraAdministrador, stage, "Configurar carrera");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}

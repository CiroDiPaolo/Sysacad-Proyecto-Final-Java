package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.excepcionPersonalizada;
import Modelo.Materia;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Path.Path.*;

public class buscarProfesorAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceBoxProfesor;

    private Stage stage;

    @FXML
    void clickBtnElegir(ActionEvent event) {
        String idProfesorElegido = Materia.cortarString(choiceBoxProfesor.getValue());
        Data data = new Data();
        if(idProfesorElegido!=null)
        {
            Data.setProfesor(idProfesorElegido);
            EscenaControl escena = new EscenaControl();
            escena.cambiarEscena(editarProfesorAdministrador,stage,"Editar profesor");
        }else{
            excepcionPersonalizada.excepcion("No eligió ningún profesor. Vuelva a intentarlo.");
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarProfesorAdministrador,stage,"Configurar profesor");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        ArrayList<Profesor> profesores = new ArrayList<>();
        profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);

        for(Profesor profesor : profesores)
        {
            choiceBoxProfesor.getItems().add(profesor.getLegajo()+ " - " + profesor.getNombre() + " " + profesor.getApellido());
        }

    }

}


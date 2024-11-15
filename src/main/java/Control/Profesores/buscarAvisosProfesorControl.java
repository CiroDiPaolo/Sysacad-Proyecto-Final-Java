package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosAvisos;
import Excepciones.CamposVaciosException;
import Modelo.Avisos;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.util.ArrayList;

import static Path.Path.*;

public class buscarAvisosProfesorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceboxAvisos;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ArrayList<Avisos> avisos = manejoArchivosAvisos.retornarAvisos(pathAvisos);

        for(Avisos aviso : avisos)
        {   if(aviso.getLegajoAutor().equals(Data.getLegajo()) && aviso.getLegajos().contains(Data.getComision().getId())) {
                 choiceboxAvisos.getItems().add(aviso.getId() + " - " + aviso.getFecha().toString() + " - " + aviso.getTitulo());
            }
        }
    }

    @FXML
    void clickBtnElegir(ActionEvent event) {
        try{
            if(!choiceboxAvisos.getValue().isEmpty())
            {
                Integer id = Integer.parseInt(Materia.cortarString(choiceboxAvisos.getValue()));
                Data.setAviso(manejoArchivosAvisos.retornarAviso(pathAvisos,id));
                EscenaControl escena = new EscenaControl();
                escena.cambiarEscena(editarAvisosProfesor,stage,"Editar aviso");
            }else {
                throw new CamposVaciosException("No ingresaste ningún aviso");
            }
        }catch (CamposVaciosException e)
        {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarAvisosProfesor,stage,"Configurar avisos");
    }
}

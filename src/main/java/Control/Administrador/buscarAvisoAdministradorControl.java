package Control.Administrador;

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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Path.Path.*;

public class buscarAvisoAdministradorControl {

    @FXML
    private Button btnElegir;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceboxAvisos;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ArrayList<Avisos> avisos = manejoArchivosAvisos.retornarAvisos(pathAvisos);

        for(Avisos aviso : avisos)
        {
            choiceboxAvisos.getItems().add(aviso.getId() + " - " + aviso.getFecha().toString() + " - " + aviso.getTitulo());
        }
    }

    @FXML
    void clickBtnElegir(ActionEvent event) {
        try{
            if(!choiceboxAvisos.getValue().isEmpty())
            {
                Integer id = Integer.parseInt(Materia.cortarString(choiceboxAvisos.getValue()));
                Data.setAviso(manejoArchivosAvisos.retornarAviso(pathAvisos,id));
                escena.cambiarEscena(editarAvisosAdministrador,stage,"Editar aviso");
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
        escena.cambiarEscena(configurarAvisosAdministrador,stage,"Configurar avisos");
    }

}

package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosAvisos;
import Modelo.Avisos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Path.Path.*;

public class verAvisosEstudianteControl {
    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextArea txtAreaAvisos;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ArrayList<Avisos> avisos;
        avisos = manejoArchivosAvisos.retornarAvisos(pathAvisos);
        StringBuilder stringAvisos = new StringBuilder();
        for(Avisos aviso : avisos)
        {   if(aviso.getLegajos().contains(Data.getLegajo()) || aviso.getAccesoAviso().toString().equals("TODOS"))
        {
            stringAvisos.append("===============================================================================================================================\n").append(aviso.getTitulo()).append("\n").append(aviso.getFecha().toString()).append("\n").append(aviso.getSubtitulo()).append("\n").append(aviso.getDescripcion() + "\n\n" );
        }
        }
        txtAreaAvisos.setText(String.valueOf(stringAvisos));
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(menuPrincipalAlumnos,stage,"Menú principal");
    }

}

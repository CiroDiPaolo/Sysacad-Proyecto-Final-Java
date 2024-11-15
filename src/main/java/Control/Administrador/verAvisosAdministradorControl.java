package Control.Administrador;

import Control.EscenaControl;
import ControlArchivos.manejoArchivosAvisos;
import Modelo.Avisos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Path.Path.configurarAvisosAdministrador;
import static Path.Path.pathAvisos;

public class verAvisosAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea txtAreaAvisos;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ArrayList<Avisos> avisos = new ArrayList<>();
        avisos = manejoArchivosAvisos.retornarAvisos(pathAvisos);
        StringBuilder stringAvisos = new StringBuilder();
        for(Avisos aviso : avisos)
        {
            stringAvisos.append("===============================================================================================================\n").append(aviso.getTitulo()).append("\n").append(aviso.getFecha().toString()).append("\n").append("Autor: " + aviso.getLegajoAutor()).append("\n").append(aviso.getSubtitulo()).append("\n").append(aviso.getDescripcion() + "\n\n" );
        }
        txtAreaAvisos.setText(String.valueOf(stringAvisos));
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarAvisosAdministrador,stage,"Configurar avisos");
    }

}

package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Modelo.MesaExamen;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class inscripcionFinalCompletada {

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea textArea;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {

            stage = (Stage) textArea.getScene().getWindow();

            MesaExamen mesa = Data.getMesaExamen();

            textArea.setText("INSCRIPCION A EXAMEN FINAL COMPLETADA\n" +
                    //"ID: " + mesa.getId() + "\n" +
                    "Turno: " + mesa.getTurno() + "\n" +
                    "Carrera: " + mesa.getCodigoCarrera() + "\n" +
                    "Materia: " + mesa.getCodigoMateria() + "\n" +
                    "Presidente: " + mesa.getCodigoPresidente() + "\n" +
                    "Vocales: " + String.join(", ", mesa.getVocales()) + "\n" +
                    "Fecha: " + mesa.getFecha() + "\n" +
                    "Hora: " + mesa.getHora() + "\n" +
                    "Aula: " + mesa.getAula());

        });
    }

}

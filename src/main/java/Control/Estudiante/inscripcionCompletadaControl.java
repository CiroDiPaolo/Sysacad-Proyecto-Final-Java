package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Turno;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import static ControlArchivos.manejoArchivosComisiones.*;
import static Path.Path.*;

public class inscripcionCompletadaControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea textArea;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            Estudiante copia = new Estudiante(Data.getEstudiante());

            copia.inscribirse(Data.getComision());

            try {

                if(Data.getEstudiante().actualizar(fileNameAlumnos, copia.estudianteAJSONObject())){

                    String turno = (Data.getComision().getTurno() == Turno.MANIANA) ? "MAÑANA" : Data.getComision().getTurno().toString();
                    textArea.setText("INSCRIPCIÓN COMPLETADA CON ÉXITO\n" + Data.getComision().getNombre() + "\n" + Data.getAux() + "\n" + Data.getComision().getDescripcion() + "\n" + "Turno: " + turno);
                    Data.setEstudiante(copia);
                    Data.getComision().setCupos(Data.getComision().getCupos()-1);
                    Data.getComision().actualizar(pathComisiones + generarNombreArchivoComision(Data.getEstudiante().getCodigoCarrera(), Data.getComision().getAnio()), Data.getComision().ComisionAJSONObject());
                }

            } catch (CamposVaciosException e) {
                throw new RuntimeException(e);
            } catch (DatosIncorrectosException e) {
                throw new RuntimeException(e);
            }

        });
    }


}

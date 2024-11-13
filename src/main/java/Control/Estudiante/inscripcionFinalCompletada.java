package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosProfesor;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMesa;
import Modelo.MesaExamen;
import Path.Path;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Iterator;

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

            Profesor p1 = Profesor.JSONObjectAProfesor(manejoArchivosProfesor.retornarProfesor(mesa.getCodigoPresidente(), Path.fileNameProfesores));
            Iterator<String> iterator = mesa.getVocales().iterator();
            Profesor p2 = Profesor.JSONObjectAProfesor(manejoArchivosProfesor.retornarProfesor(iterator.next(), Path.fileNameProfesores));
            Profesor p3 = Profesor.JSONObjectAProfesor(manejoArchivosProfesor.retornarProfesor(iterator.next(), Path.fileNameProfesores));

            mesa.setCupos(mesa.getCupos() - 1);

            EstadoAlumnoMesa estado = new EstadoAlumnoMesa();

            estado.setPresente(true);
            estado.setCodigoMesa(Data.getLegajo());

            mesa.getAlumnosInscriptos().add(estado);

            try {
                mesa.actualizar(Path.pathMesaExamen + "EXAMEN_" + mesa.getCodigoCarrera() + "_" + LocalDate.now().getYear() + ".json", mesa.toJSONObject());
            } catch (CamposVaciosException e) {
                throw new RuntimeException(e);
            } catch (DatosIncorrectosException e) {
                throw new RuntimeException(e);
            }

            textArea.setText("INSCRIPCION A EXAMEN FINAL COMPLETADA\n" +
                    "ID: " + mesa.getId() + "\n" +
                    "Turno: " + mesa.getTurno() + "\n" +
                    "Carrera: " + mesa.getCodigoCarrera() + "\n" +
                    "Materia: " + mesa.getCodigoMateria() + "\n" +
                    "Presidente: " + p1.getApellido() + " " + p1.getNombre() + "\n" +
                    "Vocales: " + p2.getApellido() + " " + p2.getNombre() + " y " + p3.getApellido() + " " + p3.getNombre() + "\n" +
                    "Fecha: " + mesa.getFecha() + "\n" +
                    "Hora: " + mesa.getHora() + "\n" +
                    "Aula: " + mesa.getAula());

        });
    }

}

package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class mostrarAlumnoControl {

    @FXML
    private Button btnVolver;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrimerParcial;

    @FXML
    private TextField txtSegundoParcial;

    @FXML
    private ChoiceBox<String> choiceAlumno;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.gestionarComisionProfesor, stage, "Gestionar Comisión");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<Estudiante> estudiantes = manejoArchivosComisiones.obtenerEstudiantesDeUnaComision(Path.fileNameAlumnos, Data.getComision().getId());

            for (Estudiante estudiante : estudiantes) {

                if((estudiante.getNombre() + " " + estudiante.getApellido()).equals(Data.getAux2()))
                {
                    choiceAlumno.getItems().add(estudiante.getNombre() + " " + estudiante.getApellido() + " - " + estudiante.getLegajo());
                }

            }

            choiceAlumno.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

                if (newValue != null) {

                    for (Estudiante estudiante : estudiantes) {

                        String nombreCompleto = estudiante.getNombre() + " " + estudiante.getApellido() + " - " + estudiante.getLegajo();

                        if (nombreCompleto.equals(newValue)) {
                            txtNombre.setText(estudiante.getNombre());
                            txtApellido.setText(estudiante.getApellido());

                            for(int i = 0; i < estudiante.getMaterias().size(); i++)
                            {
                                if (estudiante.getMaterias().get(i).getCodigoMateria().equals(Data.getComision().getCodigoMateria())) {
                                    Integer primerParcial = estudiante.getMaterias().get(i).getNotas().get("primerParcial");
                                    Integer segundoParcial = estudiante.getMaterias().get(i).getNotas().get("segundoParcial");

                                    txtPrimerParcial.setText(primerParcial != null ? String.valueOf(primerParcial) : "-");
                                    txtSegundoParcial.setText(segundoParcial != null ? String.valueOf(segundoParcial) : "-");
                                    txtEstado.setText(estudiante.getMaterias().get(i).getEstado().toString());
                                }
                            }

                            break;
                        }
                    }
                }
            });
        });
    }
}

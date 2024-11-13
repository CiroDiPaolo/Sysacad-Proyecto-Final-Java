package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoMateria;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.UnaryOperator;

public class gestionarNotasAlumnosControl {

    @FXML
    private Button btnAplicar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkPromocionado;

    @FXML
    private ChoiceBox<String> choiceAlumno;

    @FXML
    private TextField txtPrimerParcial;

    @FXML
    private TextField txtSegundoParcial;

    private Stage stage;

    /**
     * Método que se ejecuta al hacer click en el botón "Aplicar"
     * Se encarga de actualizar las notas del alumno seleccionado
     * @param event
     * @throws CamposVaciosException
     * @throws DatosIncorrectosException
     */
    @FXML
    void clickBtnAplicar(ActionEvent event) throws CamposVaciosException, DatosIncorrectosException {

        try {

            Estudiante e = new Estudiante(Data.getEstudiante());

            for (int i = 0; i < Data.getEstudiante().getMaterias().size(); i++) {

                if (e.getMaterias().get(i).getCodigoComision().equals(Data.getComision().getId())) {

                    HashMap<String, Integer> notas = new HashMap<>();

                    // Usamos un valor por defecto de 0 si el texto es "-"
                    int primerParcial = txtPrimerParcial.getText().equals("-") ? 0 : Integer.parseInt(txtPrimerParcial.getText());
                    int segundoParcial = txtSegundoParcial.getText().equals("-") ? 0 : Integer.parseInt(txtSegundoParcial.getText());

                    notas.put("primerParcial", primerParcial);
                    notas.put("segundoParcial", segundoParcial);

                    e.getMaterias().get(i).setNotas(notas);

                    // Determinamos el estado de la materia
                    if (checkPromocionado.isSelected()) {
                        e.getMaterias().get(i).setEstado(EstadoMateria.APROBADA);
                    } else {
                        if (primerParcial > 5 && segundoParcial > 5) {
                            e.getMaterias().get(i).setEstado(EstadoMateria.REGULARIZADA);
                        } else {
                            e.getMaterias().get(i).setEstado(EstadoMateria.NO_REGULARIZADA);
                        }
                    }
                }
            }

            if (Data.getEstudiante().actualizar(Path.fileNameAlumnos, e.estudianteAJSONObject())) {
                Excepciones.excepcionPersonalizada.alertaConfirmacion("Notas actualizadas correctamente!");
            }

        } catch (CamposVaciosException e) {
            throw new CamposVaciosException("Debe seleccionar un alumno");
        } catch (DatosIncorrectosException e) {
            throw new DatosIncorrectosException("Datos incorrectos");
        } catch (NumberFormatException e) {
            Excepciones.excepcionPersonalizada.alertaAtencion("Por favor ingrese un número válido o '-' para indicar que no hay nota.");
        }

        initialize();
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.gestionarComisionProfesor, stage, "Gestionar comision");

    }

    /**
     * Método que se ejecuta al iniciar
     * Se encarga de cargar los alumnos de la comisión y sus respectivas notas
     */
    @FXML
    protected void initialize() {
        Platform.runLater(() -> {

            choiceAlumno.getItems().clear();
            txtSegundoParcial.clear();
            txtPrimerParcial.clear();
            checkPromocionado.setSelected(false);

            stage = (Stage) btnVolver.getScene().getWindow();

            ArrayList<Estudiante> estudiantes = manejoArchivosComisiones.obtenerEstudiantesDeUnaComision(Path.fileNameAlumnos, Data.getComision().getId());

            for (Estudiante estudiante : estudiantes) {
                choiceAlumno.getItems().add(estudiante.getNombre() + " " + estudiante.getApellido() + " - " + estudiante.getLegajo());
            }

            choiceAlumno.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) {
                    // Si newValue es null, no se hace nada
                    return;
                }

                String legajo = newValue.substring(newValue.lastIndexOf("-") + 2);

                Estudiante e = new Estudiante();

                for(int i = 0 ; i < estudiantes.size() ; i++) {
                    if(estudiantes.get(i).getLegajo().equals(legajo)) {
                        e = estudiantes.get(i);
                        break;
                    }
                }

                ArrayList<String> parciales = manejoArchivosEstudiante.filtrarParcialesPorMateria(e.obtenerParcialesRendidos(), Data.getComision().getCodigoMateria());

                txtPrimerParcial.setText(parciales.size() > 0 && !parciales.get(0).equals("0") ? parciales.get(0) : "-");
                txtSegundoParcial.setText(parciales.size() > 1 && !parciales.get(1).equals("0") ? parciales.get(1) : "-");

                checkPromocionado.setSelected(e.getMaterias().stream().anyMatch(materia -> materia.getCodigoComision().equals(Data.getComision().getId()) && materia.getEstado() == EstadoMateria.APROBADA));

                Data.setEstudiante(e);
            });

            // Agregar TextFormatter para permitir solo números o "-"
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                if (newText.matches("-?\\d*")) {
                    return change;
                }
                return null;
            };

            txtPrimerParcial.setTextFormatter(new TextFormatter<>(filter));
            txtSegundoParcial.setTextFormatter(new TextFormatter<>(filter));
        });
    }

}

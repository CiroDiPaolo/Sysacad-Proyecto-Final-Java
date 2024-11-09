package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMateria;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.*;

public class editarMateriaAlumnoAdministradorControl implements Cloneable {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnVolver1;

    @FXML
    private ChoiceBox<String> choiceMateria;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtComision;

    @FXML
    private TextField txtFolio;

    @FXML
    private TextField txtPrimerParcial;

    @FXML
    private TextField txtSegundoParcial;

    @FXML
    private TextField txtTomo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) throws DatosIncorrectosException {
        try {

            EstadoAlumnoMateria estadoOriginal = Data.getEstudiante().getMaterias().get(Integer.parseInt(Data.getAux2()));
            EstadoAlumnoMateria estado = new EstadoAlumnoMateria(estadoOriginal);

            estado.setCodigoComision(txtComision.getText());

            int tomo = Integer.parseInt(txtTomo.getText());

            if (tomo < 0 || tomo > 999) {
                throw new DatosIncorrectosException("El tomo debe estar entre 0 y 999");
            }

            estado.setTomo(txtTomo.getText());

            int folio = Integer.parseInt(txtFolio.getText());

            if (folio < 0 || folio > 999) {
                throw new DatosIncorrectosException("El folio debe estar entre 0 y 999");
            }

            estado.setFolio(txtFolio.getText());

            HashMap<String, Integer> notas = new HashMap<>();

            int primerParcial = Integer.parseInt(txtPrimerParcial.getText());
            int segundoParcial = Integer.parseInt(txtSegundoParcial.getText());

            if (segundoParcial < 0 || segundoParcial > 10 || primerParcial < 0 || primerParcial > 10) {
                throw new DatosIncorrectosException("Las notas deben estar entre 0 y 10");
            }

            notas.put("primerParcial", primerParcial);
            notas.put("segundoParcial", segundoParcial);

            estado.setNotas(notas);

            Estudiante original = new Estudiante(Data.getEstudiante());
            Estudiante copia = new Estudiante(Data.getEstudiante());

            copia.getMaterias().set(Integer.parseInt(Data.getAux2()), estado);

            if (original.actualizar(Path.fileNameAlumnos, copia.estudianteAJSONObject())) {
                Excepciones.excepcionPersonalizada.alertaConfirmacion("Materia actualizada correctamente");
                EscenaControl escena = new EscenaControl();
                escena.cambiarEscena(Path.configurarAlumnosAdministrador, stage, "Configurar Alumno");
            }

        } catch (NumberFormatException e) {
            throw new DatosIncorrectosException("Todos los campos deben contener números enteros válidos");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.editarAlumnoAdministrador, stage, "Configurar Alumno");

    }

    private void actualizarTextFields(String codigoMateria, String nombreMateria) {

        for (int i = 0; i < Data.getEstudiante().getMaterias().size(); i++) {
            if (Data.getEstudiante().getMaterias().get(i).getCodigoMateria().equals(codigoMateria)) {
                txtComision.setText(Data.getEstudiante().getMaterias().get(i).getCodigoComision());
                txtFolio.setText(Data.getEstudiante().getMaterias().get(i).getFolio());
                txtPrimerParcial.setText(Data.getEstudiante().getMaterias().get(i).getNotas().get("primerParcial").toString());
                txtSegundoParcial.setText(Data.getEstudiante().getMaterias().get(i).getNotas().get("segundoParcial").toString());
                txtTomo.setText(Data.getEstudiante().getMaterias().get(i).getTomo());

                Data.setAux2(String.valueOf(i));

                i = Data.getEstudiante().getMaterias().size();
            }

        }

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();

            HashMap<String, String> materia = Data.getEstudiante().obtenerMaterias();

            for (Map.Entry<String, String> entry : materia.entrySet()) {
                choiceMateria.getItems().add(entry.getValue());
            }

            choiceMateria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    String selectedKey = materia.entrySet().stream().filter(entry -> newValue.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().orElse(null);
                    if (selectedKey != null) {

                        actualizarTextFields(selectedKey, newValue);

                    }
                }
            });
        });
    }
}
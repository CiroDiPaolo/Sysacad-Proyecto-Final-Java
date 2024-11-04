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

public class editarMateriaAlumnoAdministradorControl {

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
    void clickBtnCargar(ActionEvent event) {
        try {
            EstadoAlumnoMateria estado = new EstadoAlumnoMateria();

            estado.setCodigoComision(txtComision.getText());
            estado.setFolio(txtFolio.getText());
            estado.setTomo(txtTomo.getText());

            HashMap<String, Integer> notas = new HashMap<>();
            notas.put("primerParcial", Integer.parseInt(txtPrimerParcial.getText()));
            notas.put("segundoParcial", Integer.parseInt(txtSegundoParcial.getText()));
            estado.setNotas(notas);

            Estudiante original = new Estudiante(Data.getEstudiante());

            Estudiante copia = new Estudiante(Data.getEstudiante());

            copia.getMaterias().set(Integer.parseInt(Data.getAux()), estado);

            System.out.println(copia.estudianteAJSONObject());

            original.actualizar(Path.fileNameAlumnos, copia.estudianteAJSONObject());


        } catch (NumberFormatException e) {
            // Manejo de excepción para entradas no numéricas
            System.err.println("Error: Las notas deben ser números enteros.");
        } catch (DatosIncorrectosException e) {
            // Manejo de excepción para datos incorrectos
            System.err.println("Error: Datos incorrectos.");
        } catch (Exception e) {
            // Manejo de cualquier otra excepción
            System.err.println("Error: " + e.getMessage());
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

                Data.setAux(String.valueOf(i));

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

package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Modelo.EstadoAlumnoMateria;
import Modelo.Materia;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Path.Path.menuPrincipalAlumnos;
import static Path.Path.pathCarreras;

/**
 * Controlador para la pantalla de notas de un estudiante.
 */
public class notasEstudiantesControl {

    private Stage stage;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colAnio;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colComision;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colFinal;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colMateria;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colPParcial;

    @FXML
    private TableColumn<NotasMateriaTableData, String> colSParcial;

    @FXML
    private TableView<NotasMateriaTableData> tableView;

    /**
     * Método que se ejecuta al clickear el botón volver.
     * @param event El evento de clic.
     */
    @FXML
    void clickBtnVolver(ActionEvent event) {
        stage = (Stage) btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");
    }

    /**
     * Inicializa la tabla con las notas del estudiante.
     */
    @FXML
    protected void initialize() {
        Platform.runLater(() -> {

            Estudiante estudiante = Data.getEstudiante();

            ArrayList<EstadoAlumnoMateria> estadoAlumnoMaterias = (ArrayList<EstadoAlumnoMateria>) estudiante.getMaterias();

            ObservableList<NotasMateriaTableData> data = FXCollections.observableArrayList();

            for (EstadoAlumnoMateria estadoMateria : estadoAlumnoMaterias) {

                HashMap<String, Integer> notas = estadoMateria.getNotas();

                String pParcial = (notas.containsKey("primerParcial") && notas.get("primerParcial") != 0) ? String.valueOf(notas.get("primerParcial")) : "N/A";
                String sParcial = (notas.containsKey("segundoParcial") && notas.get("segundoParcial") != 0) ? String.valueOf(notas.get("segundoParcial")) : "N/A";
                String finalNota = notas.containsKey("final") ? String.valueOf(notas.get("final")) : "N/A";

                String materiaCodigo = estadoMateria.getCodigoMateria(); // Nombre de la materia.
                String materia = (manejoArchivosCarrera.obtenerMateria(pathCarreras, materiaCodigo)).getNombre();
                String anio = (manejoArchivosCarrera.obtenerMateria(pathCarreras, materiaCodigo)).getAnio();
                String comision = estadoMateria.getCodigoComision(); // Comisión de la materia.

                data.add(new NotasMateriaTableData(materia, anio, comision, pParcial, sParcial, finalNota));
            }

            colMateria.setCellValueFactory(cellData -> cellData.getValue().materiaProperty());
            colAnio.setCellValueFactory(cellData -> cellData.getValue().anioProperty());
            colComision.setCellValueFactory(cellData -> cellData.getValue().comisionProperty());
            colPParcial.setCellValueFactory(cellData -> cellData.getValue().primerParcialProperty());
            colSParcial.setCellValueFactory(cellData -> cellData.getValue().segundoParcialProperty());
            colFinal.setCellValueFactory(cellData -> cellData.getValue().notaFinalProperty());

            tableView.setItems(data);
        });
    }

    /**
     * Clase interna que representa los datos de cada fila de la tabla de notas.
     */
    public static class NotasMateriaTableData {

        private SimpleStringProperty materia;
        private SimpleStringProperty anio;
        private SimpleStringProperty comision;
        private SimpleStringProperty primerParcial;
        private SimpleStringProperty segundoParcial;
        private SimpleStringProperty notaFinal;

        public NotasMateriaTableData(String materia, String anio, String comision, String primerParcial, String segundoParcial, String notaFinal) {
            this.materia = new SimpleStringProperty(materia);
            this.anio = new SimpleStringProperty(anio);
            this.comision = new SimpleStringProperty(comision);
            this.primerParcial = new SimpleStringProperty(primerParcial);
            this.segundoParcial = new SimpleStringProperty(segundoParcial);
            this.notaFinal = new SimpleStringProperty(notaFinal);
        }

        // Getters
        public String getMateria() {
            return materia.get();
        }

        public String getAnio() {
            return anio.get();
        }

        public String getComision() {
            return comision.get();
        }

        // Métodos de propiedad para que la tabla pueda observar los valores
        public SimpleStringProperty materiaProperty() {
            return materia;
        }

        public SimpleStringProperty anioProperty() {
            return anio;
        }

        public SimpleStringProperty comisionProperty() {
            return comision;
        }

        public SimpleStringProperty primerParcialProperty() {
            return primerParcial;
        }

        public SimpleStringProperty segundoParcialProperty() {
            return segundoParcial;
        }

        public SimpleStringProperty notaFinalProperty() {
            return notaFinal;
        }
    }
}

package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.Carrera;
import Modelo.EstadoAlumnoMateria;
import Modelo.Materia;
import Path.Path;
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

import static Path.Path.menuPrincipalAlumnos;
import static Path.Path.pathCarreras;

/**
 * Controlador para la pantalla de estado académico de un estudiante.
 */
public class estadoAcademicoControl {

    private Stage stage;

    @FXML
    private Button btnVolver;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colAnio;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colEstado;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colFolio;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colMateria;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colPlan;

    @FXML
    private TableColumn<EstadoMateriaTableData, String> colTomo;

    @FXML
    private TableView<EstadoMateriaTableData> tableView;

    @FXML
    private Label tctMenuPrincipal;

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
     * Inicializa la tabla con los datos del estudiante.
     */
    @FXML
    protected void initialize() {
        Platform.runLater(() -> {

            try {
                Carrera carrera = manejoArchivosCarrera.retornarCarrera(pathCarreras, Data.getEstudiante().getCodigoCarrera());

                Estudiante estudiante = Data.getEstudiante();

                ArrayList<EstadoAlumnoMateria> estadoAlumnoMaterias = (ArrayList<EstadoAlumnoMateria>) estudiante.getMaterias();

                ObservableList<EstadoMateriaTableData> data = FXCollections.observableArrayList();

                for (EstadoAlumnoMateria estadoMateria : estadoAlumnoMaterias) {

                    String materiaCodigo = estadoMateria.getCodigoMateria(); // Nombre de la materia.
                    String materia = (manejoArchivosCarrera.obtenerMateria(pathCarreras, materiaCodigo)).getNombre();
                    String anio = (manejoArchivosCarrera.obtenerMateria(pathCarreras, materiaCodigo)).getAnio(); // Año de la materia.
                    String estado = estadoMateria.getEstado().toString(); // Estado (aprobado, pendiente, etc.).
                    String folio = estadoMateria.getFolio(); // Folio de la materia.
                    String plan = carrera.getPlan(); // Plan de la materia.
                    String tomo = estadoMateria.getTomo(); // Tomo de la materia.

                    data.add(new EstadoMateriaTableData(materia, anio, estado, folio, plan, tomo));
                }

                colMateria.setCellValueFactory(cellData -> cellData.getValue().materiaProperty());
                colAnio.setCellValueFactory(cellData -> cellData.getValue().anioProperty());
                colEstado.setCellValueFactory(cellData -> cellData.getValue().estadoProperty());
                colFolio.setCellValueFactory(cellData -> cellData.getValue().folioProperty());
                colPlan.setCellValueFactory(cellData -> cellData.getValue().planProperty());
                colTomo.setCellValueFactory(cellData -> cellData.getValue().tomoProperty());


                tableView.setItems(data);

                } catch (CamposVaciosException e) {
                    throw new RuntimeException(e);
                } catch (DatosIncorrectosException e) {
                    throw new RuntimeException(e);
                }

        });
    }

    /**
     * Clase interna que representa los datos de cada fila de la tabla.
     */
    public static class EstadoMateriaTableData {

        private SimpleStringProperty materia;
        private SimpleStringProperty anio;
        private SimpleStringProperty estado;
        private SimpleStringProperty folio;
        private SimpleStringProperty plan;
        private SimpleStringProperty tomo;

        public EstadoMateriaTableData(String materia, String anio, String estado, String folio, String plan, String tomo) {
            this.materia = new SimpleStringProperty(materia);
            this.anio = new SimpleStringProperty(anio);
            this.estado = new SimpleStringProperty(estado);
            this.folio = new SimpleStringProperty(folio);
            this.plan = new SimpleStringProperty(plan);
            this.tomo = new SimpleStringProperty(tomo);
        }

        // Getters
        public String getMateria() {
            return materia.get();
        }

        public String getAnio() {
            return anio.get();
        }

        public String getEstado() {
            return estado.get();
        }

        public String getPlan() {
            return plan.get();
        }

        // Métodos de propiedad para que la tabla pueda observar los valores
        public SimpleStringProperty materiaProperty() {
            return materia;
        }

        public SimpleStringProperty anioProperty() {
            return anio;
        }

        public SimpleStringProperty estadoProperty() {
            return estado;
        }

        public SimpleStringProperty folioProperty() {
            return folio;
        }

        public SimpleStringProperty planProperty() {
            return plan;
        }

        public SimpleStringProperty tomoProperty() {
            return tomo;
        }
    }
}

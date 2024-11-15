package Control.Estudiante;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosMesaExamen;
import Modelo.EstadoMateria;
import Modelo.Materia;
import Modelo.MesaExamen;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import ControlArchivos.*;
import java.time.LocalDate;
import static Path.Path.*;

public class inscripcionExamenFinalControl {

    @FXML
    private TableView<MesaExamen> tableTablaMaterias;

    @FXML
    private TableColumn<MesaExamen, String> colMateria;

    @FXML
    private TableColumn<MesaExamen, String> colDia;

    @FXML
    private TableColumn<MesaExamen, String> colHorario;

    @FXML
    private TableColumn<MesaExamen, String> colProfesor;

    @FXML
    private TableColumn<MesaExamen, Void> colBotones;

    @FXML
    private Button btnVolver;

    private Stage stage;

    /**
     * Metodo que se ejecuta al clickear el boton volver
     * @param event
     */
    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(menuPrincipalAlumnos, stage, "Menu Principal");
    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        colMateria.setCellValueFactory(cellData -> {
            MesaExamen mesa = cellData.getValue();
            String codigoMateria = mesa.getCodigoMateria();
            String nombreMateria = obtenerNombreMateria(codigoMateria);
            return new SimpleStringProperty(nombreMateria);
        });

        colDia.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("hora"));

        colProfesor.setCellValueFactory(cellData -> {
            MesaExamen mesa = cellData.getValue();
            String codigoProfesor = mesa.getCodigoPresidente();

            Data.setProfesor(codigoProfesor);

            Profesor profesor = Data.getProfesor();
            String nombreProfesor = (profesor != null) ? profesor.getNombre() : "Profesor Desconocido";

            return new SimpleStringProperty(nombreProfesor);
        });

        colBotones.setCellFactory(new Callback<>() {
            @Override
            public TableCell<MesaExamen, Void> call(final TableColumn<MesaExamen, Void> param) {
                return new TableCell<>() {

                    private final Button btnInscribirse = new Button("Inscribirse");

                    {
                        btnInscribirse.setOnAction((ActionEvent event) -> {
                            MesaExamen mesaExamen = getTableView().getItems().get(getIndex());

                            Data data = new Data();
                            data.setMesaExamen(mesaExamen);

                            EscenaControl escena = new EscenaControl();
                            escena.cambiarEscena(inscripcionMateriaCompletada, stage, "Inscripcion a Examen Final Completada");

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnInscribirse);
                        }
                    }
                };
            }
        });

        tableTablaMaterias.getItems().setAll(Data.getEstudiante().obtenerMesasExamenesParaAnotarse(Data.getEstudiante().obtenerMateriasSegunEstado(EstadoMateria.APROBADA),manejoArchivosMesaExamen.obtenerMesaExamenPorAnio(LocalDate.now().getYear(), Data.getEstudiante().getCodigoCarrera())));

    }

    /**
     * Método auxiliar para obtener el nombre de la materia a partir del código.
     */
    private String obtenerNombreMateria(String codigoMateria) {
        Materia materia = manejoArchivosCarrera.obtenerMateria(pathCarreras, codigoMateria);
        return (materia != null) ? materia.getNombre() : "Materia Desconocida";
    }
}

package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosMesaExamen;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMesa;
import Modelo.MesaExamen;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Path.Path;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public final class editarMesaExamenAlumnoAdministradorControl {

    //Declaracion de los componentes de la interfaz de usuario que va a manipular

    @FXML
    private Button btnActualizar;

    @FXML
    private ChoiceBox<String> choiceMesa;

    @FXML
    private TextField txtNota;

    private Stage stage;

    //Metodos

    @FXML
    void clickBtnActualizar(ActionEvent event) throws DatosIncorrectosException {
        try {
            Estudiante estudianteOriginal = Data.getEstudiante();
            Estudiante e = new Estudiante(estudianteOriginal);

            int nota = Integer.parseInt(txtNota.getText());

            if (nota < 0 || nota > 10) {
                throw new DatosIncorrectosException("La nota debe ser un número entero entre 0 y 10.");
            }

            String auxCodigoMesa = "";

            for (Map.Entry<String, EstadoAlumnoMesa> entry : e.getMaterias().get(Integer.parseInt(Data.getAux2())).getMesasExamen().entrySet()) {
                if (entry.getValue().getCodigoMesa().equals(choiceMesa.getValue())) {
                    auxCodigoMesa = entry.getValue().getCodigoMesa();
                    entry.getValue().setNota(nota);
                }
            }

            if(estudianteOriginal.actualizar(Path.fileNameAlumnos,e.estudianteAJSONObject())){

                MesaExamen mesa = manejoArchivosMesaExamen.buscarMesaExamen(Path.pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(estudianteOriginal.getCodigoCarrera(),LocalDate.now().getYear()),"id",auxCodigoMesa);

                manejoArchivosMesaExamen.actualizarEstadoEstudiante(mesa,estudianteOriginal.getLegajo(),nota);

                if (mesa.actualizar(Path.pathMesaExamen + manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(estudianteOriginal.getCodigoCarrera(), LocalDate.now().getYear()), mesa.toJSONObject())) {

                    Excepciones.excepcionPersonalizada.alertaConfirmacion("Nota actualizada con éxito.");
                    EscenaControl escena = new EscenaControl();
                    escena.cambiarEscena(Path.editarAlumnoAdministrador, stage, "Configurar Alumno");

                }

            }

        } catch (NumberFormatException e) {
            throw new DatosIncorrectosException("La nota debe ser un número entero.");
        } catch (CamposVaciosException e) {
            throw new DatosIncorrectosException("No se pudo actualizar la nota.");
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.editarAlumnoAdministrador, stage, "Configurar Alumno");

    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {

            stage = (Stage) btnActualizar.getScene().getWindow();

            HashMap<String, EstadoAlumnoMesa> mesas = Data.getEstudiante().obtenerMesasDeExamen();

            for (Map.Entry<String, EstadoAlumnoMesa> entry : mesas.entrySet()) {
                choiceMesa.getItems().add(entry.getValue().getCodigoMesa());
            }

            choiceMesa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    for (Map.Entry<String, EstadoAlumnoMesa> entry : mesas.entrySet()) {
                        if (entry.getValue().getCodigoMesa().equals(newValue)) {
                            actualizarTextFields(entry.getKey());
                            break;
                        }
                    }
                }
            });
        });
    }

    private void actualizarTextFields(String codigoMesa) {
        EstadoAlumnoMesa mesa = Data.getEstudiante().obtenerMesasDeExamen().get(codigoMesa);
        if (mesa != null) {
            txtNota.setText(String.valueOf(mesa.getNota()));
        }
    }

}

package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Modelo.EstadoAlumnoMesa;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Path.Path;
import java.util.HashMap;
import java.util.Map;

public final class editarMesaExamenAlumnoAdministradorControl {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceMesa;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtNota;

    private Stage stage;


    @FXML
    void clickBtnActualizar(ActionEvent event) throws DatosIncorrectosException {
        try {
            Estudiante estudianteOriginal = Data.getEstudiante();
            Estudiante e = new Estudiante(estudianteOriginal);

            int nota = Integer.parseInt(txtNota.getText());

            if (nota < 0 || nota > 10) {
                throw new DatosIncorrectosException("La nota debe ser un número entero entre 0 y 10.");
            }

            for (int i = 0 ; i < e.getMaterias().get(Integer.parseInt(Data.getAux2())).getMesasExamen().size(); i++){

                System.out.println(e.getMaterias().get(Integer.parseInt(Data.getAux2())).getMesasExamen().get("Mesa"+(i+1)).getCodigoMesa() + "codigo de mesa");

                if(e.getMaterias().get(Integer.parseInt(Data.getAux2())).getMesasExamen().get("Mesa"+(i+1)).getCodigoMesa().equals(choiceMesa.getValue())){

                    e.getMaterias().get(Integer.parseInt(Data.getAux2())).getMesasExamen().get("Mesa"+(i+1)).setNota(nota);

                }

            }

            if(estudianteOriginal.actualizar(Path.fileNameAlumnos,e.estudianteAJSONObject())){
                Excepciones.excepcionPersonalizada.alertaConfirmacion("Nota actualizada con éxito.");
                EscenaControl escena = new EscenaControl();
                escena.cambiarEscena(Path.editarAlumnoAdministrador, stage, "Configurar Alumno");
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

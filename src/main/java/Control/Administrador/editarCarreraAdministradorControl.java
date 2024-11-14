package Control.Administrador;


import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.excepcionPersonalizada;
import Modelo.Carrera;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;

import static Path.Path.configurarCarreraAdministrador;
import static Path.Path.pathCarreras;

public class editarCarreraAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPlan;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnActualizar(ActionEvent event) {
        String id = txtID.getText().trim();
        String nombre = txtNombre.getText().trim();
        String plan = txtPlan.getText().trim();
        boolean actividad = checkBoxActividad.isSelected();
        HashMap<String, Materia> materias = Data.getCarrera().getMaterias();
        Carrera carrera = new Carrera(id,nombre,plan,materias,actividad);
        try{
            if(carrera.actualizar(pathCarreras,carrera.carreraAJSONObject())){
                excepcionPersonalizada.alertaConfirmacion("¡La carrera ha sido correctamente actualizada!");
                escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
            }
        } catch (CamposVaciosException e) {
            e.getMessage();
        } catch (DatosIncorrectosException e) {
            e.getMessage();
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(configurarCarreraAdministrador,stage,"Configurar carrera");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        txtID.setText(Data.getCarrera().getId());
        txtNombre.setText(Data.getCarrera().getNombre());
        txtPlan.setText(Data.getCarrera().getPlan());
        checkBoxActividad.setSelected(Data.getCarrera().isActividad());
    }

}

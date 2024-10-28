package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.DatosIncorrectosException;
import Excepciones.excepcionPersonalizada;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

public class editarAlumnoAdministradorControl {


    @FXML
    private Button btnCargar;

    @FXML
    private Button btnEditarMaterias;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private Text txtEditarAlumno;

    @FXML
    private TextField txtNombre;

    @FXML
    private CheckBox checkActivo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {
        Estudiante original = Data.getEstudiante();
        Estudiante e = new Estudiante(original); // Crear una copia del estudiante original

        e.setNombre(txtNombre.getText());
        e.setApellido(txtApellido.getText());
        e.setDni(txtDni.getText());
        e.setCorreo(txtCorreo.getText());
        e.setActividad(checkActivo.isSelected());

        if (!original.compararEstudiantes(e)) {

            try {

                if (original.actualizar(Path.fileNameAlumnos, e.estudianteAJSONObject())) {

                    excepcionPersonalizada.alertaConfirmacion("Alumno actualizado correctamente");
                    EscenaControl escena = new EscenaControl();
                    escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage, "Menu Administrador");

                }

            } catch (DatosIncorrectosException ex) {
                ex.getMessage();
            }
        } else {
            excepcionPersonalizada.excepcion("No se realizaron cambios");
        }
    }

    @FXML
    void clickBtnEditarMaterias(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.editarMateriaAdministrador,stage, "Editar Materias");

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        stage = (Stage) btnCargar.getScene().getWindow();
        escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage, "Menu Administrador");

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnCargar.getScene().getWindow();

            Data data = new Data();

            JSONObject estudiante = manejoArchivosEstudiante.retornarEstudiante(data.getAux(), Path.fileNameAlumnos);

            Estudiante e = Estudiante.JSONObjectAEstudiante(estudiante);

            Data.setEstudiante(e);

            txtNombre.setText(e.getNombre());
            txtApellido.setText(e.getApellido());
            txtDni.setText(e.getDni());
            txtCorreo.setText(e.getCorreo());

            if(e.getActividad()) {
                checkActivo.setSelected(true);
            }

        });

    }

}
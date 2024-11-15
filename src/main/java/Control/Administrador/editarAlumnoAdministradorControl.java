package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosEstudiante;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.excepcionPersonalizada;
import Path.Path;
import Usuarios.Estudiante;
import Usuarios.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import static Path.Path.editarMateriaAlumnoAdministrador;

public class editarAlumnoAdministradorControl {


    @FXML
    private Button btnCargar;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtContrasenia;


    @FXML
    private TextField txtNombre;

    @FXML
    private CheckBox checkActivo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) throws CamposVaciosException, DatosIncorrectosException {
        Estudiante original = Data.getEstudiante();
        Estudiante e = new Estudiante(original);

        e.setNombre(txtNombre.getText());
        e.setApellido(txtApellido.getText());
        e.setDni(txtDni.getText());
        e.setCorreo(txtCorreo.getText());
        e.setActividad(checkActivo.isSelected());
        e.setContrasenia(txtContrasenia.getText());

        if(e.getNombre().isEmpty() || e.getApellido().isEmpty() || e.getDni().isEmpty() || e.getCorreo().isEmpty() || e.getContrasenia().isEmpty()) {

            throw new CamposVaciosException("No se permiten campos vacios");

        }

        if(!Usuario.esCorreoValido(e.getCorreo())) {

            throw new DatosIncorrectosException("Ingrese un correo valido.");

        }

        if (!original.compararEstudiantes(e)) {

            try {

                if (original.actualizar(Path.fileNameAlumnos, e.estudianteAJSONObject())) {

                    excepcionPersonalizada.alertaConfirmacion("Alumno actualizado correctamente");
                    EscenaControl escena = new EscenaControl();
                    escena.cambiarEscena(Path.configurarAlumnosAdministrador,stage, "Menu Administrador");

                }

            } catch (DatosIncorrectosException ex) {
                ex.getMessage();
            } catch (Excepciones.CamposVaciosException e1) {
                throw new RuntimeException(e1);
            }
        } else {
            excepcionPersonalizada.excepcion("No se realizaron cambios");
        }
    }

    @FXML
    void clickBtnEditarMaterias(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(editarMateriaAlumnoAdministrador,stage, "Editar Materias");

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
            txtContrasenia.setText(e.getContrasenia());

            if(e.getActividad()) {
                checkActivo.setSelected(true);
            }

        });

    }

}
package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosEstudiante;
import Path.Path;
import Usuarios.Estudiante;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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


    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {

    }

    @FXML
    void clickBtnEditarMaterias(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        stage = (Stage) btnCargar.getScene().getWindow();
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

            Data data = new Data();

            JSONObject estudiante = manejoArchivosEstudiante.retornarEstudiante(data.getAux(), Path.fileNameAlumnos);

            System.out.println(data.getAux());

            Estudiante e = Estudiante.JSONObjectAEstudiante(estudiante);

            txtNombre.setText(e.getNombre());
            txtApellido.setText(e.getApellido());
            txtDni.setText(e.getDni());
            txtCorreo.setText(e.getCorreo());

        });

    }

}


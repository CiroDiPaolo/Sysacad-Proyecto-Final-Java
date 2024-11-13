package Control.Administrador;


import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.excepcionPersonalizada;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static Path.Path.buscarProfesorAdministrador;
import static Path.Path.fileNameProfesores;

public class editarProfesorAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private Rectangle lista;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();
        String legajo = Data.getProfesor().getLegajo();
        boolean actividad = checkBoxActividad.isSelected();

        Profesor profesor = new Profesor(nombre,apellido,dni,legajo,contrasenia,correo,Data.getProfesor().getFechaDeAlta(),actividad);

        try{
            if(profesor.actualizar(fileNameProfesores,profesor.profesorAJSONObject()))
            {
                excepcionPersonalizada.alertaConfirmacion("Profesor actualizado correctamente");
                escena.cambiarEscena(buscarProfesorAdministrador,stage,"Buscar profesor");
            }
        }catch(DatosIncorrectosException e)
        {
            e.getMessage();
        }catch (CamposVaciosException e)
        {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(buscarProfesorAdministrador,stage,"Buscar profesor");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        txtNombre.setText(Data.getProfesor().getNombre());
        txtApellido.setText(Data.getProfesor().getApellido());
        txtDni.setText(Data.getProfesor().getDni());
        txtCorreo.setText(Data.getProfesor().getCorreo());
        txtContrasenia.setText(Data.getProfesor().getContrasenia());
        checkBoxActividad.setSelected(Data.getProfesor().getActividad());
    }

}

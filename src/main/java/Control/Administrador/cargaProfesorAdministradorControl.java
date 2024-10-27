package Control.Administrador;

import Control.EscenaControl;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Path.Path;
import Usuarios.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class cargaProfesorAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private Rectangle lista;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    private Stage stage;

    @FXML
    void clickBtnCargar(ActionEvent event) {

        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String dni = txtDni.getText();
        String correo = txtCorreo.getText();

        Profesor p1 = new Profesor(nombre,apellido,dni,correo);

        try {

            if(p1.crear(Path.fileNameProfesores)){

                Excepciones.excepcionPersonalizada.alertaConfirmacion("Profesor cargado con exito");

            }else{

                Excepciones.excepcionPersonalizada.excepcion("Profesor ya cargado");

            }

        } catch (EntidadYaExistente e) {
            e.getMessage();
        } catch (CamposVaciosException e){
            e.getMessage();
        } catch (DatosIncorrectosException e){
            e.getMessage();
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

        stage = (Stage)btnVolver.getScene().getWindow();
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.congifurarProfesorAdministrador,stage,"Configurar Profesor");

    }

}

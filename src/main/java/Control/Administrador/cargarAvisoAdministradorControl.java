package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosAvisos;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.EntidadYaExistente;
import Excepciones.excepcionPersonalizada;
import Modelo.AccesoAviso;
import Modelo.Avisos;
import Modelo.Turno;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashSet;

import static Path.Path.configurarAvisosAdministrador;
import static Path.Path.pathAvisos;

public class cargarAvisoAdministradorControl {

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceboxVisibilidad;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextArea txtMensaje;

    @FXML
    private TextField txtSubtitulo;

    @FXML
    private TextField txtTitulo;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnCargar(ActionEvent event) {
        String titulo = txtTitulo.getText().trim();
        String subtitulo = txtSubtitulo.getText().trim();
        String descripcion = txtMensaje.getText().trim();
        AccesoAviso accesoAviso = null;
        HashSet<String> legajos = new HashSet<>();
        try{
            accesoAviso = AccesoAviso.valueOf(choiceboxVisibilidad.getValue());
        }catch (NullPointerException e)
        {
            excepcionPersonalizada.excepcion("Ingresaste un campo vacio.");
        }
        Avisos aviso = new Avisos(manejoArchivosAvisos.obtenerSiguienteId(pathAvisos), Data.getLegajo(),titulo,subtitulo,descripcion,legajos, LocalDate.now(),accesoAviso);
        try{
            if(aviso.crear(pathAvisos))
            {
                excepcionPersonalizada.alertaConfirmacion("Aviso cargado correctamente");
                escena.cambiarEscena(configurarAvisosAdministrador,stage,"Configurar avisos");
            }
        } catch (CamposVaciosException e) {
            e.getMessage();
        } catch (DatosIncorrectosException e) {
            e.getMessage();
        } catch (EntidadYaExistente e) {
            e.getMessage();
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(configurarAvisosAdministrador,stage,"Configurar avisos");
    }

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        choiceboxVisibilidad.getItems().add(String.valueOf(AccesoAviso.valueOf("TODOS")));
        choiceboxVisibilidad.getItems().add(String.valueOf(AccesoAviso.valueOf("OCULTO")));
    }

}


package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import Excepciones.CamposVaciosException;
import Excepciones.DatosIncorrectosException;
import Excepciones.excepcionPersonalizada;
import Modelo.AccesoAviso;
import Modelo.Avisos;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashSet;

import static Path.Path.buscarAvisosAdministrador;
import static Path.Path.pathAvisos;

public class editarAvisosAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceboxVisibilidad;

    @FXML
    private TextArea txtMensaje;

    @FXML
    private TextField txtSubtitulo;

    @FXML
    private TextField txtTitulo;

    private Stage stage;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });
        txtMensaje.setText(Data.getAviso().getDescripcion());
        txtSubtitulo.setText(Data.getAviso().getSubtitulo());
        txtTitulo.setText(Data.getAviso().getTitulo());
        choiceboxVisibilidad.getItems().add(String.valueOf(AccesoAviso.valueOf("TODOS")));
        choiceboxVisibilidad.getItems().add(String.valueOf(AccesoAviso.valueOf("OCULTO")));
        choiceboxVisibilidad.setValue(Data.getAviso().getAccesoAviso().toString());
    }

    @FXML
    void clickBtnActualizar(ActionEvent event) {
        String titulo = txtTitulo.getText().trim();
        String subtitulo = txtSubtitulo.getText().trim();
        String descripcion = txtMensaje.getText().trim();
        AccesoAviso accesoAviso = null;
        HashSet<String> legajos;
        legajos = Data.getAviso().getLegajos();
        try{
            accesoAviso = AccesoAviso.valueOf(choiceboxVisibilidad.getValue());
        }catch (NullPointerException e)
        {
            excepcionPersonalizada.excepcion("Ingresaste un campo vacio.");
        }
        Avisos aviso = new Avisos(Data.getAviso().getId(),Data.getLegajo(),titulo,subtitulo,descripcion,legajos, LocalDate.now(),accesoAviso);
        try{
            if(aviso.actualizar(pathAvisos,aviso.AvisoAJSONObject()))
            {
                excepcionPersonalizada.alertaConfirmacion("Aviso cargado correctamente");
                EscenaControl escena = new EscenaControl();
                escena.cambiarEscena(buscarAvisosAdministrador,stage,"Buscar aviso");
            }
        } catch (CamposVaciosException e) {
            e.getMessage();
        } catch (DatosIncorrectosException e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(buscarAvisosAdministrador,stage,"Buscar aviso");
    }

}

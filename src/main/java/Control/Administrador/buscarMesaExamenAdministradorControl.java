package Control.Administrador;
import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosMesaExamen;
import Excepciones.CamposVaciosException;
import Excepciones.excepcionPersonalizada;
import Modelo.Materia;
import Modelo.MesaExamen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Path.Path.*;

public class buscarMesaExamenAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<Integer> choiceBoxAnio;

    @FXML
    private ChoiceBox<String> choiceBoxMesaExamen;

    private Stage stage;

    @FXML
    void clickBtnElegir(ActionEvent event) {
        String idMesa = Materia.cortarString(choiceBoxMesaExamen.getValue());
        Data data = new Data();
        try{
            data.setMesaExamen(manejoArchivosMesaExamen.buscarMesaExamen(pathMesaExamen+manejoArchivosMesaExamen.generarNombreArchivoMesaExamen(Data.getCarrera().getId(), choiceBoxAnio.getValue()),"id",idMesa));
            EscenaControl escena = new EscenaControl();
            escena.cambiarEscena(editarMesaExamenAdministrador,stage,"Actualizar mesa de examen");
        } catch (CamposVaciosException e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(opcionEditarMesaExamenAdministrador,stage,"Configuración mesa de examen");
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

        ArrayList<Integer> anios = new ArrayList<>();
        anios.add(LocalDate.now().getYear());
        anios.add((LocalDate.now().getYear()+1));
        choiceBoxAnio.getItems().addAll(anios);

        choiceBoxAnio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                actualizarListado(newValue);
            }
        });
    }

    public void actualizarListado(Integer newValue){

        try{
            choiceBoxMesaExamen.getItems().clear();
        }catch (NullPointerException e)
        {
            excepcionPersonalizada.excepcion("No hay mesas de examen existentes para ese año");
        }

        List<MesaExamen> mesaExamenList = manejoArchivosMesaExamen.obtenerMesaExamenPorAnio(newValue, Data.getCarrera().getId());
        if(!mesaExamenList.isEmpty())
        {
            for(MesaExamen mesaExamen : mesaExamenList)
            {
                choiceBoxMesaExamen.getItems().add(mesaExamen.getId() + " - " + mesaExamen.getFecha() + " - " + mesaExamen.getTurno().toString() + " - " + mesaExamen.getCodigoPresidente());
            }
        }
    }

}
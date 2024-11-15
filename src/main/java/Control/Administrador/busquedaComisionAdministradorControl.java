package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosComisiones;
import Excepciones.CamposVaciosException;
import Modelo.Comision;
import Modelo.Materia;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Path.Path.*;

public class busquedaComisionAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<Integer> choiceBoxAnio;

    @FXML
    private ChoiceBox<String> choiceBoxComision;

    private Stage stage;

    @FXML
    void clickBtnElegir(ActionEvent event) {
        String idComision = Materia.cortarString(choiceBoxComision.getValue());
        Data data = new Data();
        try{
            data.setComision(manejoArchivosComisiones.buscarComision(pathComisiones+manejoArchivosComisiones.generarNombreArchivoComision(Data.getCarrera().getId(), choiceBoxAnio.getValue()),"id",idComision));
            EscenaControl escena = new EscenaControl();
            escena.cambiarEscena(editarComisionAdministrador,stage,"Actualizar comisión");
        } catch (NullPointerException | CamposVaciosException e) {
            e.getMessage();
        }
    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(opcionConfigurarComisionAdministrador,stage,"Configurar comisión");
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
                actualizarListadoComisiones(newValue);
            }
        });
    }

    public void actualizarListadoComisiones(Integer newValue){
        choiceBoxComision.getItems().clear();

        List<Comision> comisionesList = manejoArchivosComisiones.obtenerComisionesPorAnio(newValue, Data.getCarrera().getId());
        if(!comisionesList.isEmpty())
        {
            for(Comision comision : comisionesList)
            {
                choiceBoxComision.getItems().add(comision.getId() + " - " + comision.getNombre());
            }
        }
    }
}

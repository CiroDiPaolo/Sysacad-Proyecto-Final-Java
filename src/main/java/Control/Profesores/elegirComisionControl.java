package Control.Profesores;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import ControlArchivos.manejoArchivosComisiones;
import Excepciones.CamposVaciosException;
import Modelo.Comision;
import Modelo.Materia;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;

public class elegirComisionControl {

    @FXML
    private Button btnAplicar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private ChoiceBox<String> choiceComision;

    private Stage stage;

    @FXML
    void clickBtnVolver(ActionEvent event) {

        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(Path.menuPrincipalProfesores, stage, "Menu principal profesores");

    }

    @FXML
    void clickBtnAplicar(ActionEvent event) throws CamposVaciosException {
        String seleccion = choiceComision.getValue();

        if (seleccion != null) {
            String[] partes = seleccion.split(" - ");

            String nombreComision = partes[0];
            HashSet<Comision> comisiones = Data.getProfesor().obtenerComisiones(Path.pathComisiones);
            comisiones = manejoArchivosComisiones.filtrarComisionesPorNombre(nombreComision, comisiones);

            Iterator<Comision> it = comisiones.iterator();

            while(it.hasNext()){
                Comision comision = it.next();

                if(comision.getCodigoMateria().equals(partes[2])){
                    Data data = new Data();
                    data.setComision(comision);
                    break;
                }

            }

            EscenaControl escena = new EscenaControl();
            escena.cambiarEscena(Path.gestionarComisionProfesor, stage, "Gestionar comision");

        } else {
            throw new CamposVaciosException("Debe seleccionar una comision");
        }
    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

            HashSet<Comision> comisiones = Data.getProfesor().obtenerComisiones(Path.pathComisiones);

            for(Comision comision : comisiones){

                choiceComision.getItems().add(comision.getNombre() + " - " + manejoArchivosCarrera.obtenerMateria(Path.pathCarreras,comision.getCodigoMateria()).getNombre() + " - " + comision.getCodigoMateria());
            }

        });

    }

}

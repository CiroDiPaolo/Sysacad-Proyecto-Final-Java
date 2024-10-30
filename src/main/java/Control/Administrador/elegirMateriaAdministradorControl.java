package Control.Administrador;

import Control.EscenaControl;
import Control.InicioSesion.Data;
import ControlArchivos.manejoArchivosCarrera;
import Excepciones.excepcionPersonalizada;
import Modelo.Materia;
import Path.Path;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

import static Path.Path.*;

public class elegirMateriaAdministradorControl {
    @FXML
    private Button btnElegir;

    @FXML
    private Button btnVolver;

    @FXML
    private ChoiceBox<String> choiceBoxElegirMateria;

    @FXML
    private Label tctMenuPrincipal;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    private ArrayList<String> materias;

    private ArrayList<Materia> materiasArray;
    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            materias = new ArrayList<>();
            materiasArray = new ArrayList<>();
            stage = (Stage) btnVolver.getScene().getWindow();

            for (Map.Entry<String, Materia> entry : Data.getCarrera().getMaterias().entrySet()) {
                Materia materia = entry.getValue();
                materiasArray.add(materia);
                materias.add(materia.getId() + " - " + materia.getNombre());
            }

            choiceBoxElegirMateria.getItems().addAll(materias);

        });

    }

    @FXML
    void clickBtnElegir(ActionEvent event) {
        String materiaID = choiceBoxElegirMateria.getValue();
        if(materiaID!= null)
        {
            materiaID = Materia.cortarString(materiaID);

            Data data = new Data();

            boolean flag = false;
            int i = 0;
            while(!flag && i<materiasArray.size())
            {
                if(materiasArray.get(i).getId().equals(materiaID))
                {
                    data.setMateria(materiasArray.get(i));
                    flag = true;
                    escena.cambiarEscena(Path.editarMateriaAdministrador,stage,"Editar materia");
                }
                i++;
            }

            if(!flag)
            {
                excepcionPersonalizada.alertaAtencion("No se encontró la materia");
            }
        }else {
            excepcionPersonalizada.alertaAtencion("No seleccionaste ninguna materia. Vuelva a intentar.");
        }

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {
        escena.cambiarEscena(configurarMateriasAdministrador, stage, "Configurar materia");
    }
}

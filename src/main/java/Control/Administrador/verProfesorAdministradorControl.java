package Control.Administrador;

import Control.EscenaControl;
import ControlArchivos.manejoArchivosComisiones;
import ControlArchivos.manejoArchivosMesaExamen;
import ControlArchivos.manejoArchivosProfesor;
import Modelo.Comision;
import Modelo.Materia;
import Modelo.MesaExamen;
import Usuarios.Profesor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;

import static Path.Path.*;

public class verProfesorAdministradorControl {

    @FXML
    private Button btnVolver;

    @FXML
    private CheckBox checkBoxActividad;

    @FXML
    private ChoiceBox<Integer> choiceboxAnio;

    @FXML
    private ChoiceBox<String> choiceboxComisiones;

    @FXML
    private ChoiceBox<String> choiceboxMesaExamen;

    @FXML
    private ChoiceBox<String> choiceboxProfesor;

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

    @FXML
    void clickBtnVolver(ActionEvent event) {
        EscenaControl escena = new EscenaControl();
        escena.cambiarEscena(configurarProfesorAdministrador,stage,"Configurar profesor");
    }

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) btnVolver.getScene().getWindow();
        });

        ArrayList<Profesor> profesores = manejoArchivosProfesor.retornarProfesores(fileNameProfesores);

        for (Profesor profesor : profesores) {
            choiceboxProfesor.getItems().add(profesor.getLegajo() + " - " + profesor.getNombre() + " " + profesor.getApellido());
        }

        // Listener para choiceboxProfesor
        choiceboxProfesor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String idProfesorelegido = Materia.cortarString(newValue);

                // Limpia las otras ChoiceBox
                choiceboxAnio.getItems().clear();
                choiceboxComisiones.getItems().clear();
                choiceboxMesaExamen.getItems().clear();

                Profesor profeElegido = profesores.stream()
                        .filter(profesor -> profesor.getLegajo().equals(idProfesorelegido))
                        .findFirst()
                        .orElse(new Profesor());

                LocalDate fechaAlta = profeElegido.getFechaDeAlta();
                int anioInicio = fechaAlta.getYear();
                int anioActual = LocalDate.now().getYear();

                for (int anio = anioInicio; anio <= anioActual; anio++) {
                    choiceboxAnio.getItems().add(anio);
                }

                choiceboxAnio.getItems().add(anioActual + 1);

                txtNombre.setText(profeElegido.getNombre());
                txtApellido.setText(profeElegido.getApellido());
                txtDni.setText(profeElegido.getDni());
                txtCorreo.setText(profeElegido.getCorreo());
                txtContrasenia.setText(profeElegido.getContrasenia());
                checkBoxActividad.setSelected(profeElegido.getActividad());

                choiceboxAnio.getSelectionModel().selectedItemProperty().addListener((obs, oldAnio, newAnio) -> {
                    if (newAnio != null) {
                        choiceboxComisiones.getItems().clear();
                        choiceboxMesaExamen.getItems().clear();

                        ArrayList<Comision> comisiones = new ArrayList<>();
                        comisiones = manejoArchivosComisiones.obtenerComisionesPorAnio(choiceboxAnio.getValue(),"001");
                        for(Comision comision : comisiones)
                        {   if(comision.getCodigoProfesor().equals(idProfesorelegido))
                            {
                                choiceboxComisiones.getItems().add(comision.getId()+ " - Codigo materia: " + comision.getCodigoMateria() + " - " + comision.getNombre() + " - " + comision.getTurno());
                            }
                        }

                        ArrayList<MesaExamen> mesaExamen = new ArrayList<>();
                        mesaExamen = manejoArchivosMesaExamen.obtenerMesaExamenPorAnio(choiceboxAnio.getValue(),"001");
                        for(MesaExamen mesaExamen1 : mesaExamen)
                        {   if(mesaExamen1.getCodigoPresidente().equals(idProfesorelegido))
                            {
                                choiceboxMesaExamen.getItems().add("Presidente: " + mesaExamen1.getId() + " - Codigo materia: " + mesaExamen1.getCodigoMateria() + " - " + mesaExamen1.getTurno() + " - " + mesaExamen1.getFecha().toString() + " - " + mesaExamen1.getHora().toString());
                            }else{

                                for(String vocal : mesaExamen1.getVocales())
                                {
                                    if(vocal.equals(idProfesorelegido))
                                    {
                                        choiceboxMesaExamen.getItems().add("Vocal: " + mesaExamen1.getId() + " - Codigo materia: " + mesaExamen1.getCodigoMateria() + " - " + mesaExamen1.getTurno() + " - " + mesaExamen1.getFecha().toString() + " - " + mesaExamen1.getHora().toString());
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }


}


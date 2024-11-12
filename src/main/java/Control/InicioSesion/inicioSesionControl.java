package Control.InicioSesion;

import Control.EscenaControl;
import Excepciones.DatosIncorrectosException;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class inicioSesionControl {

    private Stage stage;

    @FXML
    private Button btnAcceder;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtLegajo;

    /**
     * Metodo que se ejecuta al clickear el boton de acceder
     * @param event
     */
    @FXML
    public void clicBtnAcceder(ActionEvent event){

        try {

            inicioSesion();

        }catch (DatosIncorrectosException e){

            System.out.println(e.getMessage());

        }

    }

    /**
     * Metodo que inicia sesion
     * @throws DatosIncorrectosException
     */
    void inicioSesion() throws DatosIncorrectosException {
        int legajoFlag = inicioSesionLegajo();
        int contraseniaFlag = inicioSesionContrasenia();

        if (legajoFlag == 1 && contraseniaFlag == 1) {
            redireccionDeEscena(1);
        } else if (legajoFlag == 2 && contraseniaFlag == 2) {
            redireccionDeEscena(2);
        } else if (legajoFlag == 3 && contraseniaFlag == 3) {
            redireccionDeEscena(3);
        } else {
            throw new DatosIncorrectosException("Datos incorrectos, verifique los datos ingresados");
        }
    }

    /**
     * Metodo que verifica si la contraseña es correcta, retorna true si es correcta o false
     * @return
     */
    @FXML
int inicioSesionContrasenia() {
    int flag = 0;
    String legajo = txtLegajo.getText();

    if (legajo.startsWith("E")) {
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, txtContrasenia.getText(), "contrasenia")) {
            flag = 1;
        }
    } else if (legajo.startsWith("P")) {
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, txtContrasenia.getText(), "contrasenia")) {
            flag = 2;
        }
    } else if (legajo.startsWith("A")) {
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, txtContrasenia.getText(), "contrasenia")) {
            flag = 3;
        }
    }

    return flag;
}

    /**
     * Metodo que verifica si el legajo es correcto, retorna true si es correcto o false
     * @return
     */
    @FXML
    int inicioSesionLegajo() {

        int flag = 0;
        String legajo = txtLegajo.getText();

        if (legajo.startsWith("E")) {
            if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, legajo, "legajo")) {
                flag = 1;
            }
        } else if (legajo.startsWith("P")) {
            if (Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, legajo, "legajo")) {
                flag = 2;
            }
        } else if (legajo.startsWith("A")) {
            if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, legajo, "legajo")) {
                flag = 3;
            }
        }

        return flag;
    }

    /**
     * Metodo que redirige a la escena correspondiente
     * @param index
     */
    void redireccionDeEscena(int index){

        String constante = "";

        //Se guarda el legajo
        String legajo = txtLegajo.getText();

        //Se setea el legajo
        Data.setLegajo(legajo);

        if(index == 1){

            constante = Path.menuPrincipalAlumnos;

            //Se obtiene el estudiante que ingreso al sistema
            Data.setEstudiante(legajo);

        }else if (index == 2){

            constante = Path.menuPrincipalProfesores;

            Data.setProfesor(legajo);

        }else if (index ==3){

            constante = Path.menuPrincipalAdministrador;

        }

        // Obtener el Stage actual
        Stage stage = (Stage) btnAcceder.getScene().getWindow();

        // Cambiar la escena
        EscenaControl escenaControl = new EscenaControl();

        stage.close();

        escenaControl.cambiarVentana(constante, "Menu Principal");


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
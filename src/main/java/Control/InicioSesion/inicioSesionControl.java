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

        int index;

        //Si el inicio de sesion es correcto y retornan 1
        if (inicioSesionLegajo() == 1 && inicioSesionContrasenia() ==1){//Verifica si el legajo y la contraseña son correctos

            index = 1;

            redireccionDeEscena(index);

        }else if (inicioSesionLegajo() == 2 && inicioSesionContrasenia() == 2){

            index = 2;

            redireccionDeEscena(index);

        }else if (inicioSesionLegajo() == 3 && inicioSesionContrasenia() == 3){

            index = 3;

            redireccionDeEscena(index);

        }else{

            throw new DatosIncorrectosException("Datos incorrectos, verifique los datos ingresados");

        }

    }

    /**
     * Metodo que verifica si la contraseña es correcta, retorna true si es correcta o false
     * @return
     */
    @FXML
    int inicioSesionContrasenia() {//Verifica si la contraseña es correcta, si es correcta retorna true

        int flag = 0;

        //Busca en el JSON si se encuentra el legajo alumno
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, txtContrasenia.getText(), "contrasenia")) { // verifica si el inicio de sesión es de un alumno
            flag = 1;
        } else if (Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, txtContrasenia.getText(), "contrasenia")) { // verifica si el inicio de sesión es de un profesor
            flag = 2;
        } else if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, txtContrasenia.getText(), "contrasenia")) { // Verifica si el inicio de sesión fue de un administrador
            flag = 3;
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

        //Busca en el JSON si se encuentra el legajo alumno
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, txtLegajo.getText(), "legajo") == true) {//verifica si el inicio de sesion es de un alumno

            flag = 1;

        //verifica si el inicio de sesion es de un profesor
        }else if(Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, txtLegajo.getText(), "legajo") == true){

            flag = 2;

        //Verifica si el inicio de sesion fue de un administrador
        } else if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, txtLegajo.getText(), "legajo") == true) {

            flag = 3;

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
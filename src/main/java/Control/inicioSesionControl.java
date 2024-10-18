package Control;

import Data.inicioSesionData;
import Excepciones.DatosIncorrectosException;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class inicioSesionControl {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnAcceder;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private TextField txtLegajo;

    @FXML
    public void clicBtnAcceder(ActionEvent event) throws DatosIncorrectosException{

        int index;

        //Si el inicio de sesion es correcto y retornan 1
        if (inicioSesionLegajo() == 1 && inicioSesionContrasenia() ==1){//Verifica si el legajo y la contraseña son correctos

            index = 1;

            redireccionDeEscena(index);

        } else if (inicioSesionLegajo() == 2 && inicioSesionContrasenia() == 2){

            index = 2;

            redireccionDeEscena(index);

       }else if (inicioSesionLegajo() == 3 && inicioSesionContrasenia() == 3){

            index = 3;

            redireccionDeEscena(index);

        }else{

            throw new DatosIncorrectosException("Datos incorrectos, verifique los datos ingresados");

        }

    }

    @FXML
    int inicioSesionContrasenia() {//Verifica si la contraseña es correcta, si es correcta retorna true

        int flag = 0;

        //Busca en el JSON si se encuentra el legajo alumno
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, txtLegajo.getText(), "legajo") == true) {//verifica si el inicio de sesion es de un alumno

            flag = 1;

        }else if(Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, txtLegajo.getText(), "legajo") == true){//verifica si el inicio de sesion es de un profesor

            flag = 2;

        } /*else if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, txtLegajo.getText(), "legajo") == true) {Verifica si el inicio de sesion fue de un administrador

            flag = 3;

        }*/

        return flag;
    }

    @FXML
    int inicioSesionLegajo() {//Verifica si el legajo es correcto, si es correcto retorna true

        int flag = 0;

        //Busca en el JSON si se encuentra el legajo alumno
        if (Consultas.consultaArchivo.buscarClave(Path.fileNameAlumnos, txtLegajo.getText(), "legajo") == true) {//verifica si el inicio de sesion es de un alumno

            flag = 1;

        }else if(Consultas.consultaArchivo.buscarClave(Path.fileNameProfesores, txtLegajo.getText(), "legajo") == true){//verifica si el inicio de sesion es de un profesor

            flag = 2;

        } /*else if (Consultas.consultaArchivo.buscarClave(Path.fileNameAdministrador, txtLegajo.getText(), "legajo") == true) {Verifica si el inicio de sesion fue de un administrador

            flag = 3;

        }*/

        return flag;
    }

    void redireccionDeEscena(int index){

            String constante = "";

            if(index == 1){

                constante = Path.menuPrincipalAlumnos;

            }/*else if (index == 2){

                constante = Path.menuPrincipalProfesores;

            }else{

                constante = Path.menuPrincipalAdministrador;

            }*/

            String legajo = txtLegajo.getText();

            inicioSesionData.setLegajo(legajo);

            System.out.println("correcto");

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
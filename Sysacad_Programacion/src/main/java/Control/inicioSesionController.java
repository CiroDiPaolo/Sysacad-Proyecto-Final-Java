package Control;

import App.App;
import Path.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import domain.inicioSesion;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class inicioSesionController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnAcceder;

    @FXML
    private TextField txtContrasenia;

    @FXML
    private TextField txtLegajo;

    @FXML
    void clicBtnAcceder(ActionEvent event) {

        if(inicioSesionLegajo() == true){

            if(inicioSesionContrasenia() == true){

                System.out.println("correcto");

                stage.close();

                controlMenuPrincipal.mostrarMenuPrincipal();



            }else{

                System.out.println("incorrecto");

            }

        }else{

            System.out.println("incorrecto");

        }


    }

    @FXML
    boolean inicioSesionContrasenia() {

        boolean flag = false;

        if (inicioSesion.buscarClave(Path.fileNameAlumnos, txtContrasenia.getText(), "contrasenia") == true) {

            flag = true;

        }

        return flag;
    }

    @FXML
    boolean inicioSesionLegajo() {

        boolean flag = false;

        if (inicioSesion.buscarClave(Path.fileNameAlumnos, txtLegajo.getText(), "legajo") == true) {

                flag = true;

        }


        return flag;
    }


    public void setStage(Stage stage) {

        this.stage = stage;

    }


}
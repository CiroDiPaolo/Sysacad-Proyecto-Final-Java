package Control.Profesores;

import Control.InicioSesion.inicioSesionData;
import Usuarios.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import static Path.Path.fileNameProfesores;

public class menuPrincipalProfesoresControl {

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Text txtBienvenida;

    /**
     * Metodo que se ejecuta al inicializar la pantalla
     */
    @FXML
    protected void initialize() { setTxtBienvenida(); }

    /**
     * Metodo que setea el texto de bienvenida
     */
    protected void setTxtBienvenida() {

        String legajo = inicioSesionData.getLegajo();

        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameProfesores,legajo));
    }

}

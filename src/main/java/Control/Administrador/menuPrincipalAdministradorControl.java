package Control.Administrador;

import Control.InicioSesion.inicioSesionData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import static Path.Path.fileNameAdministrador;


public final class  menuPrincipalAdministradorControl {

    @FXML
    private Button btnOp1;

    @FXML
    private Button btnOp2;

    @FXML
    private Button btnOp3;

    @FXML
    private Button btnOp4;

    @FXML
    private Button btnOp5;

    @FXML
    private Button btnOp6;

    @FXML
    private Button btnOp7;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private Text txtBienvenida;

    @FXML
    void clickBtnOp1(ActionEvent event) {

    }

    @FXML
    void clickBtnOp2(ActionEvent event) {

    }

    @FXML
    void clickBtnOp3(ActionEvent event) {

    }

    @FXML
    void clickBtnOp4(ActionEvent event) {

    }

    @FXML
    void clickBtnOp5(ActionEvent event) {

    }

    @FXML
    void clickBtnOp6(ActionEvent event) {

    }

    @FXML
    void clickBtnOp7(ActionEvent event) {

    }

    @FXML
    protected void initialize() {
        setTxtBienvenida();
    }

    protected void setTxtBienvenida() {
        String legajo = inicioSesionData.getLegajo();
        txtBienvenida.setText("Bienvenido, " + Consultas.consultaArchivo.buscarNombreCompleto(fileNameAdministrador, legajo));
    }

}

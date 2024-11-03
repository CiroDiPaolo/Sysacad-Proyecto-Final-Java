package Control.Administrador;
import Control.EscenaControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class busquedaComisionAdministradorControl {
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnVolver;

    @FXML
    private Label tctMenuPrincipal;

    @FXML
    private TextField txtIdCarrera;

    private Stage stage;

    private EscenaControl escena = new EscenaControl();

    @FXML
    void clickBtnBuscar(ActionEvent event) {

    }

    @FXML
    void clickBtnVolver(ActionEvent event) {

    }

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {

            stage = (Stage) btnVolver.getScene().getWindow();

        });

    }
}

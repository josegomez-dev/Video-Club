package InterfazUsuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import CapaLogica.Gestor;

public class DevolucionAlquiler implements Initializable, ControlVentanas{

	Gestor gestor;

	//DEFINIR ESPACIOS DE DATOS A MOSTRAR
    @FXML
    private Label iCedula;
    @FXML
    private Label iNombre;
    @FXML
    private Label iEstado;
    @FXML
    private Label iIDPelicula;
    @FXML
    private Label iIDEjemplar;
    @FXML
    private Label iFechaAlquiler;
    @FXML
    private Label iFechaDevolucion;
    @FXML
    private Label iMulta;

    private int numAfiliado;

    //DEFINIR BOTONES
    @FXML
    private Button btnAceptar;

    private Stage dialogStage;

    public DevolucionAlquiler(){
    	gestor = new Gestor();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@Override
	public void setScreenPane(AdminVentanas pventana) {
	}

	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void mostrarInformacionCliente(String[] pinfoAfiliado, String[] pinfoAlquiler, int pnumAfiliado) {
    	iCedula.setText(pinfoAfiliado[0]);
    	iNombre.setText(pinfoAfiliado[1]);
    	iEstado.setText(pinfoAfiliado[4]);
    	mostrarInformacionAlquiler(pinfoAlquiler);
    	numAfiliado = pnumAfiliado;
    }

    private void mostrarInformacionAlquiler(String[] pinfoAlquiler) {
    	iIDPelicula.setText(pinfoAlquiler[0]);
    	iIDEjemplar.setText(pinfoAlquiler[1]);
    	iFechaAlquiler.setText(pinfoAlquiler[2]);
    	iFechaDevolucion.setText(pinfoAlquiler[3]);
    	iMulta.setText(pinfoAlquiler[4]);
    }

	public void aceptar(ActionEvent event) throws SQLException, Exception{
		gestor.alquilerDevolver(numAfiliado, iIDEjemplar.getText());
		dialogStage.close();
	}
}
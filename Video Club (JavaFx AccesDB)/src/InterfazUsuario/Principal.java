package InterfazUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Principal implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

    //DEFINIR BOTONES
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnPeliculas;
    @FXML
    private Button btnAlquileres;
    @FXML
    private Button btnInventrio;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

	public void admiCliente(ActionEvent event){
		ventana.mostrarVentana("Cliente");
	}

	public void admiPelicula(ActionEvent event){
		ventana.mostrarVentana("Pelicula");
	}

	public void admiAlquiler(ActionEvent event){
		ventana.mostrarVentana("Alquiler");
	}

	public void admiInventario(ActionEvent event){
		ventana.mostrarVentana("Inventario");
	}
}

package InterfazUsuario;

import java.net.URL;
import java.util.ResourceBundle;

import InterfazUsuario.Tablas.TablaAlquilerCliente;
import InterfazUsuario.Tablas.TablaPersonaAdicional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ConsultaCliente implements Initializable, ControlVentanas{

	//DEFINIR TABLA DE ALQUILERES
	@FXML
    private TableView<TablaAlquilerCliente> tablaAlquileres;
    @FXML
    private TableColumn<TablaAlquilerCliente, String> cCodigo;
    @FXML
    private TableColumn<TablaAlquilerCliente, String> cTitulo;
    @FXML
    private TableColumn<TablaAlquilerCliente, String> cFormato;
    @FXML
    private TableColumn<TablaAlquilerCliente, String> cFecha;

    //DEFINIR TABLA DE ALQUILERES
  	@FXML
    private TableView<TablaPersonaAdicional> tablaPA;
    @FXML
    private TableColumn<TablaPersonaAdicional, String> cCedula;
    @FXML
    private TableColumn<TablaPersonaAdicional, String> cNombre;

	//DEFINIR ESPACIOS DE DATOS A MOSTRAR
    @FXML
    private Label iCedula;
    @FXML
    private Label iNombre;
    @FXML
    private Label iApellido;
    @FXML
    private Label iTelefono;
    @FXML
    private Label iEstado;
    @FXML
    private Label iMoroso;
    @FXML
    private Label iMulta;

    //DEFINIR OVSERVABLELIST DE ALQUILERES
    private ObservableList<TablaAlquilerCliente> datosAlquileres = FXCollections.observableArrayList();

    //DEFINIR OVSERVABLELIST DE PERSONAS ADICIONALES
    private ObservableList<TablaPersonaAdicional> datosPA = FXCollections.observableArrayList();

    @Override
	public void setScreenPane(AdminVentanas pventana) {
	}

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//TABLA ALQUILERES
		cCodigo.setCellValueFactory(new PropertyValueFactory<TablaAlquilerCliente, String>("rNumero"));
    	cTitulo.setCellValueFactory(new PropertyValueFactory<TablaAlquilerCliente, String>("rTitulo"));
    	cFormato.setCellValueFactory(new PropertyValueFactory<TablaAlquilerCliente, String>("rFormato"));
    	cFecha.setCellValueFactory(new PropertyValueFactory<TablaAlquilerCliente, String>("rFecha"));
    	tablaAlquileres.setItems(datosAlquileres);

    	//TABLA PERSONAS ADICIONALES
    	cCedula.setCellValueFactory(new PropertyValueFactory<TablaPersonaAdicional, String>("rCedula"));
    	cNombre.setCellValueFactory(new PropertyValueFactory<TablaPersonaAdicional, String>("rNombre"));
    	tablaPA.setItems(datosPA);
	}

    //también recibe la matriz con alquileres realizados
    public void mostrarInformacionCliente(String[] pinfoAfiliado, String[][] ppersonas, String[][] palquileres) {
    	iCedula.setText(pinfoAfiliado[0]);
    	iNombre.setText(pinfoAfiliado[1]);
    	iApellido.setText(pinfoAfiliado[2]);
    	iTelefono.setText(pinfoAfiliado[3]);
    	iEstado.setText(pinfoAfiliado[4]);
    	iMoroso.setText((pinfoAfiliado[5] == "0" || pinfoAfiliado[5] == null || pinfoAfiliado[5].equals("0")) ? "No" : "Si");
    	iMulta.setText(pinfoAfiliado[6]);
    	listarPersonasAdicionalesCliente(ppersonas);
    	listarAlquileresCliente(palquileres);
    }

    private void listarAlquileresCliente(String[][] palquileres) {
    	for(int i=0;i<palquileres.length;i++){
    		TablaAlquilerCliente lista = new TablaAlquilerCliente(Integer.parseInt(palquileres[i][0]), palquileres[i][1], palquileres[i][2], palquileres[i][3]);
    		datosAlquileres.add(lista);
        }
    }

    private void listarPersonasAdicionalesCliente(String[][] ppersonas) {
    	for(int i=0; i<ppersonas.length; i++){
    		TablaPersonaAdicional lista = new TablaPersonaAdicional(ppersonas[i][0], ppersonas[i][1]);
    		datosPA.add(lista);
        }
    }
}
package InterfazUsuario;

import java.net.URL;

import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaEjemplar;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Ejemplar implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE EJEMPLARES
	@FXML
    private TableView<TablaEjemplar> tablaEjemplar;
    @FXML
    private TableColumn<TablaEjemplar, String> cID;
    @FXML
    private TableColumn<TablaEjemplar, String> cEstado;
    @FXML
    private TableColumn<TablaEjemplar, String> cFormato;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
    @FXML
    private Label lEstado;
    @FXML
    private Label lFormato;

    //DEFINIR FORMULARIO DEL EJEMPLAR
    @FXML
    private TextField iCodigo;
    @FXML
    private ComboBox<String> iEstado;
    @FXML
    private ComboBox<String> iFormato;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAgregar;

    //DEFINIR COMBOBOX DE ESTADOS
    private ObservableList<String> estados = FXCollections.observableArrayList("Disponible", "En reparación", "Alquilado");

    //DEFINIR COMBOBOX DE FORMATOS
    private ObservableList<String> formatos = FXCollections.observableArrayList("DVD", "Blu-ray");

    //DEFINIR OVSERVABLELIST TABLA EJEMPLAR
    private ObservableList<TablaEjemplar> datosEjemplar = FXCollections.observableArrayList();
    private ObservableList<TablaEjemplar> seleccionDatoEjemplar;
    private boolean ejmSeleccionado = true;	// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idEjmSeleccionado;// ID EJEMPLAR
    private boolean listenerEjmCreado = false;

    //DEFINIR CONSTRUCTOR
    public Ejemplar(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TABLA EJEMPLAR
		cID.setCellValueFactory(new PropertyValueFactory<TablaEjemplar, String>("rID"));
    	cEstado.setCellValueFactory(new PropertyValueFactory<TablaEjemplar, String>("rEstado"));
    	cFormato.setCellValueFactory(new PropertyValueFactory<TablaEjemplar, String>("rFormato"));
    	tablaEjemplar.setItems(datosEjemplar);
    	seleccionDatoEjemplar = tablaEjemplar.getSelectionModel().getSelectedItems();

    	//COMBOBOX de estados
		iEstado.setItems(estados);

		//COMBOBOX de formatos
		iFormato.setItems(formatos);

		btnEliminar.setDisable(true);
	}

	//DEFINIR LISTENER DE LA TABLA EJEMPLARES
    private final ListChangeListener<TablaEjemplar> selectorTablaEjemplar = new ListChangeListener<TablaEjemplar>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaEjemplar > c) {
            try {
				extraerIDEjemplarSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //EXTRAER ID DEL EJEMPLAR
    private void extraerIDEjemplarSeleccionado() throws SQLException, Exception {
    	ejmSeleccionado = false;
    	btnEliminar.setDisable(false);
        final TablaEjemplar tabla = getTablaEjmSeleccionada();
        idEjmSeleccionado = tabla.getRID();
        cargarDatosEjemplarSeleccionado(gestor.ejemplarConsultarXCodigo(idEjmSeleccionado));
        lMensaje.setText("");
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosEjemplarSeleccionado(String[] pinfo){
    	limpiarLabels();
    	iCodigo.setText(pinfo[0]);
        iEstado.setValue(pinfo[1]);
        iFormato.setValue(pinfo[2]);
    }

    //SELECCIONAR UN EJEMPLAR DE LA TABLA
    public TablaEjemplar getTablaEjmSeleccionada() {
        if (tablaEjemplar != null) {
			List<TablaEjemplar> tabla = tablaEjemplar.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaEjemplar peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA EJEMPLARES
    private void agregarListenersTablaEjemplar(){
    	seleccionDatoEjemplar = tablaEjemplar.getSelectionModel().getSelectedItems();
    	seleccionDatoEjemplar.addListener(selectorTablaEjemplar);
    	listenerEjmCreado = true;
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
	public void atras(ActionEvent event){
		seleccionDatoEjemplar.removeListener(selectorTablaEjemplar);
		tablaEjemplar.getItems().clear();
    	reiniciarValores();
    	lMensaje.setText("");
    	ventana.mostrarVentana("Pelicula");
	}

	public void guardar(ActionEvent event) throws SQLException, Exception{
		// VALIDAR FORMULARIO
    	Boolean estado = ValidacionForm.campoComboBox(iEstado, lEstado, "Dato requerido!");
    	Boolean formato = ValidacionForm.campoComboBox(iFormato, lFormato, "Dato requerido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(estado && formato){
    		if(ejmSeleccionado){
				lMensaje.setText(gestor.ejemplarRegistrar(ventana.obtenerIDPeliculaDeEjemplar(), iEstado.getValue(), iFormato.getValue()));
    		}else{
    			lMensaje.setText(gestor.ejemplarModificar(idEjmSeleccionado, iEstado.getValue(), iFormato.getValue()));
    		}
    		reiniciarValores();
    		listarInformacionEjemplares();
		}
	}

	public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionEjemplares();
	}

    //LISTAR INFORMACIÓN DE EJEMPLARES
    public void listarInformacionEjemplares() throws SQLException, Exception{
    	if(listenerEjmCreado){
        	seleccionDatoEjemplar.removeListener(selectorTablaEjemplar);
        	tablaEjemplar.getItems().clear();
        	agregarListenersTablaEjemplar();
    	}else{
    		agregarListenersTablaEjemplar();
    	}
    	String[][] listaEjemplares = gestor.ejemplarConsultarLista(ventana.obtenerIDPeliculaDeEjemplar());
		for(int i=0; i<listaEjemplares.length; i++){
	        TablaEjemplar fila = new TablaEjemplar(listaEjemplares[i][0], listaEjemplares[i][1], listaEjemplares[i][2]);
	    	datosEjemplar.add(fila);
        }
    }

    public void eliminar(ActionEvent event) throws SQLException, Exception{
    	lMensaje.setText(gestor.ejemplarEliminar(idEjmSeleccionado));
    	listarInformacionEjemplares();
    	reiniciarValores();
	}

    public void agregar(ActionEvent event){
		reiniciarValores();
		lMensaje.setText("");
	}

	//VOLVER VALORES ORIGINALES DE VARIABLES
	public void reiniciarValores(){
		btnEliminar.setDisable(true);
		ejmSeleccionado = true;
    	idEjmSeleccionado = null;
		limpiarLabels();
		limpiarComboBox();
    }

	//QUITAR LOS MENSAJES DE VALIDACIÓN
    public void limpiarLabels(){
    	lEstado.setText("");
    	lFormato.setText("");
    }

    //LIMPIAR LOS CAMPOS DEL FORMULARIO
    public void limpiarComboBox(){
    	iEstado.getSelectionModel().clearSelection();
    	iFormato.getSelectionModel().clearSelection();
    	iCodigo.setText("");
    }
}

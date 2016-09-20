package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaCliente;
import InterfazUsuario.Tablas.TablaPersonaAdicional;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Cliente implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE CLIENTES
	@FXML
    private TableView<TablaCliente> tablaCliente;
    @FXML
    private TableColumn<TablaCliente, Integer> cNumero;
    @FXML
    private TableColumn<TablaCliente, String> cCedula;
    @FXML
    private TableColumn<TablaCliente, String> cNombre;

    //DEFINIR TABLA DE CLIENTES
    @FXML
    private TableView<TablaPersonaAdicional> tablaPA;
    @FXML
    private TableColumn<TablaPersonaAdicional, String> cCedulaPA;
    @FXML
    private TableColumn<TablaPersonaAdicional, String> cNombrePA;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
    @FXML
    private Label lCedula;
    @FXML
    private Label lNombre;
    @FXML
    private Label lApellido;
    @FXML
    private Label lTelefono;
    @FXML
    private Label lCedulaPA;
    @FXML
    private Label lNombrePA;

    //DEFINIR FORMULARIO DEL AFILIADO
    @FXML
    private TextField iCedula;
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iApellido;
    @FXML
    private TextField iTelefono;
    @FXML
    private CheckBox iEstado;

    //DEFINIR FORMULARIO DE LAS PERSONAS ADICIONALES
    @FXML
    private TextField iCedulaPA;
    @FXML
    private TextField iNombrePA;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnConsultar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnGuardarPA;
    @FXML
    private Button btnListar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEliminarPA;

    //DEFINIR OVSERVABLELIST DE CLIENTE
    private ObservableList<TablaCliente> datosClientes = FXCollections.observableArrayList();
    private ObservableList<TablaCliente> seleccionDatoCliente;
    private boolean clienteSeleccionado = true;	// true : REGISTRO
										 		// false: MODIFICACIÓN
    private int numClienteSeleccionado;// ID CLIENTE
    private boolean listenerClienteCreado = true;

    //DEFINIR OVSERVABLELIST DE PERSONAS ADICIONALES
    private ObservableList<TablaPersonaAdicional> datosPA = FXCollections.observableArrayList();
    private ObservableList<TablaPersonaAdicional> seleccionDatoPA;
    private boolean paSeleccionado = true;	// true : REGISTRO
										 	// false: MODIFICACIÓN
    private String idPASeleccionado;// ID PERSONA ADICIONAL
    private boolean listenerPACreado = true;

    //DEFINIR CONSTRUCTOR
    public Cliente(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TABLA CLIENTE
		cNumero.setCellValueFactory(new PropertyValueFactory<TablaCliente, Integer>("rNumero"));
    	cCedula.setCellValueFactory(new PropertyValueFactory<TablaCliente, String>("rCedula"));
    	cNombre.setCellValueFactory(new PropertyValueFactory<TablaCliente, String>("rNombre"));
    	tablaCliente.setItems(datosClientes);
    	seleccionDatoCliente = tablaCliente.getSelectionModel().getSelectedItems();

    	//TABLA PERSONAS ADICIONALES
    	cCedulaPA.setCellValueFactory(new PropertyValueFactory<TablaPersonaAdicional, String>("rCedula"));
    	cNombrePA.setCellValueFactory(new PropertyValueFactory<TablaPersonaAdicional, String>("rNombre"));
    	tablaPA.setItems(datosPA);
    	seleccionDatoPA = tablaPA.getSelectionModel().getSelectedItems();

    	reiniciarValoresFormCliente();
    	reiniciarValoresFormPA();
    	btnGuardarPA.setDisable(true);
    	bloquearInputsPA();
	}

	//DEFINIR LISTENER DE LA TABLA CLIENTE
    private final ListChangeListener<TablaCliente> selectorTablaCliente = new ListChangeListener<TablaCliente>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaCliente > c) {
            try {
				extraerIDClienteSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //DEFINIR LISTENER DE LA TABLA CLIENTE
    private final ListChangeListener<TablaPersonaAdicional> selectorTablaPA = new ListChangeListener<TablaPersonaAdicional>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaPersonaAdicional > c) {
            try {
				extraerIDPASeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //EXTRAER ID DEL CLIENTE Y CARGAR SUS DATOS EN LOS CAMPOS DE TEXTO
    private void extraerIDClienteSeleccionado() throws SQLException, Exception {
    	clienteSeleccionado = false;
    	desbloquearInputsPA();
    	desbloquearBotones();
    	final TablaCliente tabla = getTablaClienteSeleccionada();
    	numClienteSeleccionado = tabla.getRNumero();
        cargarDatosClienteSeleccionado(gestor.afiliadoConsultarXNumero(numClienteSeleccionado));
        lMensaje.setText("");
        listarInformacionPA();
        limpiarInputsPA();
    }

  //EXTRAER ID DEL CLIENTE Y CARGAR SUS DATOS EN LOS CAMPOS DE TEXTO
    private void extraerIDPASeleccionado() throws SQLException, Exception {
    	paSeleccionado = false;
    	btnEliminarPA.setDisable(false);
    	final TablaPersonaAdicional tabla = getTablaPASeleccionada();
    	idPASeleccionado = tabla.getRCedula();
        cargarDatosPASeleccionado(gestor.personaAdicionalConsultarXCedula(idPASeleccionado));
        lMensaje.setText("");
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosClienteSeleccionado(String[] pinfo){
        iCedula.setText(pinfo[0]);
        iNombre.setText(pinfo[1]);
        iApellido.setText(pinfo[2]);
        iTelefono.setText(pinfo[3]);
        iEstado.setText(pinfo[4]);
        if(!pinfo[4].equals("Activo")){
        	iEstado.selectedProperty().set(false);
        }else{
        	iEstado.selectedProperty().set(true);
        }
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosPASeleccionado(String[] pinfo){
        iCedulaPA.setText(pinfo[0]);
        iNombrePA.setText(pinfo[1]);
    }

    //SELECCIONAR UN CLIENTE DE LA TABLA
    public TablaCliente getTablaClienteSeleccionada() {
        if (tablaCliente != null) {
			List<TablaCliente> tabla = tablaCliente.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaCliente competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    //SELECCIONAR UN CLIENTE DE LA TABLA
    public TablaPersonaAdicional getTablaPASeleccionada() {
        if (tablaCliente != null) {
			List<TablaPersonaAdicional> tabla = tablaPA.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaPersonaAdicional competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }

    //AGREGAR LISTENER A TABLA CLIENTES
    private void agregarListenersTablaCliente(){
    	seleccionDatoCliente = tablaCliente.getSelectionModel().getSelectedItems();
    	seleccionDatoCliente.addListener(selectorTablaCliente);
    	listenerClienteCreado = true;
    }

    //AGREGAR LISTENER A TABLA CLIENTES
    private void agregarListenersTablaPA(){
    	seleccionDatoPA = tablaPA.getSelectionModel().getSelectedItems();
    	seleccionDatoPA.addListener(selectorTablaPA);
    	listenerPACreado = true;
    }

    //LISTAR INFORMACIÓN DE PERSONAS ADICIONALES
    public void listarInformacionPA() throws SQLException, Exception{
    	if(listenerPACreado){
    		seleccionDatoPA.removeListener(selectorTablaPA);
        	tablaPA.getItems().clear();
        	agregarListenersTablaPA();
    	}else{
    		agregarListenersTablaPA();
    	}
    	String[][] listaPA = gestor.personaAdicionalConsultarLista(numClienteSeleccionado);
		for(int i=0; i<listaPA.length; i++){
			TablaPersonaAdicional fila = new TablaPersonaAdicional(listaPA[i][0], listaPA[i][1]);
	    	datosPA.add(fila);
        }
    }

    //CAMBIAR EL TEXTO DEL CHECKBOX A ACTIVO E INACTIVO
    public void cambiarTextoCheckBox(ActionEvent event){
    	if(!iEstado.isSelected()){
    		iEstado.setText("Inactivo");
    	}else{
    		iEstado.setText("Activo");
    	}
	}

	//DEFINIR ACCIÓN DE LOS BOTONES
	public void atras(ActionEvent event){
    	ventana.mostrarVentana("Principal");
    	reiniciarValoresFormCliente();
		limpiarTablaCliente();
		limpiarTablaPA();
		bloquearInputsPA();
		limpiarInputs();
	}

	public void agregar(ActionEvent event){
		reiniciarValoresFormCliente();
		limpiarTablaCliente();
		limpiarTablaPA();
		bloquearInputsPA();
		limpiarInputs();
	}

	public void consultar(ActionEvent event) throws SQLException, Exception{
		String[] infoAfiliado = gestor.afiliadoConsultarXNumero(numClienteSeleccionado);
		String[][] infoPA = gestor.personaAdicionalConsultarLista(numClienteSeleccionado);
		String[][] infoAlquileres = gestor.alquilerConsultarXCliente(numClienteSeleccionado);
    	ventana.mostrarVentanasConsultaCliente("Consulta", infoAfiliado, infoPA, infoAlquileres);
	}

	public void guardar(ActionEvent event) throws SQLException, Exception{
		Boolean cedula = ValidacionForm.campoDeTextoNoVacio(iCedula, lCedula, "Dato requerido!");
    	Boolean nombre = ValidacionForm.campoDeTextoNoVacio(iNombre, lNombre, "Dato requerido!");
    	Boolean apellido = ValidacionForm.campoDeTextoNoVacio(iApellido, lApellido, "Dato requerido!");
    	Boolean telefono = ValidacionForm.campoTipoNumero(iTelefono, lTelefono, "Dato requerido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(cedula && nombre && apellido && telefono){
    		if(clienteSeleccionado){
				lMensaje.setText(gestor.afiliadoRegistrar(numClienteSeleccionado, iCedula.getText(), iNombre.getText(), iApellido.getText(), iTelefono.getText(), iEstado.getText(), "0"));
    		}else{
    			//averiguar estado de morosidad
    			lMensaje.setText(gestor.afiliadoModificar(numClienteSeleccionado, iCedula.getText(), iNombre.getText(), iApellido.getText(), iTelefono.getText(), iEstado.getText()));
    		}
    		listarInformacionClientes();
    		reiniciarValoresFormCliente();
    		limpiarInputs();
		}
	}

	public void guardarPA(ActionEvent event) throws SQLException, Exception{
		Boolean cedula = ValidacionForm.campoDeTextoNoVacio(iCedulaPA, lCedulaPA, "Dato requerido!");
    	Boolean nombre = ValidacionForm.campoDeTextoNoVacio(iNombrePA, lNombrePA, "Dato requerido!");

		if(cedula && nombre){
    		if(paSeleccionado){
    			if(!gestor.personaAdicionalBuscar(iCedulaPA.getText())){
    				lMensaje.setText(gestor.personaAdicionalRegistrar(iCedulaPA.getText(), iNombrePA.getText(), numClienteSeleccionado));
    			}else{
    				lMensaje.setText("¡Persona ya registrada!");
    			}
    		}else{
    			lMensaje.setText(gestor.personaAdicionalModificar(iCedulaPA.getText(), iNombrePA.getText(), iCedula.getText()));
    		}
    		listarInformacionPA();
    		reiniciarValoresFormPA();
    		limpiarInputsPA();
		}
	}

	public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionClientes();
	}

	//LISTAR INFORMACIÓN DE CLIENTES
    public void listarInformacionClientes() throws SQLException, Exception{
    	if(listenerClienteCreado){
    		seleccionDatoCliente.removeListener(selectorTablaCliente);
        	tablaCliente.getItems().clear();
        	agregarListenersTablaCliente();
    	}else{
    		agregarListenersTablaCliente();
    	}
    	String[][] listaClientes = gestor.afiliadoConsultarLista();
		for(int i=0; i<listaClientes.length; i++){
			TablaCliente fila = new TablaCliente(Integer.parseInt(listaClientes[i][0]), listaClientes[i][1], listaClientes[i][2]);
	    	datosClientes.add(fila);
        }
    }

	public void eliminar(ActionEvent event) throws SQLException, Exception{
		gestor.eliminarPersonasAdicionales(numClienteSeleccionado);
		lMensaje.setText(gestor.afiliadoEliminar(numClienteSeleccionado));
		listarInformacionClientes();
		reiniciarValoresFormCliente();
		limpiarTablaPA();
		bloquearInputsPA();
		limpiarInputs();
	}

	public void eliminarPA(ActionEvent event) throws SQLException, Exception{
		lMensaje.setText(gestor.personaAdicionalEliminar(iCedulaPA.getText()));
		listarInformacionPA();
		reiniciarValoresFormPA();
		limpiarInputsPA();
	}

	public void reiniciarValoresFormCliente(){
		lCedula.setText("");
    	lNombre.setText("");
    	lApellido.setText("");
    	lTelefono.setText("");
    	numClienteSeleccionado = 0;
    	clienteSeleccionado = true;
    	reiniciarValoresFormPA();
    	btnGuardarPA.setDisable(true);
    	lMensaje.setText("");
    	btnEliminar.setDisable(true);
    	btnConsultar.setDisable(true);
    }

	public void limpiarTablaCliente(){
		seleccionDatoCliente.removeListener(selectorTablaCliente);
		tablaCliente.getItems().clear();
	}

	public void limpiarTablaPA(){
		seleccionDatoPA.removeListener(selectorTablaPA);
		tablaPA.getItems().clear();
	}

    public void reiniciarValoresFormPA(){
    	lCedulaPA.setText("");
    	lNombrePA.setText("");
    	paSeleccionado = true;
    	idPASeleccionado = null;
    	btnEliminarPA.setDisable(true);
    }

    public void limpiarInputs(){
    	iCedula.clear();
    	iNombre.clear();
    	iApellido.clear();
    	iTelefono.clear();
    	iEstado.setSelected(true);
    	iEstado.setText("Activo");
    }

    public void limpiarInputsPA(){
    	iCedulaPA.clear();
    	iNombrePA.clear();
    }

    public void bloquearInputs(){
    	iCedulaPA.setDisable(true);
    	iNombrePA.setDisable(true);
    }

    public void desbloquearInputs(){
    	iCedulaPA.setDisable(false);
    	iNombrePA.setDisable(false);
    }

    public void bloquearInputsPA(){
    	iCedulaPA.setDisable(true);
    	iNombrePA.setDisable(true);
    }

    public void desbloquearInputsPA(){
    	iCedulaPA.setDisable(false);
    	iNombrePA.setDisable(false);
    }

    public void desbloquearBotones(){
    	btnGuardarPA.setDisable(false);
    	btnEliminar.setDisable(false);
    	btnConsultar.setDisable(false);
    }
}

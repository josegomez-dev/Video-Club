package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaAlquiler;
import InterfazUsuario.Tablas.TablaEjmAlquiler;
import InterfazUsuario.Tablas.TablaPeliAlquiler;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Alquiler implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PELÍCULAS
	@FXML
    private TableView<TablaPeliAlquiler> tablaPelicula;
	@FXML
    private TableColumn<TablaPeliAlquiler, String> cIDPeli;
    @FXML
    private TableColumn<TablaPeliAlquiler, String> cTitulo;

    //DEFINIR TABLA DE EJEMPLARES
  	@FXML
    private TableView<TablaEjmAlquiler> tablaEjemplar;
  	@FXML
    private TableColumn<TablaEjmAlquiler, String> cIDEjm;
    @FXML
    private TableColumn<TablaEjmAlquiler, String> cFormato;

    //DEFINIR TABLA DE ALQUILERES
  	@FXML
    private TableView<TablaAlquiler> tablaAlquiler;
  	@FXML
    private TableColumn<TablaAlquiler, Integer> cCodigo;
    @FXML
    private TableColumn<TablaAlquiler, String> cCedula;
    @FXML
    private TableColumn<TablaAlquiler, String> cIDEjemplar;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
    @FXML
    private Label lFecha;
    @FXML
    private Label lNumAfiliado;
    @FXML
    private Label lIDPelicula;
    @FXML
    private Label lCodEjemplar;
    @FXML
    private Label lFechaDevolucion;

    //DEFINIR FORMULARIO DEL ALQUILER
    @FXML
    private TextField iFecha;
    @FXML
    private TextField iNumAfiliado;
    @FXML
    private TextField iIDPelicula;
    @FXML
    private TextField iCodEjemplar;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnListarPel;
    @FXML
    private Button btnListarAlq;
    @FXML
    private Button btnDevolver;
    @FXML
    private Button btnAgregar;

    //DEFINIR OVSERVABLELIST TABLA PELICULA
    private ObservableList<TablaPeliAlquiler> datosPeliculas = FXCollections.observableArrayList();
    private ObservableList<TablaPeliAlquiler> seleccionDatoPelicula;
    private String idPeliSeleccionado;// ID PELÍCULA
    private boolean listenerPeliCreado = false;

    //DEFINIR OVSERVABLELIST TABLA EJEMPLAR
    private ObservableList<TablaEjmAlquiler> datosEjemplar = FXCollections.observableArrayList();
    private ObservableList<TablaEjmAlquiler> seleccionDatoEjemplar;
    private String idEjmSeleccionado;// ID EJEMPLAR
    private boolean listenerEjmCreado = false;

    //DEFINIR OVSERVABLELIST TABLA ALQUILER
    private ObservableList<TablaAlquiler> datosAlquileres = FXCollections.observableArrayList();
    private ObservableList<TablaAlquiler> seleccionDatoAlquiler;
    private boolean alqSeleccionado = true;	// true : REGISTRO
										 	// false: MODIFICACIÓN
    private int idAlqSeleccionado;// ID ALQUILER
    private boolean listenerAlqCreado = false;

    //DEFINIR CONSTRUCTOR
    public Alquiler(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TABLA PELÍCULA
		cIDPeli.setCellValueFactory(new PropertyValueFactory<TablaPeliAlquiler,String>("rID"));
		cTitulo.setCellValueFactory(new PropertyValueFactory<TablaPeliAlquiler,String>("rTitulo"));
		tablaPelicula.setItems(datosPeliculas);
		seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();

		//TABLA EJEMPLAR
		cIDEjm.setCellValueFactory(new PropertyValueFactory<TablaEjmAlquiler,String>("rID"));
		cFormato.setCellValueFactory(new PropertyValueFactory<TablaEjmAlquiler,String>("rFormato"));
		tablaEjemplar.setItems(datosEjemplar);
		seleccionDatoEjemplar = tablaEjemplar.getSelectionModel().getSelectedItems();

		//TABLA ALQUILER
		cCodigo.setCellValueFactory(new PropertyValueFactory<TablaAlquiler, Integer>("rNumero"));
    	cCedula.setCellValueFactory(new PropertyValueFactory<TablaAlquiler, String>("rCedula"));
    	cIDEjemplar.setCellValueFactory(new PropertyValueFactory<TablaAlquiler, String>("rNombre"));
    	tablaAlquiler.setItems(datosAlquileres);
    	seleccionDatoAlquiler = tablaAlquiler.getSelectionModel().getSelectedItems();

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        iFecha.setText(formato.format(new Date()));
        btnDevolver.setDisable(true);
	}

	//DEFINIR LISTENER DE LA TABLA PELICULAS
    private final ListChangeListener<TablaPeliAlquiler> selectorTablaPelicula = new ListChangeListener<TablaPeliAlquiler>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaPeliAlquiler > c) {
            try {
				extraerIDPeliculaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //DEFINIR LISTENER DE LA TABLA EJEMPLARES
    private final ListChangeListener<TablaEjmAlquiler> selectorTablaEjemplar = new ListChangeListener<TablaEjmAlquiler>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaEjmAlquiler > c) {
            try {
				extraerIDEjemplarSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //DEFINIR LISTENER DE LA TABLA ALQUILERES
    private final ListChangeListener<TablaAlquiler> selectorTablaAlquiler = new ListChangeListener<TablaAlquiler>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaAlquiler > c) {
            try {
				extraerIDAlquilerSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //EXTRAER ID DE LA PELÍCULA Y CARGAR SUS EJEMPLARES
    private void extraerIDPeliculaSeleccionado() throws SQLException, Exception {
        final TablaPeliAlquiler tabla = getTablaPeliSeleccionada();
        iCodEjemplar.clear();
        idPeliSeleccionado = tabla.getRID();
        iIDPelicula.setText(idPeliSeleccionado);
        listarInformacionEjemplares();
    }

    //EXTRAER ID DEL EJEMPLAR
    private void extraerIDEjemplarSeleccionado() throws SQLException, Exception {
        final TablaEjmAlquiler tabla = getTablaEjmSeleccionada();
        idEjmSeleccionado = tabla.getRID();
        iCodEjemplar.setText(idEjmSeleccionado);
    }

    //EXTRAER ID DEL ALQUILER Y CARGAR SUS DATOS EN LOS CAMPOS DE TEXTO
    private void extraerIDAlquilerSeleccionado() throws SQLException, Exception {
    	alqSeleccionado = false;
    	btnDevolver.setDisable(false);
        final TablaAlquiler tabla = getTablaAlqSeleccionada();
        idAlqSeleccionado = tabla.getRNumero();
        cargarDatosAlquilerSeleccionado(gestor.alquilerConsultarXNumero(idAlqSeleccionado));
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosAlquilerSeleccionado(String[] pinfo){
    	limpiarLabels();
        iFecha.setText(pinfo[0]);
        lFechaDevolucion.setText(pinfo[1]);
        iNumAfiliado.setText(pinfo[2]);
        iIDPelicula.setText(gestor.ejemplarConsultarXCodigo(pinfo[3])[3]);
        iCodEjemplar.setText(pinfo[3]);
    }

    //SELECCIONAR UNA PELÍCULA DE LA TABLA
    public TablaPeliAlquiler getTablaPeliSeleccionada() {
        if (tablaPelicula != null) {
			List<TablaPeliAlquiler> tabla = tablaPelicula.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaPeliAlquiler peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //SELECCIONAR UN EJEMPLAR DE LA TABLA
    public TablaEjmAlquiler getTablaEjmSeleccionada() {
        if (tablaEjemplar != null) {
			List<TablaEjmAlquiler> tabla = tablaEjemplar.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaEjmAlquiler peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //SELECCIONAR UN ALQUILER DE LA TABLA
    public TablaAlquiler getTablaAlqSeleccionada() {
        if (tablaAlquiler != null) {
			List<TablaAlquiler> tabla = tablaAlquiler.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaAlquiler peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //LISTAR TÍTULOS DE PELÍCULAS
    public void listarInformacionPeliculas() throws SQLException, Exception{
    	if(listenerPeliCreado){
        	seleccionDatoPelicula.removeListener(selectorTablaPelicula);
        	tablaPelicula.getItems().clear();
        	agregarListenersTablaPelicula();
    	}else{
    		agregarListenersTablaPelicula();
    	}
    	String[][] listaPeliculas = gestor.peliculaConsultarLista();
		for(int i=0; i<listaPeliculas.length; i++){
			TablaPeliAlquiler fila = new TablaPeliAlquiler(listaPeliculas[i][0], listaPeliculas[i][1]);
	    	datosPeliculas.add(fila);
        }
    }

    //LISTAR FORMATOS DE EJEMPLARES
    public void listarInformacionEjemplares() throws SQLException, Exception{
    	if(listenerEjmCreado){
        	seleccionDatoEjemplar.removeListener(selectorTablaEjemplar);
        	tablaEjemplar.getItems().clear();
        	agregarListenersTablaEjemplar();
    	}else{
    		agregarListenersTablaEjemplar();
    	}
    	String[][] listaEjemplares = gestor.ejemplarConsultarDisponibles(idPeliSeleccionado);
		for(int i=0; i<listaEjemplares.length; i++){
			TablaEjmAlquiler fila = new TablaEjmAlquiler(listaEjemplares[i][0], listaEjemplares[i][1]);
	    	datosEjemplar.add(fila);
        }
    }

    //LISTAR INFORMACIÓN DE ALQUILERES
    public void listarInformacionAlquileres() throws SQLException, Exception{
    	if(listenerAlqCreado){
        	seleccionDatoAlquiler.removeListener(selectorTablaAlquiler);
        	tablaAlquiler.getItems().clear();
        	agregarListenersTablaAlquiler();
    	}else{
    		agregarListenersTablaAlquiler();
    	}
    	String[][] listaAlquileres = gestor.alquilerConsultarLista();
    	for(int i=0; i<listaAlquileres.length; i++){
	        TablaAlquiler lista = new TablaAlquiler(Integer.parseInt(listaAlquileres[i][0]), listaAlquileres[i][1], listaAlquileres[i][2]);
	    	datosAlquileres.add(lista);
        }
    }

    //AGREGAR LISTENER A TABLA PELÍCULAS
    private void agregarListenersTablaPelicula(){
    	seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();
    	seleccionDatoPelicula.addListener(selectorTablaPelicula);
    	listenerPeliCreado = true;
    }

    //AGREGAR LISTENER A TABLA EJEMPLARES
    private void agregarListenersTablaEjemplar(){
    	seleccionDatoEjemplar = tablaEjemplar.getSelectionModel().getSelectedItems();
    	seleccionDatoEjemplar.addListener(selectorTablaEjemplar);
    	listenerEjmCreado = true;
    }

    //AGREGAR LISTENER A TABLA ALQUILERES
    private void agregarListenersTablaAlquiler(){
    	seleccionDatoAlquiler = tablaAlquiler.getSelectionModel().getSelectedItems();
    	seleccionDatoAlquiler.addListener(selectorTablaAlquiler);
    	listenerAlqCreado = true;
    }

	//DEFINIR ACCIÓN DE LOS BOTONES
	public void atras(ActionEvent event){
		limpiarTablaPelicula();
		limpiarTablaEjemplar();
		limpiarTablaAlquiler();
		limpiarInputs();
		btnDevolver.setDisable(true);
		lMensaje.setText("");
    	ventana.mostrarVentana("Principal");
	}

	public void devolver(ActionEvent event) throws SQLException, Exception{
		if(!alqSeleccionado){
			String multaTotal = gestor.calcularMultaTotal(iFecha.getText());

			String[] infoAfiliado = gestor.afiliadoConsultarXNumero(Integer.parseInt(iNumAfiliado.getText()));
			String[] infoAlquiler = {iIDPelicula.getText(), iCodEjemplar.getText(), iFecha.getText(), lFechaDevolucion.getText(), multaTotal};
	    	ventana.mostrarVentanasDevolucionAlquiler("Devolucion", infoAfiliado, infoAlquiler, Integer.parseInt(iNumAfiliado.getText()));
	    	if(gestor.alquilerConsultarXNumero(idAlqSeleccionado) == null){
	    		lMensaje.setText("¡Alquiler devuelto!");
	    	}
	    	limpiarLabels();
    		limpiarInputs();
    		limpiarTablaPelicula();
    		limpiarTablaEjemplar();
	    	listarInformacionAlquileres();
	    	btnDevolver.setDisable(true);
		}else{
			lMensaje.setText("¡Seleccione un alquiler!");
		}
	}

	public void guardar(ActionEvent event) throws NumberFormatException, SQLException, Exception{
		// VALIDAR FORMULARIO
		Boolean fecha = ValidacionForm.campoTipoFecha(iFecha, lFecha, "Formato no válido!");
    	Boolean afiliado = ValidacionForm.campoDeTextoNoVacio(iNumAfiliado, lNumAfiliado, "Dato requerido!");
    	Boolean pelicula = ValidacionForm.campoDeTextoNoVacio(iIDPelicula, lIDPelicula, "Dato requerido!");
    	Boolean ejemplar = ValidacionForm.campoDeTextoNoVacio(iCodEjemplar, lCodEjemplar, "Dato requerido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(fecha && afiliado && pelicula && ejemplar){

    		String fechaDevolucion = gestor.fechasSumarRestarDiasFecha(iFecha.getText(), 2);
    		lFechaDevolucion.setText(fechaDevolucion);

    		if(alqSeleccionado){
    			lMensaje.setText(gestor.alquilerRegistrar(iFecha.getText(), fechaDevolucion, Integer.parseInt(iNumAfiliado.getText()), iCodEjemplar.getText()));
    		}else{
    			lMensaje.setText(gestor.alquilerModificar(idAlqSeleccionado, iFecha.getText(), fechaDevolucion, Integer.parseInt(iNumAfiliado.getText()), iCodEjemplar.getText()));
    		}
    		alqSeleccionado = true;
    		limpiarLabels();
    		limpiarInputs();
    		limpiarTablaPelicula();
    		limpiarTablaEjemplar();
    		listarInformacionAlquileres();
    		btnDevolver.setDisable(true);
		}
	}

	public void listarPeliculas(ActionEvent event) throws SQLException, Exception{
		listarInformacionPeliculas();
	}

	public void listarAlquileres(ActionEvent event) throws SQLException, Exception{
		listarInformacionAlquileres();
	}

	public void agregar(ActionEvent event){
		alqSeleccionado = true;
		limpiarTablaEjemplar();
		limpiarTablaPelicula();
		limpiarTablaAlquiler();
		limpiarLabels();
		limpiarInputs();
		btnDevolver.setDisable(true);
		lMensaje.setText("");
	}

	public void limpiarTablaPelicula(){
		seleccionDatoPelicula.removeListener(selectorTablaPelicula);
		tablaPelicula.getItems().clear();
	}

	public void limpiarTablaEjemplar(){
		seleccionDatoEjemplar.removeListener(selectorTablaEjemplar);
		tablaEjemplar.getItems().clear();
	}

	public void limpiarTablaAlquiler(){
		seleccionDatoAlquiler.removeListener(selectorTablaAlquiler);
		tablaAlquiler.getItems().clear();
	}

    public void limpiarLabels(){
    	lFecha.setText("");
    	lNumAfiliado.setText("");
    	lIDPelicula.setText("");
    	lCodEjemplar.setText("");
    	lFechaDevolucion.setText("");
    }

    public void limpiarInputs(){
    	iNumAfiliado.clear();
    	iIDPelicula.clear();
    	iCodEjemplar.clear();
    }
}
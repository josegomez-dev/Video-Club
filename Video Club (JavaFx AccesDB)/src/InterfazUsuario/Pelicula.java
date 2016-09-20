package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaPelicula;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Pelicula implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PELICULAS
	@FXML
    private TableView<TablaPelicula> tablaPelicula;
    @FXML
    private TableColumn<TablaPelicula, String> cIDPeli;
    @FXML
    private TableColumn<TablaPelicula, String> cTituloPeli;
    @FXML
    private TableColumn<TablaPelicula, String> cCategoriaPeli;

    //DEFINIR MENSAJES DE VALIDACIÓN
    @FXML
    private Label lMensaje;
    @FXML
    private Label lID;
    @FXML
    private Label lTitulo;
    @FXML
    private Label lTipo;

    //DEFINIR FORMULARIO DEL EJEMPLAR
    @FXML
    private TextField iID;
    @FXML
    private TextArea iTitulo;
    @FXML
    private ComboBox<String> iTipo;

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
    private Button btnEjemplares;
    @FXML
    private Button btnAgregar;

    //DEFINIR COMBOBOX DE CATEGORÍAS
    private ObservableList<String> categorias = FXCollections.observableArrayList(
    		"Serie TV", "Acción", "Infantil", "Comedia","Documental", "Drama", "Terror", "Romántica", "Ciencia ficción"
    );

    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaPelicula> datosPeliculas = FXCollections.observableArrayList();
    private ObservableList<TablaPelicula> seleccionDatoPelicula;
    private boolean peliSeleccionado = true;// true : REGISTRO
 											// false: MODIFICACIÓN
    private String idPeliSeleccionado;// ID PELICULA
    private boolean listenerPeliCreado = false;

    //DEFINIR CONSTRUCTOR
    public Pelicula(){
    	gestor = new Gestor();
    }

	//DEFINIR LISTENER DE LA TABLA PELICULAS
	private final ListChangeListener<TablaPelicula> selectortPelicula = new ListChangeListener<TablaPelicula>() {
    	@Override
        public void onChanged(ListChangeListener.Change < ? extends TablaPelicula > c) {
            try {
				extraerIDPeliculaSeleccionado();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    };

    //SELECCIONAR UNA PELÍCULA DE LA TABLA
    public TablaPelicula getTablaPeliSeleccionada() {
        if (tablaPelicula != null) {
			List<TablaPelicula> tabla = tablaPelicula.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                final TablaPelicula peticion = tabla.get(0);
                return peticion;
            }
        }
        return null;
    }

    //EXTRAER ID DE LA PELÍCULA
    private void extraerIDPeliculaSeleccionado() throws SQLException, Exception {
    	peliSeleccionado = false;
    	final TablaPelicula tabla = getTablaPeliSeleccionada();
        idPeliSeleccionado = tabla.getRID();
        cargarDatosPeliculaSeleccionada(gestor.peliculaConsultarXId(idPeliSeleccionado));
    	btnEjemplares.setDisable(false);
    	btnEliminar.setDisable(false);
        lMensaje.setText("");
    }

    //INICIALIZAR CONTROLADOR
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cIDPeli.setCellValueFactory(new PropertyValueFactory<TablaPelicula, String>("rID"));
	    cTituloPeli.setCellValueFactory(new PropertyValueFactory<TablaPelicula, String>("rTitulo"));
	    cCategoriaPeli.setCellValueFactory(new PropertyValueFactory<TablaPelicula, String>("rCategoria"));
    	tablaPelicula.setItems(datosPeliculas);
    	seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();
		btnEjemplares.setDisable(true);
		btnEliminar.setDisable(true);
		iTipo.setItems(categorias);
	}

    //AGREGAR LISTENER A TABLA PELÍCULAS
    private void agregarListenerstPelicula(){
    	seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();
    	seleccionDatoPelicula.addListener(selectortPelicula);
    	listenerPeliCreado = true;
    }

	//LISTAR INFORMACIÓN DE PELÍCULAS
    public void listarInformacionPeliculas() throws SQLException, Exception{
    	if(listenerPeliCreado){
        	seleccionDatoPelicula.removeListener(selectortPelicula);
        	tablaPelicula.getItems().clear();
        	agregarListenerstPelicula();
    	}else{
    		agregarListenerstPelicula();
    	}
    	String[][] listaPeliculas = gestor.peliculaConsultarLista();
		for(int i=0; i<listaPeliculas.length; i++){
			TablaPelicula fila = new TablaPelicula(listaPeliculas[i][0], listaPeliculas[i][1], listaPeliculas[i][2]);
	    	datosPeliculas.add(fila);
        }
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    // CARGAR INFORMACIÓN DEL CLIENTE SELECCIONADO DE LA TABLA
    private void cargarDatosPeliculaSeleccionada(String[] pinfo){
    	limpiarLabels();
        iID.setText(pinfo[0]);
        iTitulo.setText(pinfo[1]);
        iTipo.setValue(pinfo[2]);
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
	public void atras(ActionEvent event){
    	ventana.mostrarVentana("Principal");
	}

	public void ejemplares(ActionEvent event){
		ventana.guardarIDPeliculaDeEjemplar(idPeliSeleccionado);
		seleccionDatoPelicula.removeListener(selectortPelicula);
		tablaPelicula.getItems().clear();
    	reiniciarValores();
    	ventana.mostrarVentana("Ejemplar");
	}

	public void guardar(ActionEvent event) throws SQLException, Exception{
		// VALIDAR FORMULARIO
    	Boolean id = ValidacionForm.campoDeTextoNoVacio(iID, lID, "Dato requerido!");
    	Boolean titulo = ValidacionForm.areaDeTextoNoVacio(iTitulo, lTitulo, "Dato requerido!");
    	Boolean categoria = ValidacionForm.campoComboBox(iTipo, lTipo, "Dato requerido!");

    	// REGISTRAR O MODIFICAR DATOS Y LA TABLA SE ACTUALIZA
    	if(id && titulo && categoria){
    		if(peliSeleccionado){
    			if(!gestor.peliculaBuscar(iID.getText())){
    				lMensaje.setText(gestor.peliculaRegistrar(iID.getText(), iTitulo.getText(), iTipo.getValue()));
    			}else{
    				lMensaje.setText("Película ya registrada...");
    			}
    		}else{
    			lMensaje.setText(gestor. peliculaModificar(iID.getText(), iTitulo.getText(), iTipo.getValue()));
    		}
    		reiniciarValores();
        	listarInformacionPeliculas();
		}
	}

	public void listar(ActionEvent event) throws SQLException, Exception{
		listarInformacionPeliculas();
	}

	public void eliminar(ActionEvent event) throws SQLException, Exception{
		lMensaje.setText(gestor.peliculaEliminar(iID.getText()));
    	listarInformacionPeliculas();
    	reiniciarValores();
	}

	public void agregar(ActionEvent event){
		reiniciarValores();
		lMensaje.setText("");
	}

	//VOLVER VALORES ORIGINALES DE VARIABLES
	public void reiniciarValores(){
		iTipo.getSelectionModel().clearSelection();
		btnEjemplares.setDisable(true);
		btnEliminar.setDisable(true);
		peliSeleccionado = true;
    	idPeliSeleccionado = null;
		limpiarLabels();
		limpiarInputs();
    }

	// QUITAR LOS MENSAJES DE VALIDACIÓN
    public void limpiarLabels(){
    	lID.setText("");
    	lTitulo.setText("");
    }

    // LIMPIAR LOS CAMPOS DEL FORMULARIO
    public void limpiarInputs(){
    	iID.clear();
    	iTitulo.clear();
    }
}

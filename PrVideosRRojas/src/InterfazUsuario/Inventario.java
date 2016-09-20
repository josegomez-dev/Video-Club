package InterfazUsuario;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import CapaLogica.Gestor;
import InterfazUsuario.Tablas.TablaEjemplar;
import InterfazUsuario.Tablas.TablaPelicula;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Inventario implements Initializable, ControlVentanas{

	private AdminVentanas ventana;

	//GESTOR
	private Gestor gestor;

	//DEFINIR TABLA DE PELICULAS
	@FXML
    private TableView<TablaPelicula> tablaPelicula;
    @FXML
    private TableColumn<TablaPelicula, String> cIDPelicula;
    @FXML
    private TableColumn<TablaPelicula, String> cTitulo;
    @FXML
    private TableColumn<TablaPelicula, String> cCategoria;

    //DEFINIR TABLA DE EJEMPLARES
  	@FXML
    private TableView<TablaEjemplar> tablaEjemplar;
    @FXML
    private TableColumn<TablaEjemplar, String> cIDEjemplar;
    @FXML
    private TableColumn<TablaEjemplar, String> cEstado;
    @FXML
    private TableColumn<TablaEjemplar, String> cFormato;

    //DEFINIR BOTONES
    @FXML
    private Button btnAtras;
    @FXML
    private Button btnListar;

    //DEFINIR OVSERVABLELIST TABLA PELÍCULA
    private ObservableList<TablaPelicula> datosPeliculas = FXCollections.observableArrayList();
    private ObservableList<TablaPelicula> seleccionDatoPelicula;
    private String idPeliSeleccionado;// ID PELICULA
    private boolean listenerPeliCreado = false;

    //DEFINIR OVSERVABLELIST TABLA EJEMPLAR
    private ObservableList<TablaEjemplar> datosEjemplar = FXCollections.observableArrayList();

    //DEFINIR CONSTRUCTOR
    public Inventario(){
    	gestor = new Gestor();
    }

    public void setScreenPane(AdminVentanas pventana) {
    	ventana = pventana;
    }

    //INICIALIZAR CONTROLADOR
  	@Override
  	public void initialize(URL arg0, ResourceBundle arg1) {
  		//TABLA ALQUILER
  	    cIDPelicula.setCellValueFactory(new PropertyValueFactory<TablaPelicula, String>("rID"));
  	    cTitulo.setCellValueFactory(new PropertyValueFactory<TablaPelicula, String>("rTitulo"));
  	    cCategoria.setCellValueFactory(new PropertyValueFactory<TablaPelicula,String>("rCategoria"));
      	tablaPelicula.setItems(datosPeliculas);
      	seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();

      	//TABLA EJEMPLAR
      	cIDEjemplar.setCellValueFactory(new PropertyValueFactory<TablaEjemplar,String>("rID"));
        cEstado.setCellValueFactory(new PropertyValueFactory<TablaEjemplar, String>("rEstado"));
        cFormato.setCellValueFactory(new PropertyValueFactory<TablaEjemplar,String>("rFormato"));
        tablaEjemplar.setItems(datosEjemplar);
  	}

  	//DEFINIR LISTENER DE LA TABLA PELICULAS
    private final ListChangeListener<TablaPelicula> selectorTablaPelicula = new ListChangeListener<TablaPelicula>() {
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

    //EXTRAER ID DE LA PELÍCULA
    private void extraerIDPeliculaSeleccionado() throws SQLException, Exception {
        final TablaPelicula tabla = getTablaPeliSeleccionada();
        idPeliSeleccionado = tabla.getRID();
        listarInformacionEjemplares();
    }

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

    //LISTAR INFORMACIÓN DE PELÍCULAS
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
			TablaPelicula fila = new TablaPelicula(listaPeliculas[i][0], listaPeliculas[i][1], listaPeliculas[i][2]);
	    	datosPeliculas.add(fila);
        }
    }

    //LISTAR INFORMACIÓN DE EJEMPLARES
    public void listarInformacionEjemplares() throws SQLException, Exception{
    	tablaEjemplar.getItems().clear();
    	String[][] listaEjemplares = gestor.ejemplarConsultarLista(idPeliSeleccionado);
		for(int i=0; i<listaEjemplares.length; i++){
	        TablaEjemplar fila = new TablaEjemplar(listaEjemplares[i][0], listaEjemplares[i][1], listaEjemplares[i][2]);
	    	datosEjemplar.add(fila);
        }
    }

    //AGREGAR LISTENER A TABLA PELÍCULAS
    private void agregarListenersTablaPelicula(){
    	seleccionDatoPelicula = tablaPelicula.getSelectionModel().getSelectedItems();
    	seleccionDatoPelicula.addListener(selectorTablaPelicula);
    	listenerPeliCreado = true;
    }

    //DEFINIR ACCIÓN DE LOS BOTONES
  	public void atras(ActionEvent event){
		tablaEjemplar.getItems().clear();
		seleccionDatoPelicula.removeListener(selectorTablaPelicula);
		tablaPelicula.getItems().clear();
		ventana.mostrarVentana("Principal");
  	}

  	public void listar(ActionEvent event) throws SQLException, Exception{
  		listarInformacionPeliculas();
  	}
}
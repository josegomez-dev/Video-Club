package InterfazUsuario;

import java.io.IOException;
import java.util.HashMap;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminVentanas extends StackPane{

	private String idPeliculaDeEjemplar;

    // VENTANAS QUE VAN A MOSTRARSE
    private HashMap<String, Node> ventanas = new HashMap<>();

    // CONSTRUCTOR
    public AdminVentanas() {
        super();
    }

    public void guardarIDPeliculaDeEjemplar(String pid){
    	idPeliculaDeEjemplar = pid;
    }

    public String obtenerIDPeliculaDeEjemplar(){
    	return idPeliculaDeEjemplar;
    }

    // AGREGA UNA VENTANA A LA COLECCIÓN
    public void addVentana(String pnombre, Node pventana){
        ventanas.put(pnombre, pventana);
    }

    // RETORNA NODO CON EL NOMBRE APROPIADO
    public Node getVentana(String pnombre){
        return ventanas.get(pnombre);
    }

    // CARGA LOS ARCHIVOS .FXML, RECIBIENDO EL NOMBRE DE LA VENTANA Y EL NOMBRE DEL ARCHIVO .FXML
    public boolean cargarVentana(String pnombreVentana, String pfxml){
        try{
            FXMLLoader FXML = new FXMLLoader(getClass().getResource(pfxml));
            Parent ventana = (Parent)FXML.load();
            ControlVentanas controlVentanas = ((ControlVentanas)FXML.getController());
            controlVentanas.setScreenPane(this);
            addVentana(pnombreVentana, ventana);
            return true;
        }catch(Exception e){
            e.getMessage();
            return false;
        }
    }

    // MUESTRA LA VENTANA, RECIBIENDO EL NOMBRE DE LA MISMA, SE ASEGURA QUE LA VENTANA HAYA CARGADO.
    // CUANDO SE MUESTRE OTRA VENTANA LA ANTERIOR ES REMOVIDA.
    public boolean mostrarVentana(final String pnombre){
    	if(ventanas.get(pnombre) != null) {
			final DoubleProperty opacity = opacityProperty();

			if (!getChildren().isEmpty()){
				getChildren().remove(0);
				getChildren().add(0, ventanas.get(pnombre));
				Timeline fadeIn = new Timeline(
					new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
					new KeyFrame(new Duration(300), new KeyValue(opacity, 1.0))
				);
				fadeIn.play();
			}else{
				setOpacity(0.0);
				getChildren().add(ventanas.get(pnombre));
				Timeline fadeIn = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(500), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		}else{
			return false;
		}
    }

    //MOSTRAR LA VENTANA DE LA CONSULTA DE CLIENTES CON SU RESPECTIVA INFORMACIÓN
    public void mostrarVentanasConsultaCliente(final String pnombre, String[] pinfoAfiliado, String[][] pinfoPA, String[][] pinfoAlquileres) throws IOException{
    	// Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("ConsultaCliente.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle(pnombre);
		dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

		// Set the person into the controller.
        ConsultaCliente controller = loader.getController();
        controller.mostrarInformacionCliente(pinfoAfiliado, pinfoPA, pinfoAlquileres);

		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
    }

    public void mostrarVentanasDevolucionAlquiler(final String pnombre, String[] infoAfiliado, String[] infoAlquiler, int pnumAfiliado) throws IOException{
    	// Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("DevolucionAlquiler.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

		// Create the dialog Stage.
		Stage dialogStage = new Stage();
		dialogStage.setTitle(pnombre);
		dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

		// Set the person into the controller.
        DevolucionAlquiler controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.mostrarInformacionCliente(infoAfiliado, infoAlquiler, pnumAfiliado);

		// Show the dialog and wait until the user closes it
		dialogStage.showAndWait();
    }

    // REMUEVE LA VENTANA DE LA COLECCIÓN DE PANTALLAS
    public boolean removerVentana(String name){
        if(ventanas.remove(name) == null){
            return false;
        }else{return true;}
    }
}

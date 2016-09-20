package InterfazUsuario;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static String principal = "Principal.fxml";
	public static String cliente = "Cliente.fxml";
	public static String alquiler = "Alquiler.fxml";
	public static String ejemplar = "Ejemplar.fxml";
	public static String inventario = "Inventario.fxml";
	public static String pelicula = "Pelicula.fxml";
	public static String consulta = "ConsultaCliente.fxml";
	public static String devolucion = "DevolucionAlquiler.fxml";

	@Override
	public void start(Stage stage) throws Exception{
		AdminVentanas contenedorPrincipal = new AdminVentanas();
		contenedorPrincipal.cargarVentana("Principal", Main.principal);
		contenedorPrincipal.cargarVentana("Cliente", Main.cliente);
		contenedorPrincipal.cargarVentana("Alquiler", Main.alquiler);
		contenedorPrincipal.cargarVentana("Ejemplar", Main.ejemplar);
		contenedorPrincipal.cargarVentana("Inventario", Main.inventario);
		contenedorPrincipal.cargarVentana("Pelicula", Main.pelicula);
		contenedorPrincipal.cargarVentana("Consulta", Main.consulta);
		contenedorPrincipal.cargarVentana("Devolucion", Main.devolucion);

		contenedorPrincipal.mostrarVentana("Principal");

		Scene scene = new Scene(contenedorPrincipal);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
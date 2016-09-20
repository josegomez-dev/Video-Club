package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaPelicula {

	private final SimpleStringProperty rID;
	private final SimpleStringProperty rTitulo;
	private final SimpleStringProperty rCategoria;

	public TablaPelicula(String sid, String stitulo, String scategoria){
		this.rID = new SimpleStringProperty(sid);
		this.rTitulo = new SimpleStringProperty(stitulo);
		this.rCategoria = new SimpleStringProperty(scategoria);
	}

	public String getRID() {
		return rID.get();
	}

	public void setRID(String pid) {
		rID.setValue(pid);
	}

	public String getRTitulo() {
		return rTitulo.get();
	}

	public void setRTitulo(String ptitulo) {
		rTitulo.set(ptitulo);
	}

	public String getRCategoria() {
		return rCategoria.get();
	}

	public void setRCategoria(String pcategoria) {
		rCategoria.set(pcategoria);
	}
}
package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaPeliAlquiler {

	private final SimpleStringProperty rID;
	private final SimpleStringProperty rTitulo;

	public TablaPeliAlquiler(String sid, String stitulo){
		this.rID = new SimpleStringProperty(sid);
		this.rTitulo = new SimpleStringProperty(stitulo);
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
}
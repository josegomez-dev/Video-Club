package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaEjmAlquiler {

	private final SimpleStringProperty rID;
	private final SimpleStringProperty rFormato;

	public TablaEjmAlquiler(String sid, String sformato){
		this.rID = new SimpleStringProperty(sid);
		this.rFormato = new SimpleStringProperty(sformato);
	}

	public String getRID() {
		return rID.get();
	}

	public void setRID(String pid) {
		rID.setValue(pid);
	}

	public String getRFormato() {
		return rFormato.get();
	}

	public void setRFormato(String pformato) {
		rFormato.set(pformato);
	}
}
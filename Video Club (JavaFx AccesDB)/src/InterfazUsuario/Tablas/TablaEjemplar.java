package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaEjemplar {

	private final SimpleStringProperty rID;
	private final SimpleStringProperty rEstado;
	private final SimpleStringProperty rFormato;

	public TablaEjemplar(String sid, String sestado, String sformato){
		this.rID = new SimpleStringProperty(sid);
		this.rEstado = new SimpleStringProperty(sestado);
		this.rFormato = new SimpleStringProperty(sformato);
	}

	public String getRID() {
		return rID.get();
	}

	public void setRID(String pid) {
		rID.setValue(pid);
	}

	public String getREstado() {
		return rEstado.get();
	}

	public void setREstado(String pestado) {
		rEstado.set(pestado);
	}

	public String getRFormato() {
		return rFormato.get();
	}

	public void setRFormato(String pformato) {
		rFormato.set(pformato);
	}
}
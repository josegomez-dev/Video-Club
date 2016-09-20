package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleStringProperty;

public class TablaPersonaAdicional{

	private final SimpleStringProperty rCedula;
	private final SimpleStringProperty rNombre;

	public TablaPersonaAdicional(String scedula, String snombre){
		this.rCedula = new SimpleStringProperty(scedula);
		this.rNombre = new SimpleStringProperty(snombre);
	}

	public String getRCedula() {
		return rCedula.get();
	}

	public void setRCedula(String pcedula) {
		rCedula.set(pcedula);
	}

	public String getRNombre() {
		return rNombre.get();
	}

	public void setRNombre(String pnombre) {
		rNombre.set(pnombre);
	}
}
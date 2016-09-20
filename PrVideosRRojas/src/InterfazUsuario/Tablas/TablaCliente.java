package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaCliente{

	private final SimpleIntegerProperty rNumero;
	private final SimpleStringProperty rCedula;
	private final SimpleStringProperty rNombre;

	public TablaCliente(int snumero, String scedula, String snombre){
		this.rNumero = new SimpleIntegerProperty(snumero);
		this.rCedula = new SimpleStringProperty(scedula);
		this.rNombre = new SimpleStringProperty(snombre);
	}

	public Integer getRNumero() {
		return rNumero.get();
	}

	public void setRID(Integer pid) {
		rNumero.setValue(pid);
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
package InterfazUsuario.Tablas;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TablaAlquilerCliente{

	private final SimpleIntegerProperty rNumero;
	private final SimpleStringProperty rTitulo;
	private final SimpleStringProperty rFormato;
	private final SimpleStringProperty rFecha;

	public TablaAlquilerCliente(int snumero, String stitulo, String sformato, String sfecha){
		this.rNumero = new SimpleIntegerProperty(snumero);
		this.rTitulo = new SimpleStringProperty(stitulo);
		this.rFormato = new SimpleStringProperty(sformato);
		this.rFecha = new SimpleStringProperty(sfecha);
	}

	public Integer getRNumero() {
		return rNumero.get();
	}

	public void setRID(Integer pnumero) {
		rNumero.setValue(pnumero);
	}

	public String getRTitulo() {
		return rTitulo.get();
	}

	public void setRTitulo(String ptitulo) {
		rTitulo.set(ptitulo);
	}

	public String getRFormato() {
		return rFormato.get();
	}

	public void setRFormato(String pformato) {
		rFormato.set(pformato);
	}

	public String getRFecha() {
		return rFecha.get();
	}

	public void setRFecha(String pfecha) {
		rFecha.set(pfecha);
	}
}
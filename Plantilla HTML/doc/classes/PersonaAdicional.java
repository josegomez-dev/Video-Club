package CapaLogica;

public class PersonaAdicional {

	private String cedula;
	private String nombre;
	private String IDCliente;

	public PersonaAdicional(String pcedula, String pnombre, String pIDCliente){
		setCedula(pcedula);
		setNombre(pnombre);
		setIDCliente(pIDCliente);
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIDCliente() {
		return IDCliente;
	}

	public void setIDCliente(String iDCliente) {
		IDCliente = iDCliente;
	}

	@Override
	public String toString(){
		String 	estado  = "\nCedula: " + this.getCedula();
				estado = "\nNombre:  " + this.getNombre();
		return estado;
	}
}
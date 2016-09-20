package CapaLogica;

public class PersonaAdicional {

	private String cedula;
	private String nombre;

	public PersonaAdicional(String pcedula, String pnombre){
		setCedula(pcedula);
		setNombre(pnombre);
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

	@Override
	public String toString(){
		String 	estado  = "\nCedula: " + this.getCedula();
				estado = "\nNombre:  " + this.getNombre();
		return estado;
	}
}
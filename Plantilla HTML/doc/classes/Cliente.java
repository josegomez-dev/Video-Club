package CapaLogica;

import java.sql.SQLException;
import java.util.ArrayList;
import Multi.MultiAlquiler;
import Multi.MultiPersonaAdicional;

public class Cliente {

	private String cedula;
	private String nombre;
	private String estado;
	private String moroso;
	private int numCliente;
	private String telefono;
	private String primerApellido;
	private static int cantClientes = 0;

	private ArrayList<Alquiler> alquileres;
	private ArrayList<PersonaAdicional> amigos;

	public Cliente(int pnumero, String pcedula, String pnombre, String pprimerApellido, String ptelefono, String pestado){
		setCantClientes();
		setNumCliente(pnumero);
		setCedula(pcedula);
		setNombre(pnombre);
		setPrimerApellido(pprimerApellido);
		setTelefono(ptelefono);
		setEstado(pestado);
		alquileres = new ArrayList<>();
		amigos = new ArrayList<>();
	}

	public static int getCantClientes() {
		return cantClientes;
	}

	public static void setCantClientes() {
		cantClientes++;
	}

	public int getNumCliente() {
		return numCliente;
	}

	private void setNumCliente(int pnumero) {
		this.numCliente = pnumero;
	}

	public String getCedula() {
		return cedula;
	}

	private void setCedula(String pcedula) {
		this.cedula = pcedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String pnombre) {
		this.nombre = pnombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String pprimerApellido) {
		this.primerApellido = pprimerApellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String ptelefono) {
		this.telefono = ptelefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String pestado) {
		this.estado = pestado;
	}

	public String getMoroso() {
		return moroso;
	}

	public void setMoroso(String pmoroso) {
		this.moroso = pmoroso;
	}

	public ArrayList<Alquiler> getAlquileres() throws SQLException, Exception{
		if(alquileres.size() == 0){
			setAlquileres((new MultiAlquiler()).consultarXCliente(this.getNumCliente()));
		}
		return alquileres;
	}

	public void setAlquileres(ArrayList<Alquiler> palquileres){
		alquileres = palquileres;
	}

	public ArrayList<PersonaAdicional> getAmigos() throws SQLException, Exception{
		if(amigos.size() == 0){
			setAmigos((new MultiPersonaAdicional()).listar(this.getCedula()));
		}
		return amigos;
	}

	public void setAmigos(ArrayList<PersonaAdicional> pamigos){
		amigos = pamigos;
	}

	public String toString(){
		String 	estado 	= "\nNo. Cliente:  " + getNumCliente();
				estado += "\nCedula:       " + getCedula();
				estado += "\nNombre:       " + getNombre();
				estado += "\nApellido:     " + getPrimerApellido();
				estado += "\nTelefono:     " + getTelefono();
				estado += "\nEstado:       " + getEstado();
				estado += "\nMoroso:       " + getMoroso();
		return estado;
	}
}

package CapaLogica;

import Multi.MultiPelicula;

public class Ejemplar{

	private int numero;
	private String id;
	private String estado;
	private String formato;
	private String idPelicula;
	private static int cantEjemplares = 0;

	private Pelicula pelicula;

	public Ejemplar(String pid, int pnumero, String pestado, String pformato, String pidPelicula){
		setID(pid);
		setNumero(pnumero);
		setFormato(pformato);
		setEstado(pestado);
		setIdPelicula(pidPelicula);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int pnumero) {
		this.numero = pnumero;
	}

	public int getCantEjemplares(){
		return cantEjemplares;
	}

	public void setCantEjemplares(){
		cantEjemplares++;
	}

	public String getID() {
		return id;
	}

	public void setID(String pid) {
		this.id = pid;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String pformato) {
		this.formato = pformato;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String pestado) {
		this.estado = pestado;
	}

	public String getIdPelicula() {
		return idPelicula;
	}

	public void setIdPelicula(String pidPelicula) {
		this.idPelicula = pidPelicula;
	}

	public Pelicula getPelicula() throws Exception{
		if (pelicula == null) {
			setPelicula((new MultiPelicula()).consultarXId(this.getIdPelicula()));
		}
		return pelicula;
	}

	public void setPelicula(Pelicula ppelicula){
		pelicula = ppelicula;
	}

	public void reiniciarCantEjemplares(int pcantidad){
		Ejemplar.cantEjemplares = pcantidad;
	}

	@Override
	public String toString(){
		String 	estado  = "\nID:        " + this.getID();
				estado += "\nFormato:   " + this.getFormato();
				estado += "\nEstado:    " + this.getEstado();
				try {
					estado += "\nPelicula:  " + this.getPelicula().getTitulo();
				} catch (Exception e) {
					e.printStackTrace();
				}
		return estado;
	}
}
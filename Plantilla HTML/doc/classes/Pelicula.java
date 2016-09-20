package CapaLogica;

import java.sql.SQLException;
import java.util.ArrayList;

import Multi.MultiEjemplar;

public class Pelicula {

	private String id;
	private String titulo;
	private String categoria;
	private int totalEjemplares;
	private int ejemplaresDisponibles;
	private static long cantPeliculas = 0;

	private ArrayList<Ejemplar> ejemplares;

	public Pelicula(String pid, String ptitulo, String pcategoria){
		setCantPeliculas();
		setId(pid);
		setTitulo(ptitulo);
		setCategoria(pcategoria);
		ejemplares = new ArrayList<Ejemplar>();
	}

	public static long getCantPeliculas(){
		return cantPeliculas;
	}

	public static void setCantPeliculas(){
		cantPeliculas++;
	}

	public String getId() {
		return id;
	}

	public void setId(String pid) {
		this.id = pid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String ptitulo) {
		this.titulo = ptitulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String pcategoria) {
		this.categoria = pcategoria;
	}

	public int getTotalEjemplares() {
		return totalEjemplares;
	}

	public void setTotalEjemplares() {
		this.totalEjemplares = this.ejemplares.size();
	}

	public int getEjemplaresDisponibles() {
		return ejemplaresDisponibles;
	}

	public void setEjemplaresDisponibles(int pcantidad) {
		this.ejemplaresDisponibles = pcantidad;
	}

	public ArrayList<Ejemplar> getEjemplares() throws SQLException, Exception{
		if(ejemplares.size() == 0){
			setEjemplares((new MultiEjemplar()).listar(this.getId()));
		}
		return ejemplares;
	}

	public void setEjemplares(ArrayList<Ejemplar> pejemplares){
		ejemplares = pejemplares;
	}

	public String toString(){
		String 	estado  = "\nID:                      " + this.getId();
				estado += "\nTitulo:                  " + this.getTitulo();
				estado += "\nCategoria:               " + this.getCategoria();
				estado += "\nTotal ejemplares:        " + this.getTotalEjemplares();
				estado += "\nEjemplares disponibles:  " + this.getEjemplaresDisponibles();
		return estado;
	}
}
package CapaLogica;

import Multi.MultiCliente;
import Multi.MultiEjemplar;

public class Alquiler {

	private int numero;
	private String multa;
	private String fechaAlquiler;
	private String fechaDevolucion;
	private String idEjemplar;
	private int numAfiliado;
	private static int cantAlquileres = 0;
	private static double montoMulta = 1500;

	private Cliente cliente;
	private Ejemplar ejemplar;

	public Alquiler(int numAlquiler, String pfechaAlquiler, String pfechaDev, String pidEjemplar, int pnumAfiliado){
		setCantAlquileres();
		setNumero(numAlquiler);
		setFechaAlquiler(pfechaAlquiler);
		setFechaDevolucion(pfechaDev);
		setIdEjemplar(pidEjemplar);
		setIdAfiliado(pnumAfiliado);
		cliente = null;
		ejemplar = null;
	}

	public static long getCantAlquileres() {
		return cantAlquileres;
	}

	public static void setCantAlquileres() {
		cantAlquileres++;
	}

	public int getNumero() {
		return numero;
	}

	private void setNumero(int pnumero) {
		this.numero =  pnumero;
	}

	public int getIdAfiliado() {
		return numAfiliado;
	}

	public void setIdAfiliado(int pnumAfiliado) {
		this.numAfiliado =  pnumAfiliado;
	}

	public String getIdEjemplar() {
		return idEjemplar;
	}

	public void setIdEjemplar(String pidEjemplar) {
		this.idEjemplar =  pidEjemplar;
	}

	public String getFechaAlquiler() {
		return fechaAlquiler;
	}

	public void setFechaAlquiler(String pfechaAlquiler) {
		this.fechaAlquiler = pfechaAlquiler;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String pfechaDevolucion) {
		this.fechaDevolucion = pfechaDevolucion;
	}

	public String getMulta() {
		return multa;
	}

	public void setMulta(String pmulta) {
		this.multa = pmulta;
	}

	public static double getMontoMulta() {
		return montoMulta;
	}

	public static void setMontoMulta(double pmontoMulta) {
		Alquiler.montoMulta = pmontoMulta;
	}

	public Cliente getCliente() throws Exception{
		if (cliente == null) {
			setCliente((new MultiCliente()).consultarXNumero(this.getIdAfiliado()));
		}
		return cliente;
	}

	public void setCliente(Cliente pcliente){
		cliente = pcliente;
	}

	public Ejemplar getEjemplar() throws Exception{
		if (ejemplar == null) {
			setEjemplar((new MultiEjemplar()).consultarXCodigo(this.getIdEjemplar()));
		}
		return ejemplar;
	}

	public void setEjemplar(Ejemplar pejemplar){
		ejemplar = pejemplar;
	}

	@Override
	public String toString(){
		String 	estado  = "\nNumero:               " + this.getNumero();
				estado += "\nEjemplar:             " + this.getIdEjemplar();
				estado += "\nAfiliado:             " + this.getIdAfiliado();
				estado += "\nMulta:                " + this.getMulta();
				estado += "\nFecha de alquiler:    " + this.getFechaAlquiler();
				estado += "\nFecha de devolución:  " + this.getFechaDevolucion();
		return estado;
	}

}

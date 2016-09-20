package ConexionBD;

/**
 *Clase Conector
 *Clase que se encarga de parametrizar e inicializar la conexion
 *a la base de datos.
 */

public class Conector{
	private static String base = "BDVideos.accdb";
	private static String bd = 	System.getProperty("user.dir") +
								System.getProperty("file.separator") + base;

	//Atributos de clase.
	private static AccesoBD conectorBD = null;
	private static final String [] SETTING={"jdbc:ucanaccess://", bd, "", ""};

	/**
	 *Metodo estatico que retorna la conexion a la base de datos
	 *@return conectorBD objeto del tipo AccesoBD del paquete
	 *ConeccionBD
	 */
	public static AccesoBD getConector() throws
	java.sql.SQLException,Exception{
		if (conectorBD == null){
			conectorBD = new AccesoBD(SETTING);
		}
		return conectorBD;
	}
}
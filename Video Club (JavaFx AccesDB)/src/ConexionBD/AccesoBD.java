package ConexionBD;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoBD {

	private Connection conn=null;
	private Statement declaracion;

    protected AccesoBD(String[] pset){
    	try{
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn = DriverManager.getConnection(pset[0]+pset[1],pset[2],pset[3]);
			declaracion= conn.createStatement();
			Runtime.getRuntime().addShutdownHook(new Thread(new SalidaDelSistema()));
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Error al crear la conexion ",ex);
		}
	}

	/**
	* Metodo que conecta con la base de datos
	* @param no recibe parametros
	* @return retorna una conexion con
	*/
	public void ejecutarSQL(String sentencia)
			throws SQLException,Exception{
				declaracion.execute(sentencia);
			}

	/**
	 *Metodo que ejecuta una sentencia en la
	 *base de datos y devuelve un ResultSet
	 *con los resultados
	 *@param sentencia cadena sql que sera
	 *ejecutada en la base de datos
	 *@param retorno booleana que indica que se
	 *desea un resultado de la consulta
	 */
	public ResultSet consultarSQL(String sentencia)
	throws SQLException,Exception{
		ResultSet rs = declaracion.executeQuery(sentencia);
		return rs;
	}

	/**
	 *Permite controlar el inicio una transaccion
	 *desde afuera.  A partir de este momento
	 *todas las sentencias esperaran la orden para
	 *ser aceptadas en la base de datos
	 *
	 */
	public void iniciarTransaccion()
	throws java.sql.SQLException{
		conn.setAutoCommit(false);
	}

	/**
	 *Permite controlar el termino una transaccion
	 *desde afuera.  A partir de este momento
	 *todas las sentencias se ejecturan de forma
	 *individual en la base de datos
	 *
	 */
	public void terminarTransaccion()
	throws java.sql.SQLException{
		conn.setAutoCommit(true);
	}

	/**
	 *Indica que la transaccion ha sido aceptada
	 *
	 */
	public void aceptarTransaccion()
	throws java.sql.SQLException{
		conn.commit();
	}

	/**
	 *Indica que la transaccion debe ser
	 *deshecha porque no se realizo de
	 *forma exitosa
	 *
	 */
	public void deshacerTransaccion()
	throws java.sql.SQLException{
		conn.rollback();
	}

	public PreparedStatement sentenciaPreparadaConLlaves(String sql) throws SQLException{
		return conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	}

	public PreparedStatement sentenciaPreparada(String sql) throws SQLException{
		return conn.prepareStatement(sql);
	}

	/**
	* Metodo que cierra la conexion
	* @param no recibe parametros
	* @return no retorna valor alguno
	*
	*/
	private class SalidaDelSistema implements Runnable{
		@Override
		public void run() {
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
				RuntimeException ae= new RuntimeException();
				ae.initCause(e);
				throw ae;
			}
		}
	}
}
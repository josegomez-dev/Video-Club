package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Alquiler;
import ConexionBD.Conector;

public class MultiAlquiler {

	public Alquiler crear(String pfechaRealizado, String pfechaDevolucion, int pnumAfiliado, String pidEjm) throws SQLException,Exception{
		Alquiler ejemplar = null;
		int consecutivo;

		String sql = 	"INSERT INTO TAlquiler " +
						"(Fecha_alquiler, Fecha_devolucion, Num_Afiliado, Id_Ejemplar) " +
						"VALUES (?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pfechaRealizado);
			pstm.setString(2,pfechaDevolucion);
			pstm.setInt(3,pnumAfiliado);
			pstm.setString(4,pidEjm);
			pstm.executeUpdate();
			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consecutivo = generatedKeys.getInt(1);
				}else{
					throw new SQLException("¡Identificador del alquiler no creado!");
				}
			}
		}
		ejemplar = new Alquiler(consecutivo, pfechaRealizado, pfechaDevolucion, pidEjm, pnumAfiliado);
		return ejemplar;
	}

	public Alquiler consultarXNumero(int pnumero) throws SQLException,Exception{
		Alquiler alquiler = null;
		String sql = "SELECT * FROM TAlquiler WHERE Cod_alquiler = " + pnumero + ";";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()){
				alquiler = new Alquiler(rs.getInt("Cod_alquiler"), rs.getString("Fecha_alquiler"), rs.getString("Fecha_devolucion"), rs.getString("Id_Ejemplar"), rs.getInt("Num_Afiliado"));
			}
		}
		return alquiler;
	}

	public ArrayList<Alquiler> consultarXCliente(int pnumAfiliado) throws SQLException,Exception{
		Alquiler alquiler = null;
		ArrayList<Alquiler> listaAlquileres = null;

		String sql = "SELECT * FROM TAlquiler WHERE Num_Afiliado = " + pnumAfiliado + ";";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaAlquileres = new ArrayList<>();
			while (rs.next()){
				alquiler = new Alquiler(rs.getInt("Cod_alquiler"), rs.getString("Fecha_alquiler"), rs.getString("Fecha_devolucion"), rs.getString("Id_Ejemplar"), rs.getInt("Num_Afiliado"));
				listaAlquileres.add(alquiler);
			}
		}
		return listaAlquileres;
	}

	public ArrayList<Alquiler> listar() throws SQLException,Exception{
		Alquiler alquiler = null;
		ArrayList<Alquiler> listaAlquileres = null;

		String sql = "SELECT * FROM TAlquiler";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaAlquileres = new ArrayList<>();
			while (rs.next()){
				alquiler = new Alquiler(rs.getInt("Cod_alquiler"), rs.getString("Fecha_alquiler"), rs.getString("Fecha_devolucion"), rs.getString("Id_Ejemplar"), rs.getInt("Num_Afiliado"));
				listaAlquileres.add(alquiler);
			}
		}
		return listaAlquileres;
	}

	public void actualizar(Alquiler palquiler) throws SQLException,Exception{
		String sql =	"UPDATE TAlquiler "+
						"SET Fecha_alquiler= ?, "+
						"Fecha_devolucion= ?, "+
						"Num_Afiliado= ?, "+
						"Id_Ejemplar= ? "+
						"WHERE Cod_alquiler = ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,palquiler.getFechaAlquiler());
			pstm.setString(2,palquiler.getFechaDevolucion());
			pstm.setInt(3,palquiler.getIdAfiliado());
			pstm.setString(4,palquiler.getIdEjemplar());
			pstm.setInt(5,palquiler.getNumero());
			pstm.executeUpdate();
		}
	}

	public void borrar(String pid) throws SQLException, Exception{
		String sql = "DELETE FROM TAlquiler WHERE Id_ejemplar LIKE '%" + pid + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
}
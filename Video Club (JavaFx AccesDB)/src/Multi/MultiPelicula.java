package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Pelicula;
import ConexionBD.Conector;

public class MultiPelicula {

	public Pelicula crear(String pid, String ptitulo, String pcategoria) throws SQLException,Exception{
		Pelicula pelicula = null;

		String sql = 	"INSERT INTO TPelicula " +
						"(Id_pelicula, Titulo, Categoria) " +
						"VALUES (?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pid);
			pstm.setString(2,ptitulo);
			pstm.setString(3,pcategoria);
			pstm.executeUpdate();
			pelicula = new Pelicula(pid, ptitulo, pcategoria);
		}
		return pelicula;
	}

	public boolean buscar(String pid) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPelicula WHERE Id_pelicula LIKE '%" + pid + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	public Pelicula consultarXId(String pid) throws SQLException,Exception{
		Pelicula pelicula = null;
		String sql;

		sql = "SELECT Id_pelicula, Titulo, Categoria FROM TPelicula WHERE Id_pelicula LIKE '%" + pid + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				pelicula = new Pelicula(pid, rs.getString("Titulo"), rs.getString("Categoria"));
			}
		}
		return pelicula;
	}

	public ArrayList<Pelicula> listar() throws SQLException,Exception{
		Pelicula pelicula = null;
		ArrayList<Pelicula> listaPeliculas = null;

		String sql = "SELECT Id_pelicula, Titulo, Categoria FROM TPelicula";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPeliculas = new ArrayList<>();
			while (rs.next()){
				pelicula = new Pelicula(rs.getString("Id_pelicula"), rs.getString("Titulo"), rs.getString("Categoria"));
				listaPeliculas.add(pelicula);
			}
		}

		return listaPeliculas;
	}

	public void actualizar(Pelicula ppelicula) throws SQLException,Exception{
		String sql =	"UPDATE TPelicula "+
						"SET Titulo= ?, "+
						"Categoria= ? "+
						"WHERE Id_pelicula LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppelicula.getTitulo());
			pstm.setString(2,ppelicula.getCategoria());
			pstm.setString(3,"%"+ppelicula.getId()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pid) throws SQLException, Exception{
		String sql = "DELETE FROM TPelicula WHERE Id_pelicula LIKE '%" + pid + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
}
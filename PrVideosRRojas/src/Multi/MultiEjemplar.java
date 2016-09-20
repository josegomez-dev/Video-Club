package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Ejemplar;
import ConexionBD.Conector;

public class MultiEjemplar {

	public Ejemplar crear(String pidPelicula, String pestado, String pformato) throws SQLException,Exception{
		Ejemplar ejemplar = null;
		int consecutivo;

		String sql = 	"INSERT INTO TEjemplar " +
						"(Id_ejemplar, Estado, Formato, Id_pelicula) " +
						"VALUES (?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,"");
			pstm.setString(2,pestado);
			pstm.setString(3,pformato);
			pstm.setString(4,pidPelicula);
			pstm.executeUpdate();
			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consecutivo = generatedKeys.getInt(1);
				}else{
					throw new SQLException("¡Identificador del ejemplar no creado!");
				}
			}
		}

		ejemplar = new Ejemplar("", consecutivo, pestado, pformato, pidPelicula);
		if(contarEjemplares(pidPelicula) == 0){
			ejemplar.reiniciarCantEjemplares(1);
		}else{
			ejemplar.reiniciarCantEjemplares(contarEjemplares(pidPelicula));
		}
		return ejemplar;
	}

	public void asignarIdEjemplar(Ejemplar pejemplar) throws SQLException,Exception{
		String sql =	"UPDATE TEjemplar "+
						"SET Id_ejemplar= ? "+
						"WHERE Numero= ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pejemplar.getID());
			pstm.setInt(2,pejemplar.getNumero());
			pstm.executeUpdate();
		}
	}

	public int contarEjemplares(String pidPelicula) throws SQLException, Exception{
		int cantidad = 0;
		ArrayList<Ejemplar> lista = listar(pidPelicula);
		for(int i=0; i<lista.size(); i++){
			cantidad++;
		}
		return cantidad;
	}

	public void cambiarEstado(Ejemplar pejemplar, String pestado) throws SQLException, Exception{
		String sql =	"UPDATE TEjemplar "+
						"SET Estado= ? "+
						"WHERE Id_ejemplar LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pejemplar.getEstado());
			pstm.setString(2,"%"+pejemplar.getID()+"%");
			pstm.executeUpdate();
		}
	}

	public Ejemplar consultarXCodigo(String pid) throws SQLException,Exception{
		Ejemplar ejemplar = null;
		String sql = "SELECT * FROM TEjemplar WHERE Id_ejemplar LIKE '%" + pid + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()){
				ejemplar = new Ejemplar(rs.getString("Id_ejemplar"), rs.getInt("Numero"), rs.getString("Estado"), rs.getString("Formato"), rs.getString("Id_pelicula"));
			}
		}
		return ejemplar;
	}

	public ArrayList<Ejemplar> listar(String pid) throws SQLException,Exception{
		Ejemplar ejemplar = null;
		ArrayList<Ejemplar> listaEjemplares = null;

		String sql = "SELECT * FROM TEjemplar WHERE Id_pelicula LIKE '%" + pid + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaEjemplares = new ArrayList<>();
			while (rs.next()){
				ejemplar = new Ejemplar(rs.getString("Id_ejemplar"), rs.getInt("Numero"), rs.getString("Estado"), rs.getString("Formato"), rs.getString("Id_pelicula"));
				listaEjemplares.add(ejemplar);
			}
		}
		return listaEjemplares;
	}

	public void actualizar(Ejemplar pejemplar) throws SQLException,Exception{
		String sql =	"UPDATE TEjemplar "+
						"SET Estado= ?, "+
						"Formato= ? "+
						"WHERE Id_ejemplar LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pejemplar.getEstado());
			pstm.setString(2,pejemplar.getFormato());
			pstm.setString(3,"%"+pejemplar.getID()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pid) throws SQLException, Exception{
		String sql = "DELETE FROM TEjemplar WHERE Id_ejemplar LIKE '%" + pid + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
}
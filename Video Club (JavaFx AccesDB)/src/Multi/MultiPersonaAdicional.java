package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.PersonaAdicional;
import ConexionBD.Conector;

public class MultiPersonaAdicional {

	public PersonaAdicional crear(String pcedula, String pnombre, int pnumCliente) throws SQLException,Exception{
		PersonaAdicional personaAdicional = null;
		String idCliente = (new MultiCliente()).consultarXNumero(pnumCliente).getCedula();


		String sql = 	"INSERT INTO TPersonaAdicional " +
						"(Cedula_personaAd, Nombre, IDCliente) " +
						"VALUES (?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedula);
			pstm.setString(2,pnombre);
			pstm.setString(3,idCliente);
			pstm.executeUpdate();

			personaAdicional = new PersonaAdicional(pcedula, pnombre);
		}
		return personaAdicional;
	}


	public boolean buscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TPersonaAdicional WHERE Cedula_personaAd LIKE '%" + pcedula + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}


	public PersonaAdicional consultarXId(String pcedula) throws SQLException,Exception{
		PersonaAdicional personaAdicional = null;
		String sql;

		sql = "SELECT * FROM TPersonaAdicional WHERE Cedula_personaAd LIKE '%" + pcedula + "%';";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				personaAdicional = new PersonaAdicional(pcedula, rs.getString("Nombre"));
			}
		}
		return personaAdicional;
	}

	public ArrayList<PersonaAdicional> listar(String pid) throws SQLException,Exception{
		PersonaAdicional personaAdicional = null;
		ArrayList<PersonaAdicional> listaPersonasAdicionales = null;

		String sql = "SELECT * FROM TPersonaAdicional WHERE IDCliente LIKE '%" + pid + "%';";
		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaPersonasAdicionales = new ArrayList<>();
			while (rs.next()){
				personaAdicional = new PersonaAdicional(rs.getString("Cedula_personaAd"), rs.getString("Nombre"));
				listaPersonasAdicionales.add(personaAdicional);
			}
		}

		return listaPersonasAdicionales;
	}

	public void actualizar(PersonaAdicional ppersonaAdicional) throws SQLException,Exception{

		String sql =	"UPDATE TPersonaAdicional "+
						"SET Nombre= ?, " +
						"IDCliente= ? " +
						"WHERE Cedula_personaAd LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,ppersonaAdicional.getNombre());
			pstm.setString(2,"%"+ppersonaAdicional.getCedula()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(String pcedula) throws SQLException, Exception{
		String sql = "DELETE FROM TPersonaAdicional WHERE Cedula_personaAd LIKE '%" + pcedula + "%';";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}

}

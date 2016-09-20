package Multi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import CapaLogica.Cliente;
import ConexionBD.Conector;

public class MultiCliente {

	public Cliente crear(String pcedula, String pnombre, String papellido, String ptelefono, String pestado, String pmoroso) throws SQLException,Exception{
		Cliente cliente = null;
		int consecutivo;

		String sql = 	"INSERT INTO TCliente " +
						"(Cedula, Nombre, Apellido, Telefono, Estado, Moroso) " +
						"VALUES (?,?,?,?,?,?);";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparadaConLlaves(sql)){
			pstm.setString(1,pcedula);
			pstm.setString(2,pnombre);
			pstm.setString(3,papellido);
			pstm.setString(4,ptelefono);
			pstm.setString(5,pestado);
			pstm.setString(6,pmoroso);
			pstm.executeUpdate();
			try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					consecutivo = generatedKeys.getInt(1);
				}else{
					throw new SQLException("¡Identificador del ejemplar no creado!");
				}
			}
		}
		cliente = new Cliente(consecutivo, pcedula, pnombre, papellido, ptelefono, pestado, pmoroso);
		return cliente;
	}


	public boolean buscar(String pcedula) throws SQLException, Exception{
		boolean encontrado = false;

		String sql = "SELECT * FROM TCliente WHERE Cedula LIKE '%" + pcedula + "%';";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if(rs.next()) {
				encontrado = true;
			}
		}
		return encontrado;
	}


	public Cliente consultarXNumero(int pnumero) throws SQLException,Exception{
		Cliente cliente = null;
		String sql;

		sql = "SELECT * FROM TCliente WHERE Num_afiliado = " + pnumero + ";";

		try( ResultSet rs = Conector.getConector().consultarSQL(sql)){
			if (rs.next()) {
				cliente = new Cliente(pnumero, rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Telefono"), rs.getString("Estado"), rs.getString("Moroso"));
			}
		}
		return cliente;
	}

	public ArrayList<Cliente> listar() throws SQLException,Exception{
		Cliente cliente = null;
		ArrayList<Cliente> listaClientes = null;

		String sql = "SELECT * FROM TCliente";

		try(ResultSet rs = Conector.getConector().consultarSQL(sql)){
			listaClientes = new ArrayList<>();
			while (rs.next()){

				cliente = new Cliente(rs.getInt("Num_afiliado"), rs.getString("Cedula"), rs.getString("Nombre"), rs.getString("Apellido"), rs.getString("Telefono"), rs.getString("Estado"), rs.getString("Moroso"));
				listaClientes.add(cliente);
			}
		}

		return listaClientes;
	}

	public void actualizar(Cliente pcliente) throws SQLException,Exception{

		String sql =	"UPDATE TCliente "+
						"SET Nombre= ?, "+
						"Apellido= ?, " +
						"Telefono= ?, " +
						"Estado= ?, " +
						"Moroso= ?" +
						"WHERE Cedula LIKE ?;";

		try(PreparedStatement pstm = Conector.getConector().sentenciaPreparada(sql)){
			pstm.setString(1,pcliente.getNombre());
			pstm.setString(2,pcliente.getPrimerApellido());
			pstm.setString(3,pcliente.getTelefono());
			pstm.setString(4,pcliente.getEstado());
			pstm.setString(5,pcliente.getMoroso());
			pstm.setString(6,"%"+pcliente.getCedula()+"%");
			pstm.executeUpdate();
		}
	}

	public void borrar(int pnumAfiliado) throws SQLException, Exception{
		String sql = "DELETE FROM TCliente WHERE Num_afiliado = " + pnumAfiliado + ";";

		try{
			Conector.getConector().ejecutarSQL(sql);
		}catch (Exception e) {
			throw new Exception ("Error.");
		}
	}
}

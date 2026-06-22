package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entities.Endereco;
import entities.EnderecoCliente;
import entities.EnderecoRestaurante;

/**
 * Classe: EnderecoDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados dos endereços do cliente e do restaurante
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class EnderecoDAO {
		
	/**
	 * Responsável por receber um objeto de endereço do restaurante e inserir no banco de dados
	 * @param objeto de conexão
	 * @param objeto restaurante
	 * @return boolean
	 */
	public boolean inserirEnderecoRestaurante(Connection conn, Endereco er) {
		String sqlQuery = "INSERT INTO ENDERECO_RESTAURANTE "
				+ "(pk_fk_res_cnpj, "
				+ "pk_enr_cep, "
				+ "enr_nome, "
				+ "enr_numero) VALUES (?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, er.getIdentificacao());
			stmt.setString(2, er.getCep());
			stmt.setString(3, er.getNome());
			stmt.setInt(4, er.getNumero());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do endereço do restaurante
	 * para que possam ser utilizadas ao longo das operações
	 * @param objeto de conexão  
	 * @param cnpj do restaurante
	 * @return objeto enderecorestaurante
	 */
	public EnderecoRestaurante retornarEnderecoRestaurante(Connection conn, String cnpj) {
		String sqlQuery = "SELECT * FROM ENDERECO_RESTAURANTE WHERE pk_fk_res_cnpj = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpnj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				EnderecoRestaurante er = new EnderecoRestaurante();
				
				er.setCnpjRestaurante(resultado.getString("pk_fk_res_cnpj"));
				er.setCep(resultado.getString("pk_enr_cep"));
				er.setNome(resultado.getString("enr_nome"));
				er.setNumero(resultado.getInt("enr_numero"));
				
				return er;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_RESTAURANTE");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações do endereço de um restaurante no banco de dados 
	 * @param objeto de conexão
	 * @param objeto enderecorestaurante
	 */
	public boolean atualizarEnderecoRestaurante(Connection conn, Endereco er) {
		String sqlQuery = "UPDATE ENDERECO_RESTAURANTE SET " +
	            "pk_enr_cep = ?, " +
	            "enr_nome = ?, " +
	            "enr_numero = ? " +
	            "WHERE pk_fk_res_cnpj = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, er.getCep());
	        stmt.setString(2, er.getNome());
	        stmt.setInt(3, er.getNumero());
	        stmt.setString(4, er.getIdentificacao());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por receber um objeto de endereço do cliente e inserir no banco de dados
	 * @param objeto de conexão
	 * @param objeto enderecocliente
	 * @return boolean
	 */
	public boolean inserirEnderecoCliente(Connection conn, Endereco ec) {
		String sqlQuery = "INSERT INTO ENDERECO_CLIENTE "
				+ "(pk_fk_cli_cpf, "
				+ "pk_enc_cep, "
				+ "enc_nome, "
				+ "enc_numero) VALUES (?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, ec.getIdentificacao());
			stmt.setString(2, ec.getCep());
			stmt.setString(3, ec.getNome());
			stmt.setInt(4, ec.getNumero());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_CLIENTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do endereço do cliente
	 * para que possam ser utilizadas ao longo das operações
	 * @param objeto de conexão
	 * @param cpf do cliente
	 * @return objeto enderecocliente
	 */
	public EnderecoCliente retornarEnderecoCliente(Connection conn, String cpf) {
		String sqlQuery = "SELECT * FROM ENDERECO_CLIENTE WHERE pk_fk_cli_cpf = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cpf);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpnj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				EnderecoCliente ec = new EnderecoCliente();
				
				ec.setCpfCliente(resultado.getString("pk_fk_cli_cpf"));
				ec.setCep(resultado.getString("pk_enc_cep"));
				ec.setNome(resultado.getString("enc_nome"));
				ec.setNumero(resultado.getInt("enc_numero"));
				
				return ec;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_CLIENTE");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações do endereço de um cliente no banco de dados 
	 * @param objeto de conexão
	 * @param objeto enderecocliente
	 * @return boolean
	 */
	public boolean atualizarEnderecoCliente(Connection conn, Endereco ec) {
		String sqlQuery = "UPDATE ENDERECO_CLIENTE SET " +
	            "pk_enc_cep = ?, " +
	            "enc_nome = ?, " +
	            "enc_numero = ? " +
	            "WHERE pk_fk_cli_cpf = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, ec.getCep());
	        stmt.setString(2, ec.getNome());
	        stmt.setInt(3, ec.getNumero());
	        stmt.setString(4, ec.getIdentificacao());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENDERECO_CLIENTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}

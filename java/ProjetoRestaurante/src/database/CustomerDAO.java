package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Cliente;

/**
 * Classe: ClienteDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados do Cliente
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class ClienteDAO {
	
	/**
	 * responsável por fazer a inserção de um novo cliente no banco de dados
	 * @param objeto de conexão
	 * @param objeto cliente
	 */
	public boolean inserirCliente(Connection conn, Cliente cliente) {
		String sqlQuery = "INSERT INTO CLIENTE (pk_cli_cpf, cli_primeiro_nome, cli_nome_meio, "
				+ "cli_ultimo_nome, cli_telefone, cli_email, cli_senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cliente.getCpf());
			stmt.setString(2, cliente.getPrimeiroNome());
			stmt.setString(3, cliente.getNomeMeio());
			stmt.setString(4, cliente.getUltimoNome());
			stmt.setString(5, cliente.getTelefone());
			stmt.setString(6, cliente.getEmail());
			stmt.setString(7, cliente.getSenha());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de CLIENTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do cliente da base de dados
	 * para que possam ser utilizadas ao das operações
	 * @param objeto de conexão
	 * @param cpf do cliente buscado
	 * @return objeto cliente
	 */
	public Cliente retornarCliente(Connection conn, String cpf) {
		String sqlQuery = "SELECT * FROM CLIENTE WHERE pk_cli_cpf = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cpf);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpnj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				Cliente c = new Cliente();
							
				c.setCpf(resultado.getString("pk_cli_cpf"));
				c.setPrimeiroNome(resultado.getString("cli_primeiro_nome"));
				c.setNomeMeio(resultado.getString("cli_nome_meio"));
				c.setUltimoNome(resultado.getString("cli_ultimo_nome"));
				c.setTelefone(resultado.getString("cli_telefone"));
				c.setEmail(resultado.getString("cli_email"));
				c.setSenha(resultado.getString("cli_senha"));
				
				return c;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de CLIENTE");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações de um cliente no banco de dados 
	 * @param objeto de conexão
	 * @param objeto cliente
	 */
	public boolean atualizarCliente(Connection conn, Cliente cliente) {
		String sqlQuery = "UPDATE CLIENTE SET " +
	            "cli_primeiro_nome = ?, " +
	            "cli_nome_meio = ?, " +
	            "cli_ultimo_nome = ?, " +
	            "cli_telefone = ?, " +
	            "cli_email = ?, " +
	            "cli_senha = ? " +
	            "WHERE pk_cli_cpf = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cliente.getPrimeiroNome());
	        stmt.setString(2, cliente.getNomeMeio());
	        stmt.setString(3, cliente.getUltimoNome());
	        stmt.setString(4, cliente.getTelefone());
	        stmt.setString(5, cliente.getEmail());
	        stmt.setString(6, cliente.getSenha());
	        stmt.setString(7, cliente.getCpf());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de CLIENTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por apagar um cliente do banco de dados
	 * @param objeto de conexão
	 * @param cpf do cliente
	 * @return boolean
	 */
	public boolean deletarCliente(Connection conn, String cpf) {
		String sqlQuery = "DELETE FROM CLIENTE WHERE pk_cli_cpf = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cpf);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de CLIENTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}

package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Restaurante;

/**
 * Classe: RestauranteDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados do restaurante
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class RestauranteDAO {
	
	/**
	 * responsável por fazer a inserção de um novo restaurante no banco de dados
	 * @param objeto de conexão
	 * @param objeto restaurante
	 * @return boolean
	 */
	public boolean inserirRestaurante(Connection conn, Restaurante restaurante) {
		String sqlQuery = "INSERT INTO RESTAURANTE (pk_res_cnpj, res_nome, res_telefone, res_senha) VALUES (?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, restaurante.getCnpj());
			stmt.setString(2, restaurante.getNome());
			stmt.setString(3, restaurante.getTelefone());
			stmt.setString(4, restaurante.getSenha());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do restaurante da base de dados
	 * para que possam ser utilizadas ao longo das operações
	 * @param objeto de conexão
	 * @param cnpj: cnpj do Restaurante buscado
	 * @return um objeto Restaurante
	 */
	public Restaurante retornarRestaurante(Connection conn, String cnpj) {
		String sqlQuery = "SELECT * FROM RESTAURANTE WHERE pk_res_cnpj = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cnpj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				Restaurante r = new Restaurante();
				
				r.setCnpj(resultado.getString("pk_res_cnpj"));
				r.setNome(resultado.getString("res_nome"));
				r.setTelefone(resultado.getString("res_telefone"));
				r.setSenha(resultado.getString("res_senha"));
				
				// categoria do restaurante pode ser null
				int categoria = resultado.getInt("fk_res_id_catg");
				if (!resultado.wasNull()) {
					r.setIdCategoria(categoria);
				}
				
				return r;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações de um restaurante no banco de dados 
	 * @param objeto de conexão
	 * @param restaurante: objeto restaurante
	 * @return boolean
	 */
	public boolean atualizarRestaurante(Connection conn, Restaurante restaurante) {
		String sqlQuery = "UPDATE RESTAURANTE " +
							"SET res_nome = ?, " +
							"res_telefone = ?, " +
							"fk_res_id_catg = ?, " +
							"res_senha = ? " +
							"WHERE pk_res_cnpj = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, restaurante.getNome());
			stmt.setString(2, restaurante.getTelefone());
			stmt.setObject(3, restaurante.getIdCategoria());
			stmt.setString(4, restaurante.getSenha());
			stmt.setString(5, restaurante.getCnpj());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por apagar um restaurante do banco de dados
	 * @param objeto de conexão
	 * @param cnpj do restaurante
	 * @return boolean
	 */
	public boolean deletarRestaurante(Connection conn, String cnpj) {
		String sqlQuery = "DELETE FROM RESTAURANTE WHERE pk_res_cnpj = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer informações de todos os restaurantes do sistema
	 * @param objeto de conexão
	 * @return arraylist do tipo restaurante
	 */
	public ArrayList<Restaurante> listarRestaurantes(Connection conn){
		
		//Lista para armazenar todos as instâncias de restaurante
		ArrayList<Restaurante> listaRestaurantes = new ArrayList<Restaurante>();
		
		String sqlQuery = "SELECT * FROM RESTAURANTE";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			ResultSet resultado = stmt.executeQuery();
			
			//armazenando todos os restaurantes encontrados na lista dinânica de restaurantes
			while (resultado.next()) {
				Restaurante r = new Restaurante();
				
				r.setCnpj(resultado.getString("pk_res_cnpj"));
				r.setNome(resultado.getString("res_nome"));
				r.setTelefone(resultado.getString("res_telefone"));
				r.setIdCategoria(resultado.getInt("fk_res_id_catg"));
				r.setSenha(resultado.getString("res_senha"));
				
				listaRestaurantes.add(r);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return listaRestaurantes;
	}
}

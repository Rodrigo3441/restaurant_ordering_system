package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Produto;

/**
 * Classe: ProdutoDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados do produto
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class ProdutoDAO {
	
	/**
	 * responsável por fazer a inserção de um novo produto no banco de dados
	 * @param objeto de conexão
	 * @param objeto produto
	 * @return boolean
	 */
	public boolean inserirProduto(Connection conn, Produto produto) {
		String sqlQuery = "INSERT INTO PRODUTO (" +
				"pk_prd_codigo, " +
				"prd_nome, " +
				"prd_descricao, " +
				"fk_prd_id_catg) " +
				"VALUES (?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, produto.getCodigo());
			stmt.setString(2, produto.getNome());
			stmt.setString(3, produto.getDescricao());
			stmt.setObject(4, produto.getIdCategoria());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do produto da base de dados
	 * com base no Id do produto
	 * @param objeto de conexão
	 * @param codigo do produto buscado
	 * @return objeto produto
	 */
	public Produto retornarProdutoPorId(Connection conn, int codigo) {
		String sqlQuery = "SELECT * FROM PRODUTO WHERE pk_prd_codigo = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, codigo);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpnj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				Produto p = new Produto();
							
				p.setCodigo(resultado.getInt("pk_prd_codigo"));
				p.setNome(resultado.getString("prd_nome"));
				p.setDescricao(resultado.getString("prd_descricao"));
				
				//categoria do produto pode ser null
				int categoria = resultado.getInt("fk_prd_id_catg");
				if (!resultado.wasNull()) {
					p.setIdCategoria(categoria);
				}
				return p;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Responsável por trazer as informações do produto da base de dados
	 * com base no nome do produto
	 * @param objeto de conexão
	 * @param nome do produto buscado
	 * @return objeto produto
	 */
	public Produto retornarProdutoPorNome(Connection conn, String nome) {
		String sqlQuery = "SELECT * FROM PRODUTO WHERE prd_nome LIKE ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, "%" + nome + "%");
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpnj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				Produto p = new Produto();
							
				p.setCodigo(resultado.getInt("pk_prd_codigo"));
				p.setNome(resultado.getString("prd_nome"));
				p.setDescricao(resultado.getString("prd_descricao"));
				
				//categoria do produto pode ser null
				int categoria = resultado.getInt("fk_prd_id_catg");
				if (!resultado.wasNull()) {
					p.setIdCategoria(categoria);
				}
				return p;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações de um produto no banco de dados 
	 * @param objeto de conexão
	 * @param objeto produto
	 * @return boolean
	 */
	public boolean atualizarProduto(Connection conn, Produto produto) {
		String sqlQuery = "UPDATE PRODUTO SET "
				+ "prd_nome = ?, "
				+ "prd_descricao = ?, "
				+ "fk_prd_id_catg = ? "
				+ "WHERE pk_prd_codigo = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, produto.getNome());
	        stmt.setString(2, produto.getDescricao());
	        stmt.setObject(3, produto.getIdCategoria());
	        stmt.setObject(4, produto.getCodigo());
	
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**s
	 * Responsável por apagar um produto do banco de dados
	 * @param objeto de conexão
	 * @param codigo do produto
	 * @return boolean
	 */
	public boolean deletarProduto(Connection conn, int codigo) {
		String sqlQuery = "DELETE FROM PRODUTO WHERE pk_prd_codigo = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, codigo);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}

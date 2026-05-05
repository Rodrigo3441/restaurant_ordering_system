package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.ProdutoRestaurante;
import view.ProdutoRestauranteView;

/**
 * Classe: ProdutoRestauranteDAO
 *
 * Descrição:
 * Classe responsável por gerenciar as associações de produtos do catálogo global
 * com cada restaurante individual (n:n)
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 28-04-2026
 */

public class ProdutoRestauranteDAO {
	
	private Connection conn;
	
	/**
	 * Construtor
	 * Recebe a conexão para que seja possível estabelecer
	 * uma comunicação com o banco de dados
	 * 
	 * @param conn: objeto de conexão
	 */
	public ProdutoRestauranteDAO(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * responsável por fazer a inserção na tabela associativa entre produto e restaurante no banco de dados
	 * @param produto: objeto produto
	 */
	public boolean associarProdutoRestaurante(ProdutoRestaurante pr) {
		String sqlQuery = "INSERT INTO PRODUTO_RESTAURANTE (" +
				"pk_fk_res_cnpj, " +
				"pk_fk_prd_codigo, " +
				"pdr_qtde_estoque, " +
				"pdr_preco) " +
				"VALUES (?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, pr.getCnpjRestaurante());
			stmt.setInt(2, pr.getCodigoProduto());
			stmt.setInt(3, pr.getQuantidadeEstoque());
			stmt.setDouble(4, pr.getPreco());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO_RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer todos os produtos de determinado restaurante da base de dados
	 * @param cnpj do restaurante em sessão
	 * @return arraylist de produtos
	 */
	public ArrayList<ProdutoRestauranteView> retornarTodoProdutoRestaurante(String cnpj) {		
		String sqlQuery = "SELECT p.prd_nome, "
				+ "p.pk_prd_codigo,"
				+ "pr.pdr_preco, "
				+ "pr.pdr_qtde_estoque, "
				+ "p.prd_descricao "
				+ "FROM PRODUTO_RESTAURANTE AS pr "
				+ "INNER JOIN PRODUTO AS p "
				+ "ON p.pk_prd_codigo = pr.pk_fk_prd_codigo "
				+ "WHERE pk_fk_res_cnpj = ?";
		
		//Lista para armazenar todos as instâncias de restaurante
		ArrayList<ProdutoRestauranteView> listaProdutos = new ArrayList<ProdutoRestauranteView>();
				
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca de produto pelo cpnj, adiciona cada produto
			//com os atributos do resultado na lista de produtos
			while (resultado.next()) {
				ProdutoRestauranteView pr = new ProdutoRestauranteView();
				pr.setCodigoProduto(resultado.getInt("pk_prd_codigo"));
				pr.setNomeProduto(resultado.getString("prd_nome"));
				pr.setPrecoProduto(resultado.getDouble("pdr_preco"));
				pr.setQuantidadeEstoque(resultado.getInt("pdr_qtde_estoque"));
				pr.setDescricao(resultado.getString("prd_descricao"));
				
				listaProdutos.add(pr);
			}
			
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return listaProdutos;
	}
	
	/**
	 * Responsável por trazer as informações da tabela associativa entre produto e restaurante
	 * sendo usada para verificar se um produto já está associado a um restaurante
	 * @param codigo: codigo do produto buscado
	 * @return true ou false
	 */
	public boolean produtoJaEstaCadastrado(String cnpj, int codigo) {
		String sqlQuery = "SELECT * FROM PRODUTO_RESTAURANTE WHERE pk_fk_res_cnpj = ? AND pk_fk_prd_codigo = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			stmt.setInt(2, codigo);
			
			ResultSet resultado = stmt.executeQuery();
			
			return resultado.next();
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por deletar um produto de um restaurante, o removendo do catálogo
	 * @param cnpj do restaurante
	 * @param codigo do produto
	 * @return true or false
	 */
	public boolean deletarProduto(String cnpj, int codigo) {
		String sqlQuery = "DELETE FROM PRODUTO_RESTAURANTE WHERE pk_fk_res_cnpj = ? AND pk_fk_prd_codigo = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cnpj);
			stmt.setInt(2, codigo);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO_RESTAURANTE");
		    e.printStackTrace();
		}
		
		return false;
	}
	
}

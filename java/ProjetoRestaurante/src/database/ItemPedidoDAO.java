package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entities.ItemPedido;
import view.ItemPedidoView;

/**
 * Classe: ItemPedidoDAO
 *
 * Descrição:
 * Classe responsável por gerenciar as associações de produtos do catálogo do restaurante
 * com cada pedido (N:N)
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 07-05-2026
 */

public class ItemPedidoDAO {
	
	/**
	 * cadastra um item do pedido dos clientes no banco de dados
	 * @param objeto de conexão
	 * @param objeto itempedido
	 * @return boolean
	 */
	public boolean inserirItemPedido(Connection conn, ItemPedido ip) {
		String sqlQuery = "INSERT INTO ITEM_PEDIDO ("
						+ "pk_fk_ped_numero, "
						+ "pk_fk_prd_codigo, "
						+ "itp_quantidade) VALUES "
						+ "(?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, ip.getNumeroPedido());
			stmt.setInt(2, ip.getCodigoProduto());
			stmt.setInt(3, ip.getQuantidade());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ITEM_PEDIDO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * retorna todos os itens de um pedido para exibição no menu do cliente
	 * @param objeto de conexão
	 * @param codigoPedido
	 * @return ArrayList do tipo ItemPedidoView
	 */
	public ArrayList<ItemPedidoView> retornarItensPedido(Connection conn, int codigoPedido) {		
		String sqlQuery = "SELECT "
						+ "p.prd_nome, "
						+ "ip.itp_quantidade, "
						+ "pr.pdr_preco "
						+ "FROM ITEM_PEDIDO AS ip "
						+ "INNER JOIN PRODUTO AS p "
						+ "ON ip.pk_fk_prd_codigo = p.pk_prd_codigo "
						+ "INNER JOIN PRODUTO_RESTAURANTE AS pr "
						+ "ON p.pk_prd_codigo = pr.pk_fk_prd_codigo "
						+ "WHERE ip.pk_fk_ped_numero = ?;";
		
		//Lista para armazenar todos as instâncias de itens do pedido
		ArrayList<ItemPedidoView> listaItemPedido = new ArrayList<ItemPedidoView>();
				
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, codigoPedido);
			
			ResultSet resultado = stmt.executeQuery();
			
			while (resultado.next()) {
				ItemPedidoView ip = new ItemPedidoView();
				ip.setNome(resultado.getString("prd_nome"));
				ip.setPreco(resultado.getDouble("pdr_preco"));
				ip.setQuantidade(resultado.getInt("itp_quantidade"));
				
				listaItemPedido.add(ip);
			}
			
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PRODUTO");
		    e.printStackTrace();
		}
		
		return listaItemPedido;
	}
}

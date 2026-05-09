package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Cliente;
import entities.Pedido;
import entities.Restaurante;

/**
 * Classe: PedidoDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados do pedido
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class PedidoDAO {
	
	/**
	 * responsável por fazer a inserção de um novo pedido no banco de dados e retornar o id gerado
	 * @param objeto de conexão
	 * @param pedido: objeto pedido
	 * @return int gerado como id do pedido
	 */
	public int inserirPedido(Connection conn, Restaurante r, Cliente c) {
		String sqlQuery = "INSERT INTO PEDIDO (ped_status, "
				+ "fk_res_cnpj, "
				+ "fk_cli_cpf) "
				+ "VALUES (?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, "Em preparo");
			stmt.setString(2, r.getCnpj());
			stmt.setString(3, c.getCpf());
			
			stmt.executeUpdate();
			
			//obtém a o id do pedido gerado
			ResultSet resultado = stmt.getGeneratedKeys();
			
			if (resultado.next()) {
				return resultado.getInt(1);
			}
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return -1;
		
	}
	
	/**
	 * Responsável por trazer as informações do pedido da base de dados
	 * para que possam ser utilizadas para fins de consulta
	 * @param objeto de conexão
	 * @param numero do pedido buscado
	 * @return objeto pedido
	 */
	public Pedido retornarPedido(Connection conn, int numero) {
		String sqlQuery = "SELECT * FROM PEDIDO WHERE pk_ped_numero = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, numero);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cnpj, instancia um objeto restaurante
			//com os atributos do resultado
			if (resultado.next()) {
				Pedido p = new Pedido();
				
				p.setNumeroPedido(resultado.getInt("pk_ped_numero"));
				p.setStatus(resultado.getString("ped_status"));
				p.setCpfEntregador(resultado.getString("fk_etg_cpf"));
				p.setCnpjRestaurante(resultado.getString("fk_res_cnpj"));
				p.setCpfCliente(resultado.getString("fk_cli_cpf"));
				
				return p;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações de um pedido no banco de dados 
	 * @param objeto de conexão
	 * @param pedido: objeto do tipo pedido
	 * @return boolean
	 */
	public boolean atualizarPedido(Connection conn, Pedido pedido) {
		String sqlQuery = "UPDATE PEDIDO " +
							"SET ped_status = ?, " +
							"fk_etg_cpf = ? " +
							"WHERE pk_ped_numero = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, pedido.getStatus());
			stmt.setString(2, pedido.getCpfEntregador());
			stmt.setInt(3, pedido.getNumeroPedido());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por apagar um pedido do banco de dados
	 * @param objeto de conexão
	 * @param numero do pedido
	 * @return boolean
	 */
	public boolean deletarPedido(Connection conn, int numero) {
		String sqlQuery = "DELETE FROM PEDIDO WHERE pk_ped_numero = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setInt(1, numero);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer informações de todos os pedidos do sistema para determinado restaurante
	 * filtra os pedidos por status
	 * @param objeto de conexão
	 * @param cnpj do restaurante
	 * @param status desejado
	 * @return ArrayList com todos os pedidos
	 */
	public ArrayList<Pedido> listarPedidosPorRestaurante(Connection conn, String cnpj, String status){
		
		//Lista para armazenar todos as instâncias de pedido
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		String sqlQuery = "SELECT * FROM PEDIDO WHERE fk_res_cnpj = ? AND ped_status = ? ORDER BY ped_data ASC";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			stmt.setString(1, cnpj);
			stmt.setString(2, status);
			
			ResultSet resultado = stmt.executeQuery();
			
			//armazenando todos os restaurantes encontrados na lista dinânica de pedidos
			while (resultado.next()) {
				Pedido p = new Pedido();
				
				p.setNumeroPedido(resultado.getInt("pk_ped_numero"));
				p.setStatus(resultado.getString("ped_status"));
				p.setCpfEntregador(resultado.getString("fk_etg_cpf"));
				p.setCnpjRestaurante(resultado.getString("fk_res_cnpj"));
				p.setCpfCliente(resultado.getString("fk_cli_cpf"));
				p.setDataPedido(resultado.getTimestamp("ped_data").toLocalDateTime());
				
				listaPedidos.add(p);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return listaPedidos;
	}
	
	/**
	 * responsável por trazer informações de todos os pedidos que um cliente já fez no sistema
	 * @param objeto de conexão
	 * @param cpf do cliente
	 * @return ArrayList com todos os pedidos
	 */
	public ArrayList<Pedido> listarPedidosPorCliente(Connection conn, String cpf){
		
		//Lista para armazenar todos as instâncias de pedido
		ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		String sqlQuery = "SELECT * FROM PEDIDO WHERE fk_cli_cpf = ? ORDER BY ped_data DESC";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			stmt.setString(1, cpf);
			
			ResultSet resultado = stmt.executeQuery();
			
			//armazenando todos os restaurantes encontrados na lista dinânica de pedidos
			while (resultado.next()) {
				Pedido p = new Pedido();
				
				p.setNumeroPedido(resultado.getInt("pk_ped_numero"));
				p.setStatus(resultado.getString("ped_status"));
				p.setCpfEntregador(resultado.getString("fk_etg_cpf"));
				p.setCnpjRestaurante(resultado.getString("fk_res_cnpj"));
				p.setCpfCliente(resultado.getString("fk_cli_cpf"));
				p.setDataPedido(resultado.getTimestamp("ped_data").toLocalDateTime());
				
				listaPedidos.add(p);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de PEDIDO");
		    e.printStackTrace();
		}
		
		return listaPedidos;
	}
}

  package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.Entregador;

/**
 * Classe: EntregadorDAO
 *
 * Descrição:
 * Classe responsável por gerenciar dados do Entregador
 *
 * Responsabilidades:
 * - Conectar ao banco de dados
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 21-04-2026
 */

public class EntregadorDAO {
	
	/**
	 * responsável por fazer a inserção de um novo entregador no banco de dados
	 * @param objeto de conexão
	 * @param objeto entregador
	 * @return boolean
	 */
	public boolean inserirEntregador(Connection conn, Entregador entregador) {
		String sqlQuery = "INSERT INTO ENTREGADOR (" +
						"pk_etg_cpf, etg_primeiro_nome, " +
						"etg_nome_meio, " +
						"etg_ultimo_nome, " +
						"etg_telefone, " +
						"etg_veiculo, " +
						"etg_disponibilidade) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, entregador.getCpf());
			stmt.setString(2, entregador.getPrimeiroNome());
			stmt.setString(3, entregador.getNomeMeio());
			stmt.setString(4, entregador.getUltimoNome());
			stmt.setString(5, entregador.getTelefone());
			stmt.setString(6, entregador.getVeiculo());
			stmt.setShort(7, entregador.getDisponibilidade());
			
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENTREGADOR");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer as informações do entregador da base de dados
	 * para que possam ser utilizadas para atribuições nas entregas
	 * @param objeto de conexão
	 * @param cpf do entregador buscado
	 * @return objeto entregador
	 */
	public Entregador retornarEntregador(Connection conn, String cpf) {
		String sqlQuery = "SELECT * FROM ENTREGADOR WHERE pk_etg_cpf = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cpf);
			
			ResultSet resultado = stmt.executeQuery();
			
			//se houver resultado da busca pelo cpf, instancia um objeto entregador
			//com os atributos do resultado
			if (resultado.next()) {
				Entregador e = new Entregador();
							
				e.setCpf(resultado.getString("pk_etg_cpf"));
				e.setPrimeiroNome(resultado.getString("etg_primeiro_nome"));
				e.setNomeMeio(resultado.getString("etg_nome_meio"));
				e.setUltimoNome(resultado.getString("etg_ultimo_nome"));
				e.setTelefone(resultado.getString("etg_telefone"));
				e.setVeiculo(resultado.getString("etg_veiculo"));
				e.setDisponibilidade(resultado.getShort("etg_disponibilidade"));
				
				return e;

			}
									
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENTREGADOR");
		    e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Responsável por atualizar as informações de um entregador no banco de dados 
	 * @param objeto de conexão
	 * @param objeto entregador
	 * @return boolean
	 */
	public boolean atualizarEntregador(Connection conn, Entregador entregador) {
		String sqlQuery = "UPDATE ENTREGADOR SET " +
			            "etg_primeiro_nome = ?, " +
			            "etg_nome_meio = ?, " +
			            "etg_ultimo_nome = ?, " +
			            "etg_telefone = ?, " +
			            "etg_veiculo = ?, " +
			            "etg_disponibilidade = ? " +
			            "WHERE pk_etg_cpf = ?";

		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, entregador.getPrimeiroNome());
	        stmt.setString(2, entregador.getNomeMeio());
	        stmt.setString(3, entregador.getUltimoNome());
	        stmt.setString(4, entregador.getTelefone());
	        stmt.setString(5, entregador.getVeiculo());
	        stmt.setShort(6, entregador.getDisponibilidade());
	        stmt.setString(7, entregador.getCpf());
						
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de ENTREGADOR");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por apagar um entregador do banco de dados
	 * @param objeto de conexão
	 * @param cpf do entregador
	 * @return boolean
	 */
	public boolean deletarEntregador(Connection conn, String cpf) {
		String sqlQuery = "DELETE FROM ENTREGADOR WHERE pk_etg_cpf = ?";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			//vinculação dos atributos à query preparada
			stmt.setString(1, cpf);
			
			//execução da query e validação de êxito
			int linhasAfetadas = stmt.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			System.err.println("Erro na operação de ENTREGADOR");
		    e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Responsável por trazer informações de todos os entregadores do sistema
	 * @param objeto de conexão
	 * @return arraylist do tipo entregador
	 */
	public ArrayList<Entregador> listarEntregadores(Connection conn){
		
		//Lista para armazenar todos as instâncias de restaurante
		ArrayList<Entregador> listaEntregadores = new ArrayList<Entregador>();
		
		String sqlQuery = "SELECT * FROM ENTREGADOR";
		
		//preparação da query antes da execução
		try (PreparedStatement stmt = conn.prepareStatement(sqlQuery)){
			
			ResultSet resultado = stmt.executeQuery();
			
			//armazenando todos os restaurantes encontrados na lista dinânica de restaurantes
			while (resultado.next()) {
				Entregador e = new Entregador();
		
				e.setCpf(resultado.getString("pk_etg_cpf"));
				e.setPrimeiroNome(resultado.getString("etg_primeiro_nome"));
				e.setNomeMeio(resultado.getString("etg_nome_meio"));
				e.setUltimoNome(resultado.getString("etg_ultimo_nome"));
				e.setTelefone(resultado.getString("etg_telefone"));
				e.setVeiculo(resultado.getString("etg_veiculo"));
				e.setDisponibilidade(resultado.getShort("etg_disponibilidade"));
				
				listaEntregadores.add(e);
			}
			
			
		} catch (SQLException e) {
			System.err.println("Erro na operação de RESTAURANTE");
		    e.printStackTrace();
		}
		
		return listaEntregadores;
	}
	
}

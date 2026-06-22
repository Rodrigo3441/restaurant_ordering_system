package services;

import database.ProdutoDAO;
import java.sql.Connection;
import entities.Produto;

/**
 * Classe: ServicoProduto
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do produto
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 27-04-2026
 */
public class ProdutoService {
	private ProdutoDAO dao;
	private Connection conn;
	
	public ProdutoService(Connection conn) {
		this.dao = new ProdutoDAO();
		this.conn = conn;
	}

	/**
	 * Valida se o nome do produto é válido
	 * @param nome do produto
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * Valida se a descrição é válida
	 * @param descricao do produto
	 */
	public void validarDescricao(String descricao) {
		if (!descricaoValida(descricao)) {
			throw new IllegalArgumentException("Digite uma descrição válida");
		}
	}
	
	/**
	 * Verifica se o código do produto é válido e se ele já não foi utilizado por outro produto
	 * @param codigo do produto
	 */
	public void validarCodigo(int codigo) {
		if (!codigoValido(codigo)) {
			throw new IllegalArgumentException("Digite um código válido");
		}
		
		if (!codigoDisponivel(codigo)) {
			throw new IllegalArgumentException("Esse código já está em uso. Tente outro");
		}
	}
	
	/**
	 * Busca e retorna um produto com base no nome informado
	 * @param nome do produto buscado
	 * @return objeto Produto
	 */
	public Produto buscarProdutoPorNome(String nome) {
		nome = nome.toLowerCase().trim();
		return dao.retornarProdutoPorNome(conn, nome);
	}
	
	/**
	 * Busca e retorna um produto com base no codigo informado
	 * @param codigo do produto
	 * @return objeto Produto
	 */
	public Produto buscarProdutoPorId(int codigo) {
		return dao.retornarProdutoPorId(conn, codigo);
	}
	
	/**
	 * Realiza a inserção de um produto no catálogo global do sistema
	 * @param p objeto Produto
	 * @return boolean
	 */
	public boolean inserirProduto(Produto p) {
		return dao.inserirProduto(conn, p);
	}
	
	private boolean nomeValido(String nome) {
		return nome.length() >= 3 && nome.length() < 40;
	}
	
	private boolean descricaoValida(String descricao) {
		return descricao.length() < 255;
	}
	
	private boolean codigoValido(int codigo) {
		return codigo > 0 && codigo < 2_000_000_000;
	}
	
	/**
	 * Verifica se um código de produto informado está disponível para uso
	 * @param codigo do produto informado
	 * @return true se o código estiver disponível
	 */
	private boolean codigoDisponivel(int codigo) {
		return dao.retornarProdutoPorId(conn, codigo) == null;
	}
	
}

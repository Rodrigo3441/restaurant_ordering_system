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
	//conexão com o banco de dados que será usada em todas as operações
	private ProdutoDAO dao;
	
	public ProdutoService(Connection conn) {
		this.dao = new ProdutoDAO(conn);
	}

	/**
	 * 
	 * @param nome
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * 
	 * @param descricao
	 */
	public void validarDescricao(String descricao) {
		if (!descricaoValida(descricao)) {
			throw new IllegalArgumentException("Digite uma descrição válida");
		}
	}
	
	/**
	 * 
	 * @param codigo
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
	 * 
	 * @param nome
	 * @return
	 */
	public Produto buscarProdutoPorNome(String nome) {
		nome = nome.toLowerCase().trim();
		return dao.retornarProdutoPorNome(nome);
	}
	
	/**
	 * 
	 * @param codigo
	 * @return
	 */
	public Produto buscarProdutoPorId(int codigo) {
		return dao.retornarProdutoPorId(codigo);
	}
	
	public boolean inserirProduto(Produto p) {
		return dao.inserirProduto(p);
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
	
	private boolean codigoDisponivel(int codigo) {
		return dao.retornarProdutoPorId(codigo) == null;
	}
	
}

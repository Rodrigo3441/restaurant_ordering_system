package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.ProdutoRestauranteDAO;
import entities.ProdutoRestaurante;
import view.ProdutoRestauranteView;

/**
 * Classe: ProdutoRestauranteService
 *
 * Descrição:
 * Classe responsável por gerenciar as regras de negócio do Produto de um Restaurante N:N
 *
 * Responsabilidades:
 * - oferecer métodos de validação das informações
 * - se comunicar com a camada de dados
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class ProdutoRestauranteService {

	private ProdutoRestauranteDAO dao;
	private Connection conn;
	
	public ProdutoRestauranteService(Connection conn) {
		this.dao = new ProdutoRestauranteDAO();
		this.conn = conn;
	}
	
	/**
	 * Valida se a quantidade de estoque de um produto é válida
	 * @param quantidadeEstoque
	 */
	public void validarQuantidadeEstoque(int quantidadeEstoque) {
		if(!quantidadeEstoqueValida(quantidadeEstoque)) {
			throw new IllegalArgumentException("Insira uma quantidade de estoque válida");
		}
	}
	
	/**
	 * Valida se o preço de um produto é válido
	 * @param preco do produto
	 */
	public void validarPrecoProduto(double preco) {
		if(!precoProdutoValido(preco)) {
			throw new IllegalArgumentException("Digite um preço válido para o produto");
		}
	}
	
	/**
	 * Valida se o código de um produto é válido
	 * @param codigo do produto
	 */
	public void validarCodigoProduto(int codigo) {
		if(!codigoProdutoValido(codigo)) {
			throw new IllegalArgumentException("Digite um código válido");
		}
	}
	
	/**
	 * Verifica se um produto já está cadastrado no catálogo do restaurante
	 * @param cnpj do restaurante
	 * @param codigo do produto
	 * @return boolean
	 */
	public boolean produtoJaEstaCadastrado(String cnpj, int codigo) {
		return dao.produtoJaEstaCadastrado(conn, cnpj, codigo);
	}
	
	/**
	 * Realiza a remoção de um produto do catálogo do restaurante
	 * @param cnpj do restaurante
	 * @param codigo do produto
	 * @throws Exception se algum erro ocorrer
	 */
	public void apagarProdutoRestaurante(String cnpj, int codigo) throws Exception {
		if(!dao.deletarProduto(conn, cnpj, codigo)) {
			throw new Exception("Ocorreu um erro desconhecido ao apagar o produto.");		
		} 
		
	}
	
	/**
	 * Realiza a associação de um produto do catálogo global com o catálogo do restaurante
	 * @param pr objeto ProdutoRestaurante
	 * @return boolean
	 */
	public boolean associarProdutoRestaurante(ProdutoRestaurante pr) {
		return dao.associarProdutoRestaurante(conn, pr);
	}
	
	/**
	 * Realiza a atualização da quantidade em estoque de um produto cadastrado em um restaurante
	 * @param cnpj do restaurante
	 * @param prView objeto com dados do produto
	 * @param quantidadeEstoque do produto
	 * @return boolean
	 */
	public boolean atualizarProdutoRestaurante(String cnpj, ProdutoRestauranteView prView, int quantidadeEstoque) {
		
		ProdutoRestaurante pr = new ProdutoRestaurante();
		
		pr.setCnpjRestaurante(cnpj);
		pr.setCodigoProduto(prView.getCodigoProduto());
		pr.setPreco(prView.getPrecoProduto());
		pr.setQuantidadeEstoque(quantidadeEstoque);
		
		return dao.atualizarProdutoRestaurante(conn, pr);
	}
	
	/**
	 * Recebe o cnpj do restaurante em sessão e retorna todos os 
	 * produtos cadastrados para aquele restaurante
	 * @param cnpj do restaurante em sessão
	 * @return arraylista com todos os produtos do restaurante
	 */
	public ArrayList<ProdutoRestauranteView> retornarTodoProdutoRestaurante(String cnpj){
		return dao.retornarTodoProdutoRestaurante(conn, cnpj);
	}
	
	private boolean quantidadeEstoqueValida(int quantidadeEstoque) {
		return quantidadeEstoque >= 0;
	}
	
	private boolean precoProdutoValido(double preco) {
		return preco > 0;
	}
	
	private boolean codigoProdutoValido(int codigo) {
		return codigo > 0 && codigo < 2_000_000_000;
	}
	
	
}

package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.ProdutoRestauranteDAO;
import entities.ProdutoRestaurante;
import view.ProdutoRestauranteView;

public class ProdutoRestauranteService {

	//conexão com o banco de dados que será usada em todas as operações
	private ProdutoRestauranteDAO dao;
	
	
	public ProdutoRestauranteService(Connection conn) {
		this.dao = new ProdutoRestauranteDAO(conn);
	}
	
	/**
	 * 
	 * @param quantidadeEstoque
	 */
	public void validarQuantidadeEstoque(int quantidadeEstoque) {
		if(!quantidadeEstoqueValida(quantidadeEstoque)) {
			throw new IllegalArgumentException("Insira uma quantidade de estoque válida");
		}
	}
	
	/**
	 * 
	 * @param preco
	 */
	public void validarPrecoProduto(double preco) {
		if(!precoProdutoValido(preco)) {
			throw new IllegalArgumentException("Digite um preço válido para o produto");
		}
	}
	
	/**
	 * 
	 * @param codigo
	 */
	public void validarCodigoProduto(int codigo) {
		if(!codigoProdutoValido(codigo)) {
			throw new IllegalArgumentException("Digite um código válido");
		}
	}
	
	/**
	 * 
	 * @param cnpj
	 * @param codigo
	 * @return
	 */
	public boolean produtoJaEstaCadastrado(String cnpj, int codigo) {
		return dao.produtoJaEstaCadastrado(cnpj, codigo);
	}
	
	/**
	 * 
	 * @param cnpj
	 * @param codigo
	 * @throws Exception
	 */
	public void apagarProdutoRestaurante(String cnpj, int codigo) throws Exception {
		if(!dao.deletarProduto(cnpj, codigo)) {
			throw new Exception("Ocorreu um erro desconhecido ao apagar o produto.");		
		} 
		
	}
	
	/**
	 * 
	 * @param pr
	 * @return
	 */
	public boolean associarProdutoRestaurante(ProdutoRestaurante pr) {
		return dao.associarProdutoRestaurante(pr);
	}
	
	/**
	 * Recebe o cnpj do restaurante em sessão e retorna todos os 
	 * produtos cadastrados para aquele restaurante
	 * @param cnpj do restaurante em sessão
	 * @return arraylista com todos os produtos do restaurante
	 */
	public ArrayList<ProdutoRestauranteView> retornarTodoProdutoRestaurante(String cnpj){
		return dao.retornarTodoProdutoRestaurante(cnpj);
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

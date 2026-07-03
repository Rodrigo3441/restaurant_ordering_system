package services;

import java.sql.Connection;
import java.util.ArrayList;
import database.RestaurantProductDAO;
import entities.RestaurantProduct;
import view.RestaurantProductView;

/**
 * Class: RestaurantProductService
 *
 * Description:
 * Service class responsible for managing business rules for restaurant products in a N:N relationship.
 *
 * Responsibilities:
 * - provide validation methods for product information
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class RestaurantProductService {

	private RestaurantProductDAO dao;
	private Connection conn;
	
	public RestaurantProductService(Connection conn) {
		this.dao = new RestaurantProductDAO();
		this.conn = conn;
	}
	
	/**
	 * Validates whether a product stock quantity is valid.
	 * @param quantidadeEstoque stock quantity
	 */
	public void validarQuantidadeEstoque(int quantidadeEstoque) {
		if(!quantidadeEstoqueValida(quantidadeEstoque)) {
			throw new IllegalArgumentException("Insira uma quantidade de estoque válida");
		}
	}
	
	/**
	 * Validates whether a product price is valid.
	 * @param preco product price
	 */
	public void validarPrecoProduto(double preco) {
		if(!precoProdutoValido(preco)) {
			throw new IllegalArgumentException("Digite um preço válido para o produto");
		}
	}
	
	/**
	 * Validates whether a product code is valid.
	 * @param codigo product code
	 */
	public void validarCodigoProduto(int codigo) {
		if(!codigoProdutoValido(codigo)) {
			throw new IllegalArgumentException("Digite um código válido");
		}
	}
	
	/**
	 * Checks whether a product is already registered in the restaurant catalog.
	 * @param cnpj restaurant CNPJ
	 * @param codigo product code
	 * @return true if the product is already registered
	 */
	public boolean produtoJaEstaCadastrado(String cnpj, int codigo) {
		return dao.isProductAlreadyAdded(conn, cnpj, codigo);
	}
	
	/**
	 * Removes a product from the restaurant catalog.
	 * @param cnpj restaurant CNPJ
	 * @param codigo product code
	 * @throws Exception if an error occurs while deleting the product
	 */
	public void apagarProdutoRestaurante(String cnpj, int codigo) throws Exception {
		if(!dao.deleteProductRestaurant(conn, cnpj, codigo)) {
			throw new Exception("Ocorreu um erro desconhecido ao apagar o produto.");		
		} 
		
	}
	
	/**
	 * Associates a global catalog product with the restaurant catalog.
	 * @param pr restaurant product object
	 * @return true if the association was successful
	 */
	public boolean associarProdutoRestaurante(RestaurantProduct pr) {
		return dao.addRestaurantProduct(conn, pr);
	}
	
	/**
	 * Updates the stock quantity for a product registered in a restaurant.
	 * @param cnpj restaurant CNPJ
	 * @param prView object with product data
	 * @param quantidadeEstoque new stock quantity
	 * @return true if the update was successful
	 */
	public boolean atualizarProdutoRestaurante(String cnpj, RestaurantProductView prView, int quantidadeEstoque) {
		
		RestaurantProduct pr = new RestaurantProduct();
		
		pr.setRestaurantId(cnpj);
		pr.setProductNumber(prView.getProductNumber());
		pr.setPrice(prView.getProductPrice());
		pr.setStockAmount(quantidadeEstoque);
		
		return dao.updateProductRestaurant(conn, pr);
	}
	
	/**
	 * Returns all products registered for the restaurant identified by the provided CNPJ.
	 * @param cnpj restaurant CNPJ in session
	 * @return list of restaurant product views
	 */
	public ArrayList<RestaurantProductView> retornarTodoProdutoRestaurante(String cnpj){
		return dao.returnAllProductsPerRestaurant(conn, cnpj);
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

package services;

import database.ProductDAO;
import java.sql.Connection;
import entities.Product;

/**
 * Class: ProductService
 *
 * Description:
 * Service responsible for managing business rules related to products.
 *
 * Responsibilities:
 * - provide validation methods for product data
 * - communicate with the data access layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */
public class ProductService {
	private ProductDAO dao;
	private Connection conn;
	
	public ProductService(Connection conn) {
		this.dao = new ProductDAO();
		this.conn = conn;
	}

	/**
	 * Validates that the product name is valid
	 * @param nome product name
	 */
	public void validarNome(String nome) {
		if (!nomeValido(nome)) {
			throw new IllegalArgumentException("Digite um nome válido");
		}
	}
	
	/**
	 * Validates that the product description is valid
	 * @param descricao product description
	 */
	public void validarDescricao(String descricao) {
		if (!descricaoValida(descricao)) {
			throw new IllegalArgumentException("Digite uma descrição válida");
		}
	}
	
	/**
	 * Checks if the product code is valid and not already used by another product
	 * @param codigo product code
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
	 * Searches and returns a product by the given name
	 * @param nome product name to search for
	 * @return Product object or null if not found
	 */
	public Product buscarProdutoPorNome(String nome) {
		nome = nome.toLowerCase().trim();
		return dao.returnProductByName(conn, nome);
	}
	
	/**
	 * Searches and returns a product by the given id/code
	 * @param codigo product id
	 * @return Product object or null if not found
	 */
	public Product buscarProdutoPorId(int codigo) {
		return dao.returnProductById(conn, codigo);
	}
	
	/**
	 * Inserts a product into the system catalog
	 * @param p Product object to insert
	 * @return true if insertion succeeded
	 */
	public boolean inserirProduto(Product p) {
		return dao.addProduct(conn, p);
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
	 * Checks whether the given product code is available for use
	 * @param codigo product code to check
	 * @return true if the code is available
	 */
	private boolean codigoDisponivel(int codigo) {
		return dao.returnProductById(conn, codigo) == null;
	}
	
}

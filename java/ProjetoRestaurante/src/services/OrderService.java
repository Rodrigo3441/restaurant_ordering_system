package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import database.DeliveryPersonDAO;
import database.OrderItemDAO;
import database.OrderDAO;
import entities.Customer;
import entities.DeliveryPerson;
import entities.OrderItem;
import entities.Order;
import entities.Restaurant;
import view.OrderItemView;
import view.RestaurantProductView;
import database.RestaurantProductDAO;

/**
 * Class: OrderService
 *
 * Description:
 * Service class responsible for managing business rules related to orders.
 *
 * Responsibilities:
 * - provide validation methods for order-related information
 * - interact with the data access layer
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class OrderService {
	private Connection conn;
	private OrderDAO pedidoDAO;
	private RestaurantProductDAO produtoRestauranteDAO;
	private DeliveryPersonDAO entregadorDAO;
	private OrderItemDAO itemPedidoDAO;


	public OrderService(Connection conn) {
		this.pedidoDAO = new OrderDAO();
		this.produtoRestauranteDAO = new RestaurantProductDAO();
		this.itemPedidoDAO = new OrderItemDAO();
		this.entregadorDAO = new DeliveryPersonDAO();
		this.conn = conn;
	}
	
	/**
	 * Generic method to validate whether the provided index is valid for an ArrayList of any type
	 * @param lista the list to validate
	 * @param index the index to check
	 */
	public <T> void validarIndex(ArrayList<T> lista, int index) {
		if(!indexValido(lista, index)) {
			throw new IllegalArgumentException("Informe um índice válido");
		}
	}
	
	/**
	 * Validates whether the quantity provided by the user is valid for the given product
	 * @param produtoTemp product view with stock information
	 * @param quantidade quantity to validate
	 */
	public void validarQuantidade(RestaurantProductView produtoTemp, int quantidade) {
		if (!quantidadeValida(produtoTemp, quantidade)){
			throw new IllegalArgumentException("Digite uma quantidade válida");
		}
	}
	
	/**
	 * Instantiates an OrderItemView and returns it for the user's shopping cart
	 * @param produtoTemp product view used to create the order item
	 * @param quantidade quantity for the order item
	 * @return OrderItemView
	 */
	public OrderItemView criarItemPedido(RestaurantProductView produtoTemp, int quantidade) {
		OrderItemView ip = new OrderItemView();
		ip.setName(produtoTemp.getProductName());
		ip.setPrice(produtoTemp.getProductPrice());
		ip.setProductNumber(produtoTemp.getProductNumber());
		ip.setQuantity(quantidade);
		return ip;
	}
	
	/**
	 * Searches for a product in the shopping cart by its code and returns its index in the list.
	 * @param carrinhoCompras user's shopping cart
	 * @param codigoProduto product code to search for
	 * @return index of the product in the list, or -1 if not found
	 */
	public int retornarPosicaoItemCarrinho(
		ArrayList<OrderItemView> carrinhoCompras, 
		int codigoProduto
	) {
		for (int i = 0; i < carrinhoCompras.size(); i++) {
			if (carrinhoCompras.get(i).getProductNumber() == codigoProduto) {
				return i; //return the position of the product in the cart
			}
		}
		
		return -1; //not found
	}
	
	/**
	 * Searches for a product in the provided list by its code.
	 * @param listaProdutos list of available products
	 * @param codigoProduto product code to search for
	 * @return the matching product, or null if not found
	 */
	public RestaurantProductView retornarProdutoPeloCodigo(
		ArrayList<RestaurantProductView> listaProdutos,
		int codigoProduto
	) {
		for (int i = 0; i < listaProdutos.size(); i++) {
			if (listaProdutos.get(i).getProductNumber() == codigoProduto) {
				return listaProdutos.get(i); //return the matching product
			}
		}
		return null;
	}
	
	/**
	 * Calculates the discount for a customer's total order value.
	 * @param valorTotal customer's total order value
	 * @return the total value after applying the discount
	 */
	public double calcularDesconto(double valorTotal) {
		if (valorTotal > 300) {
			return valorTotal * 0.85;
			
		} else if (valorTotal > 200){
			return valorTotal * 0.90;
			
		} else if (valorTotal > 100) {
			return valorTotal * 0.95;
		} else {
			return valorTotal;
		}
	}
	
	
	/**
	 * Registers an order in the system along with all its items.
	 * Also decreases the restaurant's stock for purchased products.
	 * @param r restaurant object
	 * @param c customer object
	 * @param carrinhoCompras customer's shopping cart
	 */
	public void cadastrarPedido(
		Restaurant r, 
		Customer c, 
		ArrayList<OrderItemView> carrinhoCompras
	) {
		// local OrderDAO instance that will use the connection with autoCommit disabled
		OrderDAO pedidoDAO = new OrderDAO();
		
		try {
			// disable automatic commits on the database connection
			conn.setAutoCommit(false);
			
			// insert the order and get the generated primary key to insert items
			int idPedido = pedidoDAO.addOrder(conn, r, c);
			
			for (OrderItemView item: carrinhoCompras) {
				
				// decrease the purchased quantity from stock and store the result to verify
				boolean estoqueAtualizado = produtoRestauranteDAO.decreaseStockAmount(conn, r.getId(), item);
				
				if (!estoqueAtualizado) {
					throw new RuntimeException("Estoque insuficiente");
				}
				
				// instantiate an OrderItem to be inserted into the join table and set its fields
				OrderItem ip = new OrderItem();
				ip.setOrderNumber(idPedido);
				ip.setProductCode(item.getProductNumber());
				ip.setQuantity(item.getQuantity());
				
				itemPedidoDAO.addOrderItem(conn, ip);
				
				// commit after each item insertion
				conn.commit();
			}
			
		} catch (Exception e) {
			
			try {
				// on error, roll back all changes made in the database
	            conn.rollback();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
			
			throw new RuntimeException(e);
			
		} finally {
			
			try {
				// re-enable auto commit on the connection for other operations
	            conn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	
	/**
	 * Returns all orders associated with the given restaurant, filtered by preparation status
	 * @param cnpj restaurant identifier
	 * @param status order status to filter by
	 * @return list of orders
	 */
	public ArrayList<Order> retornarPedidosRestaurante(String cnpj, String status){
		return pedidoDAO.returnAllOrdersPerRestaurant(conn, cnpj, status);
	}
	
	/**
	 * Returns all orders that a given customer has placed in the system
	 * @param cpf customer's identifier
	 * @return list of orders
	 */
	public ArrayList<Order> retornarPedidosCliente(String cpf){
		return pedidoDAO.returnAllOrdersPerCustomer(conn, cpf);
	}
	
	/**
	 * Returns all items associated with a given order
	 * @param codigoPedido order id
	 * @return list of OrderItemView for the order
	 */
	public ArrayList<OrderItemView> retornarItensPedido(int codigoPedido){
		return itemPedidoDAO.returnAllOrderItem(conn, codigoPedido);
	}
	
	/**
	 * Updates delivery information for an order and the delivery person's availability
	 * using a transaction to ensure database consistency.
	 * @param p order to update
	 * @param e delivery person responsible
	 * @param eDisp new availability for the delivery person
	 * @param statusPedido new status for the order
	 * @return true if update succeeds, false otherwise
	 */
	public boolean atualizarEntregaPedido(Order p, DeliveryPerson e, short eDisp, String statusPedido) {

		    try {

			// disable database auto-commit
	        conn.setAutoCommit(false);

		        // update order attributes
	        p.setDeliveryPersonId(e.getId());
	        p.setOrderStatus(statusPedido);

		        // execute update and verify it succeeded
		        if (!pedidoDAO.updateOrder(conn, p)) {
		        	// rollback and return false if update failed
		            conn.rollback(); 
		            return false;
		        }

		        // update delivery person attributes
	        e.setAvailable(eDisp);

		      // execute update and verify it succeeded
		        if (!entregadorDAO.updateDeliveryPerson(conn, e)) {
		        	// rollback and return false if update failed
		            conn.rollback();
		            return false;
		        }

		        // commit if everything executed correctly and return success
	        conn.commit();
	        return true;

	    } catch (Exception ex) {

		        try {
		            conn.rollback();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		
		        ex.printStackTrace();
		        return false;

	    } finally {

		        try {
		            conn.setAutoCommit(true);
		        } catch (SQLException e2) {
		            e2.printStackTrace();
		        }
	    }
	}
	
	/**
	 * retorna o valor total da compra acrescido da taxa de entrega do restaurante
	 * @param valorTotal da compra
	 * @return double
	 */
	public double adicionarTaxaEntrega(double valorTotal) {
		return valorTotal + 8.00;
	}

	private <T> boolean indexValido(ArrayList<T> lista, int index) {
		return index >= 0 && index < lista.size();
	}
	
	private boolean quantidadeValida(RestaurantProductView produtoTemp, int quantidade) {
		return quantidade > 0 && quantidade <= produtoTemp.getStockAmount(); 
	}
	
}

package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import services.RestaurantService;

/**
 * Class: RestaurantSelectionMenu
 *
 * Description:
 * Class responsible for providing an interface for customers to choose
 * a restaurant to place orders.
 *
 * Responsibilities:
 * - Provide interactive menus for restaurant selection
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class RestaurantSelectionMenu {
	
	private RestaurantService servicorestaurante;
	private OrderService servicopedido;
	private Connection conn;
	private Customer cliente;
	private Scanner sc;

	public RestaurantSelectionMenu(Connection conn, Customer cliente, Scanner sc) {
		this.servicorestaurante = new RestaurantService(conn);
		this.servicopedido = new OrderService(conn);
		this.cliente = cliente;
		this.conn = conn;
		this.sc = sc;
	}
	
	
	/**
	 * Displays the interface for the user to choose which restaurant they want to order from.
	 */
	public void mostrarRestaurantes() {
		int contador = 1; //enumerador dos restaurantes
		int index;		  //escolha do usuário
		
		ArrayList<Restaurant> listaRestaurantes = servicorestaurante.returnRestaurantList();
		
		// prevents the user from advancing if there are no restaurants
		if (listaRestaurantes.isEmpty()) {
			System.out.println("ERRO: não há nenhum restaurante cadastrado no sistema!");
			return;
		}
		
		System.out.println("\nLISTA DE RESTAURANTES PARA FAZER UM PEDIDO");
		System.out.println("================================================");
		
		for (Restaurant r: listaRestaurantes) {
			System.out.println(contador + "- " + r.getName());
			contador++;
		}
		System.out.println("================================================\n");
		
		System.out.print("Informe o índice de qual restaurante você deseja comprar: ");
		
		// field for validating the index of the chosen restaurant
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        servicopedido.checkIndex(listaRestaurantes, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		// gets the restaurant chosen by the user from the list based on the index
		Restaurant r = listaRestaurantes.get(index);

		ProductSelectionMenu menuselecaoproduto = new ProductSelectionMenu(conn, sc);
		menuselecaoproduto.mostrarProdutos(r, cliente);
		
	}	
	
}

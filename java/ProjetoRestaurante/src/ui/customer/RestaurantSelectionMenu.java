package ui.cliente;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Cliente;
import entities.Restaurante;
import services.PedidoService;
import services.RestauranteService;

/**
 * Classe: MenuSelecaoRestaurante
 *
 * Descrição:
 * Classe responsável por oferecer a interface para o cliente escolher
 * um restaurante para poder fazer os pedidos
 *
 * Responsabilidades:
 * - oferecer menus interativos para o restaurante
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class MenuSelecaoRestaurante {
	
	private RestauranteService servicorestaurante;
	private PedidoService servicopedido;
	private Connection conn;
	private Cliente cliente;
	private Scanner sc;

	public MenuSelecaoRestaurante(Connection conn, Cliente cliente, Scanner sc) {
		this.servicorestaurante = new RestauranteService(conn);
		this.servicopedido = new PedidoService(conn);
		this.cliente = cliente;
		this.conn = conn;
		this.sc = sc;
	}
	
	
	/**
	 * Exibe a interface para que o usuário possa escolher de qual restaurante ele deseja comprar
	 */
	public void mostrarRestaurantes() {
		int contador = 1; //enumerador dos restaurantes
		int index;		  //escolha do usuário
		
		ArrayList<Restaurante> listaRestaurantes = servicorestaurante.listarRestaurantes();
		
		//impede o usuário de avançar se não houver restaurantes
		if (listaRestaurantes.isEmpty()) {
			System.out.println("ERRO: não há nenhum restaurante cadastrado no sistema!");
			return;
		}
		
		System.out.println("\nLISTA DE RESTAURANTES PARA FAZER UM PEDIDO");
		System.out.println("================================================");
		
		for (Restaurante r: listaRestaurantes) {
			System.out.println(contador + "- " + r.getNome());
			contador++;
		}
		System.out.println("================================================\n");
		
		System.out.print("Informe o índice de qual restaurante você deseja comprar: ");
		
		//campo para validação do índice do restaurante escolhido
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; //usuário enxerga de (1)à(N). Computador enxerga de (0)à(N-1)
			    
		        servicopedido.validarIndex(listaRestaurantes, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um índice válido: ");
		    }
		}
		
		//pega da lista o restaurante escolhido pelo usuário com base no índice
		Restaurante r = listaRestaurantes.get(index);

		MenuSelecaoProduto menuselecaoproduto = new MenuSelecaoProduto(conn, sc);
		menuselecaoproduto.mostrarProdutos(r, cliente);
		
	}	
	
}

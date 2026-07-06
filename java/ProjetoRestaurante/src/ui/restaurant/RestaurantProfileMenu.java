package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;

import entities.Restaurant;
import services.RestaurantService;

/**
 * Class: RestaurantProfileMenu
 *
 * Description:
 * Class responsible for providing the system interface for the restaurant
 *
 * Responsibilities:
 * - provide interactive menus for the user
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantProfileMenu {
	
	private RestaurantService servicorestaurante;
	private Connection conn;
	private Scanner sc;
	
	public RestaurantProfileMenu(RestaurantService servicorestaurante, Connection conn, Scanner sc) {
		this.servicorestaurante = servicorestaurante;
		this.conn = conn;
		this.sc = sc;
	}
	

	/**
	 * Responsible for displaying restaurant profile customization actions
	 * @param r Restaurant object
	 */
	public void mostrarMenuPerfil(Restaurant r) {
		int option = 9;
		
		// validation of the user's option input
		while (true) {
			
			System.out.println("\nMENU EDITAR PERFIL DO RESTAURANTE");
			System.out.println("================================================");
			System.out.println("1- Atualizar nome");
			System.out.println("2- Atualizar telefone");
			System.out.println("3- Atualizar senha");
			System.out.println("4- Atualizar endereço");
			System.out.println("5- Sair da edição de perfil");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 6)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			// access menu options			
			switch (option) {
				case 1:
					this.atualizarNome(r);
					break;
				case 2:
					this.atualizarTelefone(r);
					break;
				case 3:
					this.atualizarSenha(r);
					break;
				case 4:
					RestaurantAddressMenu menuendereco = new RestaurantAddressMenu(conn, sc);
					menuendereco.mostrar(r);
					break;
				case 5:
					System.out.println("Voltando ao menu anterior");
					return;
				
			}

		} 
		
	}
	
	/**
	 * Responsible for updating the restaurant name
	 * @param r restaurant object
	 */
	private void atualizarNome(Restaurant r) {
		// field for restaurant name validation
		while (true) {
			System.out.print("Insira o seu primeiro nome (3-40 letras): ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicorestaurante.updateName(r, nome)) {
					System.out.println("Informações alteradas com sucesso!");
					break;
				} else {
					System.out.println("Erro ao atualizar no banco");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}
	
	/**
	 * Responsible for updating the restaurant phone
	 * @param r restaurant object
	 */
	private void atualizarTelefone(Restaurant r) {
		// field for phone validation
		while (true) {
			System.out.print("Insira o novo telefone do restaurante (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicorestaurante.updatePhone(r, telefone)) {
					System.out.println("Informações alteradas com sucesso!");
					break;
				} else {
					System.out.println("Erro ao atualizar no banco");
				}
			} catch (IllegalArgumentException  e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Responsible for updating the restaurant password
	 * @param r
	 */
	private void atualizarSenha(Restaurant r) {
		// field for password validation
		while (true) {
			System.out.print("Insira a nova senha do restaurante: ");
			
			String senha = sc.next().trim();
			
			try {
				if(servicorestaurante.updatePasscode(r, senha)) {
					System.out.println("Informações alteradas com sucesso!");
					break;
				} else {
					System.out.println("Erro ao atualizar no banco");
				}
			} catch (IllegalArgumentException  e) {
				System.out.println(e.getMessage());
			}
		}	
			
	}
	
}

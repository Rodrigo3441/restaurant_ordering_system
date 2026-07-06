package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;
import entities.Restaurant;
import services.RestaurantService;

/**
 * Class: RestaurantMenu
 *
 * Description:
 * Class responsible for providing the system interface for restaurants
 *
 * Responsibilities:
 * - provide interactive menus for the customer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantMenu {
	
	private RestaurantService servicorestaurante;
	private Connection conn;
	private Scanner sc;
	
	/**
	 * Receives a connection to allow communication with the database
	 * 
	 * @param conn
	 */
	public RestaurantMenu(Connection conn, Scanner sc) {
		this.servicorestaurante = new RestaurantService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Responsible for presenting the initial options for the restaurant to log in or register an account
	 */
	public void mostrarMenuPrincipal() {
			
			int option = 9;
			
			// validate the user's option input
			while (true) {
				
				System.out.println("\nMENU RESTAURANTE");
				System.out.println("================================================");
				System.out.println("1- Iniciar Sessão");
				System.out.println("2- Fazer cadastro de restaurante");
				System.out.println("3- Voltar ao menu principal");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check whether the user's option is outside the allowed range
					if (!(option >= 0 && option <= 3)) {
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
						this.fazerLogin();
						break;
						
					case 2:
						this.fazerCadastro();
						break;
						
					case 3:
						System.out.println("Voltando ao menu principal");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
				
			}
			
		}
	
	
	/**
	 * Responsible for providing the registration interface for the restaurant
	 */
	private void fazerCadastro() {
		
		String cnpj;
		String nome;
		String telefone;
		String senha;
		
		System.out.println("\nCADASTRO DE NOVO RESTAURANTE");
		
		// field for CNPJ validation
		while (true) {
		    System.out.print("Digite o seu CNPJ (14 dígitos): ");
		    cnpj = sc.nextLine().trim();

		    try {
		        servicorestaurante.checkRestaurantId(cnpj);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for restaurant name validation
		while (true) {
		    System.out.print("Digite o nome do restaurante: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicorestaurante.checkName(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		// field for phone validation
		while (true) {
		    System.out.print("Digite o telefone do restaurante: ");
		    telefone = sc.nextLine().trim();

		    try {
		        servicorestaurante.checkPhone(telefone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
		// field for password validation
		while (true) {
		    System.out.print("Digite a senha de acesso do restaurante: ");
		    senha = sc.nextLine().trim();

		    try {
		        servicorestaurante.checkPasscode(senha);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CNPJ: %s\n", cnpj);
		System.out.printf("Nome do restaurante: %s\n", nome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Senha do restaurante: %s\n", senha);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		// validate the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new restaurant and set its attributes
				Restaurant r = new Restaurant();
				r.setId(cnpj);
				r.setName(nome);
				r.setPhone(telefone);
				r.setPasscode(senha);
				
				// call the registration method and verify whether it succeeded
				if(servicorestaurante.addRestaurant(r)) {
					System.out.println("Restaurante cadastrado com sucesso!");
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao cadastrar o restaurante.");
				}
				
				break;
				
			} else if (opt.equals("n")) {
				System.out.println("Nada foi alterado");
				return;
				
			} else {
				System.out.print("Opção inválida, tente novamente: ");
			}
			
		}
	}
	
	/**
	 * Responsible for receiving the restaurant credentials and passing them to the service layer
	 */
	private void fazerLogin() {
		System.out.print("Digite o seu CNPJ para poder inciar sessão: ");
		
		String cnpj = sc.next().trim();
		
		// store all restaurant information
		Restaurant r = servicorestaurante.returnRestaurant(cnpj);
		
		// check whether a restaurant was returned
		if(r != null) {

			System.out.print("Digite a senha do restaurante: ");
			
			String senha = sc.next().trim();
			
			// verify whether the restaurant password matches the entered password
			if (r.getPasscode().equals(senha)) {
				System.out.println("Seja bem vindo, " + r.getName() + "!");
				this.menuRestauranteLogado(r);
			} else {
				System.out.println("Usuário ou senha incorretos.");
			}
			
		} else {
			System.out.println("O CNPJ informado não está cadastrado. (será que você digitou errado?)");
		}
		
	}
	
	/**
	 * Responsible for offering the action menu for the logged-in restaurant
	 * @param r restaurant object
	 */
	private void menuRestauranteLogado(Restaurant r) {
		int option = -1;
		
		while (true) {
			System.out.println("\nMENU GERENCIADOR DO RESTAURANTE");
			System.out.println("================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1- Editar informações do restaurante");
			System.out.println("2- Gerenciar entregadores");
			System.out.println("3- Gerenciar produtos");
			System.out.println("4- Gerenciar pedidos");
			System.out.println("5- Fazer Logoff");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				
				// check whether the user's option is outside the allowed range
				if (!(option >= 0 && option <= 5)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			switch (option) {
				case 1:
					RestaurantProfileMenu menuPerfil = new RestaurantProfileMenu(servicorestaurante, conn, sc);
					menuPerfil.mostrarMenuPerfil(r);
					break;
				case 2:
					RestaurantDeliveryPersonMenu menuEntregador = new RestaurantDeliveryPersonMenu(conn, sc);
					menuEntregador.mostrarMenuEntregador();
					break;
				case 3:
					RestaurantProductMenu menuProduto = new RestaurantProductMenu(conn, sc);
					menuProduto.mostrarMenuProdutos(r);
					break;
				case 4:
					RestaurantOrderMenu menuPedido = new RestaurantOrderMenu(conn, sc);
					menuPedido.mostrarMenuPedidos(r);
					break;
				case 5:
					System.out.println("Até uma próxima.");
					return;
			}
			
		}
		
	}

}

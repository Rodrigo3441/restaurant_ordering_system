package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;

import entities.Address;
import entities.RestaurantAddress;
import entities.Restaurant;
import services.AddressService;

/**
 * Class: RestaurantAddressMenu
 *
 * Description:
 * Class responsible for providing options for the restaurant to manage its address
 *
 * Responsibilities:
 * - provide user interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantAddressMenu {

	private Address enderecoRestaurante;
	private AddressService servicoendereco;
	private Scanner sc;
	
	
	public RestaurantAddressMenu(Connection conn, Scanner sc) {
		this.servicoendereco = new AddressService(conn);
		this.sc = sc;
	}

	/**
	 * Responsible for displaying the address interface for the restaurant to manage
	 * has two states:
	 * if the restaurant has an address displays one menu, otherwise another
	 * @param r restaurant object
	 */
	public void mostrar(Restaurant r) {
		
		int option = 9;
		
		//stores the restaurant's address
		enderecoRestaurante = servicoendereco.retornarEnderecoRestaurante(r.getId());
		
		if (enderecoRestaurante == null) {
			System.out.println("Você não possui um endereço cadastrado!");
			System.out.println("O que deseja fazer?");
			
			System.out.println("\n================================================");
			System.out.println("1- Adicionar endereço");
			System.out.println("2- Voltar ao menu anterior");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			//choice for menu without registered address 
			do {
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//check if the user's option is outside the allowed range
					if (!(option > 0 && option <= 2)) {
						System.out.println("Digite uma opção válida: ");
					}
				
					} catch (Exception e) {
						sc.nextLine();
						System.out.println("Digite apenas números: ");
						option = -1;
					}
			
					switch (option) {
					case 1:
						this.cadastrarEndereco(r);
						return;
					
					case 2:
						return;
					}
			
			} while (option != 2);
			
		} else {
			
			do {
				System.out.println("\nENDERECO ATUAL RESTAURANTE:");
				System.out.println("================================================");
				System.out.println(enderecoRestaurante.formatAddress());
				System.out.println("================================================");
				System.out.println("1- Atualizar CEP");
				System.out.println("2- Atualizar nome da rua");
				System.out.println("3- Atualizar o número da rua");
				System.out.println("4- voltar ao menu anterior");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
				// choice for menu with address already registered
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//check if the user's option is outside the allowed range
					if (!(option > 0 && option <= 4)) {
						System.out.println("Digite uma opção válida: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}

				//access the menu options			
				switch (option) {
					case 1:
						this.atualizarCepRestaurante(enderecoRestaurante);
						break;
					case 2:
						this.atualizarNomeEnderecoRestaurante(enderecoRestaurante);
						break;
					case 3:
						this.atualizarNumeroEnderecoRestaurante(enderecoRestaurante);
						break;
					case 4:
						System.out.println("Voltando ao menu anterior");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
				
			} while (option != 4);		
			

		} 
		
	}	
	
	/**
	 * Implements address registration for a restaurant
	 * @param r restaurant object
	 */
	private void cadastrarEndereco(Restaurant r) {
		String cep;
		String nome;
		int numero;
	
		//field for CEP validation
		while (true) {
		    System.out.print("Digite o CEP da rua do restaurante (8 dígitos): ");
		    cep = sc.nextLine().trim();

		    try {
		        servicoendereco.validarCep(cep);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		//field for street name validation
		while (true) {
		    System.out.print("Digite o nome da rua do restaurante: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicoendereco.validarNome(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validation of the restaurant's street number
		while (true) {
		    System.out.print("Digite o número do restaurante: ");
	
		    try {
		    	
		    	numero = sc.nextInt();
			    sc.nextLine();
			    
		        servicoendereco.validarNumero(numero);
		        break;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CEP: %s\n", cep);
		System.out.printf("Nome da rua: %s\n", nome);
		System.out.printf("Número: %d\n", numero);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validation of user choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instantiation of a new restaurant address and linking of attributes
				RestaurantAddress er = new RestaurantAddress();
				er.setPostalCode(cep);
				er.setRestaurantId(r.getId());
				er.setName(nome);
				er.setNumber(numero);
				
				
				//call the method to register and check if the action was successful
				if(servicoendereco.inserirEnderecoRestaurante(er)) {
					System.out.println("Endereço do restaurante cadastrado com sucesso!");
					return;
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao cadastrar o Endereço.");
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
	 * Edit the restaurant's address
	 * @param er restaurant address object
	 */
	private void atualizarCepRestaurante(Address er) {
		//field for CEP validation
		while (true) {
			System.out.print("Digite o novo CEP do restaurante (8 dígitos): ");
			
			String cep = sc.next().trim();
			
			try {
				if(servicoendereco.atualizarCepEnderecoRestaurante(er, cep)) {
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
	 * Implements editing the address name
	 * @param er restaurant address object
	 */
	private void atualizarNomeEnderecoRestaurante(Address er) {
		
		//field for street name validation
		while (true) {
			System.out.print("Digite o novo nome da sua rua: ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicoendereco.atualizarNomeEnderecoRestaurante(er, nome)) {
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
	 * Implements editing the address number
	 * @param er restaurant address object
	 */
	private void atualizarNumeroEnderecoRestaurante(Address er) {
		
		//field for street number validation
		while (true) {
			System.out.print("Digite o novo número da sua rua: ");
	
			try {
				
				int numero = sc.nextInt();
				sc.nextLine();
				
				if(servicoendereco.atualizarNumeroEnderecoRestaurante(er, numero)) {
					System.out.println("Informações alteradas com sucesso!");
					break;
				} else {
					System.out.println("Erro ao atualizar no banco");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				break;
			}
		}
		
	}
	
}

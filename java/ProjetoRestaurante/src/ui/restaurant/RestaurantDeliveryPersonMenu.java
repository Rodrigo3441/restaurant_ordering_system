package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.DeliveryPerson;
import services.DeliveryPersonService;

/**
 * Class: RestaurantDeliveryPersonMenu
 *
 * Description:
 * Class responsible for providing the delivery person management interface
 * for restaurants in the system
 *
 * Responsibilities:
 * - provide interactive menus for the restaurant
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class RestaurantDeliveryPersonMenu {
	
	private Scanner sc;
	private DeliveryPersonService servicoentregador;
	private Connection conn;
	
	public RestaurantDeliveryPersonMenu(Connection conn, Scanner sc) {
		this.servicoentregador = new DeliveryPersonService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Method mostrarMenuEntregador
	 * 
	 * Responsible for offering the initial menu options for restaurant delivery person management
	 * 
	 */
	public void mostrarMenuEntregador() {
			
			int option = 9;
			
			// validate user's menu choice input
			do {
				
				System.out.println("\nMENU GERENCIADOR DE ENTREGADORES");
				System.out.println("================================================");
				System.out.println("1- Cadastrar um novo entregador");
				System.out.println("2- Listar todos os entregadores");
				System.out.println("3- Atualizar informação de algum entregador");
				System.out.println("4- Apagar um entregador do sistema de entregas");
				System.out.println("5- Voltar ao menu anterior");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
				
				// access the menu options
				switch (option) {
					case 1:
						this.fazerCadastro();
						break;
						
					case 2:
						this.listarEntregadores();
						System.out.println("Aperte enter para continuar");
						sc.nextLine();
						break;
						
					case 3:
						this.atualizarEntregador();
						break;
						
					case 4:
						this.removerEntregador();
						break;
						
					case 5:
						System.out.println("Voltando ao menu principal");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
	
				
			} while (option != 5);
			
		}
	
	
	/**
	 * Responsible for providing the registration interface for a new delivery person
	 * 
	 */
	private void fazerCadastro() {
		
		String cpf;
		String primeiroNome;
		String nomeMeio;
		String ultimoNome;
		String telefone;
		String placaVeiculo;
		
		System.out.println("CADASTRO DE NOVO ENTREGADOR");
		
		// field for CPF validation
		while (true) {
		    System.out.print("Digite o seu CPF (11 dígitos): ");
		    cpf = sc.nextLine().trim();

		    try {
		        servicoentregador.validarCpf(cpf);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for first name validation
		while (true) {
		    System.out.print("Digite o seu primeiro nome: ");
		    primeiroNome = sc.nextLine().trim();

		    try {
		    	servicoentregador.validarPrimeiroNome(primeiroNome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for last name validation
		while (true) {
		    System.out.print("Digite o seu último nome: ");
		    ultimoNome = sc.nextLine().trim();

		    try {
		    	servicoentregador.validarUltimoNome(ultimoNome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for middle name validation
		while (true) {
		    System.out.print("Digite o seu nome do meio: ");
		    nomeMeio = sc.nextLine().trim();

		    try {
		    	servicoentregador.validarNomeMeio(nomeMeio);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		// field for phone validation
		while (true) {
		    System.out.print("Digite o seu telefone: ");
		    telefone = sc.nextLine().trim();

		    try {
		    	servicoentregador.validarTelefone(telefone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for vehicle plate validation
		while (true) {
		    System.out.print("Digite a placa do veículo do entregador: ");
		    placaVeiculo = sc.nextLine().trim().toUpperCase();

		    try {
		    	servicoentregador.validarPlacaVeiculo(placaVeiculo);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CPF: %s\n", cpf);
		System.out.printf("Primeiro nome: %s\n", primeiroNome);
		System.out.printf("Nome do meio: %s\n", nomeMeio);
		System.out.printf("Ultimo nome: %s\n", ultimoNome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Placa do veículo: %s\n", placaVeiculo);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		// validate user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new DeliveryPerson and set attributes			
				DeliveryPerson e = new DeliveryPerson();
				e.setId(cpf);
				e.setFirstName(primeiroNome);
				e.setMiddleName(nomeMeio);
				e.setLastName(ultimoNome);
				e.setPhone(telefone);
				e.setVehicle(placaVeiculo);
				e.setAvailable((short) 0);
				
				// call the registration method and check if it succeeded
				if(servicoentregador.cadastrarEntregador(e)) {
					System.out.println("O entregador foi cadastrado com sucesso!");
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao tentar cadastrar.");
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
	 * List all delivery persons registered in the system
	 */
	private void listarEntregadores() {
		ArrayList<DeliveryPerson> listaEntregadores = servicoentregador.listarEntregadores();

		// stop execution when there are no delivery persons registered
		if (listaEntregadores.isEmpty()) {
			System.out.println("Não há nenhum entregador cadastrado no sistema!");
			return;
		}
		
		System.out.println("\nTODOS OS ENTREGADORES DO SISTEMA:");
		System.out.println("================================================");
		// print each delivery person for the restaurant
		for (DeliveryPerson e: listaEntregadores) {
			System.out.println(e);
		}
		System.out.println("================================================\n");
		
	}
	

	/**
	 * Displays the list of all delivery persons in the system and asks the restaurant to provide the CPF
	 * that will be deleted from the system
	 */
	private void removerEntregador() {
		
		this.listarEntregadores();
		
		System.out.print("Insira o CPF do entregador que você deseja remover do restaurante: ");
		
		// CPF of the delivery person to be deleted
		String cpf = sc.next().trim();
		
		// store the target delivery person for operations
		DeliveryPerson entregador = servicoentregador.retornarEntregador(cpf);
		
		if (entregador != null) {
			System.out.println(entregador);
			System.out.printf("Deseja apagar o entregador de cpf %s do seu restaurante? (s-sim/n-não): ",entregador.getId());
			
			// validate user's choice
			while (true) {
				
				String opt = sc.next();
				
				if (opt.equals("s")) {
				
					// call the deletion method and check if it succeeded
					try {
						servicoentregador.removerEntregador(cpf);
						System.out.println("Entregador deletado do sistema com sucesso!");
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					break;
					
				} else if (opt.equals("n")) {
					System.out.println("Nada foi alterado");
					return;
					
				} else {
					System.out.print("Opção inválida, tente novamente: ");
				}
				
			}
			
		} else {
			System.out.println("Não há nenhum entregador com esse cpf.");
			return;
		}
		
	}
	
	/**
	 * Displays the list of all delivery persons in the system and asks the restaurant to provide the CPF
	 * of the delivery person whose information should be updated
	 */
	private void atualizarEntregador() {
		
		this.listarEntregadores();
		
		System.out.print("Insira o CPF do entregador que você deseja atualizar as informações: ");
		
		// CPF of the delivery person to be updated
		String cpf = sc.next().trim();
		
		// store the target delivery person for operations
		DeliveryPerson entregador = servicoentregador.retornarEntregador(cpf);
		
		if (entregador != null) {
			DeliveryPersonProfileMenu menu = new DeliveryPersonProfileMenu(conn, sc);
			menu.mostrarMenuPerfil(entregador);
			
		} else {
			System.out.println("Não há nenhum entregador com esse cpf.");
		}
	}
	

}

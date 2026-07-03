package ui.customer;

import java.sql.Connection;
import java.util.Scanner;

import entities.Customer;
import entities.Address;
import entities.CustomerAddress;
import services.AddressService;

/**
 * Class: CustomerAddressMenu
 *
 * Description:
 * Responsible for presenting address management options to the customer
 *
 * Responsibilities:
 * - provide the user interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class CustomerAddressMenu {
	
	private Scanner sc;
	private Address enderecoCliente;
	private AddressService servicoendereco;
	
	
	public CustomerAddressMenu(Connection conn, Scanner sc) {
		this.servicoendereco = new AddressService(conn);
		this.sc = sc;
	}

	/**
	 * Displays the address interface so the customer can manage it.
	 * There are two states:
	 * - if the customer has an address, show the edit menu
	 * - otherwise, show the add-address menu
	 * @param c customer object
	 */
	public void mostrar(Customer c) {
		
		int option = 9;
		
		// store the customer's address
		enderecoCliente = servicoendereco.retornarEnderecoCliente(c.getId());
		
		if (enderecoCliente == null) {
			System.out.println("Você não possui um endereço cadastrado!");
			System.out.println("O que deseja fazer?");
			System.out.println("1- Adicionar endereço");
			System.out.println("2- Voltar ao menu anterior");
			
				// choice handling for menu when no address is registered
			while (true) {
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's choice is outside the allowed range
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
						this.cadastrarEndereco(c);
						return;
					
					case 2:
						System.out.println("Voltando ao menu anterior");
						return;
					}
			
			}
			
		} else {
			
			while (true) {
				System.out.println("\nENDERECO ATUAL CLIENTE:");
				System.out.println("================================================");
				System.out.println(enderecoCliente.formatAddress());
				System.out.println("================================================");
				System.out.println("1- Atualizar CEP");
				System.out.println("2- Atualizar nome da rua");
				System.out.println("3- Atualizar o número da rua");
				System.out.println("4- voltar ao menu anterior");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
				// choice handling for menu when an address is already registered
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's choice is outside the allowed range
					if (!(option > 0 && option <= 4)) {
						System.out.println("Digite uma opção válida: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}

				// access the menu options
				switch (option) {
					case 1:
						this.atualizarCepCliente(enderecoCliente);
						break;
					case 2:
						this.atualizarNomeEnderecoCliente(enderecoCliente);
						break;
					case 3:
						this.atualizarNumeroEnderecoCliente(enderecoCliente);
						break;
					case 4:
						System.out.println("Voltando ao menu anterior");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
				
			}		
			

		} 
		
	}	
	
	/**
	 * Implements the registration of an address for a customer
	 * @param c customer object
	 */
	private void cadastrarEndereco(Customer c) {
		String cep;
		String nome;
		int numero;
	
		// field for CEP validation
		while (true) {
		    System.out.print("Digite o CEP da sua rua (8 dígitos): ");
		    cep = sc.nextLine().trim();

		    try {
		        servicoendereco.validarCep(cep);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for street name validation
		while (true) {
		    System.out.print("Digite o nome da sua rua: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicoendereco.validarNome(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validation of the customer's street number
		while (true) {
		    System.out.print("Digite o número da sua rua: ");
	
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
		
		// validation of the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new CustomerAddress and bind attributes
				CustomerAddress ec = new CustomerAddress();
				ec.setPostalCode(cep);
				ec.setCustomerId(c.getId());
				ec.setName(nome);
				ec.setNumber(numero);
				
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicoendereco.inserirEnderecoCliente(ec)) {
					System.out.println("Endereço do cliente cadastrado com sucesso!");
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
	 * Implements CEP update
	 * @param ec address object
	 */
	private void atualizarCepCliente(Address ec) {
		
		// field for CEP validation
		while (true) {
			System.out.print("Digite o seu novo CEP (8 dígitos): ");
			
			String cep = sc.next().trim();
			
			try {
				if(servicoendereco.atualizarCepEnderecoCliente(ec, cep)) {
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
	 * Implements update of the address name
	 * @param ec address object
	 */
	private void atualizarNomeEnderecoCliente(Address ec) {
		
		// field for street name validation
		while (true) {
			System.out.print("Digite o novo nome da sua rua: ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicoendereco.atualizarNomeEnderecoCliente(ec, nome)) {
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
	 * Implements update of the customer's address number
	 * @param ec address object
	 */
	private void atualizarNumeroEnderecoCliente(Address ec) {
		
		// field for street number validation
		while (true) {
			System.out.print("Digite o novo número da sua rua: ");
	
			try {
				
				int numero = sc.nextInt();
				sc.nextLine();
				
				if(servicoendereco.atualizarNumeroEnderecoCliente(ec, numero)) {
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

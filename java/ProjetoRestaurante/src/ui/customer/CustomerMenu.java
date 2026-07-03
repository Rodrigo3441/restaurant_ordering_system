package ui.customer;

import java.sql.Connection;
import java.util.Scanner;
import entities.Customer;
import services.CustomerService;

/**
 * Class: CustomerMenu
 *
 * Description:
 * Provides the user interface for customer interactions.
 *
 * Responsibilities:
 * - present interactive menus for customers
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerMenu {
	
	private CustomerService servicocliente;
	private Connection conn;
	private Scanner sc;
	
	/**
	 * Receives a database connection to allow communication with the DB
	 *
	 * @param conn database connection
	 */
	public CustomerMenu(Connection conn, Scanner sc) {
		this.servicocliente = new CustomerService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Presents initial options for a customer to log in or register an account
	 */
	public void mostrarMenuPrincipal() {
			
			int option = 9;
			
			// validate user's menu option input
			while (true) {
				
				System.out.println("\nMENU CLIENTE");
				System.out.println("================================================");
				System.out.println("1- Iniciar Sessão");
				System.out.println("2- Fazer cadastro de cliente");
				System.out.println("3- Voltar ao menu principal");
				System.out.println("================================================\n");
				System.out.print("Informe a ação desejada: ");

				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					// check if the user's option is outside the allowed range
					if (!(option >= 0 && option <= 3)) {
						System.out.println("Digite uma opção válida: ");
					}
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
				
				//access the corresponding method based on the user's choice			
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
	 * Provides the registration interface for a new customer
	 */
	private void fazerCadastro() {
		
		String cpf;
		String primeiroNome;
		String nomeMeio;
		String ultimoNome;
		String telefone;
		String email;
		String senha;
		
		System.out.println("\nCADASTRO DE NOVO CLIENTE");
		
		// field for CPF validation
		while (true) {
		    System.out.print("Digite o seu CPF (11 dígitos): ");
		    cpf = sc.nextLine().trim();

		    try {
		        servicocliente.validarCpf(cpf);
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
		        servicocliente.validarPrimeiroNome(primeiroNome);
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
		        servicocliente.validarUltimoNome(ultimoNome);
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
		        servicocliente.validarNomeMeio(nomeMeio);
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
		        servicocliente.validarTelefone(telefone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for email validation
		while (true) {
		    System.out.print("Digite o seu email: ");
		    email = sc.nextLine().trim();

		    try {
		        servicocliente.validarEmail(email);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
		// field for password validation
		while (true) {
		    System.out.print("Digite a sua senha de usuário: ");
		    senha = sc.nextLine().trim();

		    try {
		        servicocliente.validarSenha(senha);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRMANDO AS INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CPF: %s\n", cpf);
		System.out.printf("Primeiro nome: %s\n", primeiroNome);
		System.out.printf("Nome do meio: %s\n", nomeMeio);
		System.out.printf("Ultimo nome: %s\n", ultimoNome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Email: %s\n", email);
		System.out.printf("Senha da conta: %s\n", senha);
		System.out.println("================================================\n");
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		// validate user's confirmation choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				// instantiate a new customer and set attributes
				Customer c = new Customer();
				c.setId(cpf);
				c.setFirstName(primeiroNome);
				c.setMiddleName(nomeMeio);
				c.setLastName(ultimoNome);
				c.setPhone(telefone);
				c.setEmail(email);
				c.setPasscode(senha);
				
					// call service method to register and check success
				if(servicocliente.cadastrarCliente(c)) {
					System.out.println("Você foi cadastrado com sucesso!");
					
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
	 * Collects user credentials and forwards them to the service layer
	 */
	private void fazerLogin() {
		System.out.print("Digite o seu CPF para poder inciar sessão: ");
		
		String cpf = sc.next().trim();
		
		// retrieve the customer information
		Customer c = servicocliente.retornarCliente(cpf);
		
		// check if a customer was returned
		if(c != null) {

			System.out.print("Digite a senha da sua conta: ");
			
			String senha = sc.next().trim();
			
			// verify that the stored password matches the entered password
			if (c.getPasscode().equals(senha)) {
				System.out.println("Seja bem vindo, " + c.getFirstName() + "!");
				this.menuClienteLogado(c);
			} else {
				System.out.println("Usuário ou senha incorretos.");
			}
			
		} else {
			System.out.println("O CPF informado não está cadastrado. (será que você digitou errado?)");
		}
		
	}
	
	/**
	 * Presents action menu for a logged-in customer
	 * @param c customer object
	 */
	private void menuClienteLogado(Customer c) {
		int option = -1;
		
		while (true) {
			System.out.println("\nMENU DO CLIENTE");
			System.out.println("================================================");
			System.out.printf("O que deseja fazer hoje, %s?\n", c.getFirstName());
			System.out.println("1- Editar perfil");
			System.out.println("2- Visualizar pedidos");
			System.out.println("3- Fazer um pedido");
			System.out.println("4- Fazer Logoff");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
					// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 4)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			switch (option) {
				case 1:
					CustomerProfileMenu menuPerfil = new CustomerProfileMenu(servicocliente, conn, sc);
					menuPerfil.mostrarMenuPerfil(c);
					break;
				case 2:
					CustomerOrderMenu menuPedidos = new CustomerOrderMenu(conn, sc);
					menuPedidos.mostrarPedidosCliente(c);
					break;
				case 3:
					RestaurantSelectionMenu menuCompras = new RestaurantSelectionMenu(conn, c, sc);
					menuCompras.mostrarRestaurantes();
					break;
				case 4:
					System.out.println("Até uma próxima.");
					return;
			}
			
		}
		
	}

}

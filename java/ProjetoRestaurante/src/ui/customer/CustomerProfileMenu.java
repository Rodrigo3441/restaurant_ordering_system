package ui.customer;

import java.sql.Connection;
import java.util.Scanner;

import entities.Customer;
import services.CustomerService;

/**
 * Class: CustomerProfileMenu
 *
 * Description:
 * Provides the user interface for customer profile management.
 *
 * Responsibilities:
 * - present interactive menus for the user to edit their profile
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class CustomerProfileMenu {
	
	private Scanner sc;
	private CustomerService servicocliente;
	private Connection conn;
	
	public CustomerProfileMenu(CustomerService servicocliente, Connection conn, Scanner sc) {
		this.servicocliente = servicocliente;
		this.conn = conn;
		this.sc = sc;
	}
	

	/**
	 * Displays the profile editing actions menu
	 * @param c Customer object
	 */
	public void mostrarMenuPerfil(Customer c) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nMENU DE EDIÇÃO DE PERFIL");
			System.out.println("================================================");
			System.out.println("1- Atualizar primeiro nome");
			System.out.println("2- Atualizar nome do meio");
			System.out.println("3- Atualizar ultimo nome");
			System.out.println("4- Atualizar telefone");
			System.out.println("5- Atualizar email");
			System.out.println("6- Atualizar senha");
			System.out.println("7- Atualizar endereço");
			System.out.println("8- Sair da edição de perfil");
			System.out.println("================================================\n");
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// check if the user's option is outside the allowed range
				if (!(option >= 0 && option <= 8)) {
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
					this.atualizarPrimeiroNome(c);
					break;
				case 2:
					this.atualizarNomeMeio(c);
					break;
				case 3:
					this.atualizarUltimoNome(c);
					break;
				case 4:
					this.atualizarTelefone(c);
					break;
				case 5:
					this.atualizarEmail(c);
					break;
				case 6:
					this.atualizarSenha(c);		
					break;
				case 7:
					CustomerAddressMenu menuenderecocliente = new CustomerAddressMenu(conn, sc);
					menuenderecocliente.mostrar(c);
					break;
				case 8:
					System.out.println("Voltando ao menu anterior");
					return;
				
			}

		}
		
	}
	
	/**
	 * Updates the customer's first name
	 * @param c customer object
	 */
	private void atualizarPrimeiroNome(Customer c) {


		// input loop for validating first name
		while (true) {
			System.out.print("Insira o seu primeiro nome (3-20 letras): ");
			
			String primeiroNome = sc.next().trim();
			
			try {
				if(servicocliente.updateFirstName(c, primeiroNome)) {
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
	 * Updates the customer's middle name
	 * @param c customer object
	 */
	private void atualizarNomeMeio(Customer c) {


		// input loop for validating middle name
		while (true) {
			System.out.print("Insira o seu nome do meio (3-40 letras): ");
			
			String nomeMeio = sc.next().trim();
			
			try {
				if(servicocliente.updateMiddleName(c, nomeMeio)) {
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
	 * Updates the customer's last name
	 * @param c customer object
	 */
	private void atualizarUltimoNome(Customer c) {


		// input loop for validating last name
		while (true) {
			System.out.print("Insira o seu ultimo nome (3-20 letras): ");
			
			String ultimoNome = sc.next().trim();
			
			try {
				if(servicocliente.updateLastName(c, ultimoNome)) {
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
	 * Updates the customer's phone number
	 * @param c customer object
	 */
	private void atualizarTelefone(Customer c) {


		// input loop for validating phone number
		while (true) {
			System.out.print("Insira o seu novo telefone (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicocliente.updatePhone(c, telefone)) {
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
	 * Updates the customer's email
	 * @param c customer object
	 */
	private void atualizarEmail(Customer c) {


		// input loop for validating email
		while (true) {
			System.out.print("Insira o seu novo email: ");
			
			String email = sc.next().trim();
			
			try {
				if(servicocliente.updateEmail(c, email)) {
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
	 * Updates the customer's password
	 * @param c customer object
	 */
	private void atualizarSenha(Customer c) {


		// input loop for validating password
		while (true) {
			System.out.print("Insira a sua nova senha: ");
			
			String senha = sc.next().trim();
			
			try {
				if(servicocliente.updatePasscode(c, senha)) {
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

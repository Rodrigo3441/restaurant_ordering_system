package ui;

import java.sql.Connection;
import java.util.Scanner;

import entities.Cliente;
import services.ClienteService;

/**
 * Classe: MenuPerfilCliente
 *
 * Descrição:
 * Classe responsável por oferecer a interface do sistema para o usuário
 *
 * Responsabilidades:
 * - oferecer menus interativos para o usuário
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class MenuPerfilCliente {
	
	private Scanner sc = new Scanner(System.in);
	private ClienteService servicocliente;
	private Connection conn;
	
	/**
	 * 
	 * @param servicocliente
	 */
	public MenuPerfilCliente(ClienteService servicocliente, Connection conn) {
		this.servicocliente = servicocliente;
		this.conn = conn;
	}
	

	/**
	 * Método mostrarMenuPerfil
	 * 
	 * Responsável por exibir as ações de personalização do perfil
	 * 
	 * @param c objeto Cliente
	 */
	public void mostrarMenuPerfil(Cliente c) {
		int option = 9;
		
		//validação da entrada de opção pelo usuário
		do {
			
			System.out.println("MENU DE EDIÇÃO DE PERFIL");
			System.out.println("1- Atualizar primeiro nome");
			System.out.println("2- Atualizar nome do meio");
			System.out.println("3- Atualizar ultimo nome");
			System.out.println("4- Atualizar telefone");
			System.out.println("5- Atualizar email");
			System.out.println("6- Atualizar senha");
			System.out.println("7- Atualizar endereço");
			System.out.println("8- Sair da edição de perfil");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				//verificar se a opção do usuário está fora do intervalo permitido
				if (!(option >= 0 && option <= 8)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			//acesso as opções do menu			
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
					MenuEnderecoCliente menuenderecocliente = new MenuEnderecoCliente(conn);
					menuenderecocliente.mostrar(c);
					break;
				case 8:
					return;
				
			}

		} while (option != 8);
		
	}
	
	/**
	 * Método atualizarPrimeiroNome
	 * Responsável por atualizar o primeiro nome do cliente
	 * @param c objeto cliente
	 */
	private void atualizarPrimeiroNome(Cliente c) {

		//campo para validação do primeiro nome
		while (true) {
			System.out.print("Insira o seu primeiro nome (3-20 letras): ");
			
			String primeiroNome = sc.next().trim();
			
			try {
				if(servicocliente.atualizarPrimeiroNome(c, primeiroNome)) {
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
	 * Método atualizarNomeMeio
	 * Responsável por atualizar o nome do meio do cliente
	 * @param c objeto cliente
	 */
	private void atualizarNomeMeio(Cliente c) {

		//campo para validação do nome do meio
		while (true) {
			System.out.print("Insira o seu nome do meio (3-40 letras): ");
			
			String nomeMeio = sc.next().trim();
			
			try {
				if(servicocliente.atualizarNomeMeio(c, nomeMeio)) {
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
	 * Método atualizarUltimoNome
	 * Responsável por atualizar o ultimo nome do cliente
	 * @param c objeto cliente
	 */
	private void atualizarUltimoNome(Cliente c) {

		//campo para validação do ultimo nome
		while (true) {
			System.out.print("Insira o seu ultimo nome (3-20 letras): ");
			
			String ultimoNome = sc.next().trim();
			
			try {
				if(servicocliente.atualizarUltimoNome(c, ultimoNome)) {
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
	 * Método atualizarTelefone
	 * Responsável por atualizar o telefone do cliente
	 * @param c objeto cliente
	 */
	private void atualizarTelefone(Cliente c) {

		//campo para validação do telefone
		while (true) {
			System.out.print("Insira o seu novo telefone (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicocliente.atualizarTelefone(c, telefone)) {
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
	 * Método atualizarEmail
	 * Responsável por atualizar o email do usuário
	 * @param c objeto cliente
	 */
	private void atualizarEmail(Cliente c) {

		//campo para validação do email
		while (true) {
			System.out.print("Insira o seu novo email: ");
			
			String email = sc.next().trim();
			
			try {
				if(servicocliente.atualizarEmail(c, email)) {
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
	 * Método atualizarSenha
	 * Responsável por atualizar a senha do usuário
	 * @param c objeto cliente
	 */
	private void atualizarSenha(Cliente c) {

		//campo para validação da senha
		while (true) {
			System.out.print("Insira a sua nova senha: ");
			
			String senha = sc.next().trim();
			
			try {
				if(servicocliente.atualizarSenha(c, senha)) {
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

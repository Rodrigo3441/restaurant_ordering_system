package ui;

import java.sql.Connection;
import java.util.Scanner;

import entities.Restaurante;
import services.RestauranteService;

/**
 * Classe: MenuPerfilRestaurante
 *
 * Descrição:
 * Classe responsável por oferecer a interface do sistema para o restaurante
 *
 * Responsabilidades:
 * - oferecer menus interativos para o usuário
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class MenuPerfilRestaurante {
	
	private Scanner sc = new Scanner(System.in);
	private RestauranteService servicorestaurante;
	private Connection conn;
	
	/**
	 * 
	 * @param servicorestaurante
	 */
	public MenuPerfilRestaurante(RestauranteService servicorestaurante, Connection conn) {
		this.servicorestaurante = servicorestaurante;
		this.conn = conn;
	}
	

	/**
	 * Método mostrarMenuPerfil
	 * Responsável por exibir as ações de personalização do perfil do restaurante
	 * @param r objeto Restaurante
	 */
	public void mostrarMenuPerfil(Restaurante r) {
		int option = 9;
		
		//validação da entrada de opção pelo usuário
		do {
			
			System.out.println("MENU EDITAR PERFIL DO RESTAURANTE");
			System.out.println("1- Atualizar nome");
			System.out.println("2- Atualizar telefone");
			System.out.println("3- Atualizar senha");
			System.out.println("4- Atualizar endereço");
			System.out.println("5- Atualizar categoria");
			System.out.println("6- Sair da edição de perfil");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				//verificar se a opção do usuário está fora do intervalo permitido
				if (!(option >= 0 && option <= 6)) {
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
					this.atualizarNome(r);
					break;
				case 2:
					this.atualizarTelefone(r);
					break;
				case 3:
					this.atualizarSenha(r);
					break;
				case 4:
					MenuEnderecoRestaurante menuendereco = new MenuEnderecoRestaurante(conn);
					menuendereco.mostrar(r);
					break;
				case 5:
					
					break;
				case 6:
					return;
				
			}

		} while (option != 6);
		
	}
	
	/**
	 * Método atualizarNome
	 * Responsável por atualizar o nome do restaurante
	 * @param r objeto restaurante
	 */
	private void atualizarNome(Restaurante r) {
		//campo para validação do nome do restaurante
		while (true) {
			System.out.print("Insira o seu primeiro nome (3-40 letras): ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicorestaurante.atualizarNome(r, nome)) {
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
	 * Método atualizarTelefone
	 * Responsável por atualizar o telefone do restaurante
	 * @param r objeto restaurante
	 */
	private void atualizarTelefone(Restaurante r) {
		//campo para validação do telefone
		while (true) {
			System.out.print("Insira o novo telefone do restaurante (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicorestaurante.atualizarTelefone(r, telefone)) {
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
	 * Responsável por atualizar a senha do restaurante 
	 * @param r
	 */
	private void atualizarSenha(Restaurante r) {
		//campo para validação da senha
		while (true) {
			System.out.print("Insira a nova senha do restaurante: ");
			
			String senha = sc.next().trim();
			
			try {
				if(servicorestaurante.atualizarSenha(r, senha)) {
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

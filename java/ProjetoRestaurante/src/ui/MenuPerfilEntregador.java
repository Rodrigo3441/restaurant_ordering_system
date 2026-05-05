package ui;

import java.sql.Connection;
import java.util.Scanner;
import entities.Entregador;
import services.EntregadorService;

/**
 * Classe: MenuPerfilEntregador
 *
 * Descrição:
 * Classe responsável por oferecer a interface do sistema para o restaurante
 * gerenciar as informações dos entregadores cadastrados
 *
 * Responsabilidades:
 * - oferecer menus interativos para o usuário
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class MenuPerfilEntregador {
	
	private Scanner sc = new Scanner(System.in);
	private EntregadorService servicoentregador;

	public MenuPerfilEntregador(Connection conn) {
		this.servicoentregador = new EntregadorService(conn);
	}
	
	/**
	 * Método mostrarMenuPerfil
	 * 
	 * Responsável por exibir as ações de personalização do perfil
	 * 
	 * @param c objeto Cliente
	 */
	public void mostrarMenuPerfil(Entregador entregador) {
		int option = 9;
		
		//validação da entrada de opção pelo usuário
		do {
			
			System.out.println("MENU DE EDIÇÃO DE ENTREGADOR");
			System.out.println("Editando:");
			System.out.println(entregador);
			System.out.println("1- Atualizar primeiro nome");
			System.out.println("2- Atualizar nome do meio");
			System.out.println("3- Atualizar ultimo nome");
			System.out.println("4- Atualizar telefone");
			System.out.println("5- Atualizar placa do veículo");
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
					this.atualizarPrimeiroNome(entregador);
					break;
				case 2:
					this.atualizarNomeMeio(entregador);
					break;
				case 3:
					this.atualizarUltimoNome(entregador);
					break;
				case 4:
					this.atualizarTelefone(entregador);
					break;
				case 5:
					this.atualizarPlacaVeiculo(entregador);
					break;
				case 6:
					return;
				
			}

		} while (option != 6);
		
	}
	
	/**
	 * Método atualizarPrimeiroNome
	 * Responsável por atualizar o primeiro nome do cliente
	 * @param c objeto cliente
	 */
	private void atualizarPrimeiroNome(Entregador entregador) {

		//campo para validação do primeiro nome
		while (true) {
			System.out.print("Insira o primeiro nome do entregador (3-20 letras): ");
			
			String primeiroNome = sc.next().trim();
			
			try {
				if(servicoentregador.atualizarPrimeiroNome(entregador, primeiroNome)) {
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
	private void atualizarNomeMeio(Entregador entregador) {

		//campo para validação do nome do meio
		while (true) {
			System.out.print("Insira o seu nome do meio (3-40 letras): ");
			
			String nomeMeio = sc.next().trim();
			
			try {
				if(servicoentregador.atualizarNomeMeio(entregador, nomeMeio)) {
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
	private void atualizarUltimoNome(Entregador entregador) {

		//campo para validação do ultimo nome
		while (true) {
			System.out.print("Insira o seu ultimo nome (3-20 letras): ");
			
			String ultimoNome = sc.next().trim();
			
			try {
				if(servicoentregador.atualizarUltimoNome(entregador, ultimoNome)) {
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
	private void atualizarTelefone(Entregador entregador) {

		//campo para validação do telefone
		while (true) {
			System.out.print("Insira o seu novo telefone (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicoentregador.atualizarTelefone(entregador, telefone)) {
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
	private void atualizarPlacaVeiculo(Entregador entregador) {

		//campo para validação do email
		while (true) {
			System.out.print("Insira a nova placa do veículo do entregador: ");
			
			String placaVeiculo = sc.next().trim().toUpperCase();
			
			try {
				if(servicoentregador.atualizarPlacaVeiculo(entregador, placaVeiculo)) {
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

package ui.restaurant;

import java.sql.Connection;
import java.util.Scanner;
import entities.DeliveryPerson;
import services.DeliveryPersonService;

/**
 * Class: DeliveryPersonProfileMenu
 *
 * Description:
 * Class responsible for providing the system interface for the restaurant
 * to manage the registered delivery person's information.
 *
 * Responsibilities:
 * - provide interactive menus to the user
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class DeliveryPersonProfileMenu {
	
	private DeliveryPersonService servicoentregador;
	private Scanner sc;

	public DeliveryPersonProfileMenu(Connection conn, Scanner sc) {
		this.servicoentregador = new DeliveryPersonService(conn);
		this.sc = sc;
	}
	
	/**
	 * Responsible for displaying profile customization actions
	 * @param entregador delivery person object
	 */
	public void mostrarMenuPerfil(DeliveryPerson entregador) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nMENU DE EDIÇÃO DE ENTREGADOR");
			System.out.println("================================================");
			System.out.println("Editando:");
			System.out.println(entregador);
			System.out.println("1- Atualizar primeiro nome");
			System.out.println("2- Atualizar nome do meio");
			System.out.println("3- Atualizar ultimo nome");
			System.out.println("4- Atualizar telefone");
			System.out.println("5- Atualizar placa do veículo");
			System.out.println("6- Sair da edição de perfil");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				// validate if the option is within the valid range
				if (!(option >= 0 && option <= 6)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			// execute the action corresponding to the user's option			
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

		}
		
	}
	
	/**
	 * Responsible for updating the delivery person's first name
	 * @param entregador delivery person object
	 */
	private void atualizarPrimeiroNome(DeliveryPerson entregador) {

		// field for first name validation
		while (true) {
			System.out.print("Insira o primeiro nome do entregador (3-20 letras): ");
			
			String primeiroNome = sc.next().trim();
			
			try {
				if(servicoentregador.updateFirstName(entregador, primeiroNome)) {
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
	 * Responsible for updating the delivery person's middle name
	 * @param entregador delivery person object
	 */
	private void atualizarNomeMeio(DeliveryPerson entregador) {

		// field for middle name validation
		while (true) {
			System.out.print("Insira o seu nome do meio (3-40 letras): ");
			
			String nomeMeio = sc.next().trim();
			
			try {
				if(servicoentregador.updateMiddleName(entregador, nomeMeio)) {
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
	 * Responsible for updating the delivery person's last name
	 * @param entregador delivery person object
	 */
	private void atualizarUltimoNome(DeliveryPerson entregador) {

		// field for last name validation
		while (true) {
			System.out.print("Insira o seu ultimo nome (3-20 letras): ");
			
			String ultimoNome = sc.next().trim();
			
			try {
				if(servicoentregador.updateLastName3(entregador, ultimoNome)) {
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
	 * Responsible for updating the delivery person's phone
	 * @param entregador delivery person object
	 */
	private void atualizarTelefone(DeliveryPerson entregador) {

		// field for phone validation
		while (true) {
			System.out.print("Insira o seu novo telefone (até 11 dígitos): ");
			
			String telefone = sc.next().trim();
			
			try {
				if(servicoentregador.updatePhone(entregador, telefone)) {
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
	 * Responsible for updating the delivery person's vehicle plate
	 * @param entregador delivery person object
	 */
	private void atualizarPlacaVeiculo(DeliveryPerson entregador) {

		// field for vehicle plate validation
		while (true) {
			System.out.print("Insira a nova placa do veículo do entregador: ");
			
			String placaVeiculo = sc.next().trim().toUpperCase();
			
			try {
				if(servicoentregador.updateVehiclePlate(entregador, placaVeiculo)) {
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

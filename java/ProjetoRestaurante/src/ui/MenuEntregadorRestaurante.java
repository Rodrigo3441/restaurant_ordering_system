package ui;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Entregador;
import services.EntregadorService;

/**
 * Classe: MenuEntregadorRestaurante
 *
 * Descrição:
 * Classe responsável por oferecer a interface de gerenciamento dos entregadores
 * para os restaurantes do sistema
 *
 * Responsabilidades:
 * - oferecer menus interativos para o restaurante
 *
 * @author Rodrigo
 * @since 30-04-2026
 */

public class MenuEntregadorRestaurante {
	
	private Scanner sc = new Scanner(System.in);
	private EntregadorService servicoentregador;
	private Connection conn;
	
	public MenuEntregadorRestaurante(Connection conn) {
		this.servicoentregador = new EntregadorService(conn);
		this.conn = conn;
	}
	
	/**
	 * Método mostrarMenuPrincipal
	 * 
	 * Responsável por oferecer opções inciais para o cliente acessar ou cadastrar uma conta
	 * 
	 */
	public void mostrarMenuEntregador() {
			
			int option = 9;
			
			//validação da entrada de opção pelo usuário
			do {
				
				System.out.println("MENU GERENCIADOR DE ENTREGADORES");
				System.out.println("1- Cadastrar um novo entregador");
				System.out.println("2- Listar todos os entregadores");
				System.out.println("3- Atualizar informação de algum entregador");
				System.out.println("4- Apagar um entregador do sistema de entregas");
				System.out.println("5- Voltar ao menu anterior");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
				
				//acesso as opções do menu			
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
	 * Responsável por fornecer a interface de cadastro para um novo entregador
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
		
		//campo para validação de CPF
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
		
		//campo para validação do primeiro nome
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
		
		//campo para validação do ultimo nome
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
		
		//campo para validação do nome do meio
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
				
		//campo para validação de telefone
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
		
		//campo para validação da placa do veículo
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
		
		System.out.println("Confirmando informações: ");
		System.out.printf("CPF: %s\n", cpf);
		System.out.printf("Primeiro nome: %s\n", primeiroNome);
		System.out.printf("Nome do meio: %s\n", nomeMeio);
		System.out.printf("Ultimo nome: %s\n", ultimoNome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Placa do veículo: %s\n", placaVeiculo);
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo Entregador e vinculação dos atributos			
				Entregador e = new Entregador();
				e.setCpf(cpf);
				e.setPrimeiroNome(primeiroNome);
				e.setNomeMeio(nomeMeio);
				e.setUltimoNome(ultimoNome);
				e.setTelefone(telefone);
				e.setVeiculo(placaVeiculo);
				e.setDisponibilidade((short) 0);
				
				//chamada do método para cadastro e verificação se houve êxito na ação
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
	 * Lista todos os entregadores que estão cadastrados no sistema
	 */
	private void listarEntregadores() {
		ArrayList<Entregador> listaEntregadores = servicoentregador.listarEntregadores();

		//Interrompe a execução quando não há produtos cadastrados
		if (listaEntregadores.isEmpty()) {
			System.out.println("Não há nenhum entregador cadastrado no sistema!");
			return;
		}
		
		System.out.println("Exibindo todos os entregadores do sistema:");
		//Imprime cada produto para aquele restaurante
		for (Entregador e: listaEntregadores) {
			System.out.println(e);
		}
		
	}
	

	/**
	 * Exibe a lista de todos os entregadores do sistema e pede ao restaurante para informar o cpf
	 * que vai ser deletado do sistema
	 */
	private void removerEntregador() {
		
		this.listarEntregadores();
		
		System.out.print("Insira o CPF do entregador que você deseja remover do restaurante: ");
		
		//cpf do entregador a ser deletado
		String cpf = sc.next().trim();
		
		//entregador alvo é armazenado para as operações serem realizadas
		Entregador entregador = servicoentregador.retornarEntregador(cpf);
		
		if (entregador != null) {
			System.out.println(entregador);
			System.out.printf("Deseja apagar o entregador de cpf %s do seu restaurante? (s-sim/n-não): ",entregador.getCpf());
			
			//validação da escolha do usuário
			while (true) {
				
				String opt = sc.next();
				
				if (opt.equals("s")) {
				
					//chamada do método para cadastro e verificação se houve êxito na ação
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
	 * Exibe a lista de todos os entregadores do sistema e pede ao restaurante para informar o cpf
	 * do entregador da qual se deseja atualizar informações
	 */
	private void atualizarEntregador() {
		
		this.listarEntregadores();
		
		System.out.print("Insira o CPF do entregador que você deseja atualizar as informações: ");
		
		//cpf do entregador a ser deletado
		String cpf = sc.next().trim();
		
		//entregador alvo é armazenado para as operações serem realizadas
		Entregador entregador = servicoentregador.retornarEntregador(cpf);
		
		if (entregador != null) {
			MenuPerfilEntregador menu = new MenuPerfilEntregador(conn);
			menu.mostrarMenuPerfil(entregador);
			
		} else {
			System.out.println("Não há nenhum entregador com esse cpf.");
		}
	}
	

}

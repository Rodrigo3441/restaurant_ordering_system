package ui;

import java.sql.Connection;
import java.util.Scanner;
import entities.Cliente;
import services.ClienteService;

/**
 * Classe: MenuCliente
 *
 * Descrição:
 * Classe responsável por oferecer a interface do sistema para o cliente
 *
 * Responsabilidades:
 * - oferecer menus interativos para o cliente
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class MenuCliente {
	
	private Scanner sc = new Scanner(System.in);
	private ClienteService servicocliente;
	private Connection conn;
	
	/**
	 * Recebe uma conexão para permitir comunicação com banco de dados
	 * 
	 * @param conn
	 */
	public MenuCliente(Connection conn) {
		this.servicocliente = new ClienteService(conn);
		this.conn = conn;
	}
	
	/**
	 * Método mostrarMenuPrincipal
	 * 
	 * Responsável por oferecer opções inciais para o cliente acessar ou cadastrar uma conta
	 * 
	 */
	public void mostrarMenuPrincipal() {
			
			int option = 9;
			
			//validação da entrada de opção pelo usuário
			do {
				
				System.out.println("MENU CLIENTE");
				System.out.println("1- Iniciar Sessão");
				System.out.println("2- Fazer cadastro de cliente");
				System.out.println("3- Voltar ao menu principal");
				
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//verificar se a opção do usuário está fora do intervalo permitido
					if (!(option >= 0 && option <= 3)) {
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
	
				
			} while (option != 3);
			
		}
	
	
	/**
	 * Método fazerCadastro
	 * 
	 * Responsável por fornecer a interface de cadastro para o cliente
	 * 
	 */
	private void fazerCadastro() {
		
		String cpf;
		String primeiroNome;
		String nomeMeio;
		String ultimoNome;
		String telefone;
		String email;
		String senha;
		
		System.out.println("CADASTRO DE NOVO CLIENTE");
		
		//campo para validação de CPF
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
		
		//campo para validação do primeiro nome
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
		
		//campo para validação do ultimo nome
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
		
		//campo para validação do nome do meio
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
				
		//campo para validação de telefone
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
		
		//campo para validação de email
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
	
		//campo para validação de senha
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
		
		System.out.println("Confirmando informações: ");
		System.out.printf("CPF: %s\n", cpf);
		System.out.printf("Primeiro nome: %s\n", primeiroNome);
		System.out.printf("Nome do meio: %s\n", nomeMeio);
		System.out.printf("Ultimo nome: %s\n", ultimoNome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Email: %s\n", email);
		System.out.printf("Senha da conta: %s\n\n", senha);
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo cliente e vinculação dos atributos
				Cliente c = new Cliente();
				c.setCpf(cpf);
				c.setPrimeiroNome(primeiroNome);
				c.setNomeMeio(nomeMeio);
				c.setUltimoNome(ultimoNome);
				c.setTelefone(telefone);
				c.setEmail(email);
				c.setSenha(senha);
				
				//chamada do método para cadastro e verificação se houve êxito na ação
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
	 * Método fazerLogin
	 * 
	 * Responsável por receber credenciais do usuário e passá-las para a camada de serviço
	 * 
	 */
	private void fazerLogin() {
		System.out.print("Digite o seu CPF para poder inciar sessão: ");
		
		String cpf = sc.next().trim();
		
		//armazena todas as informações do restaurante
		Cliente c = servicocliente.retornarCliente(cpf);
		
		//verifica se houve retorno para um restaurante
		if(c != null) {

			System.out.print("Digite a senha da sua conta: ");
			
			String senha = sc.next().trim();
			
			//verifica se o atributo senha de r confere com senha informada
			if (c.getSenha().equals(senha)) {
				System.out.println("Seja bem vindo, " + c.getPrimeiroNome() + "!");
				this.menuClienteLogado(c);
			} else {
				System.out.println("Usuário ou senha incorretos.");
			}
			
		} else {
			System.out.println("O CPF informado não está cadastrado. (será que você digitou errado?)");
		}
		
	}
	
	/**
	 * Método menuClienteLogado
	 * 
	 * Responsável por oferecer o menu de ações para o cliente em sessão
	 * 
	 * @param c objeto Cliente
	 */
	private void menuClienteLogado(Cliente c) {
		int option = -1;
		do {
			System.out.println("MENU DO CLIENTE");
			System.out.printf("O que deseja fazer hoje, %s?\n", c.getPrimeiroNome());
			System.out.println("1- Editar perfil");
			System.out.println("2- Visualizar pedidos");
			System.out.println("3- Fazer um pedido");
			System.out.println("4- Fazer Logoff");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
				//verificar se a opção do usuário está fora do intervalo permitido
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
					MenuPerfilCliente menuperfil = new MenuPerfilCliente(servicocliente, conn);
					menuperfil.mostrarMenuPerfil(c);
					break;
				case 2:
					
					break;
				case 3:
					MenuPedidoClienteSelecaoRestaurante menupedido = new MenuPedidoClienteSelecaoRestaurante(conn, c);
					menupedido.mostrarRestaurantes();
					break;
				case 4:
					System.out.println("Até uma próxima.");
					return;
			}
			
		} while (option != 4);
		
	}

}

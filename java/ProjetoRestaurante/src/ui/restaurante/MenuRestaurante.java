package ui.restaurante;

import java.sql.Connection;
import java.util.Scanner;
import entities.Restaurante;
import services.RestauranteService;

/**
 * Classe: MenuRestaurante
 *
 * Descrição:
 * Classe responsável por oferecer a interface do sistema para o restaurante
 *
 * Responsabilidades:
 * - oferecer menus interativos para o cliente
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class MenuRestaurante {
	
	private RestauranteService servicorestaurante;
	private Connection conn;
	private Scanner sc;
	
	/**
	 * Recebe uma conexão para permitir comunicação com banco de dados
	 * 
	 * @param conn
	 */
	public MenuRestaurante(Connection conn, Scanner sc) {
		this.servicorestaurante = new RestauranteService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Responsável por oferecer opções inciais para o restaurante acessar ou cadastrar uma conta
	 */
	public void mostrarMenuPrincipal() {
			
			int option = 9;
			
			//validação da entrada de opção pelo usuário
			while (true) {
				
				System.out.println("\nMENU RESTAURANTE");
				System.out.println("================================================");
				System.out.println("1- Iniciar Sessão");
				System.out.println("2- Fazer cadastro de restaurante");
				System.out.println("3- Voltar ao menu principal");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
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
				
			}
			
		}
	
	
	/**
	 * Responsável por fornecer a interface de cadastro para o restaurante
	 */
	private void fazerCadastro() {
		
		String cnpj;
		String nome;
		String telefone;
		String senha;
		
		System.out.println("\nCADASTRO DE NOVO RESTAURANTE");
		
		//campo para validação de CPF
		while (true) {
		    System.out.print("Digite o seu CNPJ (14 dígitos): ");
		    cnpj = sc.nextLine().trim();

		    try {
		        servicorestaurante.validarCnpj(cnpj);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		//campo para validação do nome do restaurante
		while (true) {
		    System.out.print("Digite o nome do restaurante: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicorestaurante.validarNome(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
				
		//campo para validação de telefone
		while (true) {
		    System.out.print("Digite o telefone do restaurante: ");
		    telefone = sc.nextLine().trim();

		    try {
		        servicorestaurante.validarTelefone(telefone);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
	
		//campo para validação de senha
		while (true) {
		    System.out.print("Digite a senha de acesso do restaurante: ");
		    senha = sc.nextLine().trim();

		    try {
		        servicorestaurante.validarSenha(senha);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CNPJ: %s\n", cnpj);
		System.out.printf("Nome do restaurante: %s\n", nome);
		System.out.printf("Telefone: %s\n", telefone);
		System.out.printf("Senha do restaurante: %s\n", senha);
		System.out.println("================================================\n");
		
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo restaurante e vinculação dos atributos
				Restaurante r = new Restaurante();
				r.setCnpj(cnpj);
				r.setNome(nome);
				r.setTelefone(telefone);
				r.setSenha(senha);
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicorestaurante.cadastrarRestaurante(r)) {
					System.out.println("Restaurante cadastrado com sucesso!");
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao cadastrar o restaurante.");
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
	 * Responsável por receber credenciais do restaurante e passá-las para a camada de serviço
	 */
	private void fazerLogin() {
		System.out.print("Digite o seu CNPJ para poder inciar sessão: ");
		
		String cnpj = sc.next().trim();
		
		//armazena todas as informações do restaurante
		Restaurante r = servicorestaurante.retornarRestaurante(cnpj);
		
		//verifica se houve retorno para um restaurante
		if(r != null) {

			System.out.print("Digite a senha do restaurante: ");
			
			String senha = sc.next().trim();
			
			//verifica se o atributo senha de r confere com senha informada
			if (r.getSenha().equals(senha)) {
				System.out.println("Seja bem vindo, " + r.getNome() + "!");
				this.menuRestauranteLogado(r);
			} else {
				System.out.println("Usuário ou senha incorretos.");
			}
			
		} else {
			System.out.println("O CNPJ informado não está cadastrado. (será que você digitou errado?)");
		}
		
	}
	
	/**
	 * Responsável por oferecer o menu de ações para o restaurante em sessão
	 * @param r objeto restaurante
	 */
	private void menuRestauranteLogado(Restaurante r) {
		int option = -1;
		
		while (true) {
			System.out.println("\nMENU GERENCIADOR DO RESTAURANTE");
			System.out.println("================================================");
			System.out.println("O que deseja fazer?");
			System.out.println("1- Editar informações do restaurante");
			System.out.println("2- Gerenciar entregadores");
			System.out.println("3- Gerenciar produtos");
			System.out.println("4- Gerenciar pedidos");
			System.out.println("5- Fazer Logoff");
			System.out.println("================================================\n");
			
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				
				//verificar se a opção do usuário está fora do intervalo permitido
				if (!(option >= 0 && option <= 5)) {
					System.out.println("Digite uma opção válida: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Digite apenas números: ");
				option = -1;
			}
			
			switch (option) {
				case 1:
					MenuPerfilRestaurante menuPerfil = new MenuPerfilRestaurante(servicorestaurante, conn, sc);
					menuPerfil.mostrarMenuPerfil(r);
					break;
				case 2:
					MenuEntregadorRestaurante menuEntregador = new MenuEntregadorRestaurante(conn, sc);
					menuEntregador.mostrarMenuEntregador();
					break;
				case 3:
					MenuProdutoRestaurante menuProduto = new MenuProdutoRestaurante(conn, sc);
					menuProduto.mostrarMenuProdutos(r);
					break;
				case 4:
					MenuPedidoRestaurante menuPedido = new MenuPedidoRestaurante(conn, sc);
					menuPedido.mostrarMenuPedidos(r);
					break;
				case 5:
					System.out.println("Até uma próxima.");
					return;
			}
			
		}
		
	}

}

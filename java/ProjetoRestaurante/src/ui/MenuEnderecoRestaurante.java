package ui;

import java.sql.Connection;
import java.util.Scanner;

import entities.EnderecoRestaurante;
import entities.Restaurante;
import services.EnderecoService;

/**
 * Classe: MenuEnderecoRestaurante
 *
 * Descrição:
 * Classe responsável por fornecer opções para o restaurante gerenciar o endereço
 *
 * Responsabilidades:
 * - fornecer interface
 * - comunicar com a camada serviço
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class MenuEnderecoRestaurante {

	private Scanner sc = new Scanner(System.in);
	private EnderecoRestaurante endereco;
	private EnderecoService servicoendereco;
	
	
	public MenuEnderecoRestaurante(Connection conn) {
		this.servicoendereco = new EnderecoService(conn);
	}

	/**
	 * Método mostrar
	 * 
	 * Responsável por exibir a interface de endereço para o cliente pode gerenciar
	 * tem dois estados:
	 * se o cliente possui um endereço exibe um menu, caso contrário outro
	 * 
	 * @param c objeto cliente
	 */
	public void mostrar(Restaurante r) {
		
		int option = 9;
		
		//armazena o endereço do cliente
		endereco = servicoendereco.retornarEnderecoRestaurante(r.getCnpj());
		
		if (endereco == null) {
			System.out.println("Você não possui um endereço cadastrado!");
			System.out.println("O que deseja fazer?");
			System.out.println("1- Adicionar endereço");
			System.out.println("2- Voltar ao menu anterior");
			
			//escolha para menu sem endereço cadastrado 
			do {
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//verificar se a opção do usuário está fora do intervalo permitido
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
						this.cadastrarEndereco(r);
						return;
					
					case 2:
						return;
					}
			
			} while (option != 2);
			
		} else {
			
			do {
				System.out.println("ENDERECO ATUAL RESTAURANTE:");
				System.out.println("CEP: " + endereco.getCep());
				System.out.println("Nome rua: " + endereco.getNome());
				System.out.println("Número do restaurante: " + endereco.getNumero());
				System.out.println("1- Atualizar CEP");
				System.out.println("2- Atualizar nome da rua");
				System.out.println("3- Atualizar o número da rua");
				System.out.println("4- voltar ao menu anterior");
				System.out.print("O que deseja fazer? ");
				
				// escolha para menu com endereço já cadastrado
				try {
					
					option = sc.nextInt();
					sc.nextLine();
					
					//verificar se a opção do usuário está fora do intervalo permitido
					if (!(option > 0 && option <= 4)) {
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
						this.atualizarCepRestaurante(endereco);
						break;
					case 2:
						this.atualizarNomeEnderecoRestaurante(endereco);
						break;
					case 3:
						this.atualizarNumeroEnderecoRestaurante(endereco);
						break;
					case 4:
						System.out.println("Voltando ao menu anterior");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
				
			} while (option != 4);		
			

		} 
		
	}	
	
	/**
	 * Método cadastrarEndereco
	 * 
	 * Implementa o cadastro de um endereço para um restaurante
	 * 
	 * @param r objeto restaurante
	 */
	private void cadastrarEndereco(Restaurante r) {
		String cep;
		String nome;
		int numero;
	
		//campo para validação do CEP
		while (true) {
		    System.out.print("Digite o CEP da rua do restaurante (8 dígitos): ");
		    cep = sc.nextLine().trim();

		    try {
		        servicoendereco.validarCep(cep);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		//campo para validação do nome da rua
		while (true) {
		    System.out.print("Digite o nome da rua do restaurante: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicoendereco.validarNome(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validação do número da rua do restaurante
		while (true) {
		    System.out.print("Digite o número do restaurante: ");
	
		    try {
		    	
		    	numero = sc.nextInt();
			    sc.nextLine();
			    
		        servicoendereco.validarNumero(numero);
		        break;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        
		    }
		}
		
		System.out.println("Confirmando informações: ");
		System.out.printf("CEP: %s\n", cep);
		System.out.printf("Nome da rua: %s\n", nome);
		System.out.printf("Número: %d\n", numero);
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo enderecorestaurante e vinculação dos atributos
				EnderecoRestaurante er = new EnderecoRestaurante();
				er.setCep(cep);
				er.setCnpjRestaurante(r.getCnpj());
				er.setNome(nome);
				er.setNumero(numero);
				
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicoendereco.inserirEnderecoRestaurante(er)) {
					System.out.println("Endereço do restaurante cadastrado com sucesso!");
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
	 * Método atualizarCepRestaurante
	 * Edição do endereço do restaurante
	 * @param er objeto enderecoRestaurante
	 */
	private void atualizarCepRestaurante(EnderecoRestaurante er) {
		//campo para validação do CEP
		while (true) {
			System.out.print("Digite o novo CEP do restaurante (8 dígitos): ");
			
			String cep = sc.next().trim();
			
			try {
				if(servicoendereco.atualizarCepEnderecoRestaurante(er, cep)) {
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
	 * Método atualizarNomeEnderecoRestaurante
	 * Implementa a edição do nome do endereço
	 * @param er objeto enderecorestaurante
	 */
	private void atualizarNomeEnderecoRestaurante(EnderecoRestaurante er) {
		
		//campo para validação do nome da rua
		while (true) {
			System.out.print("Digite o novo nome da sua rua: ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicoendereco.atualizarNomeEnderecoRestaurante(er, nome)) {
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
	 * Método atualizarNumeroEnderecoRestaurante
	 * Implementa a edição do número do endereço
	 * @param er objeto enderecorestaurante
	 */
	private void atualizarNumeroEnderecoRestaurante(EnderecoRestaurante er) {
		
		//campo para validação do número da rua
		while (true) {
			System.out.print("Digite o novo número da sua rua: ");
	
			try {
				
				int numero = sc.nextInt();
				sc.nextLine();
				
				if(servicoendereco.atualizarNumeroEnderecoRestaurante(er, numero)) {
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

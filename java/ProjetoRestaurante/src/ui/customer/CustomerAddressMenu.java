package ui.cliente;

import java.sql.Connection;
import java.util.Scanner;

import entities.Cliente;
import entities.Endereco;
import entities.EnderecoCliente;
import services.EnderecoService;

/**
 * Classe: MenuEnderecoCliente
 *
 * Descrição:
 * Classe responsável por fornecer opções para o cliente gerenciar o endereço
 *
 * Responsabilidades:
 * - fornecer interface
 * - comunicar com a camada serviço
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class MenuEnderecoCliente {
	
	private Scanner sc;
	private Endereco enderecoCliente;
	private EnderecoService servicoendereco;
	
	
	public MenuEnderecoCliente(Connection conn, Scanner sc) {
		this.servicoendereco = new EnderecoService(conn);
		this.sc = sc;
	}

	/**
	 * exibe a interface de endereço para o cliente pode gerenciar
	 * tem dois estados:
	 * se o cliente possui um endereço exibe um menu, caso contrário outro
	 * @param c objeto cliente
	 */
	public void mostrar(Cliente c) {
		
		int option = 9;
		
		//armazena o endereço do cliente
		enderecoCliente = servicoendereco.retornarEnderecoCliente(c.getCpf());
		
		if (enderecoCliente == null) {
			System.out.println("Você não possui um endereço cadastrado!");
			System.out.println("O que deseja fazer?");
			System.out.println("1- Adicionar endereço");
			System.out.println("2- Voltar ao menu anterior");
			
			//escolha para menu sem endereço cadastrado 
			while (true) {
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
						this.cadastrarEndereco(c);
						return;
					
					case 2:
						System.out.println("Voltando ao menu anterior");
						return;
					}
			
			}
			
		} else {
			
			while (true) {
				System.out.println("\nENDERECO ATUAL CLIENTE:");
				System.out.println("================================================");
				System.out.println(enderecoCliente.formatarEndereco());
				System.out.println("================================================");
				System.out.println("1- Atualizar CEP");
				System.out.println("2- Atualizar nome da rua");
				System.out.println("3- Atualizar o número da rua");
				System.out.println("4- voltar ao menu anterior");
				System.out.println("================================================\n");
				
				System.out.print("Informe a ação desejada: ");
				
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
						this.atualizarCepCliente(enderecoCliente);
						break;
					case 2:
						this.atualizarNomeEnderecoCliente(enderecoCliente);
						break;
					case 3:
						this.atualizarNumeroEnderecoCliente(enderecoCliente);
						break;
					case 4:
						System.out.println("Voltando ao menu anterior");
						return;
						
					default: 
						System.out.println("Opção inválida, tente novamente: ");
				}
				
			}		
			

		} 
		
	}	
	
	/**
	 * Implementa o cadastro de um endereço para um cliente
	 * @param c objeto cliente
	 */
	private void cadastrarEndereco(Cliente c) {
		String cep;
		String nome;
		int numero;
	
		//campo para validação do CEP
		while (true) {
		    System.out.print("Digite o CEP da sua rua (8 dígitos): ");
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
		    System.out.print("Digite o nome da sua rua: ");
		    nome = sc.nextLine().trim();

		    try {
		        servicoendereco.validarNome(nome);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// validação do número da rua do cliente cliente
		while (true) {
		    System.out.print("Digite o número da sua rua: ");
	
		    try {
		    	
		    	numero = sc.nextInt();
			    sc.nextLine();
			    
		        servicoendereco.validarNumero(numero);
		        break;
		    } catch (Exception e) {
		        System.out.println(e.getMessage());
		        
		    }
		}
		
		System.out.println("\nCONFIRMANDO INFORMAÇÕES: ");
		System.out.println("================================================");
		System.out.printf("CEP: %s\n", cep);
		System.out.printf("Nome da rua: %s\n", nome);
		System.out.printf("Número: %d\n", numero);
		System.out.println("================================================\n");
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo enderecocliente e vinculação dos atributos
				EnderecoCliente ec = new EnderecoCliente();
				ec.setCep(cep);
				ec.setCpfCliente(c.getCpf());
				ec.setNome(nome);
				ec.setNumero(numero);
				
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicoendereco.inserirEnderecoCliente(ec)) {
					System.out.println("Endereço do cliente cadastrado com sucesso!");
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
	 * Implementa a atualização do CEP
	 * @param ec objeto enderecoCliente
	 */
	private void atualizarCepCliente(Endereco ec) {
		
		//campo para validação do CEP
		while (true) {
			System.out.print("Digite o seu novo CEP (8 dígitos): ");
			
			String cep = sc.next().trim();
			
			try {
				if(servicoendereco.atualizarCepEnderecoCliente(ec, cep)) {
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
	 * Implementa a atualização do nome do endereço
	 * @param ec objeto enderecocliente
	 */
	private void atualizarNomeEnderecoCliente(Endereco ec) {
		
		//campo para validação do nome da rua
		while (true) {
			System.out.print("Digite o novo nome da sua rua: ");
			
			String nome = sc.nextLine().trim();
			
			try {
				if(servicoendereco.atualizarNomeEnderecoCliente(ec, nome)) {
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
	 * Implementa a atualização do número do endereço do cliente
	 * @param ec objeto enderecocliente
	 */
	private void atualizarNumeroEnderecoCliente(Endereco ec) {
		
		//campo para validação do número da rua
		while (true) {
			System.out.print("Digite o novo número da sua rua: ");
	
			try {
				
				int numero = sc.nextInt();
				sc.nextLine();
				
				if(servicoendereco.atualizarNumeroEnderecoCliente(ec, numero)) {
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

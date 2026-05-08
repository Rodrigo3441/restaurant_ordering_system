package ui;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Produto;
import entities.ProdutoRestaurante;
import entities.Restaurante;
import services.ProdutoService;
import services.ProdutoRestauranteService;
import view.ProdutoRestauranteView;

/**
 * Classe: MenuProdutoRestaurante
 *
 * Descrição:
 * Classe responsável por fornecer uma interface para que o restaurante possa gerenciar os seus produtos
 *
 * Responsabilidades:
 * - oferecer uma interface de comunicação dos dados
 * - se comunicar com a camada de serviço
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class MenuProdutoRestaurante {
	
	private Scanner sc = new Scanner(System.in);
	private ProdutoService servicoproduto;
	private ProdutoRestauranteService servicoprodutorestaurante;
	
	
	public MenuProdutoRestaurante(Connection conn) {
		this.servicoprodutorestaurante = new ProdutoRestauranteService(conn);
		this.servicoproduto = new ProdutoService(conn);
	}
	
	/**
	 * Exibir o menu para o restaurante poder gerenciar os produtos de seu catálogo
	 * @param r objeto restaurante
	 */
	public void mostrarMenuProdutos(Restaurante r) {
		int option = 9;
		
		//validação da entrada de opção pelo usuário
		do {
			
			System.out.println("MENU PRODUTOS DO RESTAURANTE");
			System.out.println("1- Cadastrar um novo produto");
			System.out.println("2- Gerenciar produtos cadastrados");
			System.out.println("3- Voltar ao menu anterior");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.print("Digite apenas números (1-3): ");
			}
			
			//acesso as opções do menu			
			switch (option) {
				case 1:
					this.cadastrarProduto(r.getCnpj());
					break;
				case 2:
					this.gerenciarProdutosCadastrados(r.getCnpj());
					break;
				case 3:
					return;
				default:
					System.out.print("Opção inválida, tente novamente: ");
				
			}

		} while (option != 5);
		
	}
	
	/**
	 * Método cadastrarProduto
	 * Exibir a interface para o restaurante poder cadastrar um produto em seu catálogo
	 * Identifica se o produto já está cadastrado e será só associado, ou se será inserido do zero
	 * @param cnpj do restaurante em sessão
	 */
	private void cadastrarProduto(String cnpj) {
		String nomeProduto;
		
		//campo para validação do nome do produto
		while (true) {
			System.out.print("Insira o nome do produto que você deseja cadastrar (3-40 letras): ");
			nomeProduto = sc.nextLine().trim().toLowerCase();

		    try {
		    	servicoproduto.validarNome(nomeProduto);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		Produto p = servicoproduto.buscarProdutoPorNome(nomeProduto);
		
		//verificação se houve um produto com o mesmo nome retornado
		if (p != null) {
			if (servicoprodutorestaurante.produtoJaEstaCadastrado(cnpj, p.getCodigo())) {
				System.out.println("Esse produto já está associado ao restaurante!");
				return;
			}
			
			System.out.print("Esse produto já está cadastrado no catálogo global. Deseja adicionar ele no catálogo do seu restaurante? (1-sim/2-não)");
			
			int option = 9;
			
			//validação da entrada de opção pelo usuário
			do {
				try {
					option = sc.nextInt();
					sc.nextLine();

				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Digite apenas números: ");
					option = -1;
				}
				
				//acesso as opções de decisão			
				switch (option) {
					case 1:
						this.associarProdutoRestaurante(p, cnpj);
						return;
					case 2:
						System.out.println("A operação foi cancelada pelo usuário.");
						return;
					default:
					    System.out.println("Digite uma opção válida (1 ou 2)");
						
				}
	
			} while (true);
			
		}
		
		this.cadastrarProdutoNovo(cnpj, nomeProduto);
	}
		
	/**
	 * Método cadastrarProdutoNovo
	 * Se o nome de produto que o usuário digitou não for localizado na base de dados
	 * Ele será "redirecionado" para esse método, cadastrando globalmente em produtos, e depois
	 * associando ao restaurante
	 * @param cnpj
	 * @param nomeProduto
	 */
	private void cadastrarProdutoNovo(String cnpj, String nomeProduto) {
		int codigo;
		String descricaoProduto;
		
		//campo para validação da descrição do produto
		while (true) {
			System.out.print("Insira a descrição do produto: ");
			descricaoProduto = sc.nextLine().trim().toLowerCase();

		    try {
		    	servicoproduto.validarDescricao(descricaoProduto);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		//campo para validação do código do produto
		while (true) {
			System.out.print("Insira o código do produto no catálogo global (Um valor numérico): ");
			
		    try {
		    	codigo = sc.nextInt();
				sc.nextLine();
				
		    	servicoproduto.validarCodigo(codigo);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("Confirmando informações: ");
		System.out.printf("Código global do produto: %s\n", codigo);
		System.out.printf("Nome do produto: %s\n", nomeProduto);
		System.out.printf("Descrição do produto: %s\n", descricaoProduto);
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo produto e vinculação dos atributos
				Produto p = new Produto();
				
				p.setCodigo(codigo);
				p.setNome(nomeProduto);
				p.setDescricao(descricaoProduto);
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicoproduto.inserirProduto(p)) {
					System.out.println("Produto cadastrado no catálogo global com sucesso!");
					
					//após cadastrar globalmente, chama método para vincular ao restaurante
					this.associarProdutoRestaurante(p, cnpj);
					
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
	 * Método associarProdutoRestaurante
	 * associa um produto do catálogo global de produtos à um determinado restaurante
	 * @param p objeto produto
	 * @param cnpj do restaurante
	 */
	private void associarProdutoRestaurante(Produto p, String cnpj) {
		int quantidadeEstoque;
		double preco;
		
		//campo para validação da quantidade em estoque do produto
		while (true) {
			System.out.printf("Insira a quantidade atual em estoque do produto %s: ", p.getNome());
			
		    try {
		    	quantidadeEstoque = sc.nextInt();
				sc.nextLine();
				
		    	servicoprodutorestaurante.validarQuantidadeEstoque(quantidadeEstoque);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Digite um valor válido para a quantidade em estoque");
		    }
		}
		
		//campo para validação do preço do produto
		while (true) {
			System.out.printf("Insira o preço do produto %s: ", p.getNome());
			
		    try {
		    	preco = sc.nextDouble();
				sc.nextLine();
				
		    	servicoprodutorestaurante.validarPrecoProduto(preco);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Digite um preço válido para o produto");
		    }
		}
		
		System.out.println("Confirmando informações: ");
		System.out.printf("Código global do produto: %d\n", p.getCodigo());
		System.out.printf("Nome do produto: %s\n", p.getNome());
		System.out.printf("Quantidade em estoque: %d\n", quantidadeEstoque);
		System.out.printf("Preço do produto: R$ %.2f\n", preco);
		System.out.print("Deseja confirmar as informações? (s para sim/n para cancelar): ");
		
		//validação da escolha do usuário
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("s")) {
				//instanciação de um novo produto e vinculação dos atributos
				ProdutoRestaurante pr = new ProdutoRestaurante();
				
				pr.setCnpjRestaurante(cnpj);
				pr.setCodigoProduto(p.getCodigo());
				pr.setQuantidadeEstoque(quantidadeEstoque);
				pr.setPreco(preco);
				
				//chamada do método para cadastro e verificação se houve êxito na ação
				if(servicoprodutorestaurante.associarProdutoRestaurante(pr)) {
					System.out.println("Produto associado ao catálogo do restaurante com sucesso!");
					return;
					
				} else {
					System.out.println("Ocorreu um erro desconhecido ao tentar associar.");
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
	 * Exibe todos os produtos associados ao restaurante em sessão
	 * @param cnpj do restaurante
	 */
	public void gerenciarProdutosCadastrados(String cnpj) {
		int option = 0;
		ArrayList<ProdutoRestauranteView> listaProdutos = servicoprodutorestaurante.retornarTodoProdutoRestaurante(cnpj);

		//Interrompe a execução quando não há produtos cadastrados
		if (listaProdutos.isEmpty()) {
			System.out.println("Não há produtos cadastrados para o restaurante!");
			return;
		}
		
		System.out.println("Exibindo todos os produtos do restaurante:");
		//Imprime cada produto para aquele restaurante
		for (ProdutoRestauranteView pr: listaProdutos) {
			System.out.println(pr);
		}
		
		System.out.println("Deseja realizar alguma ação?");
		System.out.println("1- Atualizar o preço de um produto");
		System.out.println("2- Atualizar a quantidade em estoque");
		System.out.println("3- Remover um produto do restaurante");
		System.out.println("4- Voltar ao menu anterior");
		
		do {
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
			}
			
			//acesso as opções do menu para o usuário		
			switch (option) {
				case 1:
					this.atualizarPreco(cnpj);
					return;
					
				case 2:
					this.atualizarQuantidadeEstoque(cnpj);
					return;
					
				case 3:
					this.removerProduto(cnpj);
					return;
				case 4:
					System.out.println("Voltando ao menu principal");
					return;
					
				default: 
					System.out.print("Opção inválida, tente novamente (1-4): ");
			}

			
		} while (option != 4);
	}
	
	/**
	 * Método atualizarPreco
	 * Atualiza o preço de um produto do restaurante em sessão
	 * @param cnpj do restaurante
	 */
	private void atualizarPreco(String cnpj) {
		System.out.println("Não implementado");
	}
	
	private void atualizarQuantidadeEstoque(String cnpj) {
		System.out.println("Não implementado");
	}
	
	/**
	 * Permite que o restaurante informe o código de um produto e o remova do seu catálogo
	 * @param cnpj do restaurante em sessão
	 */
	private void removerProduto(String cnpj) {
		int codigo;
		System.out.print("Insira o código do produto que você deseja remover do restaurante: ");
		
		//campo para validação do código do produto
		while (true) {
	
		    try {	
		    	codigo = sc.nextInt();
				sc.nextLine();
				    
		        servicoprodutorestaurante.validarCodigoProduto(codigo);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Digite um código válido: ");
		    }
		}
		
		Produto alvo = servicoproduto.buscarProdutoPorId(codigo);
		
		if (alvo != null) {
			System.out.printf("Deseja apagar o produto %s do seu restaurante? (s-sim/n-não): ",alvo.getNome());
			
			//validação da escolha do usuário
			while (true) {
				
				String opt = sc.next();
				
				if (opt.equals("s")) {
				
					//chamada do método para cadastro e verificação se houve êxito na ação
					try {
						servicoprodutorestaurante.apagarProdutoRestaurante(cnpj, codigo);
						System.out.println("Produto deletado do restaurante com sucesso!");
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
			System.out.println("Não há nenhum produto cadastrado com esse código.");
			return;
		}
		
		
	}
}

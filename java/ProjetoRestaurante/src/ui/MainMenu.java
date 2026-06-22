package ui;

import java.sql.Connection;
import java.util.Scanner;

import ui.cliente.MenuCliente;
import ui.restaurante.MenuRestaurante;

/**
 * Classe: MenuPrincipal
 *
 * Descrição:
 * Classe responsável por exibir o menu principal de interação do usuário com o sistema
 *
 * Responsabilidades:
 * - Exibir opções de acesso para Clientes e Restaurantes
 * - Fazer manipulações com os dados
 *
 * @author Rodrigo
 * @since 23-04-2026
 */

public class MenuPrincipal {
	
	private Scanner sc;
	private Connection conn;
	
	/**
	 * 
	 * @param conn
	 */
	public MenuPrincipal(Connection conn, Scanner sc) {
		this.conn = conn;
		this.sc = sc;
	}
	
	/**
	 * Método mostrar
	 * 
	 * Responsável por exibir o menu na tela para os usuários do sistema
	 * sejam Clientes ou Restaurantes
	 * 
	 */
	public void mostrar() {
		
		int option = 9;
		
		//validação da entrada de opção pelo usuário
		while (true) {
			
			System.out.println("\nMENU PRINCIPAL");
			System.out.println("================================================");
			System.out.println("Fred Food - Delivery");
			System.out.println("Seja bem vindo ao nosso sistema!\n");
			
			System.out.println("1- Acessar menu para clientes");
			System.out.println("2- Acessar menu para restaurantes");
			System.out.println("3- Sair do sistema");
			System.out.println("================================================\n");
			System.out.print("Informe a ação desejada: ");
			
			try {
				
				option = sc.nextInt();
				
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
					MenuCliente menucliente = new MenuCliente(conn, sc);
					menucliente.mostrarMenuPrincipal();
					break;
	
				case 2:
					MenuRestaurante menurestaurante = new MenuRestaurante(conn, sc);
					menurestaurante.mostrarMenuPrincipal();
					break;
					
				case 3:
					System.out.println("Obrigado por utilizar o Fred Food Delivery");
					return;
			}

		}
				
	}
	
}

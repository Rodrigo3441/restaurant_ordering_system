package application;

import java.sql.Connection;
import java.util.Scanner;
import database.DatabaseConnection;
import ui.MenuPrincipal;
import java.util.Locale;

/**
 * Classe: Programa
 *
 * Descrição:
 * Classe principal responsável por apenas iniciar a aplicação
 *
 * Responsabilidades:
 * instanciar a conexão do banco de dados e o scanner usado ao longo de toda a aplicação
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class Programa {

	public static void main(String[] args) {
		
		//definição dos caracteres para padrão dos Estados Unidos
		Locale.setDefault(Locale.US);
		
		//Conexão com o banco de dados usada ao longo de toda a aplicação
		Connection conn = DatabaseConnection.getConnection();
		
		//Scanner usado ao longo de toda a aplicação
		Scanner sc = new Scanner(System.in);
		
		//Instanciação do menu principal para o início da aplicação
		MenuPrincipal mn = new MenuPrincipal(conn, sc);
		mn.mostrar();
		
		//fecha o scanner ao término de execução da aplicação
		sc.close();
	}
}

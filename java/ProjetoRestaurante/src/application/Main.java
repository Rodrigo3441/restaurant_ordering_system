package application;

import java.sql.Connection;
import java.util.Scanner;
import database.DatabaseConnection;
import ui.MainMenu;
import java.util.Locale;

/**
 * Class: Main
 *
 * Description:
 * Main class responsible only for starting the application
 *
 * Responsibilities:
 * instantiate the database connection and the scanner used throughout the application
 *
 * @author Rodrigo
 * @since 24-04-2026
 */

public class Main {

	public static void main(String[] args) {
		
		// set locale characters to US standard
		Locale.setDefault(Locale.US);
		
		// database connection used throughout the application
		Connection conn = DatabaseConnection.getConnection();
		
		// scanner used throughout the application
		Scanner sc = new Scanner(System.in);
		
		// instantiate the main menu for application start
		MainMenu mainMenu = new MainMenu(conn, sc);
		mainMenu.mostrar();
		
		// close the scanner when application execution ends
		sc.close();
	}
}

package ui.customer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Customer;
import entities.Restaurant;
import services.OrderService;
import services.RestaurantProductService;
import view.OrderItemView;
import view.RestaurantProductView;

/**
 * Class: ProductSelectionMenu
 *
 * Description:
 * Class responsible for providing the interface for the customer to choose
 * the products they want to buy
 *
 * Responsibilities:
 * - provide interactive menus for the restaurant
 *
 * @author Rodrigo
 * @since 04-05-2026
 */

public class ProductSelectionMenu {
	
	private OrderService orderService;
	private RestaurantProductService restaurantProductsService;
	private Connection conn;
	private Scanner sc;

	public ProductSelectionMenu(Connection conn, Scanner sc) {
		this.orderService = new OrderService(conn);
		this.restaurantProductsService = new RestaurantProductService(conn);
		this.conn = conn;
		this.sc = sc;
	}
	
	
	/**
	 * Displays all products from the restaurant chosen by the user and allows them
	 * to add these products to their shopping cart
	 * @param restaurant Restaurant object
	 * @param customer Customer object
	 */
	public void displayRestaurantProducts(Restaurant restaurant, Customer customer) {

		// Stores all products offered by the restaurant selected by the customer
		ArrayList<RestaurantProductView> productList = restaurantProductsService.returnAllProductsPerRestaurant(restaurant.getId());
		
		// Stores all products that the user has added to the shopping cart
		ArrayList<OrderItemView> cart = new ArrayList<OrderItemView>();
		
		// Stop if the restaurant has no products registered
		if (productList.isEmpty()) {
			System.out.println("ERROR: There is no products registered for this restaurant!");
			return;
		}
		
		// Loop that allows the user to add multiple products to the cart
		while (true) {
			
			// Prints the current user's shopping cart
			System.out.println("\n\nYOUR CART");
			System.out.println("============================================================================");
			
			if (cart.isEmpty()) {
				System.out.println("Your cart is empty!");
			} else {
				// Prints all products with an index enumerating each item
				for (int i = 0; i < cart.size(); i++) {
					System.out.println(i+1 + "- " + cart.get(i));
				}
			}
			System.out.print("============================================================================\n\n");
			
			System.out.printf("RESTAURANT PRODUCTS %s:\n", restaurant.getName());
			System.out.println("============================================================================");
			
			for (int i = 0; i < productList.size(); i++) {
				System.out.println(i+1 + "- " + productList.get(i));
			}
			System.out.print("============================================================================\n\n");
			
			
			int userChoice = this.displayChoiceMenu();
			
			switch (userChoice) {
				case 1:
					this.chooseOrderItem(productList, cart);
					break;
				case 2:
					this.removeOrderItem(cart);
					break;
				case 3:
					// Access the details menu if the cart is not empty
					if (cart.isEmpty()) {
						System.out.println("ERROR: Your cart is empty!");
						break;
					}
					
					OrderConfirmationMenu orderConfirmationMenu = new OrderConfirmationMenu(conn, sc);
					
					// Calls the method and stores whether the order was placed or not
					boolean isOrderPlace = orderConfirmationMenu.displayOrderDetails(restaurant, customer, cart);
					
					// If it was placed, this method will return here
					if (isOrderPlace) {
						return;
					}					
					
					break;
				case 4:
					System.out.print("Order cancelled, press any key to continue: ");
					sc.nextLine();
					return;
			}
			
		}
		
	}
	
	/**
	 * Displays the available action menu for the order screen
	 * @return desired action number
	 */
	private int displayChoiceMenu() {
		int option;
	
		System.out.println("================================================");
		System.out.println("1- Add product to the cart");
		System.out.println("2- Remove product from the cart");
		System.out.println("3- Finish order");
		System.out.println("4- Cancel order");
		System.out.println("================================================\n");
		
		System.out.print("Select what you want to do: ");
			
		while (true) {
			try {
				option = sc.nextInt();
				sc.nextLine();
				
				// Check if the user's option is outside the allowed range
				if (option >= 1 && option <= 4) {
					// Return the action selected by the user
					return option; 
				} else {
					System.out.println("Enter a valid option: ");
				}
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("Enter only numbers: ");
				option = -1;
			}
		}
		
	}
	
	/**
	 * Allows the user to choose a product by index to add to the shopping cart
	 * @param productList list of all products from the selected restaurant
	 * @param cart all products that the user has already chosen
	 */
	private void chooseOrderItem(
			ArrayList<RestaurantProductView> productList, 
			ArrayList<OrderItemView> cart) {
		
		int index;		  //product index selected by the user
		
		System.out.println("Enter the index of the product you want to add to the cart");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
				index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
			    
		        orderService.checkIndex(productList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		// store the chosen product only at the moment of adding it to the cart
		RestaurantProductView produtoTemp = productList.get(index);
		
		// check to prevent selecting a product with no stock
		if (produtoTemp.getStockAmount() == 0) {
			System.out.println("This product has no stock!");
			return;
		}
		
		System.out.printf("Choosen product: %s\n", produtoTemp.getProductName());
		System.out.print("Do you want to add this product to your cart? (y-yes/n-no): ");
		
		// user indicates whether they confirm the action of adding it to the cart
		outer:
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
				case "y":
					// Before adding, check if the product is already in the cart
					// and store its index (or -1 if it does not exist)
					int cartExistingProduct = orderService.returnCartItemPosition(cart, produtoTemp.getProductNumber());
					
					if (cartExistingProduct != -1) {
						RestaurantProductView product = orderService.returnProductByNumber(productList, produtoTemp.getProductNumber());
						this.updateOrderItem(product, cart.get(cartExistingProduct));
					} else {
						cart.add(this.createOrderItem(produtoTemp, cart));
					}
					
					break outer;
				case "n":
					break outer;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
		
	}

	/**
	 * Prompts how many units of a product the user wants to buy
	 * @param tempProduct the product chosen by the user
	 * @param cart all products the customer chose
	 * @return OrderItemView
	 */
	private OrderItemView createOrderItem(
		RestaurantProductView tempProduct, 
		ArrayList<OrderItemView> cart
	) {
		int quantity; 
		
		System.out.printf("Enter how many units of %s you want to add to your cart: ", tempProduct.getProductName());
		
		// field for validating the quantity chosen by the user
		while (true) {
		    try {
		    	quantity  = sc.nextInt();
			    sc.nextLine();

		        orderService.checkQuantity(tempProduct, quantity);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		return orderService.createOrderItem(tempProduct, quantity);	
	}
	
	/**
	 * Updates the quantity of a product that the user already added to the cart
	 * @param product restaurant product
	 * @param item chosen by the customer
	 */
	private void updateOrderItem(
		RestaurantProductView product, 
		OrderItemView item
	) {
		int quantity;

		System.out.printf("The product %s is already in your cart. Do you want to update the quantity? (y-yes/n-no)", item.getName());
		
		// user indicates whether they want to update the product quantity in the cart
		outer:
		while (true) {
			String choice = sc.next().trim().toLowerCase();
		
			switch (choice) {
			case "y":	
				
				System.out.printf("Enter how many units of %s you want to buy: ", item.getName());
				
				// field for validating the quantity chosen by the user
				while (true) {
				    try {
				    	quantity  = sc.nextInt();
					    sc.nextLine();

				        orderService.checkQuantity(product, quantity);
				        item.setQuantity(quantity);
				        break;
				    } catch (IllegalArgumentException e) {
				        System.out.println(e.getMessage());
				    } 
				    catch (Exception e) {
				    	sc.nextLine();
				    	System.out.print("Enter a valid quantity: ");
				    }
				}
				
				break outer;
			case "n":
				break outer;
			default:
				System.out.print("Enter a valid option: ");
			}
		}

	}
	
	/**
	 * Prompts the index of the product the user wants to remove from the shopping cart
	 * @param cart user's shopping cart
	 */
	private void removeOrderItem(ArrayList<OrderItemView> cart) {
		// index of the product that will be removed from the cart
		int index; 
		
		if (cart.isEmpty()) {
			System.out.println("ERROR: There is no products in your cart!");
			return;
		}
		
		System.out.print("Enter the index of the product you want to remove from your cart: ");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
		    	
			    index--; // user sees from (1) to (N). Computer sees from (0) to (N-1)
				
		        orderService.checkIndex(cart, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		System.out.printf("Do you want to remove %s from your cart? (y-yes/n-no): ", cart.get(index).getName());
		
		// user indicates whether they want to remove the product from the cart
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "y":	
					cart.remove(index);			
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
	}
}

package ui.restaurant;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import entities.Product;
import entities.RestaurantProduct;
import entities.Restaurant;
import services.ProductService;
import services.OrderService;
import services.RestaurantProductService;
import view.RestaurantProductView;

/**
 * Class: RestaurantProductMenu
 *
 * Description:
 * Class responsible for providing an interface for the restaurant to manage its products
 *
 * Responsibilities:
 * - provide a data communication interface
 * - communicate with the service layer
 *
 * @author Rodrigo
 * @since 27-04-2026
 */

public class RestaurantProductMenu {
	
	private Scanner sc;
	private ProductService productService;
	private RestaurantProductService restaurantProductService;
	private OrderService orderService;
	
	
	public RestaurantProductMenu(Connection conn, Scanner sc) {
		this.restaurantProductService = new RestaurantProductService(conn);
		this.productService = new ProductService(conn);
		this.orderService = new OrderService(conn);
		this.sc = sc;
	}
	
	/**
	 * Display the menu for the restaurant to manage its product catalog
	 * @param restaurant restaurant object
	 */
	public void displayProductMenu(Restaurant restaurant) {
		int option = 9;
		
		// validate the user's menu option input
		while (true) {
			
			System.out.println("\nRESTAURANT PRODUCT MANAGEMENT");
			System.out.println("================================================");
			System.out.println("1- Add a new product");
			System.out.println("2- Manage added products");
			System.out.println("3- Return");
			System.out.println("================================================\n");
			
			System.out.print("Select what you want to do: ");
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
				System.out.print("Enter only numbers: ");
			}
			
			// process the menu options
			switch (option) {
				case 1:
					this.addProduct(restaurant.getId());
					break;
				case 2:
					this.manageAddedProducts(restaurant.getId());
					break;
				case 3:
					System.out.println("returning to the previous menu");
					return;
				default:
					System.out.print("Invalid option, try again: ");
				
			}

		}
		
	}
	
	/**
	 * Display the interface for the restaurant to add a product to its catalog
	 * Determines whether the product is already globally registered and should only be associated,
	 * or if it should be inserted from scratch
	 * @param id restaurant identifier in session
	 */
	private void addProduct(String id) {
		String productName;
		
		// field for validating the product name
		while (true) {
			System.out.print("Enter the name of the product you want to add to the restaurant (3-40 letters): ");
			productName = sc.nextLine().trim().toLowerCase();

		    try {
		    	productService.checkName(productName);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		Product product = productService.returnProductByName(productName);
		
		// check whether a product with the same name was returned
		if (product != null) {
			if (restaurantProductService.isProductAlreadyAdded(id, product.getNumber())) {
				System.out.println("This product is already added to the restaurant!");
				return;
			}
			
			System.out.print("This product is already registered on the global catalog. Do you want to add it to the restaurant catalog? (1-yes/2-no): ");
			
			int option = 9;
			
			// validate the decision option entered by the user
			do {
				try {
					option = sc.nextInt();
					sc.nextLine();

				} catch (Exception e) {
					sc.nextLine();
					System.out.println("Enter only numbers: ");
					option = -1;
				}
				
				// process decision options
				switch (option) {
					case 1:
						this.addProductRestaurant(product, id);
						return;
					case 2:
						System.out.println("Nothing has changed");
						return;
					default:
					    System.out.println("Enter a valid option: ");
						
				}

			} while (true);
			
		}
		
		this.addNewProduct(id, productName);
	}
		
	/**
	 * If the product name entered by the user is not found in the database,
	 * this method is used to register it globally and then associate it with the restaurant
	 * @param id
	 * @param productName
	 */
	private void addNewProduct(String id, String productName) {
		int productNumber;
		String description;
		
		// field for validating the product description
		while (true) {
			System.out.print("Enter the product description: ");
			description = sc.nextLine().trim().toLowerCase();

		    try {
		    	productService.checkDescription(description);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		// field for validating the product code
		while (true) {
			System.out.print("Enter the number of the product to add it on the global catalog (a numerical value): ");
			
		    try {
		    	productNumber = sc.nextInt();
				sc.nextLine();
				
		    	productService.checkNumber(productNumber);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Global product number: %s\n", productNumber);
		System.out.printf("Product name: %s\n", productName);
		System.out.printf("Product description: %s\n", description);
		System.out.println("================================================\n");
		
		System.out.print("Are these informations correct? (y-yes/n-no): ");
		
		// validate the user's choice
		while (true) {
			
			String opt = sc.next();
			
			if (opt.equals("y")) {
				// instantiate a new product and assign attributes
				Product product = new Product();
				
				product.setNumber(productNumber);
				product.setName(productName);
				product.setDescription(description);
				
				// call the registration method and verify success
				if(productService.addProduct(product)) {
					System.out.println("Product added to global catalog successfully!");
					
					// after registering globally, associate with the restaurant
					this.addProductRestaurant(product, id);
					
				} else {
					System.out.println("An error has occurred while trying to add the product to the global catalog.");
				}
				
				break;
				
			} else if (opt.equals("n")) {
				System.out.println("Nothing has changed");
				return;
				
			} else {
				System.out.print("Invalid option, try again: ");
			}
			
		}
	}
	
	/**
	 * Associate a product from the global catalog with a specific restaurant
	 * @param product product object
	 * @param id restaurant identifier
	 */
	private void addProductRestaurant(Product product, String id) {
		int stockAmount;
		double price;
		
		// field for validating the product stock quantity
		while (true) {
			System.out.printf("Enter the stock amount of the product %s: ", product.getName());
			
		    try {
		    	stockAmount = sc.nextInt();
				sc.nextLine();
				
		    	restaurantProductService.checkStockAmount(stockAmount);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Enter a numerical value for the stock amount");
		    }
		}
		
		// field for validating the product price
		while (true) {
			System.out.printf("Enter the price of the product %s: ", product.getName());
			
		    try {
		    	price = sc.nextDouble();
				sc.nextLine();
				
		    	restaurantProductService.checkProductPrice(price);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Enter a valid price for the product");
		    }
		}
		
		System.out.println("\nCONFIRM INFORMATIONS: ");
		System.out.println("================================================");
		System.out.printf("Global product number: %d\n", product.getNumber());
		System.out.printf("Product name: %s\n", product.getName());
		System.out.printf("Stock amount: %d\n", stockAmount);
		System.out.printf("Product price: R$ %.2f\n", price);
		System.out.println("================================================\n");
		
		System.out.print("Are these informations correct? (y-yes/n-no): ");
		
		// validate the user's choice
		while (true) {
			
			String option = sc.next();
			
			if (option.equals("y")) {
				// instantiate a new restaurant product and assign attributes
				RestaurantProduct resProduct = new RestaurantProduct();
				
				resProduct.setRestaurantId(id);
				resProduct.setProductNumber(product.getNumber());
				resProduct.setStockAmount(stockAmount);
				resProduct.setPrice(price);
				
				// call the association method and verify success
				if(restaurantProductService.addRestaurantProduct(resProduct)) {
					System.out.println("Product added to the restaurant catalog successfully!");
					return;
					
				} else {
					System.out.println("An error has occurred while trying to add the product to the restaurant catalog.");
				}
				
				break;
				
			} else if (option.equals("n")) {
				System.out.println("Nothing has changed");
				return;
				
			} else {
				System.out.print("Invalid option, try again: ");
			}
			
		}
		
	}
	
	/**
	 * Display all products associated with the restaurant in session
	 * @param id restaurant identifier
	 */
	public void manageAddedProducts(String id) {
		int option = 0;
		ArrayList<RestaurantProductView> productList = restaurantProductService.returnAllProductsPerRestaurant(id);

		// stop execution when there are no registered products
		if (productList.isEmpty()) {
			System.out.println("There are no products added in the restaurant!");
			return;
		}
		
		System.out.println("SHOWING ALL THE PRODUCTS OF THE RESTAURANT:");
		
		// print each product for that restaurant with an index
		System.out.println("\n============================================================================");
		for (int i = 0; i < productList.size(); i++) {
			System.out.println(i+1 + "- " + productList.get(i));
		}
		System.out.println("============================================================================\n");
		
		System.out.println("What do you want to do?");
		System.out.println("================================================");
		System.out.println("1- Update stock amount");
		System.out.println("2- Remove a product from restaurant");
		System.out.println("3- Return");
		System.out.println("================================================");
		
		System.out.print("Select what you want to do: ");
		
		while (true) {
			
			try {
				
				option = sc.nextInt();
				sc.nextLine();
				
			} catch (Exception e) {
				sc.nextLine();
			}
			
			// process the menu options for the user
			switch (option) {					
				case 1:
					this.updateStockAmount(id, productList);
					return;
					
				case 2:
					this.removeProduct(id);
					return;
				case 3:
					System.out.println("Returning to the previous menu");
					return;
					
				default: 
					System.out.print("Invalid option, try again: ");
			}

		}
		
	}
	
	/**
	 * Provide the interaction for the restaurant to update the stock quantity of a specific product
	 * @param id restaurant identifier
	 * @param productList all products of the restaurant
	 */
	private void updateStockAmount(String id, ArrayList<RestaurantProductView> productList) {
		int index;
		int stockAmount;
		
		System.out.println("Enter the index of the product you want to update: ");
		
		// field for validating user input
		while (true) {
		    try {
		    	index = sc.nextInt();
			    sc.nextLine();
			    
			    index--; // user sees from 1 to N, computer uses 0 to N-1
			    
		        orderService.checkIndex(productList, index);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid index: ");
		    }
		}
		
		RestaurantProductView targetProduct = productList.get(index);

		
		System.out.printf("Enter the new stock amount for the product %s: ", targetProduct.getProductName());
		
		// field for validating the product's stock quantity
		while (true) {
		
		    try {
		    	stockAmount = sc.nextInt();
				sc.nextLine();
				
		    	restaurantProductService.checkStockAmount(stockAmount);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.println("Enter a numerical value for the stock amount");
		    }
		}
		
		System.out.printf("Do you want to confirm the stock amount update for the product %s? (s-sim/n-não): ", targetProduct.getProductName());
		// the restaurant confirms whether to update the stock quantity
		while (true) {
			String escolha = sc.next().trim().toLowerCase();
		
			switch (escolha) {
				case "s":	
					if (restaurantProductService.updateProductRestaurant(id, targetProduct, stockAmount)) {
						System.out.println("Stock amount updated successfully!");
					} else {
						System.out.println("An error has occurred");
					}
					return;
				case "n":
					System.out.println("Nothing has changed");
					return;
				default:
					System.out.print("Enter a valid option: ");
			}
		}
		
		
	}
	
	/**
	 * Allow the restaurant to provide a product code and remove it from its catalog
	 * @param id restaurant identifier in session
	 */
	private void removeProduct(String id) {
		int productNumber;
		System.out.print("Enter the code of the product you want to remove from the restaurant: ");
		
		// field for validating the product code
		while (true) {
	
		    try {	
		    	productNumber = sc.nextInt();
				sc.nextLine();
			    	
		        restaurantProductService.checkProductNumber(productNumber);
		        break;
		    } catch (IllegalArgumentException e) {
		        System.out.println(e.getMessage());
		    } catch (Exception e) {
		    	sc.nextLine();
		    	System.out.print("Enter a valid code: ");
		    }
		}
		
		Product target = productService.returnProductById(productNumber);
		
		if (target != null) {
			System.out.printf("Do you want to remove the product %s from the restaurant? (y-yes/n-no): ",target.getName());
			
			// validate the user's choice
			while (true) {
				
				String option = sc.next();
				
				if (option.equals("y")) {
				
					// try to remove the product from the restaurant's catalog and verify success
					try {
						restaurantProductService.deleteProductRestaurant(id, productNumber);
						System.out.println("Product removed from the restaurant successfully!");
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					break;
					
				} else if (option.equals("n")) {
					System.out.println("Nothing has changed");
					return;
					
				} else {
					System.out.print("Invalid option, try again: ");
				}
				
			}
			
		} else {
			System.out.println("There are no products registered with this code.");
			return;
		}
		
		
	}
}

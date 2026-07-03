package entities;

/**
 * Entity: Product
 *
 * Description:
 * Represents a product registered in the system.
 *
 * Responsibilities:
 * - Store product information
 * - Maintain product identification and description
 * - Associate the product with a category
 *
 * @author Rodrigo
 * @since 20-04-2026
 */

public class Product {
	private Integer number;
	private String name;
	private String description;
	private Integer categoryId;
	
	// No-argument constructor
	public Product() {
		
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}

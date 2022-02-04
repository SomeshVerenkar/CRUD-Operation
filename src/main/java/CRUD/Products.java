package CRUD;

public class Products {  // I am using POJO approach i.e. Plain Old Java Object approach, here I will be using getters and setters constructor
//	to avoid using so many parameters 	
	
//  maintaining products object in this template
//	declaring private variables
	private String title;
	private String sku;
	private String description;
	
//	generating public constructor for private class
	public Products(String title, String sku, String description) {
		this.title = title;
		this.sku = sku;
		this.description = description;
	}

//	Creating getters and setters method so that I can fetch the respected values at the run time
//	also want to capture and update the values 
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

package CRUD;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {

	private WebDriver driver;
	
//	Maintaining By locators
	private By title = By.id("product_title");
	private By sku = By.name("product[sku]");
	private By description = By.name("product[description]");
	private By productsLink = By.linkText("Products");
	private By newProductLink = By.linkText("New Product");
	
	private By createProduct = By.name("commit");
	private By successMsg = By.cssSelector("div.flash.flash_notice");
	
	private By titleValue = By.xpath("//td[text()='nokia']");
	
	public ProductsPage(WebDriver driver) {
		this.driver = driver;
	}
	
//	to click on products link
	public void clickProductLink() {
		driver.findElement(productsLink).click();
	}
	
//	creating common method to fill the form
//	making it private because I don't want to give this method exposure to the test client
	private String fillProductForm(Products products) { //passing the object reference to get title, sku and description
		driver.findElement(title).clear();
		driver.findElement(title).sendKeys(products.getTitle());
		driver.findElement(sku).clear();
		driver.findElement(sku).sendKeys(products.getSku());
		driver.findElement(description).clear();
		driver.findElement(description).sendKeys(products.getDescription());
		driver.findElement(createProduct).click();
		return driver.findElement(successMsg).getText(); //to get successful message
	}
	
//	adding the product
//	not creating any dependency between methods
	public String addProduct(Products products) {
		clickProductLink();
		driver.findElement(newProductLink).click();
		return fillProductForm(products);
	}
	
//	updating some details
	public String updateProduct(Products products, String titleName) { //passing unique key
		clickProductLink();
//		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[2][text()='Edit']")).click(); //Not working, this expression took my lot of time
		driver.findElement(By.xpath("//a[text()='Edit']")).click();
		return fillProductForm(products);
	}

//		deleting the product
	public String deleteProduct(String titleName) { //passing the address to be deleted
		clickProductLink();
		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[3][text()='Delete']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);  //waiting for pop-up to display
		wait.until(ExpectedConditions.alertIsPresent()).accept();//using explicit wait to handle pop-up
		return driver.findElement(successMsg).getText(); //validation
	}
	
//	to view the product
	public String getProduct(Products products, String titleName) {
		clickProductLink();
		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[text()='View']")).click();
		return driver.findElement(titleValue).getText(); //validation
	}
	
//	not writing assertion in this class because it is a violation of the page class
	
	
	
}

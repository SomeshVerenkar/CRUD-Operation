package CRUD;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage {
//	implementation class
	
	private WebDriver driver;
	
//	Maintaining By locators
	private By title = By.id("product_title");
	private By sku = By.name("product[sku]");
	private By description = By.name("product[description]");
	
	private By productsLink = By.linkText("Products");
//	not creating 2 separate page classes for List of products page & for new product page to avoid writing multiple classes
	private By newProductLink = By.linkText("New Product");
	
	private By createProduct = By.name("commit"); 
	private By successMsg = By.cssSelector("div.flash.flash_notice"); //for validation 
	
	private By titleValue = By.xpath("//td[text()='nokia']"); //for validation
	
//	creating constructor to get the driver
//	this class is not responsible for creating the webdriver - initialization
	public ProductsPage(WebDriver driver) { //passing webdriver ref.
		this.driver = driver; //passing driver ref. here so that I can use those things in my public page actions
	}
	
//	to click on products link
	public void clickProductLink() {
		driver.findElement(productsLink).click();
	}
	
//	creating common method to fill the form
//	making it private because I don't want to give this method exposure to the test client
	private String fillProductForm(Products products) { //passing the object reference to get title, sku and description
		driver.findElement(title).clear();//to clear the existing data before updating it
		driver.findElement(title).sendKeys(products.getTitle()); //using getter method to get the title
		
		driver.findElement(sku).clear();
		driver.findElement(sku).sendKeys(products.getSku()); //to get sku
		
		driver.findElement(description).clear();
		driver.findElement(description).sendKeys(products.getDescription()); //to get description
		
		driver.findElement(createProduct).click(); //calling this method after filling up the form to click on create
		return driver.findElement(successMsg).getText(); //to get successful message but validating this string in the TC not in page object method
//	bcoz page object method should not assert anything, it will be a violation of POM
	}
	

//	creating proper concept of encapsulation where private by locators & public page that I am going to write
//	adding the product
//	not creating any dependency between methods
//	to avoid writing so many parameters I am creating objects & passing the ref. as products
	public String addProduct(Products products) { //passing product class object to get the complete object of products class so that I can take it at the runtime
		clickProductLink(); //calling this method first
		driver.findElement(newProductLink).click();
		return fillProductForm(products);
	}
	
//	updating some details
	public String updateProduct(Products products, String titleName) { //passing unique key to know for which record I need an update
//	so inside this object products, need to know exactly what changes are required
		clickProductLink(); //initially click on it
//		to avoid passing the hard coded value, I am passing titleName on top and appending it below - doing string concatenation
//		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[2][text()='Edit']")).click(); //this pattern is not working, this expression took my lot of time
//		driver.findElements(By.className("editLink")).get(2).click();//using LIST - this is not working, need to check it 
		driver.findElement(By.xpath("//a[text()='Edit']")).click();//this is working
		return fillProductForm(products);
	}

//		deleting the product
	public String deleteProduct(String titleName) { //passing the address to be deleted, it will find on the basis of title and delete it
		clickProductLink();
		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[3][text()='Delete']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);  //waiting for pop-up to display, it will be visible after few seconds
		wait.until(ExpectedConditions.alertIsPresent()).accept();//using explicit wait to handle pop-up
		return driver.findElement(successMsg).getText(); //validation
	}
	
//	to view the product
	public String getProduct(Products products, String titleName) { //it will find on the basis of title and will view it 
		clickProductLink();
		driver.findElement(By.xpath("//td[text()='"+titleName+"']//following-sibling::td/div/a[text()='View']")).click();
		return driver.findElement(titleValue).getText(); //validation
	}
	
//	not writing assertion in this class because it is a violation of the page class	
	
}

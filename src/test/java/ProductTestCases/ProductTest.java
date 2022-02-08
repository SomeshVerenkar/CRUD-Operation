package ProductTestCases;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import CRUD.Products;
import CRUD.ProductsPage;


public class ProductTest {

	private WebDriver driver;
	private ProductsPage productsPage; 	//to call addProduct method, creating products page class

	
//	method #1 - setup method
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\selenium drivers\\chromedriver.exe");		
		driver = new ChromeDriver ();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("CANNOT PROVIDE THE URL - CONFIDENTIAL");
		driver.findElement(By.id("admin_user_email")).sendKeys("admin@example.com");
		driver.findElement(By.id("admin_user_password")).sendKeys("password");
		driver.findElement(By.name("commit")).click();
		productsPage = new ProductsPage(driver);
	}
	
//	not creating any dependency between methods
//	@Parameters //build a connection b/w TC & testNG class by passing the address of parameter inside name attribute in testNG class
	@Test
	public void createProductTest() {
//	creating products class object & passing test data
		Products products = new Products("COVID19", "Danger", "Get Vaccinated"); //hard coding the data here to avoid external resources, POJO class is applicable here
		String successMsg = productsPage.addProduct(products); //creating object to call all the methods of the products page
		Assert.assertEquals(successMsg, "Product was successfully created."); //Passed - without assertion test is not complete
	

	}
	
	@Test()
	public void updateProductTest() {
		Products products = new Products("samsung", "china", "black"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		products.setTitle("Laptop");//using setter method to update the specific entry
		products.setSku("india");
		String successMsg = productsPage.updateProduct(products, products.getTitle());//using products.getTitle method to avoid hard coded value
		Assert.assertEquals(successMsg, "Product was successfully updated."); //Passed
	}
	
	@Test()
	public void deleteProductTest() {
		Products products = new Products("xiomi", "pakistan", "white"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		String successMsg = productsPage.deleteProduct("xiomi");
		Assert.assertEquals(successMsg, "Product was successfully destroyed."); //Passed
	}
	
//	to retrieve the product
	@Test()
	public void viewProductsTest() {
		Products products = new Products("nokia", "USA", "blue"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		String actualTitle = productsPage.getProduct(products, "nokia");
		Assert.assertEquals(actualTitle, products.getTitle()); //Passed - also can be used hard coded data here
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}	
}

package ProductTestCases;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
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
		driver.get("https://somesh-verenkar.herokuapp.com/admin/login");
		driver.findElement(By.id("admin_user_email")).sendKeys("admin@example.com");
		driver.findElement(By.id("admin_user_password")).sendKeys("password");
		driver.findElement(By.name("commit")).click();
		productsPage = new ProductsPage(driver);
	}
	
//	not creating any dependency between methods
	@Test
	public void createProductTest() {
		Products products = new Products("COVID19", "Danger", "Get Vaccinated"); //hard coding the data here to avoid external resources
		String successMsg = productsPage.addProduct(products); //calling all the methods of the products page
		Assert.assertEquals(successMsg, "Product was successfully created."); //Passed
	}
	
	@Test
	public void updateProductTest() {
		Products products = new Products("samsung", "china", "black"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		products.setTitle("Laptop");
		products.setSku("india");
		String successMsg = productsPage.updateProduct(products, products.getTitle());
		Assert.assertEquals(successMsg, "Product was successfully updated."); //Passed
	}
	
	@Test
	public void deleteProductTest() {
		Products products = new Products("xiomi", "pakistan", "white"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		String successMsg = productsPage.deleteProduct("xiomi");
		Assert.assertEquals(successMsg, "Product was successfully destroyed."); //Passed
	}
	
	@Test
	public void viewProductsTest() {
		Products products = new Products("nokia", "USA", "blue"); //hard coding the data here to avoid external resources
		productsPage.addProduct(products); //calling all the methods of the products page
		String actualTitle = productsPage.getProduct(products, "nokia");
		Assert.assertEquals(actualTitle, products.getTitle()); //Passed
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}	
}

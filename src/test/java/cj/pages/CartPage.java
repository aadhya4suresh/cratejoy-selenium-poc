package cj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cj.locators.CratejoyLocators;
import cj.utilities.SafeActions;

public class CartPage extends SafeActions implements CratejoyLocators {
	private WebDriver driver;
	String cartPrice;
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	public void clickOnAddToCartButton() {
		waitForPageToLoad();
		waitUntilVisible(ADD_TO_CART_BUTTON, MEDIUMWAIT);
		waitUntilClickable(ADD_TO_CART_BUTTON, MEDIUMWAIT);
		safeJavaScriptClick(ADD_TO_CART_BUTTON, MEDIUMWAIT);
		waitForPageToLoad();
	}
	
	public String getThePriceFromCart() {
		waitForPageToLoad();
		By cPrcie = By.xpath("(//div[@id='cart-products']//descendant::span)[7]");
		cartPrice = safeGetText(cPrcie, MEDIUMWAIT);
		System.out.println("Price On CART---->" + cartPrice);
		return cartPrice;
	}
	
	public void verifyThatProductAndCartPriceAreSame(String cartPrcie, String planPrice2) {
		waitForPageToLoad();
		Assert.assertEquals(cartPrcie, planPrice2, "Cart Product Price is not matcing with Plan Price");
		waitForPageToLoad();

	}
}

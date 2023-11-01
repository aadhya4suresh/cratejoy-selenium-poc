package cj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cj.locators.CratejoyLocators;
import cj.utilities.SafeActions;

public class CheckOutPage extends SafeActions implements CratejoyLocators {
	private WebDriver driver;
	String cartPrice;
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public void clickOnSecureCheckOut() throws InterruptedException {
		waitForPageToLoad();
		waitUntilVisible(SECURE_CHECKOUT_BUTTON, MEDIUMWAIT);
		waitUntilClickable(SECURE_CHECKOUT_BUTTON, MEDIUMWAIT);
		safeJavaScriptClick(SECURE_CHECKOUT_BUTTON, MEDIUMWAIT);
		waitForPageToLoad();
	}
	public String getThePriceFromCheckoutSection() {
		waitForPageToLoad();
		By pp = By.xpath("//div[@class='order-summary__section__content']");
		String text = safeGetText(pp, MEDIUMWAIT);
		String pText = text.substring(text.indexOf('$'));
		System.out.println("Price on Checkout---->" + pText);
		return pText;
	}
	
	public void verifyThatCartProductPriceAndCheckOutProductPriceAreSame(String cartPrcie, String checkOutPrcie) {
		waitForPageToLoad();
		Assert.assertEquals(cartPrcie, checkOutPrcie, "Cart Product Price is not matcing with Checkout Product Price");
		waitForPageToLoad();

	}

}

package cj.testSuite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cj.pages.BestSellerPage;
import cj.pages.CartPage;
import cj.pages.CheckOutPage;
import cj.pages.DashboardPage;
import cj.pages.ProductPage;
import cj.utilities.BaseSetup;
import cj.utilities.ConfigManager;
import cj.utilities.CustomLogger;

public class CratejoyTestCases extends BaseSetup {

	DashboardPage dashBoardPage;
	BestSellerPage bsPage;
	ProductPage productpage;
	CartPage cartPage;
	CheckOutPage cOutPage;

	static ConfigManager app = new ConfigManager("App");
	String username, password;
	String filePath;
	String productText;
	String actProductName;
	String productTextAfterClick;
	String months;
	String planPrice;
	String cartPrice;
	String checkOutPrice;

	@BeforeMethod
	public void intialize() throws Exception {
		getDriver().manage().deleteAllCookies();
		getDriver().get(app.getProperty("AppURL"));
		dashBoardPage = new DashboardPage(getDriver());
		bsPage = new BestSellerPage(getDriver());
		productpage = new ProductPage(getDriver());
		cartPage  = new CartPage(getDriver());
		cOutPage  = new CheckOutPage(getDriver());
		 CustomLogger.logInfo("Test started");
	}

	@Test
	public void TC_0001_verifyThatBestSellersFlow() throws InterruptedException {
		productText = app.getProperty("ProductName");
		months = app.getProperty("Months");
		dashBoardPage.verifyThatCratejoyPageIsDisplayed();
		CustomLogger.logInfo("Cratejoy Page is displayed");
		dashBoardPage.closeTheDiscountPupoup();
		CustomLogger.logInfo("Discount Popup is closed");
		bsPage.verifyThatBestSellersLinkIsDisplayed();
		CustomLogger.logInfo("Best seller link is displayed");
		bsPage.clickOnBestSellersLink();
		CustomLogger.logInfo("Best seller link is clicked");
		bsPage.verifyThatBestSellerPageIsDisplayed();
		CustomLogger.logInfo("Best seller Page is displayed");
		actProductName = productpage.getProductText(productText);
		CustomLogger.logInfo("Product Text is captured from the Product page");
		productpage.clickOnProductLink(productText);
		CustomLogger.logInfo("Clicked on the Product link");
		productpage.selectThePlanFromChooseTerm(months);
		CustomLogger.logInfo("1 Month plan is selected");
		planPrice = productpage.getPlanPrice(months);
		CustomLogger.logInfo("Captured the Price from the Plan page");
		productTextAfterClick = productpage.getTextAfterClickOnProduct(productText);
		CustomLogger.logInfo("Captured the Text after click on the Product link");
		productpage.verifyThatCorrectProductIsDisplayed(productTextAfterClick, actProductName);
		CustomLogger.logInfo("Selected product verified successfully");
		cartPage.clickOnAddToCartButton();
		CustomLogger.logInfo("Clicked on Add To Cart button");
		cartPrice = cartPage.getThePriceFromCart();
		CustomLogger.logInfo("Captured the Price from the Cart page");
		cartPage.verifyThatProductAndCartPriceAreSame(cartPrice, planPrice);
		CustomLogger.logInfo("Item Added to the Cart successfully");
		cOutPage.clickOnSecureCheckOut();
		CustomLogger.logInfo("Clicked on Secured checkout button");
		checkOutPrice = cOutPage.getThePriceFromCheckoutSection();
		CustomLogger.logInfo("Captured the Price from the Checkout page");
		cOutPage.verifyThatCartProductPriceAndCheckOutProductPriceAreSame(cartPrice, checkOutPrice);
		CustomLogger.logInfo("Item Added to the Checkout page successfully");

	}
}

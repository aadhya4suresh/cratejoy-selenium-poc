package cj.pages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cj.locators.CratejoyLocators;
import cj.utilities.SafeActions;

public class BestSellerPage  extends SafeActions implements CratejoyLocators{
	private WebDriver driver;


	public BestSellerPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

public void verifyThatBestSellersLinkIsDisplayed() {
	waitForPageToLoad();
	Assert.assertTrue(isElementVisible(BEST_SELLERS_LINK, MEDIUMWAIT), "Best Seller link is not Displayed");
	waitForPageToLoad();
}

public void clickOnBestSellersLink() {
	waitForPageToLoad();
	waitUntilVisible(BEST_SELLERS_LINK, MEDIUMWAIT);
	waitUntilClickable(BEST_SELLERS_LINK, MEDIUMWAIT);
	safeJavaScriptClick(BEST_SELLERS_LINK, MEDIUMWAIT);
	waitForPageToLoad();
}

public void verifyThatBestSellerPageIsDisplayed() {
	waitForPageToLoad();
	Assert.assertTrue(isElementVisible(BEST_SELLERS_PAGE, MEDIUMWAIT), "Best Seller Page is not Displayed");
	waitForPageToLoad();
}
}
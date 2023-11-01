package cj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cj.locators.CratejoyLocators;
import cj.utilities.SafeActions;

public class DashboardPage extends SafeActions implements CratejoyLocators {
	private WebDriver driver;
	String productLink;
	String AproductLink;
	String planPrice;
	String cartPrice;
	String checkoutPrice;

	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void verifyThatCratejoyPageIsDisplayed() throws InterruptedException {
		waitForPageToLoad();
		Assert.assertTrue(isElementVisible(CRATEJOY_LOGO, MEDIUMWAIT), "Cratejoy Page is not Displayed");
		waitForPageToLoad();
	}

	public void closeTheDiscountPupoup() throws InterruptedException {
		waitForPageToLoad();
		waitUntilVisible(DISCOUNT_POPUP, MEDIUMWAIT);
		waitUntilClickable(DISCOUNT_POPUP, MEDIUMWAIT);
		safeJavaScriptClick(DISCOUNT_POPUP, MEDIUMWAIT);
		waitForPageToLoad();
	}


}
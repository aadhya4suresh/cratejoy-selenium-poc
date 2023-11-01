package cj.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import cj.locators.CratejoyLocators;
import cj.utilities.SafeActions;

public class ProductPage extends SafeActions implements CratejoyLocators{ 
	private WebDriver driver;
	String productLink;
	String planPrice;
	String AproductLink;
	public ProductPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public String getProductText(String productText) {
		By pLink = By.xpath("//a[normalize-space(text())='" + productText + "']");
		productLink = safeGetText(pLink, MEDIUMWAIT);
		System.out.println(productLink);
		return productLink;
	}
	
	public void clickOnProductLink(String productText) {
		waitForPageToLoad();
		By pLink = By.xpath("//a[normalize-space(text())='" + productText + "']");
		safeClick(pLink, MEDIUMWAIT);
		waitForPageToLoad();
	}

	public void selectThePlanFromChooseTerm(String months) {
		waitForPageToLoad();
		By selectPlan = By.xpath("//h6[normalize-space(text())='" + months + "']");
		safeClick(selectPlan, MEDIUMWAIT);
		waitForPageToLoad();
	}

	public String getPlanPrice(String months) {
		waitForPageToLoad();
		By pPrcie = By.xpath("//h6[normalize-space(text())='" + months + "']//ancestor::label//descendant::span");
		planPrice = safeGetText(pPrcie, MEDIUMWAIT);
		System.out.println("Plan Price1---->" + planPrice);
		return planPrice;
	}
	
	public String getTextAfterClickOnProduct(String productText) {
		waitForPageToLoad();
		By pLink = By.xpath("(//h1[normalize-space(text())='" + productText + "'])[2]");
		AproductLink = safeGetText(pLink, MEDIUMWAIT);
		System.out.println(AproductLink);
		return AproductLink;
	}

	public void verifyThatCorrectProductIsDisplayed(String productText, String actProductName) {
		waitForPageToLoad();
		Assert.assertEquals(productText, actProductName, "Selected Product is not Matched with Displayed Product");
		waitForPageToLoad();
	}


}

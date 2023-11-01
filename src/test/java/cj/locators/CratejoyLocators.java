package cj.locators;

import org.openqa.selenium.By;

public interface CratejoyLocators {
	By CRATEJOY_LOGO = By.xpath("//a[contains(@class,'header-logo')]");	
	By DISCOUNT_POPUP = By.xpath("//button[@aria-label='Close dialog 1']");
	By BEST_SELLERS_LINK = By.xpath("//a[normalize-space(text())='Best Sellers']");
	By BEST_SELLERS_PAGE = By.xpath("//h1[contains(text(),'Bestsellers')]");
	By ADD_TO_CART_BUTTON = By.id("add-to-cart");
	By SECURE_CHECKOUT_BUTTON = By.xpath("//a[normalize-space(text())='Secure Checkout']");
	By DISCOUNT_CODE_INPUT_FIELD= By.id("checkout_reduction_code");
	}

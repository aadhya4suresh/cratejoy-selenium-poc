package cj.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v95.network.Network;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseSetup extends TestngListener implements TimeOuts {
	public WebDriver driver;

	static String fileSeperator = System.getProperty("file.separator");

	/*
	 * WebDriver Setup method which helps to launch respective browser for test per
	 * given parameters
	 * 
	 * @param BrowserName - Browser name
	 * 
	 * @param BrowserVersion - Browser version
	 * 
	 * @param OSPlatform - OS platform
	 * 
	 * @param Environment - Execution environment i.e. local or grid
	 */
	@Parameters("BrowserName")
	@BeforeClass(alwaysRun = true)
	public WebDriver setUp(@Optional("firefox") String BrowserName, ITestContext context) {
		try {
			// For launching Firefox browser
			if (BrowserName.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + fileSeperator
						+ "resources" + fileSeperator + "geckodriver.exe");
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				// FirefoxOptions
				firefoxProfile.setPreference("app.update.enabled", false);
				firefoxProfile.setPreference("browser.download.folderList", 2);
				firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				firefoxProfile.setPreference("browser.download.dir", getDownloadLocation());
				firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
						"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
				firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"application/pdf, application/x-pdf, application/acrobat, applications/vnd.pdf, text/pdf, text/x-pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/x-rar-compressed, application/zip");
				firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
				// firefoxProfile.setPreference("browser.download.forbid_open_with", true);
				FirefoxOptions fp = new FirefoxOptions();
				fp.setProfile(firefoxProfile);
				driver = new FirefoxDriver(fp);
				Reporter.log(
						"--------------------------- Launching Firefox Browser on Local machine --------------------------- ");
				System.out.println(
						"---------------------------  Launching Firefox Browser on Local machine --------------------------- ");
			}

			// for launching Google Chrome browser
			else if (BrowserName.equalsIgnoreCase("chrome")) {
				String operSys = System.getProperty("os.name").toLowerCase();
				if (operSys.contains("win")) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
							+ "resources" + fileSeperator + "chromedriver.exe");
				}
				if (operSys.contains("mac")) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + fileSeperator
							+ "resources" + fileSeperator + "chromedriver");
				}
				// driver = new ChromeDriver();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--incognito");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("download.default_directory", getDownloadLocation());
				prefs.put("download.prompt_for_download", false);
				options.setExperimentalOption("prefs", prefs);
				// options.setBinary("C:\\Desktop\\DART\\dart\\chromium\\chrome.exe");

				options.addArguments("--start-maximized");
				options.addArguments("--test-type");
				driver = new ChromeDriver(options);

				Reporter.log(
						"--------------------------- Launching Chrome Browser on Local machine -----------------------");
				System.out.println(
						"--------------------------- Launching Chrome Browser on Local machine --------------------------- ");

			} else if (BrowserName.equalsIgnoreCase("InternetExplorer")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + fileSeperator + "resources" + fileSeperator + "iedriver.exe");
				driver = new InternetExplorerDriver();
				Reporter.log(
						"--------------------------- Launching Internet Explorer on Local machine -----------------------");
				System.out.println(
						"--------------------------- Launching Internet Explorer on Local machine --------------------------- ");
			}
			context.setAttribute("driver", driver);
			driver.manage().timeouts().implicitlyWait(IMPLICITWAIT, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			System.out.println("from browser class:" + driver);
		} catch (Exception e) {
			Assert.fail("Exception occured while invoking browser, Exception message:" + e.getMessage());
		}
		return driver;
	}

	/*
	 * WebDriver tear down method which closes browser after each test
	 */
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			Assert.fail("Exception occured while closing browser instance, exception message:" + e.getMessage());
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static String getDownloadLocation() {
		String DownloadPath = System.getProperty("user.dir") + fileSeperator + "DownloadedFiles";
		return DownloadPath;
	}

	public void networkResponse() {
		DevTools devTool = ((ChromeDriver) driver).getDevTools();
		devTool.createSession();

		devTool.send(
				Network.enable(java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty()));
		devTool.addListener(Network.responseReceived(), responseReceieved -> {

			System.out.println("Response Url => " + responseReceieved.getResponse().getUrl());

			System.out.println("Response Status => " + responseReceieved.getResponse().getStatus());

			System.out.println("Response Headers => " + responseReceieved.getResponse().getHeaders().toString());

			System.out.println("Response MIME Type => " + responseReceieved.getResponse().getMimeType().toString());

			System.out.println("------------------------------------------------------");

		});
	}

}

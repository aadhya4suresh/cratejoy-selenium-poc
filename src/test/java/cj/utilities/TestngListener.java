package cj.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestngListener extends TestListenerAdapter {
	private static ExtentReports extent = ExtentManager.getInstance();
	// private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	private static ExtentTest test;

	private static char cQuote = '"';
	ConfigManager sys = new ConfigManager();
	private static String fileSeperator = System.getProperty("file.separator");
	Logger log = Logger.getLogger("TestListener");

	/**
	 * This method will be called if a test case is failed. Purpose - For attaching
	 * captured screenshots and videos in ReportNG report
	 */
	public synchronized void onTestFailure(ITestResult result) {
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		log.error("ERROR ----------" + result.getName() + " has failed-----------------");
		log.error("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\n");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		// String imagepath =
		// ExtentManager.getReportsScreenShotDirectoryPath()+fileSeperator +
		// saveScreenShot(driver);
		String imagepath = "Screenshots" + fileSeperator + saveScreenShot(driver);
		System.out.println((result.getMethod().getMethodName() + " failed!"));
		try {
			test.fail(result.getThrowable()).addScreenCaptureFromPath(imagepath);
		} catch (IOException e) {
			System.out.println("Failed to update fail status or screenshot to report:" + e.getMessage());
		}
		;
	}

	/**
	 * This method will be called if a test case is skipped.
	 * 
	 */
	public synchronized void onTestSkipped(ITestResult result) {
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log.warn("WARN ------------" + result.getName() + " has skipped-----------------");
		log.warn("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		// ************* comment below code if you are using TestNG dependency methods

		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.skip(result.getThrowable());
	}

	/**
	 * This method will be called if a test case is passed. Purpose - For attaching
	 * captured videos in ReportNG report
	 */
	public synchronized void onTestSuccess(ITestResult result) {
		log.info("###############################################################");
		log.info("SUCCESS ---------" + result.getName() + " has passed-----------------");
		log.info("###############################################################");
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.pass("Test passed");
	}

	/**
	 * This method will be called before a test case is executed. Purpose - For
	 * starting video capture and launching balloon popup in ReportNG report
	 */
	public synchronized void onTestStart(ITestResult result) {
		log.info("<h2>**************CURRENTLY RUNNING TEST************ " + result.getName() + "</h2>");
		System.out.println((result.getMethod().getMethodName() + " started!"));
		// ExtentTest extentTest =
		// extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		// test.set(extentTest);
		test = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
	}

	public synchronized void onStart(ITestContext context) {

	}

	public synchronized void onFinish(ITestContext context) {
		extent.flush();
		String zipFileName = System.getProperty("user.dir") + fileSeperator + "TestReports" + fileSeperator
				+ "LatestAutomationReport.zip";
		File f = new File(zipFileName);
		if (f.exists()) {
			f.delete();
		} 
		try {
			zipLatestAutomationReportFolder(zipFileName);
		} catch (Exception e) {
			System.out.println("Exception message while zipping the file");
		}

	}

	public String saveScreenShot(WebDriver driver) {
		try {
			log.info("Saving screenshot of current browser window");
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenShotName = "Screenshot" + UtilityMethods.getRandomNumber() + ".png";
			File targetFile = new File(ExtentManager.getReportsScreenShotDirectoryPath(), screenShotName);
			// FileUtils.copyFile(screenshotFile,targetFile );
			// String path = targetFile.getAbsolutePath();
			Files.copy(screenshotFile.toPath(), targetFile.toPath());
			log.info("Screenshot created and : " + screenShotName);
			return screenShotName;
		} catch (Exception e) {
			log.error("An exception occured while saving screenshot of current browser window.." + e.getMessage());
			return null;
		}
	}

	public void zipLatestAutomationReportFolder(String zipFileName) throws Exception {
		String folderToZip = ExtentManager.getReportsDirectoryPath();
		zipFolder(Paths.get(folderToZip), Paths.get(zipFileName));
	}

	private void zipFolder(final Path sourceFolderPath, final Path zipPath) throws Exception {
		final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
		Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
				Files.copy(file, zos);
				zos.closeEntry();
				return FileVisitResult.CONTINUE;
			}
		});
		zos.close();
	}

	public ExtentTest getExtentTestInstance() {
		return test;
	}

}

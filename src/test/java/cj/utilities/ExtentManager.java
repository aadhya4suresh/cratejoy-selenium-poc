package cj.utilities;

import java.io.File;

import org.openqa.selenium.Platform;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
	static String randomNumber = UtilityMethods.getCurrentDateTime("dd_MMM_yyyy_HH_mm_ss");
	private static String fileSeperator = System.getProperty("file.separator");
    private static ExtentReports extent;
    private static Platform platform;
    private static String reportFileName = "Test-Automaton-Report.html";
    private static String reportDirPath = System.getProperty("user.dir")+fileSeperator+"TestReports"+fileSeperator+randomNumber; 
    private static String reportScreenShotDirPath = System.getProperty("user.dir")+fileSeperator+"TestReports"+fileSeperator+randomNumber+fileSeperator+"Screenshots";
    private static String ReportFileLoc = reportDirPath +fileSeperator+ reportFileName;
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("HIHFS Automation Report");
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName("HIHFS Automation Report");
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
 
        return extent;
    }
 
    //Select the extent report file location based on platform
    private static String getReportFileLocation (Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC:
                reportFileLocation = ReportFileLoc;
                createReportPath(reportScreenShotDirPath);
                System.out.println("ExtentReport Path for MAC: " + reportScreenShotDirPath + "\n");
                break;
            case WINDOWS:
                reportFileLocation = ReportFileLoc;
                createReportPath(reportScreenShotDirPath);
                System.out.println("ExtentReport Path for WINDOWS: " + reportScreenShotDirPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }
 
    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdirs()) {
                System.out.println("Directory: " + path + " is created!" );
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }
 
    //Get current platform
    private static Platform getCurrentPlatform () {
        if (platform == null) {
            String operSys = System.getProperty("os.name").toLowerCase();
            if (operSys.contains("win")) {
                platform = Platform.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }
    
    public static String getReportsScreenShotDirectoryPath() {
    	return reportScreenShotDirPath;
    }
    
    public static String getReportsDirectoryPath() {
    	return reportDirPath;
    }
}

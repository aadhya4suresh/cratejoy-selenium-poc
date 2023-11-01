/*************************************** PURPOSE **********************************

 - This class contains all Generic methods which are not related to specific application
*/

package cj.utilities;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.swing.JOptionPane;

//import nl.flotsam.xeger.Xeger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.omg.PortableInterceptor.INACTIVE;
import org.testng.Assert;

public class UtilityMethods {

	static ConfigManager sys = new ConfigManager();
	static Logger log = Logger.getLogger("UtilityMethods");
	static Thread thread;

	/**
	 * Purpose - to get the system name
	 * 
	 * @return - String (returns system name)
	 */

	public static String machineName() {
		String sComputername = null;
		try {
			sComputername = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.error("Unable to get the hostname..." + e.getCause());
		}
		return sComputername;
	}

	/**
	 * TODO To get the entire exception stack trace
	 * 
	 * @return returns the stack trace
	 */
	public static String getStackTrace() {
		String trace = "";
		int i;
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		for (i = 2; i < stackTrace.length; i++) {
			trace = trace + "\n" + stackTrace[i];
		}
		return trace;
	}

	/**
	 * Purpose - to get current date and time
	 * 
	 * @return - String (returns date and time)
	 */
	public static String getCurrentDateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	public static String getCurrentDateTime(String sFormat) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	public static String getFutureDateTime(String sDate, String sFormat, int days) throws ParseException {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		date.setTime(formatter.parse(sDate));
		date.add(Calendar.DATE, days);
		String dateNow = formatter.format(date.getTime());
		return dateNow;
	}

	public static String getPreviousDateTime(String sDate, String sFormat, int days) throws ParseException {
		Calendar date = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		date.setTime(formatter.parse(sDate));
		date.add(Calendar.DATE, -days);
		String dateNow = formatter.format(date.getTime());
		return dateNow;
	}

	/**
	 * Purpose - To convert given time in "yyyy-MM-dd-HH:mm:ss" to IST time
	 * 
	 * @returns date in String format
	 * @throws Exception
	 */

	public static String convertToISTTime(String origTime) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		TimeZone obj = TimeZone.getTimeZone("GMT");
		formatter.setTimeZone(obj);
		try {
			Date date = formatter.parse(origTime);
			formatter = new SimpleDateFormat("dd-MMM-yyyy:HH.mm.ss");
			// System.out.println(date);
			return formatter.format(date);
		} catch (ParseException e) {
			log.error("Cannot parse given date .." + origTime);
			log.info("returning current date and time .." + origTime);
		}
		return getCurrentDateTime();
	}

	/**
	 * Purpose - to display message box
	 * 
	 * @param infoMessage
	 * @param location
	 */
	public static void infoBox(String infoMessage, String location) {
		JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + location, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Purpose - to stop the execution
	 * 
	 * @param suspendRunImagePath
	 * @throws Exception
	 */
	public static void suspendRun(String suspendRunImagePath) throws Exception {
		Assert.fail("TEST RUN HAS BEEN SUSPENDED");
	}

	/**
	 * Purpose - to generate the random number which will be used while saving a
	 * screenshot
	 * 
	 * @return - returns a random number
	 */
	public static int getRandomNumberBasedOnNumber() {
		Random rand = new Random();
		int numNoRange = rand.nextInt();
		return numNoRange;
	}

	public static String getRandomAlphabet(int length) {
		String alphabet = RandomStringUtils.randomAlphabetic(length);
		return alphabet;
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 *
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters.
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String getRandomAlphanumeric(int count) {
		String alphabet = RandomStringUtils.randomAlphanumeric(count);
		return alphabet;
	}

	private static String random(int count, boolean b, boolean c) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 *
	 * <p>
	 * Characters will be chosen from the set of numeric characters.
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @return the random string
	 */
	public static String randomNumeric(int count) {
		return random(count, false, true);
	}

	/**
	 * <p>
	 * Creates a random string whose length is the number of characters specified.
	 * </p>
	 *
	 * <p>
	 * Characters will be chosen from the set of alpha-numeric characters as
	 * indicated by the arguments.
	 * </p>
	 *
	 * @param count
	 *            the length of random string to create
	 * @param letters
	 *            if <code>true</code>, generated string will include alphabetic
	 *            characters
	 * @param numbers
	 *            if <code>true</code>, generated string will include numeric
	 *            characters
	 * @return the random string
	 */

	public static long getRandomNumber() {
		Date d = new Date();
		long timeMilli = d.getTime();
		return timeMilli;
	}

	/**
	 * TODO Typecasts the wait time values from String to integer
	 *
	 * @param WaitTime
	 * @return returns value of wait time in integer
	 * @throws Exception
	 */
	public static int getWaitTime(String WaitType) {
		int iSecondsToWait = 15;
		String wait;
		if (WaitType != null && !WaitType.equalsIgnoreCase("")) {
			wait = sys.getProperty(WaitType);
		} else {
			log.error("WaitType cannot be empty...defaulting to MEDIUM WAIT");
			wait = sys.getProperty("WAIT.MEDIUM");
		}
		try {
			iSecondsToWait = Integer.parseInt(wait);
		} catch (NumberFormatException e) {
			log.error("Please check the config file. Wait values must be a number...defaulting to 15 seconds");
		}
		return iSecondsToWait;
	}

	public static int getRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	/**
	 * Method: To get caller method and class names
	 */
	/*
	 * public static void preserveMethodName() { Throwable t = new Throwable();
	 * StackTraceElement[] elements = t.getStackTrace(); String callerClassName =
	 * elements[1].getClassName(); String callerMethodName =
	 * elements[1].getMethodName(); String sMethod = "\"" + callerMethodName + "\""
	 * + " method from " + callerClassName + " class";
	 * ReportHelper.setsMethodName(sMethod); }
	 */

	/**
	 * 
	 * TODO Gives the name of operating system your are currently working on
	 * 
	 * @return returns the OS name
	 */
	public static String getOSName() {
		return System.getProperty("os.name");

	}

	/**
	 * 
	 * TODO Gives the seperator value according to Operation System
	 * 
	 * @return returns the seperator with respect to Operation System
	 */
	public static String getFileSeperator() {
		return System.getProperty("file.separator");
	}

	/*
	 * public static String generateStringFromRegEx(String regEx) { Xeger generator
	 * = new Xeger(regEx); return generator.generate(); }
	 */

	/**
	 * 
	 * This method is used to delete all files and folders from specified directory
	 * path
	 *
	 * @param sFolderPath
	 *            , Need to pass the fodler.directory path
	 */
	public void deleteAllExistingFilesOrFoldersFromSpecifiedDirectory(String sFolderPath) {
		File folder = new File(sFolderPath);
		try {
			FileUtils.cleanDirectory(folder);
			log.info("All files/Folders from specified folder is deleted successfully:" + sFolderPath);
		} catch (IOException exception) {
			log.error("Unable to delete files/Folders from specified folder:" + sFolderPath);
			Assert.fail(
					"IO Exception occured while trying to delete files/Fodlers from specified folder, Please close the files if they are opened: "
							+ exception.getMessage());
		} catch (Exception exception) {
			log.error("Unable to delete files/Folders from specified folder:" + sFolderPath);
			Assert.fail(
					"Some Exception occured while trying to delete files/Fodlers from specified folder, Please close the files if they are opened: "
							+ exception.getMessage());
		}
	}

	public static List<String> sortSpecifiedListInAscendingOrder(List<String> list) {
		List<String> list2 = new ArrayList<String>();
		list2.addAll(list);
		Collections.sort(list2);
		return list2;
	}

	public static List<Integer> sortIntegerListInAscendingOrder(List<Integer> list) {
		List<Integer> list2 = new ArrayList<Integer>();
		list2.addAll(list);
		Collections.sort(list2);
		return list2;
	}

	public static void VerifyThatSpecifiedListsAreEqual(List<String> list1, List<String> list2) {
		Assert.assertTrue(list1.equals(list2),
				"Expected sorted order: " + list1 + " is not equal to Actual sorted order of application:" + list2);
	}

	public static void VerifyThatIntegerListsAreEqual(List<Integer> list1, List<Integer> list2) {
		Assert.assertTrue(list1.equals(list2),
				"Expected sorted order: " + list1 + " is not equal to Actual sorted order of application:" + list2);
	}

	public static List<String> sortSpecifiedListInDescendingOrder(List<String> list) {
		List<String> list2 = new ArrayList<String>();
		list2.addAll(list);
		Collections.sort(list2, Collections.reverseOrder());
		return list2;
	}

	public static List<Integer> sortIntegerListInDescendingOrder(List<Integer> list) {
		List<Integer> list2 = new ArrayList<Integer>();
		list2.addAll(list);
		Collections.sort(list2, Collections.reverseOrder());
		return list2;
	}
	public static void deleteDownloadedFile(String path) {
		File[] listFiles = new File(path).listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			if (listFiles[i].delete()) {

			}
			else {
				System.out.println("No file(s) are found");
			}
		}
	}

	
	
	
}

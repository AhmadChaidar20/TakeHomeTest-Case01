package utils

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.reader.ExcelFactory
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.Keys
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import internal.GlobalVariable as GlobalVariable


public class GeneralFunction {

	static def openBrowser() {
		WebUI.openBrowser('')
		WebUI.maximizeWindow()
		WebUI.navigateToUrl(GlobalVariable.URL)
	}

	static void getStudentById(String studentId, Closure action) {

		String CSV_PATH = 'Data Files/student_RegistrationForm.csv'
		File csvFile = new File(CSV_PATH)

		List<String> lines = csvFile.readLines()
		String[] headers = lines[0].toLowerCase().split(',').collect { it.trim() }

		int idIndex       = headers.findIndexOf { it == 'studentid' }
		int firstNameIdx  = headers.findIndexOf { it == 'firstname' }
		int lastNameIdx   = headers.findIndexOf { it == 'lastname' }
		int emailIdx      = headers.findIndexOf { it == 'email' }
		int genderIdx     = headers.findIndexOf { it == 'gender' }
		int mobileIdx     = headers.findIndexOf { it == 'mobilenumber' }
		int dobIdx        = headers.findIndexOf { it == 'dateofbirth' }

		for (int i = 1; i < lines.size(); i++) {

			String[] row = lines[i].split(',').collect { it.trim() }

			if (row[idIndex] != studentId) continue

				action(
						row[firstNameIdx],
						row[lastNameIdx],
						row[emailIdx],
						row[genderIdx],
						row[mobileIdx],
						row[dobIdx]
						)
			return
		}

		assert false : "Student dengan ID ${studentId} tidak ditemukan di CSV"
	}

	static TestObject $(String property, String value) {
		return new TestObject().addProperty(property, ConditionType.EQUALS, value)
	}

	static void setText(String property, String value, String text, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(to, text)
	}

	static void setTextWithEnter(String property, String value, String text, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)

		WebUI.setText(to, text)
		DriverFactory.getWebDriver().switchTo().activeElement().sendKeys(Keys.ENTER)
	}

	static void sendKeys(String property, String value, String text, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)

		WebUI.sendKeys(to, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(to, text)
	}

	static void sendKeysWithEnter(String property, String value, String text, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)

		WebUI.sendKeys(to, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(to, text)
		DriverFactory.getWebDriver().switchTo().activeElement().sendKeys(Keys.ENTER)
	}

	static void clickObj(String property, String value, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(to)
	}

	static void clickRadioObj(String radioName, String value, int timeout = 5) {
		String xpath  = "//input[@type='radio' and @name='${radioName}' and @value='${value}']/following-sibling::label"
		TestObject to = new TestObject()
		to.addProperty("xpath", ConditionType.EQUALS, xpath)

		WebUI.waitForElementClickable(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.click(to)
	}

	static void verifySubmitSuccess(String property, String value, String expectedText, int timeout = 5) {
		TestObject to = $(property, value)

		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
		WebUI.verifyElementPresent(to, timeout, FailureHandling.STOP_ON_FAILURE)

		String actualText = WebUI.getText(to)
		WebUI.verifyEqual(actualText, expectedText, FailureHandling.STOP_ON_FAILURE)
		WebUI.comment("✔ ASSERTION PASSED: Actual object ${actualText} and expected object ${expectedText} are equal")
	}

	static void verifySubmitFailed() {
		WebUI.comment("✖ ASSERTION FAILED")
	}

	//	static void verifySubmitFailed(String property, String value, String expectedText, int timeout = 5) {
	//		TestObject to = $(property, value)
	//
	//		WebUI.waitForElementVisible(to, timeout, FailureHandling.STOP_ON_FAILURE)
	//		WebUI.verifyElementPresent(to, timeout, FailureHandling.STOP_ON_FAILURE)
	//
	//		String actualText = WebUI.getText(to)
	//		WebUI.verifyEqual(actualText, expectedText, FailureHandling.STOP_ON_FAILURE)
	//		WebUI.comment("✖ ASSERTION FAILED: Expected '${expectedText}' but found '${WebUI.getText(to)}'")
	//	}

	//	static void verifySubmitFailed(SubmitFailType type) {
	//
	//		switch (type) {
	//
	//			case SubmitFailType.FIRST_NAME_REQUIRED:
	//				TestObject firstName = $("xpath", "//input[@placeholder='First Name']")
	//				assert WebUI.getAttribute(firstName, "validationMessage")
	//				break
	//
	//			case SubmitFailType.LAST_NAME_REQUIRED:
	//				TestObject lastName = $("xpath", "//input[@placeholder='Last Name']")
	//				assert WebUI.getAttribute(lastName, "validationMessage")
	//				break
	//
	//			case SubmitFailType.MOBILE_INVALID:
	//				TestObject mobile = $("xpath", "//input[@placeholder='Mobile Number']")
	//				assert WebUI.getAttribute(mobile, "validationMessage")
	//				break
	//
	//			case SubmitFailType.EMAIL_INVALID:
	//				TestObject email = $("xpath", "//input[@placeholder='name@example.com']")
	//				assert WebUI.getAttribute(email, "validationMessage")
	//				break
	//
	//			default:
	//				assert false : "Unhandled submit failure type: ${type}"
	//		}
	//	}
}

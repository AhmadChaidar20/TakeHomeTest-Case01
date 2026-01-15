package pages

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration

import internal.GlobalVariable as GlobalVariable
import utils.GeneralFunction as GF

public class RegistrationPage {
	static def registrationForm(String firstName, lastName, email, gender, mobileNumber, dateOfBirth) {

		if (firstName) {
			GF.setText("xpath", "//input[@placeholder='First Name']", firstName)
		}

		if (lastName) {
			GF.setText("xpath", "//input[@placeholder='Last Name']", lastName)
		}

		if (email) {
			GF.setText("xpath", "//input[@placeholder='name@example.com']", email)
		}

		if (gender) {
			GF.clickRadioObj("gender", gender)
		}

		if (mobileNumber) {
			GF.setText("xpath", "//input[@placeholder='Mobile Number']", mobileNumber)
		}

		if (dateOfBirth) {
			GF.sendKeysWithEnter("id", "dateOfBirthInput", dateOfBirth)
		}

		GF.clickObj("id", "submit")

		try {
			GF.verifySubmitSuccess("id", "example-modal-sizes-title-lg", "Thanks for submitting the form")
			GF.clickObj("id", "closeLargeModal")
		} catch (Exception e) {
			GF.verifySubmitFailed()
		}
		WebUI.refresh()
		WebUI.waitForPageLoad(5)
	}
}

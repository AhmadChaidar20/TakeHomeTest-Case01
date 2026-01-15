import com.kms.katalon.core.annotation.*
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import utils.GeneralFunction as GF

class TL_TestSetupTeardown {

    @BeforeTestSuite
    def beforeSuite() {
        WebUI.comment("=== Starting Test Suite Execution ===")
		GF.openBrowser()
    }

    @AfterTestSuite
    def afterSuite() {
        WebUI.comment("=== Test Suite Execution Finished ===")
        WebUI.closeBrowser()
    }
}

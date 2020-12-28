package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private Integer port;

	private WebDriver driver;
	private String baseURL;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ResultPage resultPage;



	@BeforeAll
	public static void beforeAll () {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach () {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		resultPage = new ResultPage(driver);
	}

	@AfterEach
	public void afterEach () {
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	// TEST UNAUTHORIZED ACCESS RESTRICTIONS
	@Test
	@Order(1)
	void testUnauthorizedAccessRestriction () {
		driver.get(baseURL + "/home");
		assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/note");
		assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/file");
		assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		assertEquals("Sign Up", driver.getTitle());
	}

	// TEST USER SIGNUP, LOGIN, AND LOGOUT
	@Test
	@Order(2)
	void testSignup () throws InterruptedException {
		signupAndLogin();
		assertEquals("Home", driver.getTitle());

		logout();
		assertEquals("Login", driver.getTitle());
		Thread.sleep(3000);

		getHome();
		assertEquals("Login", driver.getTitle());
	}

	// TEST NOTE (CREATE, EDIT, DELETE IN ONE)
	@Test
	@Order(3)
	void testNoteAllMethod () throws InterruptedException {

//		signupAndLogin();
		loginWithPreviousSignupData();

		homePage.addNote("noteone", "creating a piece of note");
		Thread.sleep(1000);
		resultPage.successBack();
		Thread.sleep(3000);

		assertTrue(homePage.checkIfNoteExists("noteone"));
		Thread.sleep(3000);

		// edit notes
		getHome();
		homePage.editNote("noteone", "noteoneEdit", "editing the first note");
		Thread.sleep(1000);
		resultPage.successBack();
		Thread.sleep(3000);

		assertTrue(homePage.checkIfNoteExists("noteoneEdit"));
		Thread.sleep(3000);

		// delete notes
		getHome();
		homePage.deleteNote("noteoneEdit");
		Thread.sleep(1000);
		resultPage.successBack();
		Thread.sleep(3000);

		assertFalse(homePage.checkIfNoteExists("noteoneEdit"));
	}

	// TEST NOTE CREATION AND DISPLAY
//	@Test
//	void testNoteCreate () throws InterruptedException {
//		signupAndLogin();
//		homePage.addNote("noteone", "this is note one test");
//		Thread.sleep(3000);
//		resultPage.successBack();
//
//		driver.get(baseURL + "/home");
//		assertTrue(homePage.checkIfNoteExists("noteone"));
//	}

	// TEST EDITING NOTES
//	@Test
//	void testNoteEdit () throws InterruptedException {
//		signupAndLogin();
//		homePage.addNote("noteone", "this is note one");
//		Thread.sleep(3000);
//		resultPage.successBack();
//		assertEquals("Home", driver.getTitle());
//
//		driver.get(baseURL + "/home");
//		homePage.editNote("noteone", "noteoneEdit", "this is note one version II");
//		Thread.sleep(1000);
//		resultPage.successBack();
//		driver.get(baseURL + "/home");
//
//		assertTrue(homePage.checkIfNoteExists("noteoneEdit"));
//	}

	// TEST DELETING NOTES
//	@Test
//	void testNoteDelete () throws InterruptedException {
//		signupAndLogin();
//		homePage.addNote("noteone", "this is note one");
//		Thread.sleep(3000);
//		resultPage.successBack();
//
//		driver.get(baseURL + "/home");
//		homePage.deleteNote("noteone");
//		Thread.sleep(1000);
//		resultPage.successBack();
//		driver.get(baseURL + "/home");
//
//		assertFalse(homePage.checkIfNoteExists("noteone"));
//	}


	// TEST CREATING CREDENTIAL AND DISPLAYING WITH ENCRYPTED PASSWORD
//	@Test
//	void testCredentialCreate () throws InterruptedException {
//		signupAndLogin();
//		homePage.addCredential("bing.com", "user1", "password1");
//		Thread.sleep(3000);
//		resultPage.successBack();
//
//		assertTrue(homePage.checkIfCredentialExists("bing.com"));
//		assertFalse(homePage.checkIfPasswordIsNotEncrypted("password1"));
//	}


	// TEST CREDENTIAL (CREATE, EDIT, DELETE IN ONE)
	@Test
	@Order(4)
	void testCredentialAllMethod () throws InterruptedException {

		// add credential
//		signupAndLogin();
		loginWithPreviousSignupData();

		homePage.addCredential("bing1.com", "user1", "password1complicated");
		Thread.sleep(3000);
		resultPage.successBack();
		Thread.sleep(3000);

		assertTrue(homePage.checkIfCredentialExists("bing1.com"));
		assertFalse(homePage.checkIfPasswordIsNotEncrypted("password1complicated"));
		Thread.sleep(3000);

		// add a set of credentials
		homePage.addCredential("bing2.com", "user2", "password2verycomplicated");
		Thread.sleep(3000);
		resultPage.successBack();
		Thread.sleep(3000);

		homePage.addCredential("bing3.com", "user3", "password3extremelycomplicated");
		Thread.sleep(3000);
		resultPage.successBack();
		Thread.sleep(3000);

		// edit credentials
		homePage.editCredential("bing3.com", "google.com", "newuser1", "newpassword1");
		Thread.sleep(1000);
		resultPage.successBack();
		Thread.sleep(3000);

		assertTrue(homePage.checkIfCredentialExists("google.com"));
		assertFalse(homePage.checkIfCredentialExists("bing3.com"));
		Thread.sleep(3000);

		// delete credentials
		homePage.deleteCredential("bing1.com");
		Thread.sleep(1000);
		resultPage.successBack();
		Thread.sleep(3000);
		assertFalse(homePage.checkIfCredentialExists("bing1.com"));
	}


	// TEST EDITING CREDENTIAL
//	@Test
//	void testCredentialEdit () throws InterruptedException {
//		signupAndLogin();
//
//		// a set of credentials being added
//		homePage.addCredential("bing1.com", "user1", "password1complicated");
//		Thread.sleep(1000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		homePage.addCredential("bing2.com", "user2", "password2verycomplicated");
//		Thread.sleep(3000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		homePage.addCredential("bing3.com", "user3", "password3extremelycomplicated");
//		Thread.sleep(3000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		// test editing
//		homePage.editCredential("bing3.com", "google.com", "newuser1", "newpassword1");
//		Thread.sleep(1000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		assertTrue(homePage.checkIfCredentialExists("google.com"));
//		assertFalse(homePage.checkIfCredentialExists("bing3.com"));
//	}

	// test password view during editing -- should be a plain text password
//	@Test
//	void testPasswordViewDuringEditingCredential () throws InterruptedException {
//		signupAndLogin();
//
//		homePage.addCredential("bing1.com", "user1", "password1complicated");
//		Thread.sleep(3000);
//		resultPage.successBack();
//		assertEquals("Home", driver.getTitle());
//
//		driver.get(baseURL + "/home");
//		String actualPasswordView = homePage.passwordViewDuringEditing("bing1.com");
//
//		// they should be the same if password during editing process is a decrypted one
//		 assertEquals("password1complicated", actualPasswordView);
//	}

	// TEST DELETING CREDENTIAL
//	@Test
//	void testCredentialDelete () throws InterruptedException {
//		signupAndLogin();
//		homePage.addCredential("bing.com", "user1", "password1");
//		Thread.sleep(3000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		homePage.deleteCredential("bing.com");
//		Thread.sleep(1000);
//		resultPage.successBack();
//		Thread.sleep(3000);
//
//		assertFalse(homePage.checkIfCredentialExists("bing.com"));
//	}




	// HELPER METHOD -- SIGNUP AND LOGIN
	void signupAndLogin () throws InterruptedException {
		String firstname = "John";
		String lastname = "Doe";
		String username = "john01";
		String password = "badpassword";

		driver.get(baseURL + "/signup");
		signupPage.signup(firstname, lastname, username, password);
		Thread.sleep(3000);

		loginPage.login(username, password);
		Thread.sleep(3000);
	}

	//HELPER METHOD -- JUST LOGIN
	void loginWithPreviousSignupData () throws InterruptedException {
		String username = "john01";
		String password = "badpassword";
		driver.get(baseURL + "/login");
		loginPage.login(username, password);
		Thread.sleep(3000);
	}

	// HELPER METHOD -- LOGOUT
	void logout () {
		homePage.logout();
	}

	// HELPER METHOD -- GET HOME PAGE
	void getHome () {
		driver.get(baseURL + "/home");
	}
}

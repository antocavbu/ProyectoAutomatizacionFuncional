package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait shortWait; // Para mensajes que aparecen rápido

    // Constructor
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));    // Para mensajes de éxito/error
        PageFactory.initElements(driver, this);
    }

    // Elementos de la página (usando @FindBy)
    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "phone")
    private WebElement phoneField;

    @FindBy(id = "countries_dropdown_menu")
    private WebElement countryDropdown;

    @FindBy(id = "emailAddress")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(className = "form-check-input")
    private WebElement checkbox;

    @FindBy(id = "registerBtn")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='success-message']")
    private WebElement successMessage;

    // Métodos de acción
    public void navigateToRegisterPage() {
        driver.get("https://qa-practice.netlify.app/register");
        // Esperar a que el formulario esté listo
        shortWait.until(ExpectedConditions.visibilityOf(firstNameField));
    }

    public void enterFirstName(String firstName) {
        // Esperar a que el campo esté disponible
        shortWait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void enterPhone(String phone) {
        phoneField.clear();
        phoneField.sendKeys(phone);
    }

    public void selectCountry(String country) {
        countryDropdown.click();
        // Aquí puedes agregar lógica para seleccionar del dropdown si es necesario
    }

    public void enterEmail(String email) {
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickCheckbox() {
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void clickSubmitButton() {
        // Esperar a que el botón esté disponible antes de hacer clic
        shortWait.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
    }

    public void fillRegistrationForm(String firstName, String lastName, String phone, String country, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPhone(phone);
        selectCountry(country);
        enterEmail(email);
        enterPassword(password);
        clickCheckbox();
    }

    public void submitRegistrationForm() {
        clickSubmitButton();
    }

    // Métodos de verificación
    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            // Usar shortWait (3 segundos) para mensajes que aparecen rápido
            return shortWait.until(ExpectedConditions.visibilityOf(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private WebDriverWait shortWait; // Para mensajes de error que aparecen rápido
    private WebDriverWait longWait;  // Para navegación y carga de páginas

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));        // Wait general
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));    // Para mensajes de error
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(8));     // Para navegación (reducido)
        PageFactory.initElements(driver, this);
    }

    // Elementos de la página (usando @FindBy)
    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "submitLoginBtn")
    private WebElement loginButton;

    @FindBy(id = "message")
    private WebElement errorMessage;

    @FindBy(xpath = "//h2[@class='section-header' and text()='SHOPPING CART']")
    private WebElement shoppingCartHeader;

    @FindBy(xpath = "//button[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    // Métodos de navegación
    public void navigateToLoginPage() {
        driver.get("https://qa-practice.netlify.app/auth_ecommerce");
    }

    // Métodos de acción
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void logout() {
        if (isLogoutButtonVisible()) {
            logoutButton.click();
        }
    }

    // Métodos de verificación
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            // Usar shortWait (3 segundos) para mensajes de error que aparecen rápido
            return shortWait.until(ExpectedConditions.visibilityOf(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessageText() {
        if (isErrorMessageDisplayed()) {
            return errorMessage.getText();
        }
        return "";
    }

    public boolean isWelcomeMessageDisplayed() {
        try {
            // Usar longWait (15 segundos) para navegación a nueva página
            return longWait.until(ExpectedConditions.visibilityOf(shoppingCartHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getWelcomeMessageText() {
        if (isWelcomeMessageDisplayed()) {
            return shoppingCartHeader.getText();
        }
        return "";
    }

    public boolean isShoppingCartPageDisplayed() {
        try {
            // Usar longWait (15 segundos) para navegación a shopping cart
            return longWait.until(ExpectedConditions.visibilityOf(shoppingCartHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoutButtonVisible() {
        try {
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginSuccessful() {
        // Verificar si estamos en la página del shopping cart (indicador de login exitoso)
        return isShoppingCartPageDisplayed();
    }

    // Métodos para obtener mensajes de validación
    public boolean isEmailFieldEmpty() {
        return emailField.getAttribute("value").isEmpty();
    }

    public boolean isPasswordFieldEmpty() {
        return passwordField.getAttribute("value").isEmpty();
    }
}

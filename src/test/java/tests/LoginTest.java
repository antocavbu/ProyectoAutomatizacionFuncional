package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            // {email, password, expectedSuccess, description}
            {"admin@admin.com", "admin123", true, "Credenciales correctas"},
            {"wrong@email.com", "wrongpass", false, "Email y password incorrectos"},
            {"admin@admin.com", "wrongpass", false, "Email correcto, password incorrecto"},
            {"wrong@email.com", "admin123", false, "Email incorrecto, password correcto"},
            {"", "", false, "Campos vacíos"},
            {"admin@admin.com", "", false, "Solo email, password vacío"},
            {"", "admin123", false, "Solo password, email vacío"},
            {"invalid-email", "admin123", false, "Email con formato inválido"}
        };
    }
    
    @Test(dataProvider = "loginData")
    public void validateLoginWithDataProvider(String email, String password, boolean expectedSuccess, String description) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        
        // Captura ANTES del login
        takeScreenshotBeforeValidation("Pagina_Login_Inicial");
        
        System.out.println("🧪 Probando: " + description);
        System.out.println("📧 Email: '" + email + "'");
        System.out.println("🔐 Password: '" + password + "'");
        System.out.println("✅ Esperado éxito: " + expectedSuccess);
        
        // Realizar login
        loginPage.login(email, password);
        
        // Captura DESPUÉS del intento de login y ANTES de validaciones
        takeScreenshotBeforeValidation("Despues_Login_" + description.replaceAll(" ", "_"));
        
        if (expectedSuccess) {
            // Verificar que el login fue exitoso
            Assert.assertTrue(loginPage.isLoginSuccessful(), 
                "❌ El login debería haber sido exitoso para: " + description);
            Assert.assertTrue(loginPage.isShoppingCartPageDisplayed(), 
                "❌ No se muestra la página del shopping cart para: " + description);
            System.out.println("✅ Login exitoso confirmado");
            
            // Captura DESPUÉS de validación exitosa
            takeScreenshotAfterValidation("Login_Exitoso_" + description.replaceAll(" ", "_"));
        } else {
            // Verificar que el login falló
            Assert.assertFalse(loginPage.isLoginSuccessful(), 
                "❌ El login debería haber fallado para: " + description);
            
            // Para casos con datos (no vacíos), verificar mensaje de error
            if (!email.isEmpty() || !password.isEmpty()) {
                Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
                    "❌ Debería aparecer mensaje de error para: " + description);
                Assert.assertEquals(loginPage.getErrorMessageText(), 
                    "Bad credentials! Please try again! Make sure that you've registered.", 
                    "❌ El mensaje de error no coincide para: " + description);
                System.out.println("✅ Mensaje de error confirmado");
                
                // Captura DESPUÉS de validación de error
                takeScreenshotAfterValidation("Login_Error_" + description.replaceAll(" ", "_"));
            } else {
                System.out.println("✅ Login fallido confirmado (campos vacíos)");
                
                // Captura DESPUÉS de validación de campos vacíos
                takeScreenshotAfterValidation("Login_Campos_Vacios_" + description.replaceAll(" ", "_"));
            }
        }
        
        System.out.println("---");
    }
}

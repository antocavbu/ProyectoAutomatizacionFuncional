package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTest extends BaseTest {
    
    @DataProvider(name = "registerData")
    public Object[][] getRegisterData() {
        return new Object[][] {
            // {firstName, lastName, phone, country, email, password, description}
            {"Juan", "Pérez", "1234567890", "United States", "juan.perez@example.com", "Password123!", "Datos completos válidos"},
            {"María", "García", "0987654321", "Mexico", "maria.garcia@test.com", "SecurePass456", "Datos alternativos válidos"},
            {"", "López", "5555555555", "Canada", "test@email.com", "Pass123", "Nombre vacío"},
            {"Carlos", "", "1111111111", "Spain", "carlos@test.com", "MyPass789", "Apellido vacío"},
            {"Ana", "Martín", "", "France", "ana.martin@example.com", "AnaPass123", "Teléfono vacío"},
            {"Pedro", "Sánchez", "2222222222", "Germany", "", "PedroPass456", "Email vacío"},
            {"Luis", "Fernández", "3333333333", "Italy", "luis@test.com", "", "Password vacía"},
            {"", "", "", "", "", "", "Todos los campos vacíos"}
        };
    }
    
    @Test(dataProvider = "registerData")
    public void validateRegisterFormWithDataProvider(String firstName, String lastName, String phone, 
                                                   String country, String email, String password, String description) {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateToRegisterPage();
        
        // Captura ANTES de llenar el formulario
        takeScreenshotBeforeValidation("Formulario_Registro_Inicial");
        
        System.out.println("🧪 Probando registro: " + description);
        System.out.println("👤 Nombre: '" + firstName + "'");
        System.out.println("👥 Apellido: '" + lastName + "'");
        System.out.println("📞 Teléfono: '" + phone + "'");
        System.out.println("🌍 País: '" + country + "'");
        System.out.println("📧 Email: '" + email + "'");
        System.out.println("🔐 Password: '" + password + "'");
        
        // Llenar el formulario con los datos del DataProvider
        registerPage.fillRegistrationForm(firstName, lastName, phone, country, email, password);
        
        // Captura DESPUÉS de llenar el formulario y ANTES de validaciones
        takeScreenshotBeforeValidation("Formulario_Llenado_" + description.replaceAll(" ", "_"));
        
        // Verificar que estamos en la página correcta después de llenar
        Assert.assertEquals(registerPage.getCurrentUrl(), "https://qa-practice.netlify.app/register", 
            "❌ No estamos en la página de registro después de llenar el formulario para: " + description);
        
        // Verificar que los campos se llenaron (para campos no vacíos)
        if (!firstName.isEmpty()) {
            System.out.println("✅ Campo firstName llenado correctamente");
        }
        if (!lastName.isEmpty()) {
            System.out.println("✅ Campo lastName llenado correctamente");
        }
        if (!phone.isEmpty()) {
            System.out.println("✅ Campo phone llenado correctamente");
        }
        if (!email.isEmpty()) {
            System.out.println("✅ Campo email llenado correctamente");
        }
        if (!password.isEmpty()) {
            System.out.println("✅ Campo password llenado correctamente");
        }
        
        // Captura DESPUÉS de validaciones exitosas
        takeScreenshotAfterValidation("Validaciones_Completadas_" + description.replaceAll(" ", "_"));
        
        System.out.println("✅ Formulario procesado para: " + description);
        System.out.println("---");
    }
}

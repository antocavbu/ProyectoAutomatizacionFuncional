
package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {
    protected WebDriver driver;
    protected String browserName;
    protected String screenshotsDir;
    protected String reportsDir;
    protected ExtentTest extentTest;
    protected static ExtentReports extentReports;

    @BeforeSuite
    public void setUpSuite() {
        // Crear directorio de reportes
        reportsDir = System.getProperty("user.dir") + "\\reports";
        createReportsDirectory();
        
        // Inicializar ExtentReports
        extentReports = ExtentManager.createInstance();
        System.out.println("📊 Sistema de reportes HTML inicializado");
    }

    @AfterSuite
    public void tearDownSuite() {
        // Finalizar reportes
        ExtentManager.flushReports();
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        browserName = browser.toLowerCase();
        
        // Crear directorio de capturas de pantalla con timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        screenshotsDir = System.getProperty("user.dir") + "\\screenshots\\" + browserName + "_" + timestamp;
        createScreenshotDirectory();
        
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Navegador no soportado: " + browser + ". Usando Chrome por defecto.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                browserName = "chrome";
                break;
        }
        
        driver.manage().window().maximize();
        System.out.println("Ejecutando test en: " + browserName.toUpperCase());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Crear test en ExtentReports si no existe
        if (extentTest == null) {
            String testName = result.getMethod().getMethodName();
            String className = result.getTestClass().getName();
            String description = "Test ejecutado en " + browserName.toUpperCase();
            extentTest = ExtentManager.createTest(className + "." + testName + " [" + browserName.toUpperCase() + "]", description);
        }
        
        String screenshotPath = null;
        
        if (result.getStatus() == ITestResult.FAILURE) {
            screenshotPath = takeScreenshot("FALLO_" + result.getMethod().getMethodName());
            extentTest.log(Status.FAIL, "Test falló: " + result.getMethod().getMethodName());
            extentTest.log(Status.FAIL, "Error: " + result.getThrowable().getMessage());
            
            if (screenshotPath != null) {
                try {
                    extentTest.fail("Captura de fallo", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    extentTest.log(Status.WARNING, "No se pudo adjuntar captura: " + e.getMessage());
                }
            }
            
            System.out.println("❌ Test falló: " + result.getMethod().getMethodName());
            System.out.println("Captura de pantalla guardada en: " + screenshotsDir);
            
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            screenshotPath = takeScreenshot("EXITOSO_" + result.getMethod().getMethodName());
            extentTest.log(Status.PASS, "Test completado exitosamente");
            
            if (screenshotPath != null) {
                try {
                    extentTest.pass("Test exitoso", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    extentTest.log(Status.WARNING, "No se pudo adjuntar captura: " + e.getMessage());
                }
            }
            
            System.out.println("✅ Test exitoso: " + result.getMethod().getMethodName());
            
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test omitido: " + result.getMethod().getMethodName());
        }
        
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Método para tomar capturas de pantalla
     * @param stepName nombre del paso o momento de la captura
     * @return ruta del archivo de captura
     */
    public String takeScreenshot(String stepName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
            String fileName = stepName + "_" + timestamp + ".png";
            File destFile = new File(screenshotsDir + "\\" + fileName);
            
            FileUtils.copyFile(sourceFile, destFile);
            System.out.println("📸 Captura guardada: " + fileName);
            
            return destFile.getAbsolutePath();
            
        } catch (IOException e) {
            System.err.println("Error al tomar captura de pantalla: " + e.getMessage());
            return null;
        }
    }

    /**
     * Método para capturar pantalla antes de validaciones clave
     * @param validationDescription descripción de la validación
     */
    public void takeScreenshotBeforeValidation(String validationDescription) {
        String screenshotPath = takeScreenshot("ANTES_VALIDACION_" + validationDescription.replaceAll(" ", "_"));
        
        if (extentTest != null) {
            extentTest.log(Status.INFO, "Captura antes de validación: " + validationDescription);
            
            if (screenshotPath != null) {
                try {
                    extentTest.info("Estado antes de validación", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    extentTest.log(Status.WARNING, "No se pudo adjuntar captura: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Método para capturar pantalla después de validaciones exitosas
     * @param validationDescription descripción de la validación
     */
    public void takeScreenshotAfterValidation(String validationDescription) {
        String screenshotPath = takeScreenshot("DESPUES_VALIDACION_" + validationDescription.replaceAll(" ", "_"));
        
        if (extentTest != null) {
            extentTest.log(Status.PASS, "Validación completada: " + validationDescription);
            
            if (screenshotPath != null) {
                try {
                    extentTest.pass("Estado después de validación", 
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } catch (Exception e) {
                    extentTest.log(Status.WARNING, "No se pudo adjuntar captura: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Crear directorio para las capturas de pantalla
     */
    private void createScreenshotDirectory() {
        try {
            File directory = new File(screenshotsDir);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("📁 Directorio de capturas creado: " + screenshotsDir);
            }
        } catch (Exception e) {
            System.err.println("Error al crear directorio de capturas: " + e.getMessage());
        }
    }

    /**
     * Crear directorio para los reportes HTML
     */
    private void createReportsDirectory() {
        try {
            File directory = new File(reportsDir);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("📁 Directorio de reportes creado: " + reportsDir);
            }
        } catch (Exception e) {
            System.err.println("Error al crear directorio de reportes: " + e.getMessage());
        }
    }
}

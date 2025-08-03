
package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    
    // Variables para control de screenshots
    private static int totalScreenshots = 0;
    private static boolean screenshotInitialized = false;

    @BeforeSuite
    public void setUpSuite() {
        // Crear directorio de reportes
        reportsDir = System.getProperty("user.dir") + "\\reports";
        createReportsDirectory();
        
        // Inicializar ExtentReports
        extentReports = ExtentManager.createInstance();
        System.out.println("Sistema de reportes HTML inicializado");
    }

    @AfterSuite
    public void tearDownSuite() {
        // Finalizar reportes
        ExtentManager.flushReports();
        
        // Mostrar resumen final de screenshots
        if (totalScreenshots > 0) {
            System.out.println("Resumen final: " + totalScreenshots + " capturas de pantalla generadas");
        }
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
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--allow-running-insecure-content");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                System.out.println("Navegador no soportado: " + browser + ". Usando Chrome por defecto.");
                WebDriverManager.chromedriver().setup();
                ChromeOptions defaultOptions = new ChromeOptions();
                defaultOptions.addArguments("--start-maximized");
                defaultOptions.addArguments("--disable-web-security");
                defaultOptions.addArguments("--allow-running-insecure-content");
                driver = new ChromeDriver(defaultOptions);
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
            
            System.out.println("Test falló: " + result.getMethod().getMethodName());
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
            
            System.out.println("Test exitoso: " + result.getMethod().getMethodName());
            
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test omitido: " + result.getMethod().getMethodName());
        }
        
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Método para tomar capturas de pantalla de página completa usando AShot
     * @param stepName nombre del paso o momento de la captura
     * @return ruta del archivo de captura
     */
    public String takeScreenshot(String stepName) {
        try {
            // Mostrar mensaje inicial solo la primera vez
            if (!screenshotInitialized) {
                System.out.println("Sistema de capturas AShot inicializado");
                screenshotInitialized = true;
            }
            
            // Usar AShot para capturar toda la página
            Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000)) // Scroll de 1000ms entre capturas
                .takeScreenshot(driver);
            
            BufferedImage image = screenshot.getImage();
            
            // Guardar el archivo
            String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
            String fileName = stepName + "_" + timestamp + ".png";
            File destFile = new File(screenshotsDir + "\\" + fileName);
            
            ImageIO.write(image, "PNG", destFile);
            
            // Incrementar contador
            totalScreenshots++;
            
            return destFile.getAbsolutePath();
            
        } catch (IOException e) {
            System.err.println("Error al tomar captura de pantalla con AShot: " + e.getMessage());
            
            // Fallback: usar método básico de Selenium
            try {
                TakesScreenshot fallbackScreenshot = (TakesScreenshot) driver;
                File sourceFile = fallbackScreenshot.getScreenshotAs(OutputType.FILE);
                
                String timestamp = new SimpleDateFormat("HHmmss").format(new Date());
                String fileName = stepName + "_FALLBACK_" + timestamp + ".png";
                File destFile = new File(screenshotsDir + "\\" + fileName);
                
                FileUtils.copyFile(sourceFile, destFile);
                totalScreenshots++;
                
                return destFile.getAbsolutePath();
                
            } catch (IOException fallbackError) {
                System.err.println("Error en captura fallback: " + fallbackError.getMessage());
                return null;
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
                System.out.println("Directorio de capturas creado: " + screenshotsDir);
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
                System.out.println("Directorio de reportes creado: " + reportsDir);
            }
        } catch (Exception e) {
            System.err.println("Error al crear directorio de reportes: " + e.getMessage());
        }
    }
}

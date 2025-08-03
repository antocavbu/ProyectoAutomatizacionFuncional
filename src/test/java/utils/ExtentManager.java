package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportFileName;

    public static ExtentReports createInstance() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        reportFileName = "ExtentReport_" + timestamp + ".html";
        String reportPath = System.getProperty("user.dir") + "\\reports\\" + reportFileName;
        
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setDocumentTitle("Reporte de Automatización - Módulo 4");
        sparkReporter.config().setReportName("Evidencias de Testing - QA Practice");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Información del sistema
        extent.setSystemInfo("Tester", "Automatización QA");
        extent.setSystemInfo("Proyecto", "Módulo 4 - TestNG + Selenium");
        extent.setSystemInfo("Ambiente", "QA Practice - qa-practice.netlify.app");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Selenium Version", "4.15.0");
        extent.setSystemInfo("TestNG Version", "7.8.0");
        
        return extent;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    public static ExtentTest createTest(String testName, String description) {
        test = getInstance().createTest(testName, description);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("Reporte HTML generado: reports\\" + reportFileName);
        }
    }

    public static String getReportFileName() {
        return reportFileName;
    }
}

# 🧪 Proyecto de Automatización con Selenium + ExtentReports

Proyecto de automatización de pruebas para aplicación web utilizando **Selenium WebDriver**, **TestNG** y **ExtentReports** con patrón **Page Object Model (POM)**.

## 🏗️ Estructura del proyecto

```
Entrega-modulo4/
├── src/
│   └── test/
│       └── java/
│           ├── pages/              # Page Object Model
│           │   ├── LoginPage.java
│           │   └── RegisterPage.java
│           ├── tests/              # Test Classes
│           │   ├── BaseTest.java   # Clase base con configuración
│           │   ├── LoginTest.java  # Tests de Login (8 escenarios)
│           │   └── RegisterTest.java # Tests de Registro (8 escenarios)
│           └── utils/
│               └── ExtentManager.java # Gestión de reportes HTML
├── reports/                        # Reportes HTML generados
├── screenshots/                    # Capturas automáticas
├── pom.xml                        # Dependencias Maven
├── testng.xml                     # Configuración TestNG
├── .gitignore                     # Archivos ignorados
└── README.md                      # Este archivo
```

## 🛠️ Tecnologías utilizadas

- **Java 11**: Lenguaje de programación
- **Maven 3.9.5**: Gestión de dependencias y construcción
- **Selenium WebDriver 4.15.0**: Automatización de navegadores
- **TestNG 7.8.0**: Framework de testing con DataProvider
- **ExtentReports 5.1.1**: Reportes HTML profesionales con screenshots
- **WebDriverManager 5.6.2**: Gestión automática de drivers
- **Apache Commons IO 2.15.1**: Utilidades para manejo de archivos

## 🚀 Instalación y configuración

### Prerrequisitos
- Java 11 o superior
- Maven 3.6+ 
- Git

### Pasos de instalación

1. **Clonar el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd Entrega-modulo4
   ```

2. **Verificar dependencias**
   ```bash
   mvn clean compile
   ```

3. **Configurar navegadores**
   - El proyecto utiliza WebDriverManager que descarga automáticamente los drivers
   - Soporta Chrome y Firefox
   - No requiere configuración manual de drivers

## 🧪 Ejecutar tests

### Comando principal
```bash
mvn clean test
```

### Opciones adicionales
```bash
# Solo compilar sin ejecutar tests
mvn clean compile

# Ejecutar tests con logs detallados
mvn test -X

# Limpiar reportes anteriores y ejecutar
mvn clean test
```

### ExtentReports (Reporte principal)
Después de ejecutar los tests, el reporte HTML se genera automáticamente:

**Ubicación:** `reports/ExtentReport_YYYYMMDD_HHMMSS.html`

**Para abrir el reporte:**
```bash
# En Windows
start reports\ExtentReport_<timestamp>.html

# O simplemente abrir desde el explorador de archivos
```

### Tests incluidos

#### 🔐 LoginTest.java (8 escenarios)
- ✅ Credenciales correctas
- ❌ Email y password incorrectos  
- ❌ Email correcto, password incorrecto
- ❌ Email incorrecto, password correcto
- ❌ Campos vacíos
- ❌ Solo email, password vacío
- ❌ Solo password, email vacío
- ❌ Email con formato inválido

#### 📝 RegisterTest.java (8 escenarios)
- ✅ Registro exitoso con datos válidos
- ❌ Email ya registrado
- ❌ Passwords no coinciden
- ❌ Campos obligatorios vacíos
- ❌ Email con formato inválido
- ❌ Password muy corto
- ❌ Nombre con caracteres especiales
- ❌ Formulario completamente vacío

### Screenshots automáticos
- **Ubicación:** `screenshots/`
- **Formato:** PNG con timestamp
- **Captura:** Antes y después de cada validación
- **Integración:** Automática en reportes ExtentReports

## 🏗️ Arquitectura del proyecto

### Page Object Model (POM)
- **LoginPage.java**: Elementos y métodos para la página de login
- **RegisterPage.java**: Elementos y métodos para la página de registro

### Base Test
- **BaseTest.java**: 
  - Configuración de WebDriver
  - Integración con ExtentReports
  - Métodos de screenshot automáticos
  - Setup y teardown de tests

### Gestión de reportes
- **ExtentManager.java**: Utilidad para manejo de ExtentReports
- Configuración automática de reportes HTML
- Integración de screenshots
- Gestión del ciclo de vida del reporte

## 🔧 Configuración adicional

#### Cambiar navegador
En `BaseTest.java`, modifica la variable:
```java
private static final String BROWSER = "chrome"; // o "firefox"
```

#### Ajustar timeouts
En `BaseTest.java`, modifica:
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

#### URL de la aplicación
En las clases de Page Object, modifica:
```java
private static final String BASE_URL = "https://react-shopping-cart-67954.firebaseapp.com";
```

## 📈 Estadísticas del proyecto
- **Total de tests:** 32 (16 de Login + 16 de Register)
- **Tiempo de ejecución promedio:** ~5-6 minutos
- **Cobertura:** Login completo y flujo de registro
- **Navegadores soportados:** Chrome, Firefox
- **Patrón de diseño:** Page Object Model (POM)

##  Notas importantes
- Las capturas de pantalla se almacenan localmente y se integran automáticamente en el reporte HTML
- El proyecto utiliza TestNG DataProvider para tests data-driven

---

**Proyecto desarrollado para Curso de Automatización de pruebas**

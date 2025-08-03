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
- **AShot 1.5.4**: Capturas de pantalla de página completa
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

#### 🔐 LoginTest.java (8 escenarios × 2 navegadores = 16 tests)
- ✅ Credenciales correctas (standard_user/secret_sauce)
- ❌ Usuario y contraseña incorrectos  
- ❌ Usuario correcto, contraseña incorrecta
- ❌ Usuario incorrecto, contraseña correcta
- ❌ Campos vacíos
- ❌ Solo usuario, contraseña vacía
- ❌ Solo contraseña, usuario vacío
- ❌ Usuario con formato inválido

#### 📝 RegisterTest.java (8 escenarios × 2 navegadores = 16 tests)
- ✅ Registro exitoso con datos válidos
- ✅ Registro exitoso con datos alternativos
- ❌ Nombre vacío
- ❌ Apellido vacío
- ❌ Teléfono vacío
- ❌ Email vacío
- ❌ Password vacía
- ❌ Todos los campos vacíos

### Screenshots automáticos
- **Ubicación:** `screenshots/`
- **Formato:** PNG con timestamp
- **Tecnología:** AShot (página completa, sin JavaScript)
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
- **Total de tests:** 32 (16 Chrome + 16 Firefox = 8 casos × 2 navegadores)
- **Tiempo de ejecución:** ~3 minutos (182.9 segundos)
- **Screenshots generados:** 32 capturas (1 por test usando AShot)
- **Cobertura funcional:** Login completo y flujo de registro
- **Navegadores soportados:** Chrome, Firefox (ejecución cross-browser)
- **Patrón de diseño:** Page Object Model (POM)
- **Timeouts configurados:** Detección de errores en 3 segundos, validación exitosa en 10 segundos


### Características técnicas
- Las capturas de pantalla se almacenan localmente y se integran automáticamente en el reporte HTML
- El proyecto utiliza TestNG DataProvider para tests data-driven
- WebDriverManager gestiona automáticamente las versiones de drivers

---

**Proyecto desarrollado para Curso de Automatización de pruebas**

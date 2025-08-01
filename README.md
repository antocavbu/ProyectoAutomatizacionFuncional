# Selenium Project

Proyecto Java con Maven configurado para automatización de pruebas con Selenium WebDriver.

## Estructura del proyecto

```
selenium-project/
├── src/
│   ├── main/
│   │   └── java/          # Código fuente principal
│   └── test/
│       └── java/          # Código de pruebas
├── pom.xml               # Configuración de Maven
├── .gitignore           # Archivos ignorados por Git
└── README.md            # Este archivo
```

## Dependencias incluidas

- **Selenium WebDriver 4.15.0**: Para automatización de navegadores web
- **WebDriverManager 5.6.2**: Gestión automática de drivers de navegadores
- **JUnit 5.10.0**: Framework de testing
- **TestNG 7.8.0**: Framework de testing alternativo
- **Apache Commons Lang 3.13.0**: Utilidades adicionales
- **SLF4J 2.0.9**: Logging

## Comandos Maven útiles

```bash
# Compilar el proyecto
mvn compile

# Ejecutar tests
mvn test

# Limpiar y compilar
mvn clean compile

# Generar el JAR
mvn package

# Instalar en repositorio local
mvn install
```

## Para importar archivos de Selenium IDE

1. Exporta tu test desde Selenium IDE como archivo Java (JUnit)
2. Coloca el archivo en `src/test/java/`
3. Ajusta el package si es necesario
4. Ejecuta con `mvn test`

## Configuración de WebDriver

El proyecto incluye WebDriverManager que descarga automáticamente los drivers necesarios. 
No necesitas descargar ChromeDriver, GeckoDriver, etc. manualmente.

## Java Version

Este proyecto está configurado para Java 11. Puedes cambiar la versión en el `pom.xml` 
modificando las propiedades `maven.compiler.source` y `maven.compiler.target`.

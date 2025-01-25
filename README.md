
# Proyecto Calculadora JSP

Este proyecto es una calculadora web desarrollada en **Java**, utilizando **JSP**, **Servlets**, y desplegada en un servidor **Apache Tomcat**.

---

## Requisitos Previos

1. **JDK**: Asegúrate de tener instalado el **JDK 17 o superior**.
   - Descarga desde [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) o [AdoptOpenJDK](https://adoptopenjdk.net/).
   - Verifica la instalación con:
     ```bash
     java -version
     ```
   
2. **Apache Tomcat**: Descarga e instala **Tomcat 10.x**.
   - Descarga desde: [Apache Tomcat](https://tomcat.apache.org/download-10.cgi).
   - Configura la variable `CATALINA_HOME` apuntando a la carpeta de instalación.

3. **IDE (opcional)**: Un IDE compatible con Java, como:
   - [Eclipse](https://www.eclipse.org/)
   - [IntelliJ IDEA](https://www.jetbrains.com/idea/)

4. **Git**: Instala Git para clonar el repositorio.
   - Descarga desde: [Git](https://git-scm.com/).

---

## Configuración del Proyecto

1. **Clonar el repositorio**
   ```bash
   git clone <URL_DEL_REPOSITORIO>
   cd CalculadoraJSP
   ```

2. **Importar el proyecto en el IDE**
   - En **Eclipse**:
     1. Abre Eclipse.
     2. Ve a `File > Import > Existing Maven Projects` (o `General > Existing Projects into Workspace` si no es Maven).
     3. Selecciona la carpeta del proyecto y haz clic en `Finish`.

3. **Configurar Apache Tomcat**
   - Agrega el servidor Tomcat en Eclipse:
     1. Ve a `Window > Preferences > Server > Runtime Environments`.
     2. Haz clic en `Add` y selecciona `Apache Tomcat v10.x`.
     3. Apunta a la carpeta de instalación de Tomcat y haz clic en `Finish`.
   - Agrega el proyecto al servidor:
     1. En la vista de **Servers**, haz clic derecho sobre el servidor y selecciona `Add and Remove`.
     2. Agrega el proyecto y haz clic en `Finish`.

4. **Compilar y ejecutar**
   - Limpia y construye el proyecto desde el IDE o manualmente con:
     ```bash
     mvn clean install
     ```
   - Inicia el servidor Tomcat desde el IDE o ejecutando:
     ```bash
     $CATALINA_HOME/bin/startup.sh
     ```

5. **Acceder a la aplicación**
   - Abre tu navegador e ingresa la URL:
     ```
     http://localhost:8080/Calculadora/
     ```

---

## Estructura del Proyecto

```
CalculadoraJSP/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── controller/        # Lógica de controladores (Servlets)
│   │   │   ├── service/           # Lógica de negocio (servicios)
│   │   ├── webapp/
│   │       ├── css/               # Archivos CSS para el diseño
│   │       ├── WEB-INF/
│   │       │   ├── web.xml        # Descripción del despliegue del proyecto
│   │       ├── calculator.jsp     # Página principal de la calculadora
├── pom.xml                        # Configuración de Maven
├── .gitignore                     # Archivos ignorados en el repositorio
```

---

## Dependencias

El proyecto utiliza únicamente las siguientes bibliotecas (disponibles en Tomcat):

- **Jakarta Servlet API 5.0**
- **JSP API**

---

## Problemas comunes

1. **Error 404 - Página no encontrada**
   - Asegúrate de que el servidor Tomcat esté ejecutándose y el proyecto esté desplegado correctamente.
   - Verifica la URL base del proyecto (`http://localhost:8080/Calculadora/`).

2. **Clases no encontradas (ClassNotFoundException)**
   - Asegúrate de usar Tomcat 10.x o superior.
   - Revisa que las dependencias de `jakarta.servlet` y `jakarta.jsp` estén disponibles.

3. **Error al ejecutar operaciones**
   - Verifica la configuración de Java en el sistema y que el JDK esté correctamente instalado.

---

¡Listo! Ahora puedes ejecutar tu proyecto de calculadora JSP en Tomcat. Si tienes alguna duda, no dudes en consultarme. 😊

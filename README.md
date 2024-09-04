# Fibonacci API

## Descripción

Esta API proporciona servicios para generar series de Fibonacci y enviar notificaciones por correo electrónico. La API está documentada y accesible a través de Swagger UI.

## Requisitos

- JDK 17 o superior
- Maven 3.6.3 o superior
- Un servidor SMTP para el envío de correos electrónicos

## Instalación

1. **Clonar el Repositorio**

   Clona el repositorio desde GitHub:

   ```bash
   git clone https://github.com/tu_usuario/tu_repositorio.git
   ```

2. **Navegar al Directorio del Proyecto**

   ```bash
   cd tu_repositorio
   ```

3. **Construir el Proyecto**

   Utiliza Maven para construir el proyecto:

   ```bash
   mvn clean install
   ```

## Configuración

1. **Configurar las Propiedades del Servidor SMTP**

   Abre el archivo `src/main/resources/application.properties` y configura las propiedades del servidor SMTP. Aquí hay un ejemplo con valores ficticios:

   ```properties
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=tu_correo@gmail.com
   spring.mail.password=tu_contraseña
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

   Asegúrate de reemplazar `tu_correo@gmail.com` y `tu_contraseña` con las credenciales de tu servidor SMTP.

2. **Configuración Adicional**

   Puedes ajustar otras configuraciones según sea necesario. El archivo `application.properties` también contiene configuraciones para la base de datos y Swagger UI.

## Ejecución

1. **Iniciar la Aplicación**

   Para iniciar la aplicación, utiliza el siguiente comando:

   ```bash
   mvn spring-boot:run
   ```

2. **Acceder a la API**

   La API estará disponible en `http://localhost:8080`.

3. **Acceder a Swagger UI**

   La documentación de Swagger UI estará disponible en:

    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Uso de la API

### Generar Serie de Fibonacci

**Endpoint:** `POST /api/fibonacci/generate`

**Descripción:** Genera una serie de Fibonacci basada en la cadena de tiempo proporcionada y envía un correo electrónico con la serie generada.

**Request:**

- **Body:** JSON con la cadena de tiempo (formato `"HH:MM:SS"`)
- **Params:** `email` (dirección de correo electrónico para el envío)

**Response:**

- **Status:** `200 OK`
- **Body:** Lista de enteros representando la serie de Fibonacci generada

**Ejemplo:**

```bash
curl -X POST "http://localhost:8080/api/fibonacci/generate?email=ejemplo@correo.com" -H "Content-Type: application/json" -d "\"12:30:10\""
```

### Obtener Todas las Series de Fibonacci

**Endpoint:** `GET /api/fibonacci/all`

**Descripción:** Obtiene todas las series de Fibonacci almacenadas en la base de datos.

**Response:**

- **Status:** `200 OK`
- **Body:** Lista de objetos FibonacciSeries con `id`, `executionTime`, y `series`

**Ejemplo:**

```bash
curl -X GET "http://localhost:8080/api/fibonacci/all"
```

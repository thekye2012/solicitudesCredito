## Configuración e Instalación

Para configurar e iniciar el entorno de desarrollo y pruebas de esta API, sigue los pasos detallados a continuación:

1. **Clonar el Repositorio**

   Clona el repositorio usando el siguiente comando:
   ```
    git clone https://github.com/thekye2012/solicitudesCredito.git
    ```

2. **Abrir el Proyecto en Intellij IDEA**

Abre el proyecto clonado en Intellij IDEA para comenzar a trabajar en él.

3. **Configurar el `POM.xml`**

Abre el archivo `POM.xml` y configura las claves o el token de SONAR. Por defecto, se utilizan las credenciales `admin:admin`.

4. **Configuración de la Red del Servidor Docker**

Si la red de tu servidor Docker está personalizada, debes ingresar al archivo `.env` y modificar el `POSTGRES_HOST` para el servidor PostgreSQL, o ajustar el `POSTGRES_HOST` si se encuentra ocupado.

5. **Ejecución del Archivo `docker-compose.yml`**

Ejecuta el archivo `docker-compose.yml`. En caso de que la ejecución de `docker-compose` falle en el servicio "Solicitudes", sigue estos pasos:
- Ejecuta `mvn clean` y luego `mvn package`.
- Intenta iniciar nuevamente el servicio ejecutando el `docker-compose`.
- Si el problema persiste, verifica que tu base de datos PostgreSQL esté correctamente configurada.

6. **Acceso a la Documentación Swagger**

Una vez iniciado el servicio, ingresa a `http://localhost:3000/swagger-ui/index.html` para ver la documentación Swagger y probar los endpoints disponibles.


## Documentación de la API

A continuación, se detalla la documentación para los endpoints disponibles en nuestra API. Para cada endpoint, se describe el método HTTP, el cuerpo de la solicitud y el formato de la respuesta esperada.

### Alta de Solicitud de Crédito

- **Endpoint:** `/solicitudes/alta`
- **Método:** POST
- **Cuerpo de la solicitud:**

  ```json
  {
    "promotor": "SLP34/CURP",
    "empresa": "XXXXX",
    "cliente": {
      "nombre": "PERLA TOMASA",
      "apellidoPaterno": "CABRERA",
      "apellidoMaterno": "ROMAN"
    },
    "solicitud": {
      "idSolicitud": "110102044798",
      "monto": 167000,
      "producto": "IMSS",
      "tipoSolicitudStr": "CREDITO NUEVO",
      "idTipoSolicitud": "394",
      "tasa": 39,
      "plazo": 0,
      "frecuencia": "Semanal/Mensual/Catorcenal",
      "fechaSolicitud": "20220727"
    }
  }

  ```

- **Respuesta esperada:**

  ```json
  {
    "success": true,
    "status": 200,
    "data": {
      "idSolicitudCredito": 1,
      "promotor": "SLP34/CURP",
      "empresa": "XXXXX",
      "cliente": {
        "idCliente": 1,
        "nombre": "PERLA TOMASA",
        "apellidoPaterno": "CABRERA",
        "apellidoMaterno": "ROMAN"
      },
      "solicitud": {
        "id": 1,
        "idSolicitud": "110102044798",
        "monto": 167000,
        "producto": "IMSS",
        "tipoSolicitudStr": "CREDITO NUEVO",
        "idTipoSolicitud": "394",
        "tasa": 39,
        "plazo": 0,
        "frecuencia": "Semanal/Mensual/Catorcenal",
        "fechaSolicitud": "20220727",
        "idEstatus": "INGRESADO"
      }
    },
    "timestamp": "2024-02-12T03:52:07.638775800"
  }
  ```

### Cambio de Estatus de Solicitud de Crédito

- **Endpoint:** `/solicitudes/cambioEstatus`
- **Método:** PATCH
- **Cuerpo de la solicitud:**

  ```json
  {
    "idSolicitud": "110102044798",
    "idEstatus": "APROB",
    "motivo": [
      {
        "codigo": "123",
        "descripcion": "Documentos incompletos"
      }
    ],
    "fechaCambio": "20220727"
  }
  ```

- **Respuesta esperada:**

  ```json
  {
    "success": true,
    "status": 200,
    "data": {
      "id": 1,
      "idSolicitud": "110102044798",
      "monto": 167000,
      "producto": "IMSS",
      "tipoSolicitudStr": "CREDITO NUEVO",
      "idTipoSolicitud": "394",
      "tasa": 39,
      "plazo": 0,
      "frecuencia": "Semanal/Mensual/Catorcenal",
      "fechaSolicitud": "20220727",
      "idEstatus": "APROB"
    },
    "timestamp": "2024-02-12T03:52:28.707566"
  }
  ```
### Dispersion de Solicitud de Crédito

- **Endpoint:** `/solicitudes/dispersar`
- **Método:** PUT
- **Cuerpo de la solicitud:**

  ```json
  {
    "idSolicitud": "110102044798",
    "idCredito": 1234,
    "capitalDispersado": 100.50,
    "monto": 100.50,
    "tasa": 40.35,
    "plazo": 30,
    "frecuencia": "semanal"
  }
  ```

- **Respuesta esperada:**

  ```json
  {
    "success": true,
    "status": 200,
    "data": {
      "id": 1,
      "idCredito": 1234,
      "solicitudCredito": {
        "idSolicitudCredito": 1,
        "promotor": "SLP34/CURP",
        "empresa": "XXXXX",
        "cliente": {
          "idCliente": 1,
          "nombre": "PERLA TOMASA",
          "apellidoPaterno": "CABRERA",
          "apellidoMaterno": "ROMAN"
        },
        "solicitud": {
          "id": 1,
          "idSolicitud": "110102044798",
          "monto": 167000,
          "producto": "IMSS",
          "tipoSolicitudStr": "CREDITO NUEVO",
          "idTipoSolicitud": "394",
          "tasa": 39,
          "plazo": 0,
          "frecuencia": "Semanal/Mensual/Catorcenal",
          "fechaSolicitud": "20220727",
          "idEstatus": "DISPERSADO"
        }
      },
      "capitalDispersado": 100.5,
      "monto": 100.5,
      "plazo": 30,
      "tasa": 40.35,
      "frecuencia": "semanal",
      "fechaCredito": "2024-02-12"
    },
    "timestamp": "2024-02-12T03:52:51.214961300"
  }
  ```


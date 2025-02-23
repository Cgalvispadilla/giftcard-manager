# giftcard-manager

Tecnologías utilizadas:

- **Java 23**
- **Spring boot 3.4.3**
- **Maven**
- **H2 Database**
- **JWT**

## Descripción de la necesidad

Se requiere un sistema para administrar tarjetas de regalo (Gift Cards) en formato
monolítico usando Java (Spring Boot).

Se busca que el servicio permita:

1. Crear y gestionar tarjetas de regalo (registro, consultas, actualizaciones y
   borrados).
2. Registrar y autenticar usuarios que utilizarán el sistema (básico).
3. Enviar notificaciones (simuladas o reales) cuando se cree o redima una
   tarjeta.
4. Mantener una arquitectura de capas para mayor claridad y mantenibilidad.

## Requerimientos

1. Módulo de Usuarios
    - Registro de nuevos usuarios.
    - Autenticación básica (JWT, sesiones o similar).
    - Se recomienda proteger endpoints críticos para solo usuarios
      autenticados.
2. Módulo de Tarjetas Regalo
    - Crear una nueva tarjeta regalo (con campos como código, monto,
      fechaCreacion, fechaExpiracion).
    - Consultar tarjetas (por ID, listado completo).
    - Actualizar tarjetas (monto, estado).
    - Eliminar tarjetas.
    - Redimir la tarjeta (simular uso o marcar como consumida).
    - Notificaciones en creación o redención (puede ser un correo real con
      JavaMail o una simple impresión por consola para simular).

## Compilación y ejecución

### prerequisitos:

1. Configuración smtp.gmail.com para envío de correos electrónicos:

para realizar la configuración debe ir a la pagina https://myaccount.google.com/apppasswords y generar una contraseña de
aplicación para la aplicación de correo electrónico, luego
se debe modificar el archivo application.properties ubicado en src/main/resources

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=email
spring.mail.password=password generada
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Al usar H2 Database, se puede acceder a la consola de administración de la base de datos en la siguiente
URL: http://localhost:8080/h2-console y esta no requiere configuración adicional.

2. Uso de postgresql: si desea usar postgresql, debe modificar el archivo application.properties ubicado en
   src/main/resources

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/giftcard
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

se debe agregar en el archivo pom.xml la siguiente dependencia

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

Ademas, debe tener instalado docker para generar la imagen de la siguiente manera

```bash
  docker run --name giftcard-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=giftcard -p 5432:5432 -d postgres
```

### Compilación

Para compilar el proyecto, ejecute el siguiente comando en la raíz del proyecto:

```bash
  mvn clean install
```

### Ejecución

Para ejecutar el proyecto, ejecute el siguiente comando en la raíz del proyecto:

```bash
  mvn spring-boot:run
```

## Endpoints

Puede encontrar la coleccion de postman en la siguiente ruta: src/main/resources/GiftCard
Manager.postman_collection.json

### Create User

- Method: POST
- URL: http://localhost:8080/api/auth/register
- Headers: Content-Type: application/json
- Body:

```json
{
  "userName": "cgalvis",
  "password": "12345678CGp*&"
} 
``` 

### Login

- Method: POST
- URL: http://localhost:8080/api/auth/login
- Headers: Content-Type: application/json
- Body:

```json
{
  "userName": "cgalvis",
  "password": "12345"
}
```

### Create GiftCard

- Method: POST
- URL: http://localhost:8080/api/giftcards
- Headers: Content-Type: application/json, Authorization: Bearer {{token}}
- Body:

```json
{
  "code": "QWRASA2",
  "amount": 999999,
  "emailTo": "carlosgalvispadilla@gmail.com",
  "expirationDate": "2025-12-22T00:00:00.000Z"
}
```

### Find GiftCard By Id

- Method: GET
- URL: http://localhost:8080/api/giftcards/{{id}}
- Headers: Authorization: Bearer {{token}}

### Find all GiftCards

- Method: GET
- URL: http://localhost:8080/api/giftcards
- Headers: Authorization: Bearer {{token}}

### Update GiftCard

- Method: PUT
- URL: http://localhost:8080/api/giftcards/1
- Headers: Content-Type: application/json, Authorization: Bearer {{token}}
- Body:

```json 
{
  "amount": 9999,
  "consumed": true
}
```

### Delete GiftCard

- Method: DELETE
- URL: http://localhost:8080/api/giftcards/1
- Headers: Authorization: Bearer {{token}}

### Redeem GiftCard

- Method: POST
- URL: http://localhost:8080/api/giftcards/1/redeem
- Headers: Authorization: Bearer {{token}}



  
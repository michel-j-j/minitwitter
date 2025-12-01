# Minitwitter

Minitwitter es una aplicación web simple, al estilo de Twitter, que permite a los usuarios publicar mensajes cortos, ver un timeline y retuitear los mensajes de otros. Este proyecto está construido con un backend de **Spring Boot** y un frontend de **React**.

## ✨ Características

*   **Gestión de Usuarios:** Creación de nuevos usuarios en el sistema.
*   **Publicación de Tweets:** Los usuarios pueden publicar mensajes de hasta 280 caracteres.
*   **Timeline:** Visualización de los tweets de los usuarios a los que sigues (funcionalidad a futuro) o un timeline global.
*   **Retweets:** Los usuarios pueden retuitear los mensajes de otros.

## 🛠️ Tecnologías Utilizadas

*   **Backend:**
    *   Java
    *   Spring Boot
    *   Spring Data JPA (Hibernate)
    *   Base de datos en memoria H2
    *   Maven / Gradle
*   **Frontend:**
    *   React
    *   Tailwind CSS
    *   Vite / Create React App

## 🚀 Cómo Empezar

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

Asegúrate de tener instalado lo siguiente:
*   JDK 17 o superior
*   Maven o Gradle
*   Node.js y npm (o yarn)

### 1. Configuración del Backend

```sh
# 1. Clona el repositorio
git clone https://github.com/tu-usuario/minitwitter.git
cd minitwitter

# 2. Navega al directorio del backend y compila el proyecto
# (Asegúrate de estar en la raíz del proyecto donde está el pom.xml o build.gradle)
mvn clean install

# 3. Ejecuta la aplicación Spring Boot
mvn spring-boot:run
```

El backend estará disponible en `http://localhost:8080`.

### 2. Configuración del Frontend

```sh
# 1. Abre una nueva terminal y navega al directorio del frontend
cd frontend # O el nombre de tu carpeta de frontend

# 2. Instala las dependencias
npm install

# 3. Inicia el servidor de desarrollo de React
npm run dev
```

El frontend estará disponible en `http://localhost:5173` (o el puerto que configure tu herramienta, como 3000).

## ⚙️ Uso y Endpoints de la API

Para interactuar con la aplicación, puedes usar un cliente de API como `curl` o Postman.

### **Paso Fundamental: Crear Usuarios**

**Es obligatorio crear usuarios antes de poder realizar cualquier otra acción.** La aplicación no funcionará correctamente sin usuarios existentes.

Ejecuta los siguientes comandos en tu terminal para crear algunos usuarios de ejemplo:

```sh
# --- 1. CREAR USUARIOS ---
curl -X POST "http://localhost:8080/api/users?userName=alice"
curl -X POST "http://localhost:8080/api/users?userName=bobsmith"
curl -X POST "http://localhost:8080/api/users?userName=charlie"
curl -X POST "http://localhost:8080/api/users?userName=diana_dev"
curl -X POST "http://localhost:8080/api/users?userName=edward123"
```

### Otros Endpoints

Una vez creados los usuarios, puedes interactuar con la API para crear tweets y retweets.

#### **2. Crear un Tweet**

Publica un nuevo tweet como uno de los usuarios creados (por ejemplo, `alice`, cuyo ID de usuario suele ser `1`).

```sh
curl -X POST "http://localhost:8080/api/tweets" \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 1,
           "text": "¡Hola, este es mi primer tweet en Minitwitter!"
         }'
```

#### **3. Hacer un Retweet**

Haz un retweet de un tweet existente (por ejemplo, el tweet con ID `1`) como otro usuario (por ejemplo, `bobsmith`, cuyo ID de usuario suele ser `2`).

```sh
curl -X POST "http://localhost:8080/api/tweets/retweet" \
     -H "Content-Type: application/json" \
     -d '{
           "userId": 2,
           "originalTweetId": 1
         }'
```

#### **4. Obtener el Timeline de un Usuario**

Visualiza todos los tweets y retweets de un usuario específico.

```sh
curl -X GET "http://localhost:8080/api/users/1/timeline"
```

## 📂 Estructura del Proyecto

```
minitwitter/
├─── src/main/java/tpindividual/minitwitter/  # Lógica del backend (controladores, servicios, repositorios, entidades)
├─── src/main/resources/                     # Archivos de configuración de Spring (application.properties)
├─── frontend/                               # Código fuente del frontend en React
│    ├─── src/
│    └─── ...
├─── pom.xml                                 # Dependencias y configuración de Maven
└─── README.md                               # Este archivo
```

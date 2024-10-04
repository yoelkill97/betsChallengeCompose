
# BetsChallenge - Historial de Apuestas 🏆

Este es un proyecto de una aplicación de apuestas desarrollada en Kotlin utilizando `Jetpack Compose` como herramienta de UI, `Firebase` como backend para autenticación y almacenamiento de datos, y la arquitectura `MVVM` con `Hilt` para la inyección de dependencias. La aplicación permite a los usuarios ver su historial de apuestas, filtrar apuestas por estado y tipo, y ver detalles de cada apuesta.

## 📝 Descripción del Proyecto

La aplicación **BetsChallenge** permite a los usuarios:
- Autenticarse mediante un correo y contraseña utilizando **Firebase Authentication**.
- Ver un listado completo de sus apuestas en la sección de "Historial de Apuestas".
- Filtrar las apuestas por `estado` (open, close, won) y `tipo` (simple, system).
- Buscar apuestas por nombre del evento utilizando un campo de búsqueda en la barra superior.
- Ver detalles adicionales de cada apuesta.

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalados los siguientes requisitos:

- **Android Studio**: versión recomendada `Arctic Fox` o superior.
- **JDK**: Java 8 o superior.
- **Firebase Project**: Debes tener configurado un proyecto en [Firebase Console](https://console.firebase.google.com/).
- **Configuración de Firebase Authentication**: habilitar la autenticación con correo y contraseña.

## 🚀 Configuración y Ejecución del Proyecto

Sigue estos pasos para configurar y ejecutar la aplicación en tu entorno local:

### 1. Clonar el Repositorio

Clona el repositorio en tu entorno local usando el siguiente comando:

```bash
git clone https://github.com/yoelkill97/betsChallengeCompose.git
```

### 2. Importar el Proyecto en Android Studio

1. Abre **Android Studio**.
2. Selecciona **File > Open** y elige la carpeta del proyecto clonado.
3. Asegúrate de que el proyecto esté sincronizado correctamente con `Gradle`.

### 3. Configurar Firebase

1. Inicia sesión en [Firebase Console](https://console.firebase.google.com/).
2. Crea un nuevo proyecto o selecciona un proyecto existente.
3. Agrega la aplicación de Android al proyecto usando el nombre de paquete de la app.
4. Descarga el archivo `google-services.json` y colócalo en la carpeta `app/` del proyecto.
5. Asegúrate de que la autenticación con correo y contraseña esté habilitada.

### 4. Ejecutar la Aplicación

1. Conecta un dispositivo físico o inicia un emulador de Android.
2. Ejecuta el proyecto desde Android Studio con **Run > Run 'app'** .
3. Inicia sesión con un usuario existente en Firebase o regístrate como nuevo usuario.


### ✨ Principales Componentes

- **`LoginPage`**: Pantalla de inicio de sesión con autenticación de Firebase.
- **`RegisterPage`**: Pantalla de registro para nuevos usuarios.
- **`BetList`**: Lista de apuestas con opción de filtrar y buscar.
- **`BetDetailPage`**: Detalles de cada apuesta con más información.

### 🗂️ Principales Paquetes

- **`data`**: Contiene las clases y lógica relacionadas con la obtención de datos (repositorios, fuentes de datos).
- **`domain`**: Contiene las entidades y casos de uso.
- **`presentation`**: Contiene la lógica de presentación y las pantallas.

## 🔧 Funcionalidades Clave

1. **Autenticación de Usuarios**:
   - Login y registro utilizando Firebase Authentication.
   - Validación de entradas (correo electrónico y contraseña).
2. **Visualización de Apuestas**:
   - Mostrar la lista completa de apuestas del usuario.
   - Filtrar apuestas por estado y tipo.
   - Buscar apuestas por nombre del evento.
3. **Detalles de Apuestas**:
   - Mostrar información detallada de cada apuesta con un diseño visual.
4. **Interfaz de Usuario**:
   - Implementación de `Jetpack Compose` para una interfaz moderna y responsive.

## 🛠️ Tecnologías Utilizadas

- **Kotlin**: Lenguaje principal de desarrollo.
- **Jetpack Compose**: Framework de UI moderno para la creación de interfaces.
- **Firebase**: Para la autenticación y almacenamiento en tiempo real.
- **Hilt**: Inyección de dependencias simplificada.
- **MVVM**: Patrón de arquitectura para organizar el código.

## 📦 Dependencias Principales

Las principales dependencias utilizadas en el proyecto se encuentran en el archivo `build.gradle`:

```gradle
dependencies {
    // Firebase
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.firebase:firebase-database-ktx'

    // Jetpack Compose
    implementation "androidx.compose.ui:ui:1.3.0"
    implementation "androidx.navigation:navigation-compose:2.4.0-alpha10"

    // Hilt - Dependency Injection
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-android-compiler:2.38.1"

    // Otros
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"
    implementation "androidx.activity:activity-compose:1.3.1"
}
```

## 🐞 Depuración y Solución de Problemas

Si encuentras problemas al ejecutar la aplicación, verifica lo siguiente:

1. Asegúrate de tener el archivo `google-services.json` en la carpeta `app`.
2. Revisa que la base de datos de Firebase esté correctamente configurada y con permisos de lectura/escritura.
3. Asegúrate de que las dependencias de `Gradle` estén correctamente sincronizadas.
4. Verifica las reglas de seguridad de Firebase para asegurar que la aplicación pueda acceder a los datos.


¡Gracias ! Si tienes alguna pregunta o sugerencia, no dudes en abrir un issue o contactar al equipo de desarrollo.

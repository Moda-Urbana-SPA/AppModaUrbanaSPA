# AppModaUrbanaSPA-main

Aplicación Android para la venta de ropa, lista para ejecutarse localmente con un catálogo incluido.

## Requisitos
- Android Studio con el Android SDK correspondiente.
- compileSdk: 35
- minSdk: 24
- targetSdk: 35

## Instalación y ejecución
Clona el repositorio y genera el build con Gradle:
```bash
git clone https://github.com/Moda-Urbana-SPA/AppModaUrbanaSPA.git
cd AppModaUrbanaSPA
./gradlew assembleDebug
```
Después, abre el proyecto en Android Studio y ejecuta el módulo `app` en un emulador o dispositivo.

## Estructura principal del repositorio
En la raíz del proyecto encontrarás, entre otros, los siguientes elementos:
- `.gradle`
- `.idea`
- `app/`
- `gradle/`
- `gradlew`, `gradlew.bat`
- `gradle.properties`, `local.properties`
- `settings.gradle.kts`

Dentro del módulo `app/` están los fuentes y recursos principales:
- `app/build.gradle.kts`
- `app/src/`

## Datos y recursos incluidos
- El catálogo de productos está empaquetado en el archivo de assets: `app/src/main/assets/products.json` (contiene 8 productos).
- Las imágenes que muestra la app provienen de URLs externas incluidas en ese JSON; algunas muestras:
  - `https://cl-dam-resizer.ecomm.cencosud.com/.../780528-0001-001.jpg`
  - `https://encrypted-tbn0.gstatic.com/images?q=...`
  - `https://http2.mlstatic.com/D_NQ_NP_949458-MLU78166593109_082024-O.webp`
  - `https://images.pexels.com/photos/6389841/pexels-photo-6389841.jpeg`
  - `https://lsco.scene7.com/is/image/lsco/A34940025-front-gstk?$laydownfront$`
  - `https://m.media-amazon.com/images/I/71xppkZgaHL._AC_UY1000_.jpg`

## Permisos
- `android.permission.INTERNET` — necesario para descargar las imágenes remotas del catálogo.

## Perfiles

### Descripción general
La aplicación maneja un modelo de cliente que se utiliza en las pantallas de Checkout y Perfil. Los atributos que se usan habitualmente son: `name`, `email`, `phone`, `address`, `comuna` y `notes`.

### Modelo de datos
- Clase: `app/src/main/java/com/example/modaurbanaspa/model/Customer.kt`  
  Estructura: `data class Customer(name, email, phone?, address, comuna, notes?)`

### Pantalla de Perfil
- Interfaz: `app/src/main/java/com/example/modaurbanaspa/ui/screens/ProfileScreen.kt`  
  Muestra los datos del cliente y ofrece opciones para editar la información o cerrar sesión.

### Flujos relacionados
- Checkout: `app/src/main/java/com/example/modaurbanaspa/ui/screens/CheckoutScreen.kt` — el proceso de compra usa los datos del cliente.  
- Estado del carrito: `app/src/main/java/com/example/modaurbanaspa/viewmodel/CartViewModel.kt` — expone y gestiona la información necesaria para el checkout y el perfil.

### Persistencia y origen de datos del perfil
En el código actual la información de perfil se gestiona dentro de la app y no se sincroniza con un servidor.

## 3. Arquitectura y flujo

### Patrón general
La aplicación sigue una separación de responsabilidades: **UI (Compose) ⇄ ViewModel ⇄ Repositorio**. Las operaciones de I/O se ejecutan con coroutines y el estado se comparte con `StateFlow` o `LiveData` hacia la UI.

### Módulos y paquetes clave
- `app/src/main/java/com/example/modaurbanaspa/ui/`  
  - `screens/` — pantallas: `CatalogScreen.kt`, `ProductDetailScreen.kt`, `CartScreen.kt`, `CheckoutScreen.kt`, `ProfileScreen.kt`.  
  - `navigation/` — `AppNavGraph.kt` (gestión de rutas y navegación).
- `app/src/main/java/com/example/modaurbanaspa/viewmodel/`  
  - `CartViewModel.kt` y otros ViewModels que controlan el estado de la UI.
- `app/src/main/java/com/example/modaurbanaspa/repository/`  
  - `LocalCatalogRepository.kt` — encargado de cargar los productos desde el JSON empaquetado.
- `app/src/main/java/com/example/modaurbanaspa/model/`  
  - Modelos: `Product.kt`, `Variant.kt`, `Customer.kt`.

### Fuente de datos
- **Local (assets):** `app/src/main/assets/products.json` — lectura desde `LocalCatalogRepository.getAll()` (se abre el recurso con `context.assets.open("products.json")` y se parsea a objetos `Product`).
- **Recursos externos:** las imágenes provienen de las URLs contenidas en el JSON; por eso la app requiere permiso de Internet.

### Flujo de carga del catálogo (resumen)
1. La pantalla de catálogo solicita la lista al ViewModel.  
2. El ViewModel obtiene los datos a través de `LocalCatalogRepository.getAll()` en una coroutine.  
3. El repositorio lee el JSON desde `assets`, lo parsea y devuelve `List<Product>`.  
4. El ViewModel publica la lista mediante `StateFlow`/`LiveData`.  
5. La pantalla consume ese estado y dibuja la lista, usando Coil para cargar las imágenes remotas.

### Flujo carrito / checkout (resumen)
1. El usuario añade productos desde el catálogo o la pantalla de detalle.  
2. `CartViewModel` mantiene el contenido del carrito y expone el estado.  
3. En la pantalla del carrito y en Checkout se muestra el carrito y se recopila la información del cliente para completar la compra.

### Navegación
- `AppNavGraph.kt` — rutas entre Catalog → Detail → Cart → Checkout → Profile.

### Concurrencia y estado
- Coroutines para operaciones de I/O y trabajo en background.  
- `StateFlow` y `LiveData` para propagar cambios de estado hacia la UI.

### Recursos y permisos
- `app/src/main/AndroidManifest.xml` — incluye `android.permission.INTERNET`.

## Funcionalidades del proyecto

- **Catálogo de productos (local)**  
  - Datos: `app/src/main/assets/products.json` (8 items).  
  - Repositorio: `app/src/main/java/com/example/modaurbanaspa/repository/LocalCatalogRepository.kt`.

- **Listado de productos (UI)**  
  - Pantalla: `app/src/main/java/com/example/modaurbanaspa/ui/screens/CatalogScreen.kt`.  
  - Las imágenes se cargan con Coil desde las URLs contenidas en el JSON.

- **Carrito de compras**  
  - Lógica y estado: `app/src/main/java/com/example/modaurbanaspa/viewmodel/CartViewModel.kt`.

- **Perfil de usuario**  
  - Interfaz: `app/src/main/java/com/example/modaurbanaspa/ui/screens/ProfileScreen.kt`.  
  - El perfil se utiliza en el flujo de Checkout.

- **Operaciones asíncronas**  
  - Repositorios y ViewModels usan `kotlinx.coroutines` para evitar bloquear la interfaz.

- **Recursos externos**  
  - Las imágenes del catálogo se descargan desde Internet; por eso la app necesita permiso de red.


# Aplicación sobre Pokedex

## ¿Qué es?
Es una aplicación que te permite gestionar tus propios Pokémon: explorarlos, capturarlos, ver sus detalles y almacenarlos en tu propia Pokedex digital. Es como un juego de Pokemon, pero con un toque más orientado a la gestión de tus Pokemon.

Actualmente, la app aún está en desarrollo y le faltan algunas funcionalidades (bastantes), pero ya tiene varias características interesantes.

## Características principales
- **Autenticación de usuarios**: Puedes registrarte e iniciar sesión para tener tu propia cuenta. Se ha incluido autentificación mediante tu cuenta de Google.
- **Lista de todos los Pokemon**: Explora la lista de Pokémon. Al clicar sobre cualquiera, puedes ver sus detalles (peso, altura, tipo). Desde esta nueva pantalla, podrás capturar los que más te gusten.
- **Pokedex (Lista de Pokemon capturados)**: Una vez captures un Pokémon, aparecerá en tu lista de "Pokedex" donde podrás ver toda la información de los Pokémon que has guardado. Tendrás un botón donde podrás eliminarlo de la pokedex. Recuerda que desde el menú de ajustes se ha creado un “switch” que nos permite “habilitar / inhabilitar” la eliminación de los pokemon de la pokedex.
- **Ajustes**: Tienes un par de opciones para personalizar tu experiencia, como el modo oscuro y la opción de elegir el idioma (aunque está en progreso).
- **Menú lateral**: Un menú lateral facilita la navegación por la aplicación.

## Lo que falta por hacer:
- **Idiomas**: El cambio de idioma no está completamente implementado. No me llega a cambiar de idioma de "Español" a "Inglés", ni viceversa.
- **Actualización de Pokémon capturados en recycler view instantánea**: No he podido conseguir que se detecte en recycler view que el pokemon ha sido capturado, por lo que ese cardview no cambia a color rojo de forma instantanea, hasta que no actualizamos vista del recycler, pasando a otra pestaña o bajando / subiendo en recycler. Una vez que cambiamos de vistaaparece correctamente.
- **Fallos adicionales**: Bastantes más, pero le he dedicado mucho tiempo.

## Tecnologías utilizadas
- **Firebase**: Para la autenticación de usuarios y la base de datos (Firestore) donde se guardan los Pokémon capturados.
- **Retrofit**: Usado para hacer peticiones a la API de Pokémon y obtener la información de los Pokémon.
- **Picasso**: Para cargar las imágenes de los Pokémon de forma rápida y eficiente.
- **RecyclerView**: Para mostrar la lista de Pokémon y capturados

## ¿Cómo empezar?

### Clonación del repositorio
1. Clona el repositorio en tu máquina local con el siguiente comando:
   ```bash
   git clone https://github.com/ramireramos/tarea03

2. Entra en la carpeta del proyecto:
   ```bash
   cd tarea03
3. Abre el proyecto con Android Studio.
4. Asegúrate de configurar Firebase.
5. Sincroniza el proyecto con Gradle.
6. Ejecuta la app en tu dispositivo o emulador Android.

## Configuración de Firebase
- Necesitarás configurar Firebase en el proyecto para usar la autenticación y Firestore correctamente.

## Reflexiones
Este proyecto ha sido una excelente forma de practicar mis habilidades de desarrollo de aplicaciones Android. He tenido la oportunidad de aprender cómo integrar Firebase para la autenticación y el almacenamiento de datos, así como cómo hacer uso de Retrofit para interactuar con APIs externas. Aún falta mucho por hacer, pero he aprendido bastante durante el proceso.

Algunos de los desafíos han sido la integración con Firebase Firestore, especialmente con la gestión de usuarios y el manejo de datos asíncronos (que todavía está en proceso). Pero, poco a poco, la app ha ido tomando forma.

Me ha costado bastante la uesta en marcha y verificar que todo funcione, aunque todavía hay caracateristicas qe no funcionan como quisiera, me estaré esforzando al máximo para conseguir el objetivo.
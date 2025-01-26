plugins {
    id("com.android.application")  // Plugin para aplicaciones Android

    // Agrega el plugin de Google Services para integraciones como Firebase
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        // Configuración para la firma de la aplicación en modo debug
        create("app") {
            storeFile = file("C:\\Users\\surfc\\.android\\debug.keystore")
        }
    }

    namespace = "ramirezramos.francisco.tarea03"  // Define el namespace del paquete
    compileSdk = 34  // SDK de compilación

    defaultConfig {
        // Información básica de la aplicación
        applicationId = "ramirezramos.francisco.tarea03"  // ID de la aplicación
        minSdk = 24  // SDK mínimo requerido para la app
        targetSdk = 34  // SDK objetivo de la app
        versionCode = 1  // Código de versión (usado para actualizaciones)
        versionName = "1.0"  // Nombre de la versión

        // Define el runner de pruebas
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {

            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Configura la compatibilidad con Java 11
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        // Activa las características de ViewBinding y DataBinding
        viewBinding = true  // Usar ViewBinding
        dataBinding = true  // Usar DataBinding
    }
}

dependencies {

    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))

    // Dependencias relacionadas con Firebase
    implementation("com.google.firebase:firebase-auth-ktx")  // Firebase Authentication (KTX)
    implementation("com.google.android.gms:play-services-auth:20.7.0")  // Servicios de autenticación de Google
    implementation("com.google.firebase:firebase-auth:22.0.0")  // Firebase Authentication
    implementation("com.google.firebase:firebase-analytics")  // Firebase Analytics
    implementation("com.google.firebase:firebase-firestore:24.1.0")  // Firestore para la base de datos
    implementation("com.squareup.retrofit2:retrofit:2.9.0")  // Retrofit para hacer peticiones HTTP
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")  // Conversor de Retrofit a JSON usando Gson
    implementation("com.squareup.picasso:picasso:2.8")  // Picasso para cargar imágenes

    // Otras dependencias de la app
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)  // LiveData
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.auth)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.gevcorst.k_forceopenweather"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gevcorst.k_forceopenweather"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        val GEOCODING_API_KEY =
            gradleLocalProperties(rootDir).getProperty("GEOCODING_API_KEY")
        buildConfigField("String", "GEOCODING_KEY", "$GEOCODING_API_KEY")
        val OPEN_WEATHER_API_KEY = gradleLocalProperties(rootDir).getProperty("OPENWEATHERMAP_KEY")
        buildConfigField("String", "OPEN_WEATHER_KEY", "$OPEN_WEATHER_API_KEY")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.10.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.compose.material3:material3")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")
    //glide Image painter
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.github.bumptech.glide:compose:1.0.0-alpha.3")
    //coil async image loader
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("androidx.datastore:datastore-core:1.1.0-alpha05")
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha05")
    implementation("com.google.dagger:hilt-android-testing:2.48.1")
    implementation ("org.slf4j:slf4j-nop:2.0.9")
    implementation("androidx.compose.ui:ui-test-junit4:1.6.0-alpha07")
    implementation("androidx.arch.core:core-testing:2.2.0")

    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    //moshi
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("com.squareup.moshi:moshi:1.15.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    //navigation-compose
    implementation ("androidx.navigation:navigation-compose:2.7.4")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-beta01")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    // core
    testImplementation( "junit:junit:4.13.2")

// for coroutine handling
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

// mockito for creating mocks and templates
    //MockK
    testImplementation ("io.mockk:mockk:1.13.8")
    androidTestImplementation("io.mockk:mockk-android:1.13.8")
    testImplementation ("org.mockito:mockito-core:5.6.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")

// turbine for testing the flows
    testImplementation ("app.cash.turbine:turbine:1.0.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
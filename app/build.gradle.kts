plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.internprojectdemoarapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.internprojectdemoarapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    androidResources {
        noCompress += "glb"
    }

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // ✅ Force ARCore to 1.31.0 (which is compatible with Sceneform 1.21.0)
    configurations.all {
        resolutionStrategy {
            force("com.google.ar:core:1.31.0")
        }
    }

    // ✅ Use correct Sceneform version (1.21.0 is latest available)
    implementation("com.gorisse.thomas.sceneform:sceneform:1.21.0")
    implementation("com.gorisse.thomas.sceneform:ux:1.21.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

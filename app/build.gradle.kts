plugins {
  id(Config.Plugins.androidApplication)
  id(Config.Plugins.kotlinAndroid)
}

android {
  compileSdkVersion(Config.Versions.compileSDK)
  buildToolsVersion(Config.Versions.buildTools)

  defaultConfig {
    applicationId = "com.sergiofierro.mailapp"
    minSdkVersion(Config.Versions.minSDK)
    targetSdkVersion(Config.Versions.targetSDK)
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "includes" to listOf("*.jar"))))
  implementation(Config.Dependencies.kotlin)
  implementation(Config.Dependencies.androidCore)
  implementation(Config.Dependencies.appcompat)
  implementation(Config.Dependencies.constraintLayout)
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("androidx.constraintlayout:constraintlayout:2.0.1")
  testImplementation(Config.Dependencies.junit)
} 
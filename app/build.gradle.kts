android {
    namespace = "info.nightscout.androidaps"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "info.nightscout.androidaps"
        minSdk = 23
        targetSdk = 34
        versionCode = 23001
        versionName = "3.0.0.1"
        
        // 添加构建配置字段
        buildConfigField("boolean", "CI_BUILD", System.getenv("CI") ?: "false")
    }

    signingConfigs {
        create("release") {
            // 从环境变量获取签名配置
            storeFile = file(System.getenv("STORE_FILE") ?: "release.keystore")
            storePassword = System.getenv("STORE_PASSWORD") ?: ""
            keyAlias = System.getenv("KEY_ALIAS") ?: ""
            keyPassword = System.getenv("KEY_PASSWORD") ?: ""
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // 应用签名配置
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // 添加构建特性
    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        // 设置Java兼容版本
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // 确保使用最新版本的依赖
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    // 添加其他必要依赖...
}

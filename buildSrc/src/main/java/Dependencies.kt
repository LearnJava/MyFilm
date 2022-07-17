import org.gradle.api.JavaVersion

object Versions {
    //AndroidX
    const val androidX = "1.3.1"
    //Kotlin
    const val kotlinKTX = "1.6.0"
    const val kotlinJDK7 = "1.5.20"
    const val kotlinCoroutinescore = "1.5.1"
    const val kotlinCoroutineandroid = "1.5.0"
    //Design
    const val designMaterial = "1.4.0"
    const val designConstrlayout = "2.1.1"
    // Test
        // Core
    const val testJunit = "4.13.2"
    const val testExt = "1.1.2"
    const val testEspressocore = "3.3.0"
    const val testCore = "2.1.0"
    const val testCoroutines = "1.4.3"
        // Mockito
    const val testMockitoCore = "3.3.3"
    const val testMockitoInline = "2.8.9"
    const val testMockitoKotlin = "1.5.0"
        // Robolectric
    const val testRobolectric = "4.5.1"
    const val testRobolectricCore = "1.4.0"
    const val testRobolectricEspresso = "3.3.0"
        // UI Automator
    const val testUIAutomator = "2.2.0"
    const val testUIAutomatorrules = "1.4.0-alpha05"
    // Fragment
    const val testFragment = "1.4.1"
    // RecyclerView
    const val testRecyclerView = "3.4.0"
    // Room
    const val room = "2.4.0"
    const val roomJDBC = "3.34.0"
    // Koin
    const val koin = "3.1.2"
    const val koinRetrofitcoroutines = "0.9.2"
    const val koinTestsandroidx = "1.0.0"
    // Coroutines
    const val coroutines = "1.3.9"
    // Retrofit
    const val retrofit = "2.9.0"
    const val retrofitGson = "2.7.1"
    const val retrofitRxjava2 = "2.4.0"
    const val retrofitRxjava3 = "2.9.0"
    const val retrofit2CoroutinesAdapter = "0.9.2"
    // OkHTTP
    const val okHTTP = "4.6.0"
    // Glide
    const val glide = "4.11.0"
    // Picasso
    const val picasso = "2.71828"
    // RxJava
    //const val rxJava = "3.0.0"
    const val rxJavaAndroid = "2.1.1"
    const val rxJavaRxJava = "3.0.0-RC3"
    // Cicerony
    const val cicerony = "6.6"
    // Lifecycle
    const val lifecycle = "2.2.0"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidX}"
}

object Kotlin {
    const val ktx = "androidx.core:core-ktx:${Versions.kotlinKTX}"
    const val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinJDK7}"
    const val coroutinescore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutinescore}"
    const val coroutinesandroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutineandroid}"
}

object Design {
    //You should not use the com.android.support and com.google.android.material dependencies in your app at the same time
    const val material = "com.google.android.material:material:${Versions.designMaterial}"
    const val constrlayout = "androidx.constraintlayout:constraintlayout:${Versions.designConstrlayout}"
}

object Tests {
    // Core
    const val junit = "junit:junit:${Versions.testJunit}"
    const val ext = "androidx.test.ext:junit:${Versions.testExt}"
    const val espressocore = "androidx.test.espresso:espresso-core:${Versions.testEspressocore}"
    const val filetreedir = "'libs'"
    const val filetreeinclude = "'*.jar'"
    const val core = "androidx.arch.core:core-testing:${Versions.testCore}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.testCoroutines}"
    // Mockito
    const val mockitocore = "org.mockito:mockito-core:${Versions.testMockitoCore}"
    const val mockitoinline = "org.mockito:mockito-inline:${Versions.testMockitoInline}"
    const val mockitokotlin = "com.nhaarman:mockito-kotlin:${Versions.testMockitoKotlin}"
    // Robolectric
    const val robolectric = "org.robolectric:robolectric:${Versions.testRobolectric}"
    const val robolectriccore = "androidx.test:core:${Versions.testRobolectricCore}"
    const val robolectricrunner = "androidx.test:runner:${Versions.testRobolectricCore}"
    const val robolectrictruth = "androidx.test.ext:truth:${Versions.testRobolectricCore}"
    const val robolectricespressocore =
        "androidx.test.espresso:espresso-core:${Versions.testRobolectricEspresso}"
    const val robolectricespressointents =
        "androidx.test.espresso:espresso-intents:${Versions.testRobolectricEspresso}"
    // UI Automator
    const val uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.testUIAutomator}"
    const val uiautomatorrules = "androidx.test:rules:${Versions.testUIAutomatorrules}"
    // Fragment
    const val fragment = "androidx.fragment:fragment-testing:${Versions.testFragment}"
    // RecyclerView
    const val recyclerView = "androidx.test.espresso:espresso-contrib:${Versions.testRecyclerView}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
    const val m1Support = "org.xerial:sqlite-jdbc:${Versions.roomJDBC}"
    const val rxJava3 = "androidx.room:room-rxjava3:${Versions.room}"
    const val ktx = "androidx.room:room-ktx:${Versions.room}"
}

object Koin {
    //Koin core features
    const val core = "io.insert-koin:koin-core:${Versions.koin}"
    //Koin main features for Android (Scope,ViewModel ...)
    const val android = "io.insert-koin:koin-android:${Versions.koin}"
    //Koin Java Compatibility
    const val compat = "io.insert-koin:koin-android-compat:${Versions.koin}"
    // Koin for Retrofit 2 coroutines (needs for CoroutineCallAdapterFactory())
    const val retrofitcoroutines = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.koinRetrofitcoroutines}"
    // Koin for Tests
    const val testsandroidx = "androidx.test:core:${Versions.koinTestsandroidx}"
    const val testsio = "io.insert-koin:koin-test:${Versions.koin}"
}

object Coroutines {
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
}

object Retrofit {
    const val runtime = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val rxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitRxjava2}"
    const val rxJava3 = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofitRxjava3}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHTTP}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofit2CoroutinesAdapter}"
}

object OkHTTP {
    const val runtime = "com.squareup.okhttp3:okhttp:${Versions.okHTTP}"
}

object Glide {
    const val runtime = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Picasso {
    const val runtime = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object RxJava {
    //    const val android = "io.reactivex.rxjava3:rxandroid:${Versions.rxJava}"
//    const val reactive = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
    const val android = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroid}"
    const val reactive = "io.reactivex.rxjava3:rxjava:${Versions.rxJavaRxJava}"
}

object Cicerony {
    const val runtime = "com.github.terrakok:cicerone:${Versions.cicerony}"
}

object Lifecycle {
    const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    // LiveData
    const val ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    // Lifecycles only (without ViewModel or LiveData)
    const val runtimektx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val process = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    const val java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
}
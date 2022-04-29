plugins {
    id("com.android.application") version "7.1.3" apply false
    id("com.android.library") version "7.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.6.21" apply false
    id("com.google.protobuf") version "0.8.18" apply false
}

extra.apply{
    set("grpcVersion", "1.45.0")
    set("grpcKotlinVersion", "1.2.1")
    set("protobufVersion", "3.19.4")
    set("coroutinesVersion", "1.6.0")
    set("core-ktx", "1.7.0")
    set("appcompat", "1.4.1")
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
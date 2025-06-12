plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"

tasks {
    shadowJar {
        destinationDirectory = rootDir
        manifest {
            attributes["Main-Class"] = "com.samsung.TestShellApplication"
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ssd"))
    implementation(project(":common"))
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
}

tasks.test {
    useJUnitPlatform()
}
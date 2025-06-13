plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "unspecified"

tasks {
    shadowJar {
        destinationDirectory = rootDir
        archiveFileName = "ssd.jar"
        manifest {
            attributes["Main-Class"] = "com.samsung.Main"
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
    implementation(project(":common"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
}

tasks.test {
    useJUnitPlatform()
}
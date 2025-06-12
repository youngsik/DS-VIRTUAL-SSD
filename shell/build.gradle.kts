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
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.0")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")

//
//    compileOnly("org.projectlombok:lombok:1.18.32")
//    annotationProcessor("org.projectlombok:lombok:1.18.32")
//    testCompileOnly("org.projectlombok:lombok:1.18.32")
//    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
//
//    implementation("org.slf4j:slf4j-api:2.0.13")
//    implementation("ch.qos.logback:logback-classic:1.4.14")
}

tasks.test {
    useJUnitPlatform()
}
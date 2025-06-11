plugins {
    id("java")
}

group = "org.example"

tasks.jar {
    manifest {
        destinationDirectory = rootDir
        attributes["Main-Class"] = "com.samsung.TestShellApplication" // ← 실제 main 클래스 FQCN
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
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.0")
}

tasks.test {
    useJUnitPlatform()
}
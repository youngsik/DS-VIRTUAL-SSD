// 공통 설정
allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.withType<JavaPlugin> {
        dependencies {
            "testImplementation"(platform("org.junit:junit-bom:5.10.0"))
            "testImplementation"("org.junit.jupiter:junit-jupiter")
            "testImplementation"("org.assertj:assertj-core:3.27.3")
            "testImplementation"("org.mockito:mockito-junit-jupiter:5.18.0")

            "compileOnly"("org.projectlombok:lombok:1.18.32")
            "annotationProcessor"("org.projectlombok:lombok:1.18.32")
            "testCompileOnly"("org.projectlombok:lombok:1.18.32")
            "testAnnotationProcessor"("org.projectlombok:lombok:1.18.32")

            "implementation"("ch.qos.logback:logback-core:1.4.14")
            "implementation"("org.slf4j:slf4j-api:2.0.13")
            "implementation"("ch.qos.logback:logback-classic:1.4.14")
        }

        extensions.configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

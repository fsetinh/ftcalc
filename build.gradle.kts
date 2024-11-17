plugins {
    id("java")
    id("application") // Correct way to apply the application plugin
}

group = "com.venture"
version = "beta-1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.venture.ftcalc.CarLoanCalculator") // Specify your main class here
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.venture.ftcalc.CarLoanCalculator" // Specify the main class for the JAR
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Ensure you're using a compatible JDK version
    }
}

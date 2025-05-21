import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "7.0.2"
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    id("java")
}

val googleApiClientVersion = "2.0.0"
val googleOauthClientVersion = "1.34.1"
val googleApiServicesVersion = "v1-rev20220404-2.0.0"
val mockitoKotlinVersion = "5.4.0"
val javaMailVersion = "1.6.2"
val freeMarkerVersion = "2.3.34"
val openApiVersion = "2.8.8"
val log4jApiVersion = "2.24.3"
val aspectJVersion = "1.9.24"

group = "com.josdem.gmail"
version = "1.0.0.1"


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    jvmToolchain(21)
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

spotless {
    java {
        googleJavaFormat()
        removeUnusedImports()
        endWithNewline()
    }
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**", "**/build-*/**")
        ktlint()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.google.api-client:google-api-client:$googleApiClientVersion")
    implementation("com.sun.mail:javax.mail:$javaMailVersion")
    implementation("com.google.oauth-client:google-oauth-client-jetty:$googleOauthClientVersion")
    implementation("com.google.apis:google-api-services-gmail:$googleApiServicesVersion")
    implementation("org.freemarker:freemarker:$freeMarkerVersion")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    //Swagger Dependency
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openApiVersion")
    //BOM so we don't have to provide versions for each Log module explicitly
    implementation("org.apache.logging.log4j:log4j-bom:${log4jApiVersion}")
    //Log4j dependencies
    implementation("org.apache.logging.log4j:log4j-api:${log4jApiVersion}")
    //Aspect support
    implementation("org.aspectj:aspectjweaver:${aspectJVersion}")
    implementation("org.aspectj:aspectjrt:${aspectJVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
    systemProperties(System.getProperties().toMap() as Map<String, Object>)
}

tasks.withType<BootRun> {
    systemProperties(System.getProperties().toMap() as Map<String, Object>)
}

tasks.withType<Test> {
    dependsOn("spotlessApply")
}

tasks.processResources {
    filesMatching("application.yml") {
        expand(project.properties)
    }
}

//FIXME: idk if its right
tasks.register<Copy>("copyCredentials") {

    val source = file("src/main/resources/credentials/dummy_credentials.json")
    val destination = file("src/main/resources/credentials")

    from(source)
    into(destination)
    rename { "credentials.json" }
}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.n26"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
	implementation("io.github.resilience4j:resilience4j-retrofit:1.7.1")
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:1.7.1")
	implementation("io.github.resilience4j:resilience4j-retry:1.7.1")
	implementation("io.github.resilience4j:resilience4j-spring-boot2:1.7.1")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

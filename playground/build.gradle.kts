plugins {
  java
  id("org.springframework.boot") version "3.5.4"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.jsonschema2pojo") version "1.2.2"
}

group = "com.example"
version = "1.0.0"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

// enable Java preview features
val compileJvmArgs = listOf("--enable-preview")
val runtimeJvmArgs = listOf(
  "--enable-preview",
  "--add-opens", "java.base/java.lang=ALL-UNNAMED",
  "--add-opens", "java.base/java.util=ALL-UNNAMED",
  "--add-opens", "java.base/jdk.internal.misc=ALL-UNNAMED"
)
tasks.withType<JavaCompile>().configureEach {
  options.compilerArgs.addAll(compileJvmArgs)
}
tasks.withType<Test>().configureEach {
  jvmArgs(runtimeJvmArgs)
}
tasks.withType<JavaExec>().configureEach {
  jvmArgs(runtimeJvmArgs)
}

repositories {
  mavenCentral()
}

dependencies {
  val mapstruct_version = "1.6.3"
  val graal_vm_version = "24.2.1"

  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-json")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testCompileOnly("org.springframework.boot:spring-boot-devtools")
  annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
  compileOnly("org.projectlombok:lombok")
  compileOnly("org.projectlombok:lombok-mapstruct-binding:0.2.0")
  testCompileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")
  testAnnotationProcessor("org.projectlombok:lombok")
  implementation("org.mapstruct:mapstruct:${mapstruct_version}")
  annotationProcessor("org.mapstruct:mapstruct-processor:${mapstruct_version}")
  testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstruct_version}")
  implementation("org.modelmapper:modelmapper:3.2.3")
  implementation("org.jspecify:jspecify:1.0.0")
  implementation("com.ginsberg:gatherers4j:0.11.0")

  implementation("io.projectreactor:reactor-core")
  implementation("com.google.guava:guava:33.4.8-jre")
  implementation("org.apache.commons:commons-lang3:3.17.0")
  implementation("org.apache.commons:commons-collections4:4.5.0")
  implementation("org.apache.commons:commons-text:1.13.1")
  implementation("org.apache.tika:tika-core:3.1.0")
  implementation("io.reactivex.rxjava3:rxjava:3.1.10")
  implementation("com.github.akarnokd:rxjava3-extensions:3.1.1")
  implementation("io.vavr:vavr:0.10.6")
  implementation("one.util:streamex:0.8.3")
  implementation("com.github.java-json-tools:json-patch:1.13")
  implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
  implementation("org.glassfish.jaxb:jaxb-runtime:4.0.5")
  implementation("jakarta.activation:jakarta.activation-api:2.1.3")
  implementation("io.github.threeten-jaxb:threeten-jaxb-core:2.2.0")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:")
  implementation("org.apache.groovy:groovy-jsr223:")
  implementation("org.graalvm.polyglot:polyglot:$graal_vm_version")
//  implementation("org.graalvm.polyglot:js:$graal_vm_version")
  implementation("org.graalvm.polyglot:js-community:$graal_vm_version")
  implementation("org.graalvm.js:js-scriptengine:$graal_vm_version")
  implementation("com.github.javafaker:javafaker:1.0.2") {
    exclude("org.yaml", "snakeyaml")
  }
  implementation("org.instancio:instancio-junit:5.4.1")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.awaitility:awaitility")
  testImplementation("uk.org.webcompere:system-stubs-core:2.1.8")
  testImplementation("uk.org.webcompere:system-stubs-jupiter:2.1.8")
}

jsonSchema2Pojo {
  sourceFiles = files("src/main/resources/schema")
  targetPackage = "com.example.types"
  generateBuilders = true
  dateType = "java.time.LocalDate"
  dateTimeType = "java.time.OffsetDateTime"
  timeType = "java.time.LocalTime"
}

tasks.withType<Test> {
  useJUnitPlatform()
}
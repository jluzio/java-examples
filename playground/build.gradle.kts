import org.gradle.internal.deployment.RunApplication

plugins {
  java
  id("org.springframework.boot") version "3.4.2"
  id("io.spring.dependency-management") version "1.1.7"
  id("org.jsonschema2pojo") version "1.2.1"
}

group = "com.example"
version = "1.0.0"

java {
  sourceCompatibility = JavaVersion.VERSION_23
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

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  val mapstruct_version = "1.6.2"
//  val graal_vm_version = "23.0.5"
  val graal_vm_version = "24.0.2"

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
  implementation("org.modelmapper:modelmapper:3.2.1")

  implementation("io.projectreactor:reactor-core")
  implementation("com.google.guava:guava:33.2.1-jre")
  implementation("org.apache.commons:commons-lang3:3.13.0")
  implementation("org.apache.commons:commons-collections4:4.4")
  implementation("org.apache.commons:commons-text:1.10.0")
  implementation("org.apache.tika:tika-core:2.9.0")
  implementation("io.reactivex.rxjava3:rxjava:3.1.7")
  implementation("com.github.akarnokd:rxjava3-extensions:3.1.1")
  implementation("io.vavr:vavr:0.10.4")
  implementation("one.util:streamex:0.8.2")
  implementation("com.github.java-json-tools:json-patch:1.13")
  implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.1")
  implementation("org.glassfish.jaxb:jaxb-runtime:4.0.3")
  implementation("jakarta.activation:jakarta.activation-api:2.1.2")
  implementation("io.github.threeten-jaxb:threeten-jaxb-core:2.1.0")
  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:")
  implementation("org.apache.groovy:groovy-jsr223:")
  implementation("org.graalvm.polyglot:polyglot:$graal_vm_version")
//  implementation("org.graalvm.polyglot:js:$graal_vm_version")
  implementation("org.graalvm.polyglot:js-community:$graal_vm_version")
  implementation("org.graalvm.js:js-scriptengine:$graal_vm_version")
  implementation("com.github.javafaker:javafaker:1.0.2") {
    exclude("org.yaml", "snakeyaml")
  }
  implementation("org.instancio:instancio:1.0.4")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.awaitility:awaitility")
  testImplementation("uk.org.webcompere:system-stubs-core:2.0.2")
  testImplementation("uk.org.webcompere:system-stubs-jupiter:2.0.2")
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

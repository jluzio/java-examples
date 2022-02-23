package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.features.lang.AnnotationTest.SomeAnnotation;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@SomeAnnotation("someValue")
class AnnotationTest {

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  static @interface SomeAnnotation {
    String value();
  }

  @Test
  void createInstance() {
    SomeAnnotation annotation = new SomeAnnotation() {
      @Override
      public String value() {
        return "someValue";
      }

      @Override
      public Class<? extends Annotation> annotationType() {
        return SomeAnnotation.class;
      }
    };
    log.debug("annotation: {}", annotation);
    assertThat(annotation)
        .isNotNull()
        .extracting(SomeAnnotation::value)
        .isEqualTo("someValue");

    SomeAnnotation annotationOnClass = this.getClass().getAnnotation(SomeAnnotation.class);
    log.debug("annotationOnClass: {}", annotationOnClass);
    assertThat(annotationOnClass)
        .isEqualTo(annotation);
  }

}

package com.example.java.playground.features.javadoc;

class SnippetTest {

  public class ShowExample {
    /**
     * The following code shows how to use {@code Optional.isPresent}:
     * {@snippet :
     * if (v.isPresent()) {
     *     System.out.println("v: " + v.get());
     * }
     * }
     */
    void test() {
      //...
    }

    /**
     // TODO: external snippet with
     * The following code shows how to use {@code Optional.isPresent}:
     * {@snippet file="ShowOptional.java" region="example"}
     */
    void test2() {
      //...
    }
  }

}

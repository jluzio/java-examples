import java.util.stream.Stream;

// Automatic import of the java.base module
void main() {
  println("main");
  println(Stream.of(1, 2, 3).toList());
}

import java.util.stream.Stream;

// Automatic import of the java.base module
void main() {
  IO.println("main");
  IO.println(Stream.of(1, 2, 3).toList());
}

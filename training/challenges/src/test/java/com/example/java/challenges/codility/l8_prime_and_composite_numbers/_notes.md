https://codility.com/media/train/8-PrimeNumbers.pdf

# Counting divisors

~~~java
public int countDivisors(int N) {
  int factors = 0;
  int i = 1;
  double sqrtN = Math.sqrt(N);
  for (; i < sqrtN; i++) {
    if (N % i == 0) {
      factors += 2;
    }
  }
  if (i * i == N) {
    factors++;
  }
  return factors;
}
~~~

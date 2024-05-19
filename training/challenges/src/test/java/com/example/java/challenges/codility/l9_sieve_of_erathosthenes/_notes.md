https://codility.com/media/train/9-Sieve.pdf

# Find Prime numbers
~~~text
Chapter 11
Sieve of Eratosthenes
The Sieve of Eratosthenes is a very simple and popular technique for finding all the prime
numbers in the range from 2 to a given number n. The algorithm takes its name from the
process of sieving—in a simple way we remove multiples of consecutive numbers.
Initially, we have the set of all the numbers {2, 3, . . . , n}. At each step we choose the
smallest number in the set and remove all its multiples. Notice that every composite number
has a divisor of at most √n. In particular, it has a divisor which is a prime number. It
is sufficient to remove only multiples of prime numbers not exceeding √n. In this way, all
composite numbers will be removed.
~~~

~~~python
# Sieve of Eratosthenes. O(n log log n)
def sieve(n):
    sieve = [True] * (n + 1)
    sieve[0] = sieve[1] = False
    i = 2
    while (i * i <= n):
        if (sieve[i])
            k = i * i
            while (k <= n):
                sieve[k] = False
                k += i
            i += 1
    return sieve        
~~~

# Factorization into prime numbers
Figure if a number can be composed by the product of 2 prime numbers.

## prepare array
~~~python
def arrayF(n):
    F = [0] * (n + 1)
    i = 2
    while (i * i <= n):
        if (F[i] == 0):
            k = i * i
            while (k <= n):
                if (F[k] == 0):
                    F[k] = i;
                k += i
        i += 1
    return F
~~~

## factorization of x
~~~python
def factorization(x, F):
    primeFactors = []
    while (F[x] > 0):
        primeFactors += [F[x]]
        x /= F[x]
    primeFactors += [x]
    return primeFactors
~~~

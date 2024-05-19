https://codility.com/media/train/9-Sieve.pdf

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
https://codility.com/media/train/7-MaxSlice.pdf

f we assume that the maximum sum of a slice ending in position i equals
max_ending, then the maximum slice ending in position i+1 equals max(0, max_ending+ a i+1 ).
~~~
void golden_max_slice(int[] A):
  int max_slice = A[0];
  int max_ending = A[0];
  for (int i = 1; i < A.length; i++) {
    max_ending = Math.max(A[i], max_ending + A[i]);
    max_slice = Math.max(max_ending, max_slice);
  }
  return max_slice;
~~~
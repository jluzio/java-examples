# Codility Solutions

## Test cases

The test cases follow a predictable methodology :

* The examples provided in the problem description are explicitly tested.

### Correctness tests
* An empty or zero test case is tested - the result is often not explicitly described, but will actually be there implicitly.
* A minimum test case - using just one input, or whatever is the absolute minimal conceiveable input.
* A simple, or 'small' test case or two - just some basic, as you might reasonably anticipate, examples.
* Edge cases - test cases written to root out those awkward -off-by-one- scenarios that inevitably suck up 80% of the time required to devise a solution.

### Performance tests

* Not always, as the problem dictates, some medium sized test cases eg: ~100 - ~5000 length arrays.
* Always some 'extreme' test cases typically involving generating maximal random datasets.
* Worst case scenario is tested - the biggest possible numbers in the biggest resultsets - with the intent to test the speed and space restraints.

## Notes

* You are safe to assume they won't test, mark you down for, failing to guard against the explicit assumptions described.
    * So if it says N is 0..1000, they won't feed in an N=1001 just to see if you protected against it.
* The "Open reading material", at the top of each lesson, is worth reading before attempting the exercises as they are
  short and focus exactly on what you'll need to solve the following puzzles.
* During the actual interview testing/exam:
    * The report sent to the company is much more detailed than the one sent to the candidate: every edit and run
      is recorded and presented to the company (if you use the browser to build your solution).
    * If you are given multiple tasks, you are permitted to read them, and commence them, and submit them in any order.
    * Before submitting your solution, there is no feedback regarding it's efficiency; but it does affect your score, and report!

## How to approach coding challenges

* Read the puzzle over and over; soak up all the reading material
* Focus on the inputs and the outputs of the routine
* Ignore the edge cases (to start)
* Work through the most basic examples on paper before you code:
    * the single input base case...
    * the two input simple case...
    * in a different order...
    * now three... etc.
* Break it down to the pieces
* Think through all the tactics you know (stacks, prefix sums, sorts, slices) and see if they can help
* Build up a model solution, on paper, to where you have an idea how to solve it
* Draw a flowchart diagram
* Tough call to decide whether to go for the brute force solution first then optimize, or hope to save-not waste-time,
  by going directly for the optimal solution.
* Now, code and test the most basic example: one/most simple/minimal input case
* Then, gradually less simple examples: if you get confused, go back to the paper
* Finally, work in the edge cases
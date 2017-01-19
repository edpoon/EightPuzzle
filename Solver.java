public class Solver {
    // We define a state of the game to be the board position, the number of moves to reach the board position, and the previous state
    // First, insert the initial state (the initial board, 0, moves, and a null previous state)
    // Then, delete from the priority queue the state with the minimum priority and insert onto the priority queue all neighbouring states
    // Repeat until the state dequeued is the goal state.

    /**
     * The current board position
     */
    private RandomPermutation current;

    /**
     * The previous board position
     */
    private RandomPermutation previous;

    /**
     *
     */


    /**
     * Find a solution to the initial board
     */
    public Solver(RandomPermutation initial[]) {
    }

    /**
     * @return The minimum number of moves to solve the initial board
     */
    public int moves() {

    }

    /**
     * Returns an Iterable of RandomPermutation positions in solution
     */
    public Iterable<RandomPermutation> solution() {
    }
}

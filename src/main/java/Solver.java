import java.util.Stack;
import java.util.PriorityQueue;

public class Solver {
    /** Represents a state of the game */
    private class State implements Comparable<State>{

        /** The board position. */
        RandomPermutation position;

        /** The number of moves to reach the board position. */
        int moves;

        /** The previous state of the game. */
        State previous;

        /**
         * Constructs an instance of a game state
         * @param position represents the current board position
         * @param moves the number of moves needed to reach this board position from the initial state
         * @param previous the previous state.
         */
        public State(RandomPermutation position, int moves, State previous) {
            this.position = position;
            this.moves = moves;
            this.previous = previous;
        }

        /**
         * Used to compare which of the two states are closer to the goal state according to the Manhattan heuristic
         * @param state the state to compare this to.
         * @return A negative integer, zero, or a positive integer if this object is less than, equal to, or greater
         */
        @Override
        public int compareTo(State state) {
            return Integer.compare(this.position.manhattan() + this.moves, state.position.manhattan() + state.moves);
        }
    }

    /** A list of board positions corresponding to a solution to an instance of the game. */
    private Stack<RandomPermutation> solution;

    /**
     * Constructor that finds a solution to the initial board.
     * @param board the position that the board is currently in.
     */
    public Solver(RandomPermutation board) {
        State initial = new State(board, 0, null);
        PriorityQueue<State> queue = new PriorityQueue<>();
        queue.add(initial);

        // enqueue neighbouring positions if initial state isn't goal state
        if (queue.peek().position.hamming() != 0) {
            State state = queue.remove();
            for (RandomPermutation neighbour : state.position.neighbours()) {
                State neighbouringState = new State(neighbour, state.moves + 1, state);
                queue.add(neighbouringState);
            }
        }

        while((queue.peek().position.hamming() != 0)) {
            State state = queue.remove();
            for (RandomPermutation neighbour : state.position.neighbours()) {
                // don't add a state if the neighbouring position is the same as the previous state position
                if (!neighbour.equals(state.previous.position)) {
                    State neighbouringState = new State(neighbour, state.moves + 1, state);
                    queue.add(neighbouringState);
                }
            }
        }

        solution = new Stack<>();
        State state = queue.remove();
        while(state.previous != null) {
            solution.push(state.position);
            state = state.previous;
        }
    }

    /** @return The minimum number of moves to solve the initial board. */
    public int moves() {
        return solution.size();
    }

    /** @return An iterable of RandomPermutation positions in solution. */
    public Stack<RandomPermutation> solution() {
        return solution;
    }
}

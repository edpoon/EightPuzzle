import java.util.Random;
import java.util.PriorityQueue;

public class RandomPermutation implements Cloneable, Comparable<RandomPermutation>{
    private static final int UP = 0;

    private static final int DOWN = 1;

    private static final int LEFT = 2;

    private static final int RIGHT = 3;

    /**
     * A two-dimensional matrix that represents the board
     */

    private int[][] board;

    /**
     * An integer that represents the row where the zero is located
     */

    private int zeroRow;

    /**
     * An integer that represents the column where the zero is located
     */

    private int zeroColumn;

    /**
     * Constructs the RandomPermutation given a row and column
     * The size of the matrix is constructed using the given dimensions
     * The matrix is automatically in the winning position
     * @param row The number of rows for the board
     * @param column The number of columns for the board
     */

    public RandomPermutation(int row, int column) {
        board = new int[row][column];
        int count = 1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                board[i][j] = count;
                count++;
            }
        }

        // Set the bottom right cell as empty cell
        board[row - 1][column - 1] = 0;

        // Update location of empty cell accordingly
        zeroRow = row - 1;
        zeroColumn = column - 1;
    }

    /**
     * Returns a <code>String</code> representation of this <code>Board</code>
     *
     * @return the String representation of this Board
     */

    public String toString() {
        String string = "";
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                string += "[" + board[i][j] + "]";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Randomly shuffles the numbers around by moving the empty cell around
     * and checking if it the new proposed position
     * is within the indexes of the array
     */

    public void shuffle() {
        // The direction to move the empty cell
        int direction;

        for (int i = 0; i < 100; i++) {
            // Generate a direction to move the empty cell
            Random rng = new Random();
            direction = rng.nextInt(4);

            // If possible to move empty cell up, do it
            if (direction == UP && zeroRow - 1 >= 0) {
                move(UP);
            } else if (direction == DOWN && zeroRow + 1 < board.length) {
                // If possible to move empty cell down, do it
                move(DOWN);
            } else if (direction == LEFT && zeroColumn - 1 >= 0) {
                // If possible to move empty cell left, do it
                move(LEFT);
            } else if (direction == RIGHT && zeroColumn + 1 < board[0].length) {
                // If possible to move empty cell right, do it
                move(RIGHT);
            } else {
                // The direction generated is not possible
                i--;
            }
        }
    }

    /**
     * Swaps two cells on the board
     * @param x0 Row coordinate for the first tile
     * @param y0 Column coordinate for the first tile
     * @param x1 Row coordinate for the second tile
     * @param y1 Column coordinate for the second tile
     */
    public void swap(int x0, int y0, int x1, int y1) {
        int value = board[x0][y0];
        board[x0][y0] = board[x1][y1];
        board[x1][y1] = value;
    }

    /**
     * Moves the empty to one of four positions which is determined randomly
     * in the shuffle method
     * @param direction Represents a randomly generated move
     */

    public void move(int direction) {
        switch (direction) {
        case UP:
            swap(zeroRow, zeroColumn, zeroRow - 1, zeroColumn);
            zeroRow--;
            break;
        case DOWN:
            swap(zeroRow, zeroColumn, zeroRow + 1, zeroColumn);
            zeroRow++;
            break;
        case LEFT:
            swap(zeroRow, zeroColumn, zeroRow, zeroColumn - 1);
            zeroColumn--;
            break;
        case RIGHT:
            swap(zeroRow, zeroColumn, zeroRow, zeroColumn + 1);
            zeroColumn++;
            break;
        }
        updateZero(zeroRow, zeroColumn);
    }

    /**
     * Returns the number of the matrix at a given row and column
     * @param row The row coordinate for the tile
     * @param column The column coordinate for the tile
     * @return Returns the value of the tile
     */

    public int getType(int row, int column) {
        return board[row][column];
    }

    /**
     * @return Returns the row where the zero is located
     */

    public int getZeroRow() {
        return zeroRow;
    }

    /**
     * @return Returns the column where the zero is located
     */

    public int getZeroColumn() {
        return zeroColumn;
    }

    /**
     * Given a row and column, updates the position of the zero
     * @param row The row coordinate for the tile to be set to zero
     * @param column The column coordinate for the tile to be set to zero
     */
    public void updateZero(int row, int column) {
        zeroRow = row;
        zeroColumn = column;
        board[zeroRow][zeroColumn] = 0;
    }

    /**
     * Given a row, column and number, updates the position of that
     * matrix entry with the given number
     * @param row
     * @param column
     * @param type
     */

    public void update(int row, int column, int type) {
        board[row][column] = type;
    }

    /**
     * Returns the number of blocks out of place.
     * Note that we do not count the blank tile when computing the Hamming priority.
     */
    public int hamming() {
        int hamming = 0;
        int count = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != count) {
                    hamming++;
                }
                count++;
            }
        }
        return hamming - 1;
    }

    /**
     * Returns sum of Manhattan distances between blocks and goals.
     * Note that we do not count the blank tile when computing the Manhattan priority.
     */
    public int manhattan() {
        String string = "";
        int manhattan = 0;
         for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != 0) {
                    // Calculate the goal position of this tile
                    int row = (board[i][j] - 1) / 3;
                    int column = (board[i][j] - 1) % 3;
                    manhattan += Math.abs(i - row) + Math.abs(j - column);
                    int diff = Math.abs(i - row) + Math.abs(j - column);
                    string += board[i][j] + ": " + diff + "\n";
                }
            }
        }
         // return string;
         return manhattan;
    }

    /**
     *
     * @return A copy of this object
     */
    protected RandomPermutation clone() {
        RandomPermutation clone = new RandomPermutation(board.length, board[0].length);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                clone.board[i][j] = board[i][j];
            }
        }
        clone.zeroRow = getZeroRow();
        clone.zeroColumn = getZeroColumn();
        return clone;
    }

    /**
     * * Compares this object with the specified object for order.
     * @param other The other object to be compared
     * @return A negative integer, zero, or a positive integer if this object is less than, equal to, or greater than the specified object
     */
    public int compareTo(RandomPermutation other) {
        return Integer.compare(this.hamming(), other.hamming());
    }

    /**
     * @return An iterable of all neighbouring board position
     */
    public Iterable<RandomPermutation> neighbours() {
        PriorityQueue<RandomPermutation> neighbours = new PriorityQueue<>();
        RandomPermutation neighbour;

        if (zeroRow - 1 >= 0) {
            // up
            neighbour = this.clone();
            neighbour.swap(zeroRow, zeroColumn, zeroRow - 1, zeroColumn);
            neighbour.zeroRow--;
            neighbours.add(neighbour);
        } if (zeroRow + 1 < board.length) {
            // down
            neighbour = this.clone();
            neighbour.swap(zeroRow, zeroColumn, zeroRow + 1, zeroColumn);
            neighbour.zeroRow++;
            neighbours.add(neighbour);
        } if (zeroColumn - 1 >= 0) {
            // left
            neighbour = this.clone();
            neighbour.swap(zeroRow, zeroColumn, zeroRow, zeroColumn - 1);
            neighbour.zeroColumn--;
            neighbours.add(neighbour);
        } if (zeroColumn + 1 < board[0].length) {
            // right
            neighbour = this.clone();
            neighbour.swap(zeroRow, zeroColumn, zeroRow, zeroColumn + 1);
            neighbour.zeroColumn++;
            neighbours.add(neighbour);
        }

        return neighbours;
    }
}

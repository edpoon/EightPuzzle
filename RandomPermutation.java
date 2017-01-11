import java.util.Random;

public class RandomPermutation {

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
     * @param row
     * @param column
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
        String representation = "";
        for (int i = 0; i < board.length; i++) {
            representation += board[i];
        }
        return representation;
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
            if (direction == 0 && zeroRow - 1 >= 0) {
                move(direction);
            } else if (direction == 1 && zeroRow + 1 < board.length) {
                // If possible to move empty cell down, do it
                move(direction);
            } else if (direction == 2 && zeroColumn - 1 >= 0) {
                // If possible to move empty cell left, do it
                move(direction);
            } else if (direction == 3 && zeroColumn + 1 < board[0].length) {
                // If possible to move empty cell right, do it
                move(direction);
            } else {
                // The direction generated is not possible
                i--;
            }
        }
    }

    /**
     * Moves the empty to one of four positions which is determined randomnly
     * in the shuffle method
     * @param choice Represents a randomly generated move
     */

    public void move(int choice) {
        // Move empty cell up
        if (choice == 0) {
            board[zeroRow][zeroColumn] = board[zeroRow - 1][zeroColumn];
            zeroRow--;
        }
        // Move empty cell down
        else if (choice == 1) {
            board[zeroRow][zeroColumn] = board[zeroRow + 1][zeroColumn];
            zeroRow++;
        }
        // Move empty cell left
        else if (choice == 2) {
            board[zeroRow][zeroColumn] = board[zeroRow][zeroColumn - 1];
            zeroColumn--;
        }
        // Move empty cell right
        else if (choice == 3) {
            board[zeroRow][zeroColumn] = board[zeroRow][zeroColumn + 1];
            zeroColumn++;
        }
        // Update the position of the empty cell
        updateZero(zeroRow, zeroColumn);
    }

    /**
     * Returns the number of the matrix at a given row and column
     * @param row
     * @param column
     * @return
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
     * @param row
     * @param column
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
}

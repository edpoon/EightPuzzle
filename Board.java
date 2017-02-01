import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.lang.Math;

/**
 * In this application, <b>Board</b> is a specialized type of <b>JPanel</b> that
 * holds cells (balls). The board also holds the higher level logic of the game
 * for selecting and removing cells.
 *
 * @author Edward Poon, University of Ottawa
 * @author Marcel Turcotte, University of Ottawa
 *
 */

public class Board extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    /**
     * Defines the total number of rows.
     */

    private static final int NUMBER_OF_ROWS = 3;

    /**
     * Defines the total number of columns.
     */

    private static final int NUMBER_OF_COLUMNS = 3;

    /**
     * A two dimensional array to keep references to all the cells of the board.
     */

    // change visiblility modifier later
    public Cell[][] board;

    /**
     * Used by the logic to avoid processing multiple clicks.
     */

    private boolean allowsClicks = false;

    /**
     * An object of RandomPermutation that contains a matrix that represents the board.
     */

    private RandomPermutation permutation;

    /**
     * A number that represents which row the empty cell is contained in.
     */

    private int zeroRow;

    /**
     * A number that represents which column the empty the empty cell is contained in.
     */

    private int zeroColumn;

    /**
     * A number that represents the number of moves the user has taken.
     */

    private int moves;

    /**
     * Constructor used to create the cell
     */

    public Board() {
        // Set up the GUI
        setBackground(Color.WHITE);
        setLayout(new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));

        // Setup lower logic of game
        permutation = new RandomPermutation(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
        permutation.shuffle();

        zeroRow = permutation.getZeroRow();
        zeroColumn = permutation.getZeroColumn();

        // Setup higher logic of game
        board = new Cell[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
                // Set GUI to match board
                board[row][column] = new Cell(this, row, column, permutation.getType(row, column));
                add(board[row][column]);
            }
        }
    }

    /**
     * Re-initializes all the cells of the grid and resets the board.
     */

    public void init() {
        permutation.shuffle();
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
                board[row][column].reset(permutation, row, column);
            }
        }
        zeroRow = permutation.getZeroRow();
        zeroColumn = permutation.getZeroColumn();
        permutation.updateZero(zeroRow, zeroColumn);
        moves = 0;
    }

    /**
     * Returns <b>true</b> if clicks are allowed, and false otherwise.
     *
     * @return true if clicks are allowed
     */

    public boolean allowsClicks() {
        return allowsClicks;
    }

    /**
     * A setter for the attribute <b>allowsClick</b>.
     *
     * @param allowClicks
     *            the allowClicks to set
     */

    public void setAllowsClicks(boolean allowClicks) {
        this.allowsClicks = allowClicks;
    }

    /**
     * Sets the attribute <b>selected</b> to <b>false</b> for all the cells of
     * the grid.
     */

    public void deselectAllCells() {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
                board[row][column].setSelected(false);
            }
        }
    }

    /**
     * Returns <b>true</b> if the cell found at <b>row</b>, <b>column</b> is
     * beside the empty cell and <b>false</b> otherwise.
     *
     * @param row
     *            the specified row
     * @param column
     *            the specified column
     * @return true if the specified cell is beside the empty cell.
     */

    public boolean hasNeighbourZero(int row, int column) {
        boolean horizontal, vertical;
        // Check if the zero cell is left or right of the current cell
        horizontal = zeroRow == row && Math.abs(zeroColumn - column) == 1;
        // Check if the zero cell is right on top or right below the current cell;
        vertical = zeroColumn == column && Math.abs(zeroRow - row) == 1;

        return vertical || horizontal;
    }

    /**
     * This method is called after a call to <b>hasNeighbourZero</b> in
     * swap the empty cell with the clicked cell that is adjacent to it
     *
     * @param row
     *            the specified row
     * @param column
     *            the specified column
     */

    public void swap(int row, int column) {
        board[row][column].setSelected(true);

        if (hasNeighbourZero(row, column)) {
            int tempType = board[row][column].getType();
            // Swap the empty cell with the selected cell in the GUI
            board[zeroRow][zeroColumn].setType(tempType);
            board[row][column].setType(0);
            // Swap the empty cell with the selected cell in the lower logic
            permutation.update(zeroRow, zeroColumn, tempType);
            permutation.updateZero(row, column);

            zeroRow = row;
            zeroColumn = column;
        }
    }

    /**
     * Returns <b>true</b> if and only if all the cells are <b>Empty</b>.
     *
     * @return <b>true</b> if and only if all the cells are <b>Empty</b>
     */

    public boolean solved() {
        return (permutation.hamming() == 0);
    }

    /**
     * This method must be implemented as part of the contract specified by
     * ActionListener. This method will be called each time the user clicks a
     * button. Upon a first click, if there are adjacent cells of the same type,
     * the cell and the adjacent cells of the same type are selected. Upon a
     * second click, the cell and the adjacent cells of the same type are
     * removed from the <b>Board</b>. This causes other cells to fall down, and
     * right.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Cell) {
            Cell src = (Cell) e.getSource();
            if (src.getType() != Cell.EMPTY && allowsClicks() && !src.selected()) {
                setAllowsClicks(false);
                deselectAllCells();
                if (hasNeighbourZero(src.getRow(), src.getColumn())) {
                    swap(src.getRow(), src.getColumn());
                    moves++;
                }
                src.setSelected(false);
                setAllowsClicks(true);
            }
        }

        if (solved()) {
            JOptionPane.showMessageDialog(this, "You won the game in " + moves + " moves!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
            init();
        }
    }

    public RandomPermutation getPermutation() {
        return permutation;
    }
}

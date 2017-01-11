import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * In the application <b>Puzzler</b>, a <b>Cell</b> is a specialized type of
 * <b>JButton</b> that represents a ball in the game. Upon a first click, if
 * there are adjacent cells of the same type, this cell and the adjacent cells
 * of the same type are selected. Upon a second click, this cell and the
 * adjacent cells of the same type are removed from the <b>Board</b>. This
 * causes other cells to fall down, and right.
 *
 * @author Edward Poon, University of Ottawa
 * @author Marcel Turcotte, University of Ottawa
 */

public class Cell extends JButton {

    private static final long serialVersionUID = 1L;

    /**
     * Number of Numbers
     */

    public static final int NUM_NUMBERS = 9;

    /**
     * Symbolic constant that represents an empty cell
     */

    public static final int EMPTY = 0;

    /**
     * The cell type. Valid values are Empty, Blue, Green, etc.
     */

    private int type;

    /**
     * This instance variable is true if the cell is selected.
     */

    private boolean selected = false;

    /**
     * The coordinate of this cell on the <b>Board</b>.
     */

    private int row, column;

    /**
     * A an array is used to cache all the images. Since the images are not
     * modified. All the cells that display the same image reuse the same
     * <b>ImageIcon</b> object. Notice the use of the keyword <b>static</b>.
     */

    private static final ImageIcon[] icons = new ImageIcon[NUM_NUMBERS + 2];

    /**
     * Constructor used for initializing a cell of a specified type.
     *
     * @param board
     *            the grid that holds all the cells
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param type
     *            specifies the type of this cell
     */

    public Cell(Board board, int row, int column, int type) {
        this.row = row;
        this.column = column;
        this.type = type;

        setBackground(Color.WHITE);
        setIcon(getImageIcon());

        Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        setBorder(emptyBorder);
        setBorderPainted(false);
        addActionListener(board);
    }

    /**
     * Determine the image to use based on the cell type. Uses
     * <b>getResource</b> to locate the image file, either on the file system or
     * the .jar file. Implements a caching mechanism.
     *
     * @return the image to be displayed by the button
     */

    private ImageIcon getImageIcon() {
        int id;
        if (selected) {
            id = NUM_NUMBERS + 1;
        } else {
            id = type;
        }

        if (icons[id] == null) {
            String strId = Integer.toString(id);
            icons[id] = new ImageIcon("src/data/img-0" + strId + ".png");
        }
        return icons[id];
    }

    /**
     * This method is called when the used clicks the reset button. A new cell
     * type is generated. Its image is updated. The cell is unselected.
     */

    public void reset(RandomPermutation permutation, int row, int column) {
        type = permutation.getType(row, column);
        setIcon(getImageIcon());
        selected = false;
    }

    /**
     * Returns the cell type of this cell.
     *
     * @return the type
     */

    public int getType() {
        return type;
    }

    /**
     * Returns true if <b>this</b> and the <b>other</b> cell have the same type.
     *
     * @param other
     *            other cell used for the comparison
     * @return true if both cells have the same type
     */

    public boolean zeroType(Cell other) {
        return 0 == other.type;
    }

    /**
     * Changes the cell type of this cell. The image is updated accordingly.
     *
     * @param type
     *            the type to set
     */

    public void setType(int type) {
        this.type = type;
        setIcon(getImageIcon());
    }

    /**
     * Returns true if this cell is empty, i.e. its type is
     * <b>CellType.Empty</b>.
     *
     * @return true is this cell is empty
     */

    public boolean isEmpty() {
        return type == EMPTY;
    }

    /**
     * Returns true is this cell is selected. Note: <b>isSelected</b> would have
     * been a better name. However, this would overwrite the parent method
     * <b>isSelected</b>.
     *
     * @return true is this cell is selected.
     */

    public boolean selected() {
        return selected;
    }

    /**
     * Sets the value of the attribute <b>selected</b>.
     *
     * @param selected
     *            the new value for the attribute selected
     */

    public void setSelected(boolean selected) {
        this.selected = selected;
        setIcon(getImageIcon());
    }

    /**
     * Getter method for the attribute row.
     *
     * @return the value of the attribute row
     */

    public int getRow() {
        return row;
    }

    /**
     * Getter method for the attribute column.
     *
     * @return the value of the attribute column
     */

    public int getColumn() {
        return column;
    }
}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This is the main window of the application. A <b>Board</b> object is placed
 * in the center of the frame. The reset button is placed at the bottom.
 *
 * <a href=
 * "http://developer.apple.com/library/safari/#samplecode/Puzzler/Introduction/Intro.html%23//apple_ref/doc/uid/DTS10004409"
 * >Based on Puzzler by Apple</a>.
 *
 * @author Edward Poon, University of Ottawa
 * @author Marcel Turcotte, University of Ottawa
 */

public class EightPuzzle extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    /**
     * Keeps a reference to the object <b>board</b> in order to call the method
     * reset whenever the user clicks the reset button.
     */

    private Board board;

    /**
     * Creates the layout of the application.
     */

    public EightPuzzle() {
        super("Eight Puzzle");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);

        board = new Board();
        add(board, BorderLayout.CENTER);

        JButton button = new JButton("Start new game");
        button.setFocusPainted(false);
        button.addActionListener(this);

        // solve button
        JButton solve = new JButton("Solve");
        solve.setFocusPainted(false);
        solve.addActionListener(this);

        JPanel control = new JPanel();
        control.setBackground(Color.WHITE);
        control.add(button);
        control.add(solve);
        add(control, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setVisible(true);

        board.setAllowsClicks(true);
    }

    /**
     * This method must be implemented as part of the contract specified by
     * ActionListener. When the user clicks the reset button, it calls the
     * method <b>reset</b> of the object designated by <b>board</b>.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Solve")) {
            board.setAllowsClicks(false);

            // benchmarks
            long start, stop;
            start = System.currentTimeMillis();

            Solver solver = new Solver(board.getPermutation());

            stop = System.currentTimeMillis();
            System.out.printf("Solved in %d moves with runtime: %d ms.", solver.moves(), stop - start);

            // half a second
            Timer timer = new Timer(500, null);
            timer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!solver.solution().empty()) {
                        RandomPermutation position = solver.solution().pop();
                        int row = position.getZeroRow();
                        int col = position.getZeroColumn();
                        // don't allow the user to click when the solver is working
                        board.setAllowsClicks(true);
                        board.board[row][col].doClick();
                        board.setAllowsClicks(false);
                    } else {
                        timer.stop();
                        board.setAllowsClicks(true);
                    }
                }
            });
            timer.start();
            board.setAllowsClicks(false);
        } else if (e.getActionCommand().equals("Start new game") && board.allowsClicks()) {
            board.setAllowsClicks(false);
            board.init();
            board.setAllowsClicks(true);
        }
    }

    /**
     * Java programs start by executing the main method. Here, this main method
     * creates the main window of the application.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        new EightPuzzle();
    }
}

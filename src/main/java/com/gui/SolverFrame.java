package main.java.com.gui;

import main.java.com.model.Puzzle;
import main.java.com.model.SudokuBoard;
import main.java.com.structs.PuzzleNavigator;
import main.java.com.utils.LoadDataJson;
import main.java.com.utils.OutputDataJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SolverFrame extends JFrame implements ActionListener {

    Puzzle activePuzzle;
    PuzzleNavigator<Puzzle> puzzleHistory;
    private BoardPanel userPanel;
    private BoardPanel outputPanel;
    private ImageIcon icon;
    JPanel headerPanel;
    JButton verifyButton;
    JButton solveButton;
    JButton clearButton;
    JButton leftButton;
    JButton rightButton;
    JLabel valid;
    JLabel invalid;



    public SolverFrame() {
        puzzleHistory = LoadDataJson.puzzles();
        frameSetup();
        headerSetup();
        validSetup();
        userPanel = new BoardPanel(20, 175, true);
        outputPanel = new BoardPanel(500, 175, false);
        this.add(headerPanel);
        this.add(userPanel);
        this.add(outputPanel);
        this.revalidate();
        this.repaint();
        this.setVisible(true);

        if (puzzleHistory.getCurrentValue() != null) {
            activePuzzle = new Puzzle(puzzleHistory.getCurrentValue());
            userPanel.updateBoard(activePuzzle.getInitialBoard());
            outputPanel.clearSquares();
            setDirectionButtons();
        }

    }

    private void headerSetup() {
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30,30,30));
        headerPanel.setBounds(0, 0, 960, 75);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setLayout(null);

        JLabel header = new JLabel("Sudoku Solver V1.0");
        header.setForeground(Color.white);
        header.setFont(new Font("Monotype Corsiva", Font.PLAIN, 20));
        header.setBounds(400,0,160,30);
        headerPanel.add(header);

        solveButton = new JButton("Solve!");
        solveButton.setBounds(360, 40, 100, 25);
        solveButton.addActionListener(this);
        solveButton.setFocusable(false);
        headerPanel.add(solveButton);

        verifyButton = new JButton("Verify!");
        verifyButton.setBounds(500, 40, 100, 25);
        verifyButton.addActionListener(this);
        verifyButton.setFocusable(false);
        headerPanel.add(verifyButton);

        clearButton = new JButton("Clear Board!");
        clearButton.setBounds(100, 40, 150, 25);
        clearButton.addActionListener(this);
        clearButton.setFocusable(false);
        headerPanel.add(clearButton);

        leftButton = new JButton("<");
        leftButton.setBounds(100, 10, 65, 25);
        leftButton.addActionListener(this);
        leftButton.setFocusable(false);
        headerPanel.add(leftButton);

        rightButton = new JButton(">");
        rightButton.setBounds(185, 10, 65, 25);
        rightButton.addActionListener(this);
        rightButton.setFocusable(false);
        headerPanel.add(rightButton);
    }

    private void frameSetup() {
        ImageIcon icon = new ImageIcon("src/main/resources/icons/SudokuIcon.jpg");
        this.getContentPane().setBackground(new Color(0x913b1C));
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Sudoku Solver");
        this.setSize(970, 700);
    }

    private void validSetup() {
        ImageIcon validIcon = new ImageIcon("src/main/resources/icons/Valid.jpg");
        validIcon = new ImageIcon(validIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon invalidIcon = new ImageIcon("src/main/resources/icons/Invalid.jpg");
        invalidIcon = new ImageIcon(invalidIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        valid = new JLabel("Valid!");
        valid.setFont(new Font("Algerian", Font.BOLD, 30));
        valid.setForeground(Color.GREEN);
        valid.setHorizontalTextPosition(JLabel.CENTER);
        valid.setVerticalTextPosition(JLabel.TOP);
        valid.setBounds(175, 85, 250, 80);
        valid.setIconTextGap(-10);
        valid.setIcon(validIcon);
        invalid = new JLabel("Invalid");
        invalid.setFont(new Font("Algerian", Font.BOLD, 30));
        invalid.setForeground(Color.red);
        invalid.setHorizontalTextPosition(JLabel.CENTER);
        invalid.setVerticalTextPosition(JLabel.TOP);
        invalid.setBounds(175, 85, 250, 80);
        invalid.setIconTextGap(-10);
        invalid.setIcon(invalidIcon);
        invalid.setVisible(false);
        valid.setVisible(false);
        this.add(valid);
        this.add(invalid);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == verifyButton) {
            verifyUserBoard();
        } else if (e.getSource() == solveButton) {
            solveUserBoard();
        } else if (e.getSource() == clearButton) {
            clearBoard();
        } else if (e.getSource() == leftButton) {
            setPreviousPuzzle();
        } else if (e.getSource() == rightButton) {
            setNextPuzzle();
        }
    }

    private void verifyUserBoard() {
        activePuzzle = new Puzzle(userPanel.getBoard());
        saveCurrentBoard();
        setDirectionButtons();
        if (activePuzzle.isSolution()) {
            invalid.setVisible(false);
            valid.setVisible(true);
        } else {
            valid.setVisible(false);
            invalid.setVisible(true);
        }
    }

    private void solveUserBoard() {
        valid.setVisible(false);
        invalid.setVisible(false);
        activePuzzle = new Puzzle(userPanel.getBoard());
        saveCurrentBoard();
        SudokuBoard solvedBoard;
        if ((solvedBoard = activePuzzle.getSolvedBoard()) == null) {
            invalid.setVisible(true);
        } else {
            outputPanel.updateBoard(solvedBoard);
        }
    }

    private void clearBoard() {
        userPanel.clearSquares();
        outputPanel.clearSquares();
    }

    private void setPreviousPuzzle() {
        if (!puzzleHistory.hasOlderValue()) {return;}
        activePuzzle = new Puzzle(puzzleHistory.getOlderValue());
        userPanel.updateBoard(activePuzzle.getInitialBoard());
        outputPanel.clearSquares();
        setDirectionButtons();
    }

    private void setNextPuzzle() {
        if (!puzzleHistory.hasNewerValue()) {return;}
        activePuzzle = new Puzzle(puzzleHistory.getNewerValue());
        userPanel.updateBoard(activePuzzle.getInitialBoard());
        outputPanel.clearSquares();
        setDirectionButtons();
    }

    private void setDirectionButtons() {
        rightButton.setEnabled(puzzleHistory.hasNewerValue());
        leftButton.setEnabled(puzzleHistory.hasOlderValue());
    }

    private void saveCurrentBoard() {
        puzzleHistory.add(new Puzzle(activePuzzle));
        puzzleHistory.getNewestValue();
        OutputDataJson.puzzle(activePuzzle);
        setDirectionButtons();
    }
}

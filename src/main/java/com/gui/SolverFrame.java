package main.java.com.gui;

import main.java.com.model.*;
import main.java.com.structs.PuzzleNavigator;
import main.java.com.utils.BoardPanelFactory;
import main.java.com.utils.LoadDataJson;
import main.java.com.utils.OutputDataJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
TODO:
 * #Done Implement switching puzzle types and subtypes.
 * #Done Fix Loading Logic.
 * Improve Visuals Using 2D Graphics.
 * Make GRID25x25 visually usable.
 * Add additional Puzzles.
 * Optimize Sudoku solving logic.
 * #Done Ensure boardTypeSelector removes old options on puzzleTypeSelector change.
 */

public class SolverFrame extends JFrame implements ActionListener {

    private final int userPanelY = 175;
    private final int outputPanelY = 175;
    private final int userPanelX = 20;
    private final int outputPanelX = 500;

    private Puzzle activePuzzle;
    private PuzzleNavigator<Puzzle> puzzleHistory;
    private JLayeredPane layeredPane;
    private BoardPanel userPanel;
    private BoardPanel outputPanel;
    private ImageIcon icon;
    private JPanel headerPanel;
    private JPanel background;
    private JButton verifyButton;
    private JButton solveButton;
    private JButton clearButton;
    private JButton leftButton;
    private JButton rightButton;
    private JComboBox puzzleTypeSelector;
    private JComboBox boardTypeSelector;
    private JLabel valid;
    private JLabel invalid;

    private PuzzleType puzzleType;
    private BoardType boardType;

    private BoardType[] boardTypes;
    private final PuzzleType[] puzzleTypes = Puzzle.getValidPuzzleTypes();


    public SolverFrame() {
        layeredPane = this.getLayeredPane();
        puzzleHistory = LoadDataJson.puzzles();
        frameSetup();
        backgroundSetup();
        validSetup();

        if (puzzleHistory.getCurrentValue() != null) {
            activePuzzle = new Puzzle(puzzleHistory.getCurrentValue());
            puzzleType = activePuzzle.getPuzzleType();
            boardType = activePuzzle.getBoardType();
            userPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, userPanelX, userPanelY, true);
            userPanel.updateBoard(activePuzzle.getInitialBoard());
            outputPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, outputPanelX, outputPanelY, false);
            boardTypes = Puzzle.getValidBoardTypes(activePuzzle.getPuzzleType());
        } else {
            puzzleType = PuzzleType.SUDOKU;
            boardType = BoardType.GRID_4x4;
            userPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, userPanelX, userPanelY, true);
            outputPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, outputPanelX, outputPanelY, false);
        }
        headerSetup();

        layeredPane.add(headerPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(userPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(outputPanel, JLayeredPane.PALETTE_LAYER);
        this.revalidate();
        this.repaint();
        setDirectionButtons();
        this.setVisible(true);
    }

    private void headerSetup() {
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30,30,30));
        headerPanel.setBounds(0, 0, 960, 75);
        headerPanel.setLayout(null);

        JLabel header = new JLabel("Puzzle Solver V2.0");
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

        puzzleTypeSelector = new JComboBox();
        puzzleTypeSelector.setBounds(720, 10, 125, 25);
        puzzleTypeSelector.setFocusable(false);
        for (PuzzleType type : puzzleTypes) {
            puzzleTypeSelector.addItem(type.toString());
        }
        headerPanel.add(puzzleTypeSelector);

        boardTypeSelector = new JComboBox();
        boardTypeSelector.setBounds(720, 40, 125, 25);
        boardTypeSelector.setFocusable(false);
        for (BoardType type : boardTypes) {
            boardTypeSelector.addItem(type.toString());
        }
        headerPanel.add(boardTypeSelector);

        puzzleTypeSelector.addActionListener(this);
        boardTypeSelector.addActionListener(this);
    }

    private void backgroundSetup() {

        background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int squareSize = 96;
                for (int y = 0; y < getHeight(); y += squareSize) {
                    for (int x = 0; x < getWidth(); x += squareSize) {
                        if ((x / squareSize + y / squareSize) % 2 == 0) {
                            g.setColor(new Color(60, 40, 160));
                        } else {
                            g.setColor(new Color(45, 30, 120));
                        }
                        g.fillRect(x, y, squareSize, squareSize);
                    }
                }
            }
        };
        background.setBounds(0, 0, 970, 700);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    private void frameSetup() {
        ImageIcon icon = new ImageIcon("src/main/resources/icons/PuzzlePiece.png");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Puzzle Solver");
        this.setSize(970, 700);
    }

    private void validSetup() {
        ImageIcon validIcon = new ImageIcon("src/main/resources/icons/Valid.png");
        validIcon = new ImageIcon(validIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon invalidIcon = new ImageIcon("src/main/resources/icons/Invalid.png");
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
        layeredPane.add(valid, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(invalid, JLayeredPane.PALETTE_LAYER);
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
        } else if (e.getSource() == puzzleTypeSelector) {
            setUserPuzzleType();
        } else if (e.getSource() == boardTypeSelector) {
            setUserBoardType();
        }
    }

    private void verifyUserBoard() {
        activePuzzle = new Puzzle(puzzleType, boardType, userPanel.getBoard().getBoard());
        saveCurrentPuzzle();
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
        activePuzzle = new Puzzle(puzzleType, boardType, userPanel.getBoard().getBoard());
        saveCurrentPuzzle();
        PuzzleBoard solvedBoard;
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
        valid.setVisible(false);
        invalid.setVisible(false);
        if (!puzzleHistory.hasOlderValue()) {return;}
        activePuzzle = new Puzzle(puzzleHistory.getOlderValue());
        puzzleType = activePuzzle.getPuzzleType();
        boardType = activePuzzle.getBoardType();
        makeNewBoardPanels();
        userPanel.updateBoard(activePuzzle.getInitialBoard());
        if (activePuzzle.hasSolvedBoard()) {
            outputPanel.updateBoard(activePuzzle.getSolvedBoard());
        } else {
            outputPanel.clearSquares();
        }
        setDirectionButtons();
        this.revalidate();
        this.repaint();
    }

    private void setNextPuzzle() {
        valid.setVisible(false);
        invalid.setVisible(false);
        if (!puzzleHistory.hasNewerValue()) {return;}
        activePuzzle = new Puzzle(puzzleHistory.getNewerValue());
        puzzleType = activePuzzle.getPuzzleType();
        boardType = activePuzzle.getBoardType();
        makeNewBoardPanels();
        userPanel.updateBoard(activePuzzle.getInitialBoard());
        if (activePuzzle.hasSolvedBoard()) {
            outputPanel.updateBoard(activePuzzle.getSolvedBoard());
        } else {
            outputPanel.clearSquares();
        }
        setDirectionButtons();
        this.revalidate();
        this.repaint();
    }

    private void setDirectionButtons() {
        rightButton.setEnabled(puzzleHistory.hasNewerValue());
        leftButton.setEnabled(puzzleHistory.hasOlderValue());
    }

    private void makeNewBoardPanels() {
        layeredPane.remove(userPanel);
        layeredPane.remove(outputPanel);
        userPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, userPanelX, userPanelY, true);
        outputPanel = BoardPanelFactory.createBoardPanel(puzzleType, boardType, outputPanelX, outputPanelY, false);
        layeredPane.add(userPanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(outputPanel, JLayeredPane.PALETTE_LAYER);
        revalidate();
        repaint();
    }

    private void saveCurrentPuzzle() {
        puzzleHistory.add(new Puzzle(activePuzzle));
        puzzleHistory.getNewestValue();
        OutputDataJson.puzzle(activePuzzle);
        setDirectionButtons();
    }

    private void setUserPuzzleType() {
        valid.setVisible(false);
        invalid.setVisible(false);
        puzzleType = puzzleTypes[puzzleTypeSelector.getSelectedIndex()];
        boardTypes = Puzzle.getValidBoardTypes(puzzleType);
        boardType = boardTypes[0];
        boardTypeSelector.removeAllItems();
        for (BoardType boardtype : boardTypes) {
            boardTypeSelector.addItem(boardtype.toString());
        }
        if (puzzleType.equals(PuzzleType.SUDOKU)) {
            boardTypeSelector.setSelectedIndex(1);
        }
        makeNewBoardPanels();
    }

    private void setUserBoardType() {
        valid.setVisible(false);
        invalid.setVisible(false);
        if (boardTypeSelector.getItemCount() != 0) {
            boardType = boardTypes[boardTypeSelector.getSelectedIndex()];
            makeNewBoardPanels();
        }
    }
}

package main.java.com.gui;

import main.java.com.model.BoardType;
import main.java.com.model.PuzzleBoard;
import main.java.com.model.SudokuBoard;

import javax.swing.*;
import java.awt.*;

abstract public class BoardPanel extends JPanel {

    protected boolean isUserBoard;
    protected BoardSquare[][] squares;
    protected BoardType boardType;

    public BoardPanel(BoardType boardType, int x, int y, boolean isUserBoard) {
        this.setBackground(new Color(0xAAAAAA));
        this.setBounds(x, y, 440, 440);
        this.setLayout(null);
        this.boardType = boardType;
        this.isUserBoard = isUserBoard;
    }

    public boolean isUserBoard() {
        return isUserBoard;
    }

    abstract public void updateBoard(PuzzleBoard board);

    abstract public PuzzleBoard getBoard();

    abstract public void clearSquares();


}

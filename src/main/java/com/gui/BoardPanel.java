package main.java.com.gui;

import main.java.com.model.SudokuBoard;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private boolean userBoard;
    private SudokuBoard board;
    private BoardSquare[][] squares;

    public BoardPanel(int x, int y, boolean userBoard) {
        this.setBackground(new Color(0xAAAAAA));
        this.setBounds(x, y, 440, 440);
        this.setLayout(null);
        this.userBoard = userBoard;
        this.board = new SudokuBoard();
        if (userBoard) {
            squares = new UserSquare[SudokuBoard.NUMROWS][SudokuBoard.NUMCOLS];
        } else {
            squares = new OutputSquare[SudokuBoard.NUMROWS][SudokuBoard.NUMCOLS];
        }
        for (int i = 0; i < SudokuBoard.NUMROWS; i++) {
            for (int j = 0; j < SudokuBoard.NUMCOLS; j++) {
                if (userBoard) {
                    squares[i][j] = new UserSquare((j * 47 + 12), (i * 47 + 12));
                } else {
                    squares[i][j] = new OutputSquare((j * 47 + 12), (i * 47 + 12));
                }
                this.add(squares[i][j]);
            }
        }
        setSquares();
    }

    public void updateBoard(SudokuBoard board) {
        this.board = board;
        setSquares();
    }

    private void setSquares() {
        for (int i = 0; i < SudokuBoard.NUMROWS; i++) {
            for (int j = 0; j < SudokuBoard.NUMCOLS; j++) {
                squares[i][j].setSquareContents(board.get(i, j));
            }
        }
    }

    public SudokuBoard getBoard() {
        for (int i = 0; i < SudokuBoard.NUMROWS; i++) {
            for (int j = 0; j < SudokuBoard.NUMCOLS; j++) {
                board.set(i, j, squares[i][j].getSquareContents());
            }
        }
        return new SudokuBoard(board);
    }

    public void clearSquares() {
        for (int i = 0; i < SudokuBoard.NUMROWS; i++) {
            for (int j = 0; j < SudokuBoard.NUMCOLS; j++) {
                board.set(i,j,'.');
            }
        }
        setSquares();
    }


}

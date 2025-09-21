package main.java.com.gui;

import main.java.com.model.BoardType;
import main.java.com.model.PuzzleBoard;
import main.java.com.model.SudokuBoard;

public class SudokuBoardPanel extends BoardPanel{

    private final int numRows;
    private final int numCols;

    public SudokuBoardPanel(BoardType boardType, int x, int y, boolean isUserBoard) {
        super(boardType, x, y, isUserBoard);
        switch (boardType) {
            case GRID_4x4:
                numRows = 4;
                numCols = 4;
                break;
            case GRID_9x9:
                numRows = 9;
                numCols = 9;
                break;
            case GRID_16x16:
                numRows = 16;
                numCols = 16;
                break;
            case GRID_25x25:
                numRows = 25;
                numCols = 25;
                break;
            default:
                throw new IllegalStateException("Invalid Board Type");
        }
        final int squareHeight = (int)(((float)440 - 24) / numRows - 6);
        final int squareWidth = (int)(((float)440 - 24) / numCols - 6);
        final int horizontalSpacing = (int)(((float)440 - 12) / numCols);
        final int verticalSpacing = (int)(((float)440 - 12) / numRows);
        final int horizontalOffset = 12;
        final int verticalOffset = 12;
        if (isUserBoard) {
            squares = new UserCharSquare[numRows][numCols];
        } else {
            squares = new OutputCharSquare[numRows][numCols];
        }
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                if (isUserBoard) {
                    squares[i][j] = new UserCharSquare((j * horizontalSpacing + horizontalOffset), (i * verticalSpacing + verticalOffset), squareWidth, squareHeight);
                } else {
                    squares[i][j] = new OutputCharSquare((j * horizontalSpacing + horizontalOffset), (i * verticalSpacing + verticalOffset), squareWidth, squareHeight);
                }
                this.add(squares[i][j]);
            }
        }
    }

    @Override
    public void updateBoard(PuzzleBoard board) {
        setSquares(board.getBoard());
    }

    private void setSquares(char[][] board) {
        if (board.length != numRows || board[0].length != numCols) {
            throw new IllegalStateException("Invalid Board Argument");
        }
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                squares[i][j].setSquareContents(board[i][j]);
            }
        }
    }

    public SudokuBoard getBoard() {
        char[][] board = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = squares[i][j].getSquareContents();
            }
        }
        return new SudokuBoard(boardType, board);
    }

    public void clearSquares() {
        char[][] board = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = ' ';
            }
        }
        setSquares(board);
    }


}

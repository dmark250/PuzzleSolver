package main.java.com.model;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class SudokuBoard {

    public static final int NUMROWS = 9;
    public static final int NUMCOLS = 9;
    private char[][] board;

    public SudokuBoard(char[][] board) {
        this.board = board;
    }

    /**
     * Creates a board initialized with all '.'
     */
    public SudokuBoard() {
        this.board = new char[NUMROWS][NUMCOLS];
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                this.board[i][j] = '.';
            }
        }
    }

    public SudokuBoard(SudokuBoard sudokuBoard) {
        this.board = sudokuBoard.getBoard();
    }

    public char[][] getBoard() {
        char[][] returnArray = new char[NUMROWS][NUMCOLS];
        for (int i = 0; i < NUMROWS; i++) {
            returnArray[i] = Arrays.copyOf(this.board[i], this.board[i].length);
        }
        return returnArray;
    }

    /**
     * Determines if the board is able to be solved.
     *
     * @return - Returns true if it is solvable, false if it is not.
     */
    public boolean isSolvable() {
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                if (board[i][j] >= '1' && board[i][j] <= '9') {
                    for (int k = i + 1; k < 9; k++) {
                        if (board[i][j] == board[k][j]) {
                            return false;
                        }
                    }
                    for (int k = j + 1; k < 9; k++) {
                        if (board[i][j] == board[i][k]) {
                            return false;
                        }
                    }
                    int rowBlock = (i / 3) * 3;
                    int colBlock = (j / 3) * 3;
                    for (int m = i; m < 3; m++) {
                        for (int n = 0; n < 3; n++) {
                            if (board[i][j] == board[rowBlock + m][colBlock + n] && (m + rowBlock != i || n + colBlock != j)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determines if the board is completed and valid.
     *
     * @return - true if solved, false if not solved.
     */
    public boolean isSolved() {
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                if (this.board[i][j] > '9' || this.board[i][j] < '1') { return false;}
            }
        }
        return this.isSolvable();
    }

    /**
     * Solves the board, updating in place.
     *
     * @return - true if successfully solved, false if not.
     */
    public boolean solveBoard() {
        if (!this.isSolvable()) {
            return false;
        }
        Stack<CoordinateXY> moves = new Stack<>();
        boolean[][] editable = new boolean[NUMROWS][NUMCOLS];

        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                if (board[i][j] > '9' || board[i][j] < '1') {
                    editable[i][j] = true;
                } else {
                    editable[i][j] = false;
                }
            }
        }
        CoordinateXY pos = new CoordinateXY(0, 0);
        while (pos.getY() < NUMROWS) {
            if (editable[pos.getY()][pos.getX()]) {
                if (board[pos.getY()][pos.getX()] > '9'
                        || board[pos.getY()][pos.getX()] < '1') {
                    board[pos.getY()][pos.getX()] = '1';
                } else {
                    board[pos.getY()][pos.getX()]++;
                }
                while (!isValidMove(pos) || board[pos.getY()][pos.getX()] > '9') {
                    board[pos.getY()][pos.getX()]++;
                    if (board[pos.getY()][pos.getX()] > '9') {
                        board[pos.getY()][pos.getX()] = '.';
                        try {
                            pos = moves.pop();
                        } catch (EmptyStackException e) {
                            return false;
                        }
                        board[pos.getY()][pos.getX()]++;
                    }
                }
                moves.push(new CoordinateXY(pos.getX(), pos.getY()));
            }
            advancePosition(pos);
        }
        return isSolved();
    }

    /**
     * Determines if a single square is a valid compared to its surroundings.
     *
     * @param coordinate
     * @return - true if valid, false if invalid.
     */
    private boolean isValidMove(CoordinateXY coordinate) {
        for (int i = 0; i < NUMROWS; i++) {
            if (coordinate.getY() != i && board[coordinate.getY()][coordinate.getX()] == board[i][coordinate.getX()]) {
                return false;
            }
        }
        for (int i = 0; i < NUMROWS; i++) {
            if (coordinate.getX() != i && board[coordinate.getY()][coordinate.getX()] == board[coordinate.getY()][i]) {
                return false;
            }
        }
        int sectorRow = (coordinate.getY() / 3) * 3;
        int sectorCol = (coordinate.getX() / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[sectorRow + i][sectorCol + j] == board[coordinate.getY()][coordinate.getX()]
                && (sectorRow + i != coordinate.getY() || sectorCol + j != coordinate.getX())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Advances the coordinate one square on the board
     * left - right then top - bottom.
     * NOTE: Can advance off board in y direction.
     *
     * @param coordinate
     */
    private void advancePosition(CoordinateXY coordinate) {
        coordinate.setX(coordinate.getX()+1);
        if (coordinate.getX() >= NUMCOLS) {
            coordinate.setX(0);
            coordinate.setY(coordinate.getY()+1);
        }
    }

    public boolean isSameBoard(SudokuBoard otherBoard) {
        if (otherBoard == null) {
            return false;
        }
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                if (this.board[i][j] != (otherBoard.board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public char get(int row, int col) {
        return board[row][col];
    }

    public void set(int row, int col, char value) {
        if (col > NUMCOLS || row > NUMROWS || col < 0 || row < 0) {
            return;
        }
        board[row][col] = value;
    }
}


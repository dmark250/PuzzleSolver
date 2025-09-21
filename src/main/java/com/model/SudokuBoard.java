package main.java.com.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;

public class SudokuBoard extends PuzzleBoard{

    public final int NUMROWS;
    public final int NUMCOLS;
    public ArrayList<Character> validChars;

    public SudokuBoard(BoardType boardType, char[][] board) {
        super(boardType);
        switch (boardType) {
            case GRID_4x4:
                NUMROWS = 4;
                NUMCOLS = 4;
                validChars = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_9x9:
                NUMROWS = 9;
                NUMCOLS = 9;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_16x16:
                NUMROWS = 16;
                NUMCOLS = 16;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                for (int i = 0; i < 7; i++) {
                    validChars.add((char)('a' + i));
                }
                break;
            case GRID_25x25:
                NUMROWS = 25;
                NUMCOLS = 25;
                validChars = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    validChars.add((char)('A' + i));
                }
                break;
            default:
                throw new IllegalStateException("Invalid Board Type for Sudoku Board");
        }
        this.board = board;
    }

    /**
     * Creates a board initialized with all '.'
     */
    public SudokuBoard(BoardType boardType) {
        super(boardType);
        switch (boardType) {
            case GRID_4x4:
                NUMROWS = 4;
                NUMCOLS = 4;
                validChars = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_9x9:
                NUMROWS = 9;
                NUMCOLS = 9;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_16x16:
                NUMROWS = 16;
                NUMCOLS = 16;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                for (int i = 0; i < 7; i++) {
                    validChars.add((char)('a' + i));
                }
                break;
            case GRID_25x25:
                NUMROWS = 25;
                NUMCOLS = 25;
                validChars = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    validChars.add((char)('A' + i));
                }
                break;
            default:
                throw new IllegalStateException("Invalid Board Type for Sudoku Board");
        }
        this.board = new char[NUMROWS][NUMCOLS];
        for (int i = 0; i < NUMROWS; i++) {
            for (int j = 0; j < NUMCOLS; j++) {
                this.board[i][j] = '.';
            }
        }
    }

    public SudokuBoard(SudokuBoard sudokuBoard) {
        super(sudokuBoard.getBoardType());
        switch (this.getBoardType()) {
            case GRID_4x4:
                NUMROWS = 4;
                NUMCOLS = 4;
                validChars = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_9x9:
                NUMROWS = 9;
                NUMCOLS = 9;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                break;
            case GRID_16x16:
                NUMROWS = 16;
                NUMCOLS = 16;
                validChars = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    validChars.add((char)('1' + i));
                }
                for (int i = 0; i < 7; i++) {
                    validChars.add((char)('a' + i));
                }
                break;
            case GRID_25x25:
                NUMROWS = 25;
                NUMCOLS = 25;
                validChars = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    validChars.add((char)('A' + i));
                }
                break;
            default:
                throw new IllegalStateException("Invalid Board Type for Sudoku Board");
        }
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
                if (validChars.contains(board[i][j])) {
                    for (int k = i + 1; k < NUMROWS; k++) {
                        if (board[i][j] == board[k][j]) {
                            return false;
                        }
                    }
                    for (int k = j + 1; k < NUMCOLS; k++) {
                        if (board[i][j] == board[i][k]) {
                            return false;
                        }
                    }
                    int rowBlockSize = (int)(Math.sqrt(NUMROWS) + 0.01);
                    int colBlockSize = (int)(Math.sqrt(NUMCOLS) + 0.01);
                    int rowBlock = (i / rowBlockSize) * rowBlockSize;
                    int colBlock = (j / colBlockSize) * colBlockSize;
                    for (int m = i; m < rowBlockSize; m++) {
                        for (int n = 0; n < colBlockSize; n++) {
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
                if (!validChars.contains(board[i][j])) { return false;}
            }
        }
        return this.isSolvable();
    }

    /**
     * Solves the board, updating in place.
     * Uses a brute force algorithm, not effective over 9x9.
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
                editable[i][j] = !validChars.contains(board[i][j]);
            }
        }
        CoordinateXY pos = new CoordinateXY(0, 0);
        while (pos.getY() < NUMROWS) {
            if (editable[pos.getY()][pos.getX()]) {
                if (!validChars.contains(board[pos.getY()][pos.getX()])) {
                    board[pos.getY()][pos.getX()] = validChars.getFirst();
                } else if (validChars.size() > validChars.indexOf(board[pos.getY()][pos.getX()]) + 1){
                    board[pos.getY()][pos.getX()] = validChars.get(validChars.indexOf(board[pos.getY()][pos.getX()]) + 1);
                } else {
                    board[pos.getY()][pos.getX()] = '.';
                }
                while (!isValidMove(pos) || !validChars.contains(board[pos.getY()][pos.getX()])) {
                    if (!validChars.contains(board[pos.getY()][pos.getX()])) {
                        board[pos.getY()][pos.getX()] = '.';
                        try {
                            pos = moves.pop();
                        } catch (EmptyStackException e) {
                            return false;
                        }
                    }
                    if (validChars.size() > validChars.indexOf(board[pos.getY()][pos.getX()]) + 1){
                        board[pos.getY()][pos.getX()] = validChars.get(validChars.indexOf(board[pos.getY()][pos.getX()]) + 1);
                    } else {
                        board[pos.getY()][pos.getX()] = '.';
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
        for (int i = 0; i < NUMCOLS; i++) {
            if (coordinate.getX() != i && board[coordinate.getY()][coordinate.getX()] == board[coordinate.getY()][i]) {
                return false;
            }
        }
        int rowBlockSize = (int)(Math.sqrt(NUMROWS) + 0.01);
        int colBlockSize = (int)(Math.sqrt(NUMCOLS) + 0.01);
        int sectorRow = (coordinate.getY() / rowBlockSize) * rowBlockSize;
        int sectorCol = (coordinate.getX() / colBlockSize) * colBlockSize;
        for (int i = 0; i < rowBlockSize; i++) {
            for (int j = 0; j < colBlockSize; j++) {
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

    public boolean isSameBoard(PuzzleBoard otherBoard) {
        if (otherBoard == null) {
            return false;
        } if (otherBoard.getBoardType() != this.getBoardType()) {
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

    public boolean solveBoardFast() {
        return false;
    }
}


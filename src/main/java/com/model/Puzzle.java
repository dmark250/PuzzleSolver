package main.java.com.model;

public class Puzzle {
    private final SudokuBoard initialBoard;
    private SudokuBoard solvedBoard;
    private Boolean solvable;

    public Puzzle(char[][] board) {
        this.initialBoard = new SudokuBoard(board);
        this.solvedBoard = null;
        this.solvable = null;
    }

    public Puzzle(char[][] initialBoard, char[][] solvedBoard) {
        this.initialBoard = new SudokuBoard(initialBoard);
        this.solvedBoard = new SudokuBoard(solvedBoard);
        this.solvable = null;
    }

    public Puzzle(SudokuBoard board) {
        this.initialBoard = new SudokuBoard(board);
        this.solvedBoard = null;
        this.solvable = null;
    }

    public Puzzle(Puzzle puzz) {
        this.initialBoard = new SudokuBoard(puzz.getInitialBoard());
        this.solvable = puzz.solvable;
        if (this.solvable != null && this.solvable == true) {
            this.solvedBoard = puzz.getSolvedBoard();
        } else {
            this.solvedBoard = null;
        }
    }

    public SudokuBoard getInitialBoard() {
        return this.initialBoard;
    }

    /**
     * Determines if the puzzle is able to be solved.
     * @return - Returns true if it is solvable, false if it is not.
     */
    public boolean isSolvable() {
        if (this.solvable != null) {
            return this.solvable;
        }
        return (this.solvable = this.initialBoard.isSolvable());
    }

    /**
     * Returns a solved board given the input board.
     * @return = Returns null if the board was unable to be solved.
     */
    public SudokuBoard getSolvedBoard() {
        if (this.solvedBoard != null) {
            return new SudokuBoard(this.solvedBoard);
        }
        if (!this.isSolvable()) {
            return null;
        }
        SudokuBoard solvingBoard = new SudokuBoard(initialBoard);
        if (!solvingBoard.solveBoard()) {
            return null;
        }
        this.solvedBoard = solvingBoard;
        return new SudokuBoard(this.solvedBoard);
    }

    /**
     * Determines if the initial board given was a valid solution puzzle.
     * @return - returns true if valid, false if invalid.
     */
    public boolean isSolution() {
        return this.initialBoard.isSolved();
    }

    public boolean hasSolvedBoard() {
        return solvedBoard != null;
    }


}
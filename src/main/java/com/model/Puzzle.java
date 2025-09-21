package main.java.com.model;

import main.java.com.utils.PuzzleBoardFactory;

public class Puzzle {

    private PuzzleBoard initialBoard;
    private PuzzleBoard solvedBoard;
    private Boolean solvable;

    protected BoardType boardType;
    protected PuzzleType puzzleType;

    public PuzzleType getPuzzleType() {
        return this.puzzleType;
    }
    public BoardType getBoardType() {
        return this.boardType;
    }

    public Puzzle(PuzzleType puzzleType, BoardType boardType, char[][] board) {
        this.puzzleType = puzzleType;
        this.boardType = boardType;
        this.initialBoard = PuzzleBoardFactory.createBoard(puzzleType, boardType, board);
        this.solvedBoard = null;
        this.solvable = null;
    }

    public Puzzle(PuzzleType puzzleType, BoardType boardType, char[][] initialBoard, char[][] solvedBoard) {
        this.puzzleType = puzzleType;
        this.boardType = boardType;
        this.initialBoard = PuzzleBoardFactory.createBoard(puzzleType, boardType, initialBoard);
        this.solvedBoard = PuzzleBoardFactory.createBoard(puzzleType, boardType, solvedBoard);
        this.solvable = null;
    }

    public Puzzle(Puzzle puzz) {
        this.puzzleType = puzz.puzzleType;
        this.boardType = puzz.boardType;
        this.initialBoard = PuzzleBoardFactory.createBoard(this.puzzleType, this.boardType, puzz.initialBoard.getBoard());
        this.solvable = puzz.solvable;
        if (this.solvable != null && this.solvable) {
            this.solvedBoard = PuzzleBoardFactory.createBoard(puzzleType, boardType, puzz.solvedBoard.getBoard());
        } else {
            this.solvedBoard = null;
        }
    }

    public PuzzleBoard getInitialBoard() {
        return PuzzleBoardFactory.createBoard(puzzleType, boardType, initialBoard.getBoard());
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
    public PuzzleBoard getSolvedBoard() {
        if (this.solvedBoard != null) {
            return PuzzleBoardFactory.createBoard(puzzleType, boardType, solvedBoard.getBoard());
        }
        if (!this.isSolvable()) {
            return null;
        }
        PuzzleBoard solvingBoard = PuzzleBoardFactory.createBoard(puzzleType, boardType, initialBoard.getBoard());
        if (!solvingBoard.solveBoard()) {
            return null;
        }
        this.solvedBoard = solvingBoard;
        return PuzzleBoardFactory.createBoard(puzzleType, boardType, solvedBoard.getBoard());
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

    static public BoardType[] getValidBoardTypes(PuzzleType puzzleType) {
        return switch (puzzleType) {
            case SUDOKU -> new BoardType[] {
                    BoardType.GRID_4x4,
                    BoardType.GRID_9x9,
                    BoardType.GRID_16x16,
                    BoardType.GRID_25x25
            };
            // TODO: ADD ADDITIONAL PUZZLE TYPES.
            default -> throw new IllegalStateException("Invalid PuzzleType argument");
        };

    }

    static public PuzzleType[] getValidPuzzleTypes() {
        return new PuzzleType[] {
                PuzzleType.SUDOKU,
                PuzzleType.FLOW
        };
    }

    public void setInitialBoard(PuzzleBoard board) {
        this.initialBoard = board;
    }

    public void setSolvedBoard(PuzzleBoard board) {
        this.solvedBoard = board;
    }
}
package main.java.com.model;

abstract public class PuzzleBoard {

    private final BoardType boardType;
    protected char[][] board;

    public BoardType getBoardType() {
        return this.boardType;
    }

    public PuzzleBoard(BoardType boardType) {
        this.boardType = boardType;
    }

    abstract public boolean solveBoard();

    abstract public boolean isSolved();

    abstract public boolean isSolvable();

    abstract public char[][] getBoard();
}

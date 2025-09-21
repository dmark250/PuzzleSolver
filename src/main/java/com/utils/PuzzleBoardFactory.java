package main.java.com.utils;

import main.java.com.model.BoardType;
import main.java.com.model.PuzzleBoard;
import main.java.com.model.PuzzleType;
import main.java.com.model.SudokuBoard;

public class PuzzleBoardFactory {

    static public PuzzleBoard createBoard(PuzzleType puzzleType, BoardType boardType) {
        return switch (puzzleType) {
            case SUDOKU -> new SudokuBoard(boardType);
            // TODO: Implement other boards
            default -> throw new IllegalStateException("Invalid Puzzle Type");
        };
    }
    static public PuzzleBoard createBoard(PuzzleType puzzleType, BoardType boardType, char[][] board) {
        return switch (puzzleType) {
            case SUDOKU -> new SudokuBoard(boardType, board);
            // TODO: Implement other boards
            default -> throw new IllegalStateException("Invalid Puzzle Type");
        };
    }
}

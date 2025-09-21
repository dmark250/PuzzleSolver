package main.java.com.utils;

import main.java.com.gui.BoardPanel;
import main.java.com.gui.SudokuBoardPanel;
import main.java.com.model.BoardType;
import main.java.com.model.PuzzleType;
import main.java.com.model.SudokuBoard;

public class BoardPanelFactory {

    static public BoardPanel createBoardPanel(PuzzleType puzzleType, BoardType boardType, int x, int y, boolean isUserBoard) {
        return switch (puzzleType) {
            case SUDOKU -> new SudokuBoardPanel(boardType, x, y, isUserBoard);
            // TODO: Implement other boards
            default -> throw new RuntimeException();
        };
    }

}

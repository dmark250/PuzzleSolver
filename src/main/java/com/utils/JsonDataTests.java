package main.java.com.utils;

import main.java.com.model.Puzzle;
import main.java.com.structs.PuzzleNavigator;

import java.util.List;

public class JsonDataTests {

    static final char[][] INITIALTESTBOARD = new char[][]{
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
    };

    public static void main(String[] args) {

        PuzzleNavigator<Puzzle> puzzles = LoadDataJson.puzzles();
        System.out.println("Puzzle Size: " + puzzles.getNumValues());
        if (puzzles.getOldestValue().getSolvedBoard() == null) {
            System.out.println("Failed :(");
            return;
        }
        OutputDataJson.puzzle(puzzles.getCurrentValue());
        System.out.println("Successful");

    }
}
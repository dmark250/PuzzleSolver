package main.java.com.utils;

import main.java.com.model.Puzzle;
import com.google.gson.Gson;
import main.java.com.structs.PuzzleNavigator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class LoadDataJson {

    private static final String puzzleJsonFileName = "data/Puzzles.ndjson";
    private static final Gson gson = new Gson();

    private static Scanner createScanner(String fileName) {
        Scanner s = null;
        try {
            s = new Scanner(new File(fileName));
        } catch (FileNotFoundException er) {
            System.out.println("Unable to access File: " + fileName);
            System.exit(1);
        }
        return s;
    }

    public static PuzzleNavigator<Puzzle> puzzles() {
        PuzzleNavigator<Puzzle> puzzles = new PuzzleNavigator<>();
        Scanner scanner = createScanner(puzzleJsonFileName);
        while (scanner.hasNextLine()) {
            puzzles.add(gson.fromJson(scanner.nextLine(), Puzzle.class));
        }
        puzzles.getNewestValue();
        return puzzles;
    }
}

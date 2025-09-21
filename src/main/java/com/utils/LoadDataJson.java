package main.java.com.utils;

import com.google.gson.GsonBuilder;
import main.java.com.model.Puzzle;
import com.google.gson.Gson;
import main.java.com.structs.PuzzleNavigator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadDataJson {

    private static final String puzzleJsonFileName = "data/Puzzles.ndjson";
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Puzzle.class, new PuzzleTypeAdapter()).create();

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

    /**
     * Loads a list of Puzzle and Puzzle subtypes into a
     * PuzzleNavigator set to the newest entry.
     * @return
     */
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

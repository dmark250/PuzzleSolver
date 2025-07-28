package main.java.com.utils;

import main.java.com.model.Puzzle;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputDataJson {

    private static final String puzzleJsonFileName = "data/Puzzles.ndjson";

    private static final Gson gson = new Gson();


    public static void puzzle(Puzzle puzz) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(puzzleJsonFileName, true));
            pw.println(gson.toJson(puzz));


            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

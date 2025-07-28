import main.java.com.gui.SolverFrame;
import main.java.com.model.Puzzle;
import main.java.com.structs.PuzzleNavigator;
import main.java.com.utils.LoadDataJson;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        PuzzleNavigator<Puzzle> puzzleList = LoadDataJson.puzzles();

        SolverFrame frame = new SolverFrame();

    }
}
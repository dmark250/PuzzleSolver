package main.java.com.utils;

import com.google.gson.*;
import main.java.com.model.Puzzle;
import main.java.com.model.PuzzleBoard;

import java.lang.reflect.Type;

public class PuzzleTypeAdapter implements JsonSerializer<Puzzle>, JsonDeserializer<Puzzle> {
    @Override
    public JsonElement serialize(Puzzle puzzle, Type type, JsonSerializationContext context) {
        JsonElement element = new Gson().toJsonTree(puzzle);
        element.getAsJsonObject().addProperty("type", puzzle.getInitialBoard().getClass().getName());
        return element;
    }

    @Override
    public Puzzle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // 1. Load the type of PuzzleBoard
        String boardTypeName = jsonObject.get("type").getAsString();

        try {
            Class<? extends PuzzleBoard> boardClass = (Class<? extends PuzzleBoard>) Class.forName(boardTypeName);

            // 2. Deserialize the board field using the actual class
            JsonElement initialBoardJson = jsonObject.get("initialBoard");
            PuzzleBoard initialBoard = context.deserialize(initialBoardJson, boardClass);

            JsonElement solvedBoardJson = jsonObject.get("solvedBoard");
            PuzzleBoard solvedBoard = context.deserialize(solvedBoardJson, boardClass);

            // 3. Now deserialize the full Puzzle without the initialBoard field
            //    You must *remove* the initialBoard field before doing this.
            jsonObject.remove("initialBoard");
            jsonObject.remove("solvedBoard");
            jsonObject.remove("type"); // avoid trying to map to a nonexistent field

            // Deserialize the rest of the Puzzle
            Puzzle puzzle = new Gson().fromJson(jsonObject, Puzzle.class);

            // 4. Inject the board
            puzzle.setInitialBoard(initialBoard);
            puzzle.setSolvedBoard(solvedBoard);

            return puzzle;

        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Could not find class: " + boardTypeName, e);
        }
    }
}

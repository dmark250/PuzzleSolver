package main.java.com.model;

public class SolverMove {

    private final int row;
    private final int col;
    private final char value;

    public SolverMove(int row, int col, char value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}

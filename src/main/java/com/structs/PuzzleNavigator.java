package main.java.com.structs;

import java.util.Arrays;

public class PuzzleNavigator <T>{

    private T[] values;
    private int sizeValues;
    private int numValues;
    private int location;

    @SuppressWarnings("unchecked")
    public PuzzleNavigator() {
        this.sizeValues = 20;
        this.numValues = 0;
        this.values = ((T[]) new Object[sizeValues]);
    }

    /**
     * Returns the number of values in the Navigator.
     * @return number of values.
     */
    public int getNumValues() {
        return this.numValues;
    }

    /**
     * Moves to the previous value and returns it.
     * Returns null if no values exist or is oldest value.
     * @return previous value.
     */
    public T getOlderValue() {
        if (!hasOlderValue()) {return null;}
        location--;
        return values[location];
    }

    /**
     * Moves to the previous value and returns it.
     * Returns null if no values exist or is newest value.
     * @return previous value.
     */
    public T getNewerValue() {
        if (!hasNewerValue()) {return null;}
        location++;
        return values[location];
    }

    /**
     * Returns the newest value and sets it as current position.
     * Returns null if no values exist.
     * @return newest value
     */
    public T getNewestValue() {
        if (numValues == 0) {return null;}
        location = numValues - 1;
        return values[location];
    }

    /**
     * Returns the oldest value and sets it as current position.
     * Returns null if no values exist.
     * @return oldest value
     */
    public T getOldestValue() {
        if (numValues == 0) {return null;}
        location = 0;
        return values[location];

    }

    /**
     * Returns the current value if one exists or null if
     * the PuzzleNavigator is empty.
     * @return
     */
    public T getCurrentValue() {
        if (numValues == 0) {return null;}
        return values[location];
    }

    /**
     * Adds a new element in the newest position.
     * Does not affect current position.
     * @param value
     */
    public void add(T value) {
        if (numValues == sizeValues) {
            sizeValues += 20;
            values = Arrays.copyOf(values, sizeValues);
        }
        values[numValues] = value;
        numValues++;
    }

    /**
     * Checks if the current value is the oldest.
     * @return - false if oldest, true if it is not.
     */
    public boolean hasOlderValue() {
        return location != 0;
    }

    /**
     * Checks if the current value is the newest.
     * @return - false if newest, true if it is not.
     */
    public boolean hasNewerValue() {
        if (numValues == 0) {return false;}
        return location != (numValues - 1);
    }

}

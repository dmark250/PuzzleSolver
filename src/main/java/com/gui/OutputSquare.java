package main.java.com.gui;

import javax.swing.*;
import java.awt.*;

public class OutputSquare extends BoardSquare{

    JLabel outputText;

    public OutputSquare(int x, int y) {
        super(x, y);
        this.outputText = new JLabel("B");
        this.outputText.setFont(BoardSquare.textFont);
        this.outputText.setForeground(Color.black);
        this.add(outputText);
        this.revalidate();
        this.repaint();
    }

    @Override
    public char getSquareContents() {
        if (this.outputText.getText().isBlank()) {
            return '.';
        }
        char returnChar = this.outputText.getText().charAt(0);
        if (returnChar > '9' || returnChar < '1') {
            return '.';
        }
        return returnChar;
    }

    @Override
    public void setSquareContents(char squareChar) {
        this.outputText.setText(String.valueOf(squareChar));
        this.revalidate();
        this.repaint();
    }
}

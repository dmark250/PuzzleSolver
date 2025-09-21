package main.java.com.gui;

import javax.swing.*;
import java.awt.*;

public class OutputCharSquare extends BoardSquare{

    JLabel outputText;

    public OutputCharSquare(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.outputText = new JLabel("");
        outputText.setBounds(0, 0, width, height);
        outputText.setVerticalAlignment(JLabel.CENTER);
        outputText.setHorizontalAlignment(JLabel.CENTER);
        this.outputText.setFont(getTextFont(height));
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
        return this.outputText.getText().charAt(0);
    }

    @Override
    public void setSquareContents(char squareChar) {
        this.outputText.setText(String.valueOf(squareChar));
        this.revalidate();
        this.repaint();
    }
}

package main.java.com.gui;

import javax.swing.*;
import java.awt.*;

abstract public class BoardSquare extends JPanel {

    static protected Font textFont = new Font("Serif", Font.BOLD, 25);

    public BoardSquare(int x, int y) {
        this.setBounds(x, y, 39, 39);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public abstract char getSquareContents();

    public abstract void setSquareContents(char squareChar);

}

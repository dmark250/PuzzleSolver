package main.java.com.gui;

import javax.swing.*;
import java.awt.*;

abstract public class BoardSquare extends JPanel {

    static protected Font getTextFont(int height) {
        return new Font("Serif", Font.BOLD, (int)(height * 0.75) );
    }

    public BoardSquare(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        this.setBackground(Color.white);
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public abstract char getSquareContents();

    public abstract void setSquareContents(char squareChar);

}

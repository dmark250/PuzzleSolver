package main.java.com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserSquare extends BoardSquare{

    JTextField userText;

    public UserSquare(int x, int y) {
        super(x, y);
        userText = new JTextField();
        userText.setColumns(1);
        userText.setPreferredSize(new Dimension(30, 30));
        userText.setFont(BoardSquare.textFont);
        userText.setBackground(Color.white);
        userText.setText("A");
        userText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                userText.selectAll();
            }
        });
        this.add(userText);

    }

    @Override
    public char getSquareContents() {
        if (userText.getText().isBlank()) {
            return '.';
        }
        char returnChar = userText.getText().charAt(0);
        if (returnChar > '9' || returnChar < '1') {
            return '.';
        }
        return returnChar;
    }

    @Override
    public void setSquareContents(char squareChar) {
        userText.setText(String.valueOf(squareChar));
    }
}

package main.java.com.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UserCharSquare extends BoardSquare{

    JTextField userText;

    public UserCharSquare(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setLayout(null);
        userText = new JTextField();
        userText.setBounds((int)(.1 * width), 0, (int)(.8 * width), height);
        userText.setColumns(1);
        userText.setFont(getTextFont(height));
        userText.setBackground(Color.white);
        userText.setText("");
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
        return userText.getText().charAt(0);
    }

    @Override
    public void setSquareContents(char squareChar) {
        userText.setText(String.valueOf(squareChar));
    }
}

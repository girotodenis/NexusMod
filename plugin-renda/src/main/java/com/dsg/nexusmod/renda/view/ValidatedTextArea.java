package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ValidatedTextArea extends JPanel {
    private JTextArea textArea;
    private JLabel errorLabel;
    private Border defaultBorder;
    
    public ValidatedTextArea(int rows, int columns) {
        setLayout(new BorderLayout(0, 2));
        
        textArea = new JTextArea(rows, columns);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        defaultBorder = scrollPane.getBorder();
        
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font(errorLabel.getFont().getName(), Font.PLAIN, 10));
        errorLabel.setVisible(false);
        
        add(scrollPane, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }
    
    public void setError(String errorMessage) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            textArea.setBorder(BorderFactory.createLineBorder(Color.RED));
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
        } else {
            clearError();
        }
    }
    
    public void clearError() {
        textArea.setBorder(null);
        errorLabel.setVisible(false);
    }
    
    public String getText() {
        return textArea.getText();
    }
    
    public void setText(String text) {
        textArea.setText(text);
    }
}
package com.dsg.nexusmod.renda.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class ValidatedTextField extends JPanel {
    private JTextField textField;
    private JLabel errorLabel;
    private Border defaultBorder;
    
    public ValidatedTextField() {
        setLayout(new BorderLayout(0, 2));
        
        textField = new JTextField();
        defaultBorder = textField.getBorder();
        
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font(errorLabel.getFont().getName(), Font.PLAIN, 10));
        errorLabel.setVisible(false);
        
        add(textField, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.SOUTH);
    }
    
    public JTextField getTextField() {
        return textField;
    }
    
    public void setError(String errorMessage) {
        if (errorMessage != null && !errorMessage.isEmpty()) {
            textField.setBorder(BorderFactory.createLineBorder(Color.RED));
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
        } else {
            clearError();
        }
    }
    
    public void clearError() {
        textField.setBorder(defaultBorder);
        errorLabel.setVisible(false);
    }
    
    public String getText() {
        return textField.getText();
    }
    
    public void setText(String text) {
        textField.setText(text);
    }
}
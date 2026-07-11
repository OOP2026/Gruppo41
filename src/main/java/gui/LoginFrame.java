package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JPanel {
    private final JTextField loginField;
    private final JPasswordField passwordField;
    private final Controller controller;
    private final MainFrame mainFrame;

    public LoginFrame(MainFrame mainFrame, Controller controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        add(new JLabel("Login:"), gbc);
        loginField = new JTextField(15);
        gbc.gridx = 1; add(loginField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; passwordField = new JPasswordField(15);
        add(passwordField, gbc);
        
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton btn = new JButton("LOGIN");
        add(btn, gbc);
        
        btn.addActionListener(e -> {
            if (controller.login(loginField.getText(), new String(passwordField.getPassword()))) {
                mainFrame.cambiaSchermata(controller.getUtenteLoggato());
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali errate");
            }
        });
    }
}

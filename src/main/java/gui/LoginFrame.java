package gui;

import controller.Controller;
import model.Utente;

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
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("ACCESSO AL SISTEMA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Login:"), gbc);
        gbc.gridx = 1;
        loginField = new JTextField(15);
        add(loginField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("LOGIN");
        add(loginButton, gbc);

        loginButton.addActionListener(e -> eseguiLogin());
    }

    private void eseguiLogin() {
        String login = loginField.getText().trim();
        String password = new String(passwordField.getPassword());

        boolean successo = controller.login(login, password);

        if (successo) {
            mainFrame.cambiaSchermata(controller.getUtenteLoggato());
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}

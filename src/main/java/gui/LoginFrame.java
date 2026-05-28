package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;

    class LoginPanel extends JPanel {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private MainFrame mainFrame;
    private Controller controller;

    public LoginPanel(MainFrame mainFrame, Controller controller) {
        this.mainFrame = mainFrame;
        this.controller = controller;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("ACCESSO AL SISTEMA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; emailField = new JTextField(15); add(emailField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0; add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; passwordField = new JPasswordField(15); add(passwordField, gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        loginButton = new JButton("LOGIN");
        add(loginButton, gbc);

        loginButton.addActionListener(e -> eseguiLogin());
    }

    private void eseguiLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Il controller autentica l'utente e restituisce l'istanza specifica (Studente, Docente, ecc.)
        Utente utenteLoggato = controller.login(email, password);

        if (utenteLoggato != null) {
            emailField.setText("");
            passwordField.setText("");
            mainFrame.cambiaSchermata(utenteLoggato);
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}

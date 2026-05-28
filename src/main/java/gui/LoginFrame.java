package gui;

import controller.controller;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private Controller controller;

    public LoginFrame(controller controller) {
        this.controller = controller;

        setTitle("LOGIN");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("LOGIN");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> login());
    }

    private void login() {

        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        boolean accesso = controller.login(email, password);

        if (accesso) {
            JOptionPane.showMessageDialog(this,
                    "Login effettuato con successo");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenziali errate",
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

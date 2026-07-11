package gui;

import controller.Controller;
import model.Coordinatore;
import model.Docente;
import model.ResponsabileOrario;
import model.Studente;
import model.Utente;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField loginField;
    private final JPasswordField passwordField;
    private final Controller controller;

    public LoginFrame(Controller controller) {
        this.controller = controller;

        setTitle("Accesso al Sistema");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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
            apriSchermataPerRuolo(controller.getUtenteLoggato());
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void apriSchermataPerRuolo(Utente utente) {
        JFrame frame;

        if (utente instanceof Coordinatore) {
            frame = new CoordinatoreFrame((Coordinatore) utente, controller);
        } else if (utente instanceof ResponsabileOrario) {
            frame = new ResponsabileOrarioFrame((ResponsabileOrario) utente, controller);
        } else if (utente instanceof Docente) {
            frame = new DocenteFrame((Docente) utente, controller);
        } else if (utente instanceof Studente) {
            frame = new StudenteFrame((Studente) utente, controller);
        } else {
            return;
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

package gui;

import controller.Controller;
import model.*;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private Controller controller;
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;

    public LoginFrame(Controller controller) {
        this.controller = controller;
        setTitle("Accesso al Sistema Gestione Orari");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 247, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        userField = new JTextField(15);
        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        passField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passField, gbc);

        loginButton = new JButton("Accedi");
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> eseguiLogin());
    }

    private void eseguiLogin() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (controller.login(username, password)) {
            this.dispose();
            
            // Gestione della navigazione interamente nello strato GUI (Risolve la violazione ArchUnit)
            Utente utenteLoggato = controller.getUtenteLoggato();
            if (utenteLoggato instanceof Coordinatore) {
                new CoordinatoreFrame(controller).setVisible(true);
            } else if (utenteLoggato instanceof ResponsabileOrario) {
                new ResponsabileOrarioFrame(controller).setVisible(true);
            } else if (utenteLoggato instanceof Docente) {
                new DocenteFrame(controller).setVisible(true);
            } else if (utenteLoggato instanceof Studente) {
                new StudenteFrame(controller).setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali non valide. Riprova.", "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}

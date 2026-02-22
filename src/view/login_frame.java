/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import DAO.DBUtil;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author HP
 */
public class login_frame extends javax.swing.JFrame {

    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JTextField textLogin;
    private JPasswordField textPassword;
    private JButton btnLogin;
    private JLabel lblTitle;
    private JLabel lblSubtitle;

    public login_frame() {
        initComponents();
        setTitle("Connexion - Restaurant IAI");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Configuration du layout principal
        mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // PANEL GAUCHE (VIOLET AVEC TEXTE)
        leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBackground(new Color(102, 0, 102));

        JLabel welcomeText = new JLabel("<html><div style='text-align: center;'>"
                + "<span style='font-size: 48px; font-family: Script MT Bold;'>BIENVENUE</span><br>"
                + "<span style='font-size: 36px; font-family: Script MT Bold;'>CHEZ NOUS</span>"
                + "</div></html>");
        welcomeText.setForeground(Color.WHITE);
        welcomeText.setHorizontalAlignment(JLabel.CENTER);

        leftPanel.add(welcomeText);

        // PANEL DROIT (FORMULAIRE)
        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre
        lblTitle = new JLabel("CONNEXION");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(102, 0, 102));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        rightPanel.add(lblTitle, gbc);

        // Sous-titre
        lblSubtitle = new JLabel("Veuillez vous connecter pour continuer");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(Color.GRAY);
        lblSubtitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        rightPanel.add(lblSubtitle, gbc);

        // Espace
        gbc.gridy = 2;
        rightPanel.add(Box.createVerticalStrut(20), gbc);

        // Label Login
        JLabel lblLogin = new JLabel("Nom d'utilisateur");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLogin.setForeground(new Color(51, 51, 51));
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(lblLogin, gbc);

        // Champ Login
        textLogin = new JTextField(20);
        textLogin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        rightPanel.add(textLogin, gbc);

        // Label Password
        JLabel lblPassword = new JLabel("Mot de passe");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(new Color(51, 51, 51));
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        rightPanel.add(lblPassword, gbc);

        // Champ Password
        textPassword = new JPasswordField(20);
        textPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        rightPanel.add(textPassword, gbc);

        // Bouton de connexion
        btnLogin = new JButton("SE CONNECTER");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(new Color(102, 0, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(this::btnLoginActionPerformed);

        // Effet hover
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(124, 93, 149));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(102, 0, 102));
            }
        });

        gbc.gridy = 7;
        gbc.insets = new Insets(30, 10, 10, 10);
        rightPanel.add(btnLogin, gbc);

        // Lien mot de passe oublié
        JLabel forgotPassword = new JLabel("Mot de passe oublié ?");
        forgotPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        forgotPassword.setForeground(new Color(102, 102, 102));
        forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPassword.setHorizontalAlignment(JLabel.CENTER);
        forgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(login_frame.this,
                        "Contactez l'administrateur pour réinitialiser votre mot de passe.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        gbc.gridy = 8;
        gbc.insets = new Insets(10, 10, 10, 10);
        rightPanel.add(forgotPassword, gbc);

        // Ajout des panels au panel principal
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // Ajout au frame
        getContentPane().add(mainPanel);

        // Raccourci clavier (Entrée pour se connecter)
        getRootPane().setDefaultButton(btnLogin);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String login = textLogin.getText().trim();
        String pass = new String(textPassword.getPassword());

        // Validation des champs
        if (login.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs !",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (java.sql.Connection con = DBUtil.getConnection()) {
            // Requête préparée pour la sécurité
            String sql = "SELECT * FROM Utilisateur WHERE login=? AND mot_de_passe=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, login);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Authentification réussie
                JOptionPane.showMessageDialog(this,
                        "Bienvenue " + login + " !",
                        "Connexion réussie",
                        JOptionPane.INFORMATION_MESSAGE);

                // Redirection vers le menu principal
                teste teste = new teste();
                teste.setVisible(true);
                teste.setExtendedState(JFrame.MAXIMIZED_BOTH);

                // Fermeture de l'écran de connexion
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Login ou mot de passe incorrect.",
                        "Échec de connexion",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion à la base : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login_frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login_frame().setVisible(true);
            }
        });
    }
}

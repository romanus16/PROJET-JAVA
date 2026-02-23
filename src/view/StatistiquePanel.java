/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Business.Produit;
import Business.Commande;
import DAO.CommandeDAO;
import DAO.ProduitDAO;
import DAO.DBException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author HP
 */
public class StatistiquePanel extends javax.swing.JPanel {
    
    // DAO
    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;
    
    //Modèles de tableaux
    private DefaultTableModel modelTopProduits;
    private DefaultTableModel modelAlertes;
    
    //Constructeur
    public StatistiquePanel() {
        initComponents();
        
        // Initialiser les DAOs
        commandeDAO = new CommandeDAO();
        produitDAO = new ProduitDAO();
        
        // Préparer les tableaux
        preparerTableaux();
        
        // Charger les statistiques
        chargerStatistiques();
    }
    
    //Initialisation des composants
    private void preparerTableaux() {
        // Tableau des top produits
        modelTopProduits = new DefaultTableModel(
            new String[]{"Produit", "Catégorie", "Quantité vendue", "Chiffre d'affaires"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableTopProduits.setModel(modelTopProduits);
        
        // Tableau des alertes
        modelAlertes = new DefaultTableModel(
            new String[]{"Produit", "Stock actuel", "Seuil", "Déficit"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAlertesStock.setModel(modelAlertes);
        
        // Centrer les colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        tableTopProduits.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableTopProduits.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tableAlertesStock.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tableAlertesStock.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableAlertesStock.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }
    
    //Charger toutes les statistiques
    private void chargerStatistiques() {
        chargerStatistique();
        chargerTopProduits();
        chargerAlertesStock();
        chargerValeurStock();
    }
    
    
    private void chargerStatistique() {
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            ProduitDAO produitDAO = new ProduitDAO();
            
            // CA du jour
            double caJour = commandeDAO.chiffreAffairesDuJour();
            lblCAJour.setText(String.format("%,.0f FCFA", caJour));
            
            // CA du mois (approximation)
            double caMois = caJour * 30; // Simplification
            lblCAMois.setText(String.format("%,.0f FCFA", caMois));
            
            // Top produits (simulation)
            chargerTopProduits();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur chargement stats: " + e.getMessage());
        }
    }
    
    // Charger le top 5 des produits vendus
    private void chargerTopProduits() {
        try {
            modelTopProduits.setRowCount(0);
            
            // Récupérer tous les produits
            List<Produit> produits = produitDAO.listerTous();
            
            // Simuler les ventes (à remplacer par des vraies stats de votre BD)
            Map<Integer, Integer> ventes = new HashMap<>();
            Map<Integer, Double> caProduit = new HashMap<>();
            
            for (Produit p : produits) {
                // Simulation: les premiers produits ont plus de ventes
                int quantite = 50 - (p.getId() % 30);
                ventes.put(p.getId(), quantite);
                caProduit.put(p.getId(), quantite * p.getPrixVente());
            }
            
            // Trier et afficher top 5
            produits.sort((p1, p2) -> 
                ventes.get(p2.getId()).compareTo(ventes.get(p1.getId())));
            
            int count = 0;
            for (Produit p : produits) {
                if (count >= 5) break;
                
                Object[] ligne = {
                    p.getNom(),
                    p.getCategorie().getLibelle(),
                    ventes.get(p.getId()),
                    String.format("%,.0f FCFA", caProduit.get(p.getId()))
                };
                modelTopProduits.addRow(ligne);
                count++;
            }
            
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement top produits: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Charger les alertes de stock
    private void chargerAlertesStock() {
        try {
            modelAlertes.setRowCount(0);
            List<Produit> alertes = produitDAO.produitsEnAlerte();
            
            for (Produit p : alertes) {
                int deficit = p.getSeuilAlerte() - p.getStockActuel();
                if (deficit < 0) deficit = 0;
                
                Object[] ligne = {
                    p.getNom(),
                    p.getStockActuel(),
                    p.getSeuilAlerte(),
                    deficit
                };
                modelAlertes.addRow(ligne);
            }
            
            lblNbAlertes.setText(String.valueOf(alertes.size()));
            
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement alertes: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Charger la valeur totale du stock
    private void chargerValeurStock() {
        try {
            List<Produit> produits = produitDAO.listerTous();
            double valeurTotale = 0;
            int totalProduits = 0;
            
            for (Produit p : produits) {
                valeurTotale += p.getStockActuel() * p.getPrixVente();
                totalProduits += p.getStockActuel();
            }
            
            lblValeurStock.setText(String.format("%,.0f FCFA", valeurTotale));
            lblTotalProduits.setText(String.valueOf(totalProduits));
            
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur calcul valeur stock: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelHeader = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelIcon = new javax.swing.JLabel();
        jPanelMain = new javax.swing.JPanel();
        jPanelCA = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCAJour = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCAMois = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblCAAnnee = new javax.swing.JLabel();
        jPanelCommandes = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblNbCommandes = new javax.swing.JLabel();
        jPanelStock = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblValeurStock = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblTotalProduits = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblNbAlertes = new javax.swing.JLabel();
        jPanelTop = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTopProduits = new javax.swing.JTable();
        jPanelAlert = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAlertesStock = new javax.swing.JTable();
        btnRafraichir = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanelHeader.setBackground(new java.awt.Color(102, 0, 102));
        jPanelHeader.setLayout(new java.awt.GridBagLayout());

        jLabelTitle.setFont(new java.awt.Font("Script MT Bold", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitle.setText("STATISTIQUES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 6, 40, 320);
        jPanelHeader.add(jLabelTitle, gridBagConstraints);

        jLabelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/finance_mode_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 336, 40, 0);
        jPanelHeader.add(jLabelIcon, gridBagConstraints);

        add(jPanelHeader, java.awt.BorderLayout.NORTH);

        jPanelMain.setLayout(new java.awt.GridBagLayout());

        jPanelCA.setBorder(javax.swing.BorderFactory.createTitledBorder("Chiffre d'Affaires"));
        jPanelCA.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Aujourd'hui :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelCA.add(jLabel1, gridBagConstraints);

        lblCAJour.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCAJour.setForeground(new java.awt.Color(0, 102, 0));
        lblCAJour.setText("0 FCFA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 20);
        jPanelCA.add(lblCAJour, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Ce mois :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanelCA.add(jLabel3, gridBagConstraints);

        lblCAMois.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCAMois.setForeground(new java.awt.Color(0, 102, 0));
        lblCAMois.setText("0 FCFA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 20);
        jPanelCA.add(lblCAMois, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Cette année :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanelCA.add(jLabel5, gridBagConstraints);

        lblCAAnnee.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblCAAnnee.setForeground(new java.awt.Color(0, 102, 0));
        lblCAAnnee.setText("0 FCFA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelCA.add(lblCAAnnee, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelCA, gridBagConstraints);

        jPanelCommandes.setBorder(javax.swing.BorderFactory.createTitledBorder("Commandes"));
        jPanelCommandes.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Commandes aujourd'hui :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelCommandes.add(jLabel7, gridBagConstraints);

        lblNbCommandes.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblNbCommandes.setForeground(new java.awt.Color(0, 0, 153));
        lblNbCommandes.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelCommandes.add(lblNbCommandes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelCommandes, gridBagConstraints);

        jPanelStock.setBorder(javax.swing.BorderFactory.createTitledBorder("Stock"));
        jPanelStock.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Valeur totale du stock :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelStock.add(jLabel9, gridBagConstraints);

        lblValeurStock.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblValeurStock.setForeground(new java.awt.Color(153, 0, 0));
        lblValeurStock.setText("0 FCFA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 20);
        jPanelStock.add(lblValeurStock, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Total articles :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanelStock.add(jLabel11, gridBagConstraints);

        lblTotalProduits.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTotalProduits.setForeground(new java.awt.Color(153, 0, 0));
        lblTotalProduits.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 20);
        jPanelStock.add(lblTotalProduits, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Alertes stock :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 5);
        jPanelStock.add(jLabel13, gridBagConstraints);

        lblNbAlertes.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblNbAlertes.setForeground(new java.awt.Color(255, 0, 0));
        lblNbAlertes.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelStock.add(lblNbAlertes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelMain.add(jPanelStock, gridBagConstraints);

        jPanelTop.setBorder(javax.swing.BorderFactory.createTitledBorder("Top 5 Produits vendus"));
        jPanelTop.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Les produits les plus vendus :");
        jPanelTop.add(jLabel15, java.awt.BorderLayout.NORTH);

        tableTopProduits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Produit", "Catégorie", "Quantité", "CA"
            }
        ));
        jScrollPane1.setViewportView(tableTopProduits);

        jPanelTop.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        jPanelMain.add(jPanelTop, gridBagConstraints);

        jPanelAlert.setBorder(javax.swing.BorderFactory.createTitledBorder("Alertes Stock"));
        jPanelAlert.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Produits en dessous du seuil d'alerte :");
        jPanelAlert.add(jLabel17, java.awt.BorderLayout.NORTH);

        tableAlertesStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Produit", "Stock", "Seuil", "Déficit"
            }
        ));
        jScrollPane2.setViewportView(tableAlertesStock);

        jPanelAlert.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        jPanelMain.add(jPanelAlert, gridBagConstraints);

        btnRafraichir.setBackground(new java.awt.Color(51, 153, 255));
        btnRafraichir.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRafraichir.setText("RAFRAÎCHIR LES STATISTIQUES");
        btnRafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRafraichirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelMain.add(btnRafraichir, gridBagConstraints);

        add(jPanelMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRafraichirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRafraichirActionPerformed
        chargerStatistiques();
    }//GEN-LAST:event_btnRafraichirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRafraichir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelIcon;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanelAlert;
    private javax.swing.JPanel jPanelCA;
    private javax.swing.JPanel jPanelCommandes;
    private javax.swing.JPanel jPanelHeader;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelStock;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCAAnnee;
    private javax.swing.JLabel lblCAJour;
    private javax.swing.JLabel lblCAMois;
    private javax.swing.JLabel lblNbAlertes;
    private javax.swing.JLabel lblNbCommandes;
    private javax.swing.JLabel lblTotalProduits;
    private javax.swing.JLabel lblValeurStock;
    private javax.swing.JTable tableAlertesStock;
    private javax.swing.JTable tableTopProduits;
    // End of variables declaration//GEN-END:variables
}
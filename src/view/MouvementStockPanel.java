/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Business.MouvementStock;
import Business.Produit;
import Business.MouvementStock.TypeMouvement;
import DAO.MouvementStockDAO;
import DAO.ProduitDAO;
import DAO.DBException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;

/**
 *
 * @author HP
 */
public class MouvementStockPanel extends javax.swing.JPanel {
    
    // ========== DAOs ==========
    private MouvementStockDAO mouvementDAO;
    private ProduitDAO produitDAO;
    
    // ========== Modèles de tableaux ==========
    private DefaultTableModel modelMouvements;
    private DefaultTableModel modelAlertes;
    
    // ========== Constructeur ==========
    public MouvementStockPanel() {
        initComponents();
        
        // Initialiser les DAOs
        mouvementDAO = new MouvementStockDAO();
        produitDAO = new ProduitDAO();
        
        // Préparer les tableaux
        preparerTableaux();
        
        // Charger les données
        chargerProduits();
        chargerMouvements();
        chargerAlertes();
        
        // Configurer la comboBox des types
        txtType.removeAllItems();
        txtType.addItem("ENTREE");
        txtType.addItem("SORTIE");
    }
    
    // ========== Initialisation des tableaux ==========
    private void preparerTableaux() {
        // Tableau des mouvements
        modelMouvements = (DefaultTableModel) tbnMouvementStock.getModel();
        modelMouvements.setRowCount(0);
        
        // Tableau des alertes
        modelAlertes = (DefaultTableModel) tbnAlertStock.getModel();
        modelAlertes.setRowCount(0);
        
        // Centrer certaines colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        tbnMouvementStock.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tbnMouvementStock.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Type
        tbnMouvementStock.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Quantité
        
        tbnAlertStock.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Stock
        tbnAlertStock.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Seuil
        tbnAlertStock.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Déficit
    }
    
    // ========== Charger les produits dans la comboBox ==========
    private void chargerProduits() {
        try {
            txtProduitsStock.removeAllItems();
            List<Produit> produits = produitDAO.listerTous();
            
            for (Produit p : produits) {
                txtProduitsStock.addItem(p);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement produits: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ========== Charger l'historique des mouvements ==========
    private void chargerMouvements() {
        try {
            modelMouvements.setRowCount(0);
            List<MouvementStock> mouvements = mouvementDAO.listerMouvements();
            
            for (MouvementStock m : mouvements) {
                Object[] ligne = {
                    m.getId(),
                    new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(m.getDateMouvement()),
                    m.getType(),
                    m.getProduit().getNom(),
                    m.getQuantite(),
                    m.getMotif()
                };
                modelMouvements.addRow(ligne);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement mouvements: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ========== Charger les alertes de stock ==========
    private void chargerAlertes() {
        try {
            modelAlertes.setRowCount(0);
            List<Produit> alertes = produitDAO.produitsEnAlerte();
            
            for (Produit p : alertes) {
                int deficit = p.getSeuilAlerte() - p.getStockActuel();
                if (deficit < 0) deficit = 0;
                
                Object[] ligne = {
                    p.getNom(),
                    p.getCategorie().getLibelle(),
                    p.getStockActuel(),
                    p.getSeuilAlerte(),
                    deficit
                };
                modelAlertes.addRow(ligne);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement alertes: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ========== Vider le formulaire ==========
    private void viderFormulaire() {
        if (txtProduitsStock.getItemCount() > 0) {
            txtProduitsStock.setSelectedIndex(0);
        }
        txtQuantiteStock.setText("");
        if (txtType.getItemCount() > 0) {
            txtType.setSelectedIndex(0);
        }
        txtMotif.setText("");
    }
    
    // ========== Valider la saisie ==========
    private boolean validerSaisie() {
        if (txtProduitsStock.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un produit",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String quantiteStr = txtQuantiteStock.getText().trim();
        if (quantiteStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Veuillez saisir une quantité",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            int quantite = Integer.parseInt(quantiteStr);
            if (quantite <= 0) {
                JOptionPane.showMessageDialog(this,
                    "La quantité doit être supérieure à 0",
                    "Validation", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Quantité invalide",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (txtType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez sélectionner un type de mouvement",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String motif = txtMotif.getText().trim();
        if (motif.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Veuillez saisir un motif",
                "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    // ========== Enregistrer un mouvement ==========
    private void enregistrerMouvement() {
        if (!validerSaisie()) return;
        
        Produit produit = (Produit) txtProduitsStock.getSelectedItem();
        int quantite = Integer.parseInt(txtQuantiteStock.getText().trim());
        TypeMouvement type = TypeMouvement.valueOf((String) txtType.getSelectedItem());
        String motif = txtMotif.getText().trim();
        
        // Vérifier le stock pour une sortie
        if (type == TypeMouvement.SORTIE && produit.getStockActuel() < quantite) {
            JOptionPane.showMessageDialog(this,
                "Stock insuffisant! Disponible: " + produit.getStockActuel(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirmer l'enregistrement de ce mouvement ?\n" +
            "Produit: " + produit.getNom() + "\n" +
            "Type: " + type + "\n" +
            "Quantité: " + quantite,
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Créer le mouvement
                MouvementStock mouvement = new MouvementStock(produit, type, quantite, motif);
                
                // Enregistrer le mouvement
                if (mouvementDAO.enregistrerMouvement(mouvement)) {
                    // Mettre à jour le stock du produit
                    int signe = (type == TypeMouvement.ENTREE) ? quantite : -quantite;
                    produitDAO.mettreAJourStock(produit.getId(), signe);
                    
                    JOptionPane.showMessageDialog(this,
                        "✅ Mouvement enregistré avec succès",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                    
                    viderFormulaire();
                    chargerMouvements();
                    chargerAlertes();
                    chargerProduits(); // Recharger pour mettre à jour les stocks
                }
            } catch (DBException e) {
                JOptionPane.showMessageDialog(this,
                    "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbnMouvementStock = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtProduitsStock = new javax.swing.JComboBox<>();
        txtQuantiteStock = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtType = new javax.swing.JComboBox<>();
        txtMotif = new javax.swing.JTextField();
        btnEnregistrer = new javax.swing.JButton();
        btnVider = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbnAlertStock = new javax.swing.JTable();
        btnRafraichir = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Historique des Mouvements")));

        tbnMouvementStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Type", "Produit", "Quantité", "Motif"
            }
        ));
        jScrollPane2.setViewportView(tbnMouvementStock);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 907;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel4.add(jPanel6, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Nouveau Mouvement"));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Produit :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Quantité :");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Type :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Motif :");

        btnEnregistrer.setBackground(new java.awt.Color(51, 153, 255));
        btnEnregistrer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEnregistrer.setText("ENREGISTRER");
        btnEnregistrer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnregistrerActionPerformed(evt);
            }
        });

        btnVider.setText("VIDER");
        btnVider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtProduitsStock, 0, 257, Short.MAX_VALUE)
                            .addComponent(txtQuantiteStock)
                            .addComponent(txtType, 0, 257, Short.MAX_VALUE)
                            .addComponent(txtMotif)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(btnEnregistrer)
                        .addGap(18, 18, 18)
                        .addComponent(btnVider)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtProduitsStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtQuantiteStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtMotif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnregistrer)
                    .addComponent(btnVider))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        jPanel4.add(jPanel7, gridBagConstraints);

        jTabbedPane1.addTab("📦 Mouvements", jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        tbnAlertStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Produit", "Catégorie", "Stock", "Seuil", "Déficit"
            }
        ));
        jScrollPane1.setViewportView(tbnAlertStock);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("⚠️ Alerte Stock", jPanel5);

        btnRafraichir.setText("Rafraîchir");
        btnRafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRafraichirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRafraichir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRafraichir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 56;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -50;
        gridBagConstraints.ipady = -21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        add(jPanel1, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(102, 0, 102));
        jPanel8.setForeground(new java.awt.Color(102, 0, 102));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION DES STOCKS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 6, 40, 320);
        jPanel8.add(jLabel1, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bookmark_stacks_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 336, 40, 0);
        jPanel8.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 0);
        add(jPanel8, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnregistrerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnregistrerActionPerformed
        enregistrerMouvement();
    }//GEN-LAST:event_btnEnregistrerActionPerformed

    private void btnViderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViderActionPerformed
        viderFormulaire();
    }//GEN-LAST:event_btnViderActionPerformed

    private void btnRafraichirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRafraichirActionPerformed
        chargerProduits();
        chargerMouvements();
        chargerAlertes();
    }//GEN-LAST:event_btnRafraichirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnregistrer;
    private javax.swing.JButton btnRafraichir;
    private javax.swing.JButton btnVider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbnAlertStock;
    private javax.swing.JTable tbnMouvementStock;
    private javax.swing.JTextField txtMotif;
    private javax.swing.JComboBox<Business.Produit> txtProduitsStock;
    private javax.swing.JTextField txtQuantiteStock;
    private javax.swing.JComboBox<String> txtType;
    // End of variables declaration//GEN-END:variables
}
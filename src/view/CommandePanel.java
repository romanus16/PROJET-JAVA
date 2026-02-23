/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Business.Commande;
import Business.LigneCommande;
import Business.Produit;
import Business.Commande.EtatCommande;
import DAO.CommandeDAO;
import DAO.LigneCommandeDAO;
import DAO.ProduitDAO;
import DAO.DBException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class CommandePanel extends javax.swing.JPanel {
    
    // DAO
    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    
    // Modèles de tableaux
    private DefaultTableModel modelProduitsDispo;
    private DefaultTableModel modelCommandeEnCours;
    
    //Données de la commande en cours
    private Commande commandeEnCours;
    private List<LigneCommande> lignesCommande;
    private double totalCommande = 0;
    
    //Constructeur
    public CommandePanel() {
        initComponents();
        
        // Initialiser les DAO
        commandeDAO = new CommandeDAO();
        produitDAO = new ProduitDAO();
        ligneCommandeDAO = new LigneCommandeDAO();
        
        // Préparer les tableaux
        preparerTableaux();
        
        // Charger les données
        chargerProduitsDisponibles();
        nouvelleCommande();
    }
    
    private void preparerTableaux() {
        // Tableau des produits disponibles
        modelProduitsDispo = (DefaultTableModel) tbnProduitDispo.getModel();
        modelProduitsDispo.setRowCount(0);
        
        // Configuration des colonnes du tableau des produits disponibles
        try {
            tbnProduitDispo.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
            tbnProduitDispo.getColumnModel().getColumn(1).setPreferredWidth(150); // Produit
            tbnProduitDispo.getColumnModel().getColumn(2).setPreferredWidth(100); // Catégorie
            tbnProduitDispo.getColumnModel().getColumn(3).setPreferredWidth(80);  // Prix
            tbnProduitDispo.getColumnModel().getColumn(4).setPreferredWidth(60);  // Stock
        } catch (Exception e) {
            // Ignorer si les colonnes n'existent pas
        }
        
        // Tableau de la commande en cours
        modelCommandeEnCours = (DefaultTableModel) tbnCommandeEnCour.getModel();
        modelCommandeEnCours.setRowCount(0);
        
        // Centrer certaines colonnes du tableau de commande
        try {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            
            tbnCommandeEnCour.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Prix
            tbnCommandeEnCour.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Quantité
            tbnCommandeEnCour.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Total
        } catch (Exception e) {
            // Ignorer si les colonnes n'existent pas
        }
    }
    
    //Chargement des produits disponibles 
    private void chargerProduitsDisponibles() {
        try {
            modelProduitsDispo.setRowCount(0);
            List<Produit> produits = produitDAO.listerTous();
            
            for (Produit p : produits) {
                if (p.getStockActuel() > 0) { // Afficher seulement les produits en stock
                    Object[] ligne = {
                        p.getId(),
                        p.getNom(),
                        p.getCategorie().getLibelle(),
                        p.getPrixVente(),
                        p.getStockActuel()
                    };
                    modelProduitsDispo.addRow(ligne);
                }
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur chargement produits: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Chargement des produits dans la comboBox
    private void chargerProduitsDansCombo() {
        try {
            txtProduits.removeAllItems();
            List<Produit> produits = produitDAO.listerTous();
            
            for (Produit p : produits) {
                if (p.getStockActuel() > 0) {
                    txtProduits.addItem(p);
                }
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur chargement produits: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Nouvelle commande
    private void nouvelleCommande() {
        commandeEnCours = new Commande();
        commandeEnCours.setId(-1);
        commandeEnCours.setEtat(EtatCommande.EN_COURS);
        
        lignesCommande = new ArrayList<>();
        totalCommande = 0;
        
        actualiserAffichageCommande();
        chargerProduitsDansCombo();
        txtQuantite.setValue(1);
        
        // Activer/désactiver les boutons
        btnNouveauCommande.setEnabled(false);
        btnSupprimerLigne.setEnabled(true);
        btnValiderCommande.setEnabled(true);
        btnAnnulerCommande.setEnabled(true);
        btnAjouterLigne.setEnabled(true);
    }
    
    //Actualiser l'affichage de la commande
    private void actualiserAffichageCommande() {
        modelCommandeEnCours.setRowCount(0);
        totalCommande = 0;
        
        for (LigneCommande ligne : lignesCommande) {
            Object[] ligneData = {
                ligne.getProduit().getNom(),
                ligne.getPrixUnitaire(),
                ligne.getQuantite(),
                ligne.getMontantLigne()
            };
            modelCommandeEnCours.addRow(ligneData);
            totalCommande += ligne.getMontantLigne();
        }
        
        jLabel4.setText("TOTAL: " + totalCommande + " FCFA");
    }
    
    //Vérifier le stock
    private boolean verifierStock(Produit produit, int quantite) {
        return produit.getStockActuel() >= quantite;
    }
    
    //Ajouter une ligne à la commande
    private void ajouterLigneCommande() {
        if (txtProduits.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un produit",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Produit produit = (Produit) txtProduits.getSelectedItem();
        int quantite = (int) txtQuantite.getValue();
        
        if (quantite <= 0) {
            JOptionPane.showMessageDialog(this, 
                "La quantité doit être supérieure à 0",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!verifierStock(produit, quantite)) {
            JOptionPane.showMessageDialog(this, 
                "Stock insuffisant! Disponible: " + produit.getStockActuel(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Vérifier si le produit est déjà dans la commande
        for (LigneCommande ligne : lignesCommande) {
            if (ligne.getProduit().getId() == produit.getId()) {
                int nouvelleQuantite = ligne.getQuantite() + quantite;
                if (!verifierStock(produit, nouvelleQuantite)) {
                    JOptionPane.showMessageDialog(this, 
                        "Stock insuffisant pour ajouter cette quantité!",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ligne.setQuantite(nouvelleQuantite);
                actualiserAffichageCommande();
                return;
            }
        }
        
        // Nouveau produit
        LigneCommande nouvelleLigne = new LigneCommande(produit, quantite);
        lignesCommande.add(nouvelleLigne);
        actualiserAffichageCommande();
    }
    
    //Supprimer une ligne sélectionnée
    private void supprimerLigneSelectionnee() {
        int row = tbnCommandeEnCour.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner une ligne à supprimer",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        lignesCommande.remove(row);
        actualiserAffichageCommande();
    }
    
    //Générer le reçu
    private void genererRecu() {
        StringBuilder recu = new StringBuilder();
        recu.append("══════════════════════════════════════════\n");
        recu.append("            RESTAURANT IAI                \n");
        recu.append("          📋 REÇU DE COMMANDE            \n");
        recu.append("══════════════════════════════════════════\n\n");
        
        // Date et heure
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        recu.append("Date: ").append(sdf.format(new java.util.Date())).append("\n");
        recu.append("Commande N°: ").append(System.currentTimeMillis() % 10000).append("\n\n");
        
        recu.append("------------------------------------------\n");
        recu.append(String.format("%-20s %5s %10s\n", "Produit", "Qte", "Prix"));
        recu.append("------------------------------------------\n");
        
        // Lignes de commande
        for (LigneCommande ligne : lignesCommande) {
            String nom = ligne.getProduit().getNom();
            if (nom.length() > 18) nom = nom.substring(0, 18);
            
            recu.append(String.format("%-20s %5d %10.2f\n", 
                nom, 
                ligne.getQuantite(), 
                ligne.getMontantLigne()
            ));
        }
        
        recu.append("------------------------------------------\n");
        recu.append(String.format("%-26s %10.2f\n", "TOTAL:", totalCommande));
        recu.append("══════════════════════════════════════════\n");
        recu.append("         MERCI DE VOTRE VISITE !         \n");
        recu.append("══════════════════════════════════════════\n");
        
        // Afficher le reçu
        JTextArea textArea = new JTextArea(recu.toString());
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(450, 500));
        
        JOptionPane.showMessageDialog(this, scrollPane, "📄 REÇU DE COMMANDE", 
            JOptionPane.PLAIN_MESSAGE);
    }
    
    // Valider la commande
    private void validerCommande() {
        if (lignesCommande.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "La commande doit contenir au moins un produit",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Confirmer la validation de cette commande ?\nTotal: " + totalCommande + " FCFA",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Mettre à jour le stock
                for (LigneCommande ligne : lignesCommande) {
                    produitDAO.mettreAJourStock(
                        ligne.getProduit().getId(), 
                        -ligne.getQuantite()
                    );
                }
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Commande validée avec succès!",
                    "Succès", JOptionPane.INFORMATION_MESSAGE);
                
                // Générer le reçu
                genererRecu();
                
                // Nouvelle commande
                nouvelleCommande();
                chargerProduitsDisponibles();
                
            } catch (DBException e) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la validation: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    //Annuler la commande
    private void annulerCommande() {
        if (lignesCommande.isEmpty()) {
            nouvelleCommande();
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment annuler cette commande ?",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            nouvelleCommande();
            chargerProduitsDisponibles();
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbnCommandeEnCour = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNouveauCommande = new javax.swing.JButton();
        btnSupprimerLigne = new javax.swing.JButton();
        btnValiderCommande = new javax.swing.JButton();
        btnAnnulerCommande = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbnProduitDispo = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtQuantite = new javax.swing.JSpinner();
        txtProduits = new javax.swing.JComboBox<>();
        btnAjouterLigne = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION DES COMMANDES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 18, 48, 367);
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dining_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 394, 48, 0);
        jPanel2.add(jLabel5, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(450);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSplitPane1.setInheritsPopupMenu(true);
        jSplitPane1.setVerifyInputWhenFocusTarget(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Commande en Cour"));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        tbnCommandeEnCour.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Produit", "Prix Unitaire", "Quantité", "Total Ligne"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbnCommandeEnCour);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 659;
        gridBagConstraints.ipady = 363;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 11, 0, 0);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        btnNouveauCommande.setBackground(new java.awt.Color(51, 102, 255));
        btnNouveauCommande.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNouveauCommande.setText("Nouvelle Commande");

        btnSupprimerLigne.setBackground(new java.awt.Color(255, 102, 102));
        btnSupprimerLigne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSupprimerLigne.setText("Supprimer une ligne");
        btnSupprimerLigne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerLigneActionPerformed(evt);
            }
        });

        btnValiderCommande.setBackground(new java.awt.Color(51, 255, 0));
        btnValiderCommande.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnValiderCommande.setForeground(new java.awt.Color(51, 51, 51));
        btnValiderCommande.setText("Valider une commande");
        btnValiderCommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValiderCommandeActionPerformed(evt);
            }
        });

        btnAnnulerCommande.setBackground(new java.awt.Color(204, 0, 0));
        btnAnnulerCommande.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAnnulerCommande.setForeground(new java.awt.Color(102, 102, 102));
        btnAnnulerCommande.setText("Annuler commande");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("TOTAL:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNouveauCommande)
                .addGap(18, 18, 18)
                .addComponent(btnSupprimerLigne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnValiderCommande)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAnnulerCommande)
                .addContainerGap(96, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(323, 323, 323))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNouveauCommande)
                    .addComponent(btnSupprimerLigne)
                    .addComponent(btnValiderCommande)
                    .addComponent(btnAnnulerCommande))
                .addGap(36, 36, 36))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 90;
        gridBagConstraints.ipady = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 5, 0);
        jPanel3.add(jPanel1, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Produit Disponible"));
        jPanel4.setAutoscrolls(true);
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 431));

        jScrollPane2.setAutoscrolls(true);

        tbnProduitDispo.setAutoCreateRowSorter(true);
        tbnProduitDispo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Produit", "Catégorie", "Prix", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbnProduitDispo);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Produits :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Quantité :");

        btnAjouterLigne.setBackground(new java.awt.Color(51, 255, 51));
        btnAjouterLigne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAjouterLigne.setText("AJOUTER A LA COMMANDE");
        btnAjouterLigne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterLigneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtQuantite)
                    .addComponent(txtProduits, 0, 228, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnAjouterLigne, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(109, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProduits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAjouterLigne)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setLeftComponent(jPanel4);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNouveauCommandeActionPerformed(java.awt.event.ActionEvent evt) {
        nouvelleCommande();
    }

    private void btnSupprimerLigneActionPerformed(java.awt.event.ActionEvent evt) {
        supprimerLigneSelectionnee();
    }

    private void btnValiderCommandeActionPerformed(java.awt.event.ActionEvent evt) {
        validerCommande();
    }

    private void btnAnnulerCommandeActionPerformed(java.awt.event.ActionEvent evt) {
        annulerCommande();
    }

    private void btnAjouterLigneActionPerformed(java.awt.event.ActionEvent evt) {
        ajouterLigneCommande();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjouterLigne;
    private javax.swing.JButton btnAnnulerCommande;
    private javax.swing.JButton btnNouveauCommande;
    private javax.swing.JButton btnSupprimerLigne;
    private javax.swing.JButton btnValiderCommande;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tbnCommandeEnCour;
    private javax.swing.JTable tbnProduitDispo;
    private javax.swing.JComboBox<Business.Produit> txtProduits;
    private javax.swing.JSpinner txtQuantite;
    // End of variables declaration//GEN-END:variables
}
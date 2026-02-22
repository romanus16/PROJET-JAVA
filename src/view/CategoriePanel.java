/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;
import Business.Categorie;
import DAO.CategorieDAO;
import DAO.DBException;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;

public class CategoriePanel extends javax.swing.JPanel {
    private CategorieDAO categorieDAO;
    private DefaultTableModel tableModel;
    private Categorie categorieSelectionnee = null;
    
    public CategoriePanel() {
        initComponents();
        categorieDAO = new CategorieDAO();
        preparerTableau();
        chargerCategories();
    }
    
    private void preparerTableau() {
        // Récupérer le modèle du tableau
        tableModel = (DefaultTableModel) tableCategories.getModel();
        
        // Vider les lignes par défaut
        tableModel.setRowCount(0);
    }
    
    private void chargerCategories() {
        try {
            // Vider le tableau
            tableModel.setRowCount(0);
            
            // Récupérer toutes les catégories
            List<Categorie> categories = categorieDAO.listerToutes();
            
            // Ajouter chaque catégorie au tableau
            for (Categorie c : categories) {
                Object[] ligne = {
                    c.getId(),
                    c.getLibelle()
                };
                tableModel.addRow(ligne);
            }
        } catch (DBException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Erreur lors du chargement : " + e.getMessage(),
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viderFormulaire() {
        txtLibelle.setText("");
        categorieSelectionnee = null;
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);
        btnAjouter.setEnabled(true);
        tableCategories.clearSelection();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtLibelle = new javax.swing.JTextField();
        btnAjouter = new javax.swing.JButton();
        btnModifier = new javax.swing.JButton();
        btnSupprimer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION DES CATEGORIES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 12, 37, 297);
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/category_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(23, 211, 37, 0);
        jPanel1.add(jLabel2, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nouvelle Categorie"));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Libelle :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 241, 0, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtLibelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLibelleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 245;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 18, 0, 0);
        jPanel2.add(txtLibelle, gridBagConstraints);

        btnAjouter.setBackground(new java.awt.Color(51, 255, 51));
        btnAjouter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAjouter.setText("AJOUTER");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 197, 30, 0);
        jPanel2.add(btnAjouter, gridBagConstraints);

        btnModifier.setBackground(new java.awt.Color(255, 255, 0));
        btnModifier.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnModifier.setText("MODIFIER");
        btnModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 116, 30, 0);
        jPanel2.add(btnModifier, gridBagConstraints);

        btnSupprimer.setBackground(new java.awt.Color(255, 0, 0));
        btnSupprimer.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSupprimer.setText("SUPPRIMER");
        btnSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(29, 118, 30, 162);
        jPanel2.add(btnSupprimer, gridBagConstraints);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Liste des Categorie "));

        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Libelle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoriesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableCategories);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtLibelleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLibelleActionPerformed
        // Cette méthode peut rester vide
    }//GEN-LAST:event_txtLibelleActionPerformed

    private void btnModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifierActionPerformed
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Aucune catégorie sélectionnée",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nouveauLibelle = txtLibelle.getText().trim();
        
        if (nouveauLibelle.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez saisir un libellé",
                "Erreur",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
	if (nouveauLibelle.equals(categorieSelectionnee.getLibelle())) {
        	javax.swing.JOptionPane.showMessageDialog(this,
            	"Aucune modification détectée",
            	"Information",
            	javax.swing.JOptionPane.INFORMATION_MESSAGE);
       	return;
    	}
    
        
        try {
            // Vérifier si le nouveau libellé existe déjà (sauf pour la même catégorie)
            if (!nouveauLibelle.equals(categorieSelectionnee.getLibelle()) && 
                categorieDAO.existeLibelle(nouveauLibelle)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Ce libellé existe déjà pour une autre catégorie",
                    "Erreur",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Modifier la catégorie
            categorieSelectionnee.setLibelle(nouveauLibelle);
            boolean modifie = categorieDAO.modifier(categorieSelectionnee);
            
            if (modifie) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Catégorie modifiée avec succès",
                    "Succès",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Vider le formulaire et recharger
                viderFormulaire();
                chargerCategories();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Échec de la modification",
                    "Erreur",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (DBException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Erreur : " + e.getMessage(),
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModifierActionPerformed

    private void btnSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprimerActionPerformed
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Aucune catégorie sélectionnée",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Demander confirmation
        int confirmation = javax.swing.JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment supprimer la catégorie : " + categorieSelectionnee.getLibelle() + " ?",
            "Confirmation",
            javax.swing.JOptionPane.YES_NO_OPTION);
        
        if (confirmation != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            boolean supprime = categorieDAO.supprimer(categorieSelectionnee.getId());
            
            if (supprime) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Catégorie supprimée avec succès",
                    "Succès",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Vider le formulaire et recharger
                viderFormulaire();
                chargerCategories();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Échec de la suppression",
                    "Erreur",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (DBException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Erreur : " + e.getMessage(),
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSupprimerActionPerformed

    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        String libelle = txtLibelle.getText().trim();
        
        // Validation
        if (libelle.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Veuillez saisir un libellé",
                "Erreur",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Vérifier si la catégorie existe déjà
            if (categorieDAO.existeLibelle(libelle)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Cette catégorie existe déjà",
                    "Erreur",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Créer et ajouter la catégorie
            Categorie nouvelleCategorie = new Categorie(libelle);
            boolean ajoute = categorieDAO.ajouter(nouvelleCategorie);
            
            if (ajoute) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Catégorie ajoutée avec succès",
                    "Succès",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Vider le champ
                txtLibelle.setText("");
                
                // Recharger le tableau
                chargerCategories();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Échec de l'ajout",
                    "Erreur",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (DBException e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Erreur : " + e.getMessage(),
                "Erreur",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAjouterActionPerformed

    private void tableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoriesMouseClicked
        int ligneSelectionnee = tableCategories.getSelectedRow();
        if (ligneSelectionnee != -1) {
            int id = (int) tableModel.getValueAt(ligneSelectionnee, 0);
            String libelle = (String) tableModel.getValueAt(ligneSelectionnee, 1);
            
            categorieSelectionnee = new Categorie(id, libelle);
            txtLibelle.setText(libelle);
            btnModifier.setEnabled(true);
            btnSupprimer.setEnabled(true);
            btnAjouter.setEnabled(false);
        }
    }//GEN-LAST:event_tableCategoriesMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnModifier;
    private javax.swing.JButton btnSupprimer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTextField txtLibelle;
    // End of variables declaration//GEN-END:variables
}
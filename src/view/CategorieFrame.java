/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import Business.Categorie;
import DAO.CategorieDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 *
 * @author HP
 */


public class CategorieFrame extends javax.swing.JFrame {

    /**
     * Creates new form CategorieFrame
     */
    public CategorieFrame() {
        initComponents();
        setTitle("Gestion des Catégories");
        setSize(800, 500);
        setLocationRelativeTo(null);
        chargerCategories();
    }
    private void chargerCategories() {
        try {
            CategorieDAO dao = new CategorieDAO();
            List<Categorie> categories = dao.listerToutes();
            
            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Libellé"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            for (Categorie cat : categories) {
                model.addRow(new Object[]{cat.getId(), cat.getLibelle()});
            }
            
            tableCategories.setModel(model);
            viderFormulaire();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnAjouterActionPerformed(ActionEvent evt) {
        String libelle = txtLibelle.getText().trim();
        
        if (libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un libellé",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            CategorieDAO dao = new CategorieDAO();
            
            // Vérifier si le libellé existe déjà
            if (dao.existeLibelle(libelle)) {
                JOptionPane.showMessageDialog(this, "Cette catégorie existe déjà",
                        "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Categorie categorie = new Categorie(libelle);
            if (dao.ajouter(categorie)) {
                JOptionPane.showMessageDialog(this, "Catégorie ajoutée avec succès",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
                chargerCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void btnModifierActionPerformed(ActionEvent evt) {
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Aucune catégorie sélectionnée",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String nouveauLibelle = txtLibelle.getText().trim();
        
        if (nouveauLibelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un libellé",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Vérifier si le libellé n'a pas changé
        if (nouveauLibelle.equals(categorieSelectionnee.getLibelle())) {
            JOptionPane.showMessageDialog(this, "Aucune modification détectée",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirmer la modification de la catégorie ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CategorieDAO dao = new CategorieDAO();
                
                // Vérifier si le nouveau libellé existe déjà (pour une autre catégorie)
                if (dao.existeLibelle(nouveauLibelle)) {
                    JOptionPane.showMessageDialog(this, "Cette catégorie existe déjà",
                            "Validation", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                categorieSelectionnee.setLibelle(nouveauLibelle);
                if (dao.modifier(categorieSelectionnee)) {
                    JOptionPane.showMessageDialog(this, "Catégorie modifiée avec succès",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    chargerCategories();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void btnSupprimerActionPerformed(ActionEvent evt) {
        if (categorieSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Aucune catégorie sélectionnée",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer la catégorie : " + 
                categorieSelectionnee.getLibelle() + " ?\n" +
                "Cette action est irréversible.",
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CategorieDAO dao = new CategorieDAO();
                if (dao.supprimer(categorieSelectionnee.getId())) {
                    JOptionPane.showMessageDialog(this, "Catégorie supprimée avec succès",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    chargerCategories();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Erreur: " + e.getMessage() + "\n" +
                        "La catégorie est probablement utilisée par des produits.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtLibelle = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        categorieSelectionnee = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("📁 GESTION DES CATÉGORIES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nouvelle Carégorie :"));

        jLabel2.setText("Libelle :");

        jButton1.setBackground(new java.awt.Color(0, 204, 51));
        jButton1.setText("➕ Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setText("🗑️ Supprimer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        categorieSelectionnee.setBackground(new java.awt.Color(255, 255, 0));
        categorieSelectionnee.setText("✏️ Modifier");
        categorieSelectionnee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categorieSelectionneeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(162, 162, 162)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(categorieSelectionnee)
                        .addGap(47, 47, 47)
                        .addComponent(jButton2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtLibelle, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtLibelle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(categorieSelectionnee))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Liste des catégorie :"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Libellé"
            }
        ));
        jScrollPane1.setViewportView(tableCategories);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
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
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void categorieSelectionneeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categorieSelectionneeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categorieSelectionneeActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CategorieFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CategorieFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CategorieFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CategorieFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CategorieFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton categorieSelectionnee;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTextField txtLibelle;
    // End of variables declaration//GEN-END:variables
}

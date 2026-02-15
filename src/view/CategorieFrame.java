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
// Variable pour stocker la catégorie choisie dans le tableau
    private Categorie categorieSelectionneeData = null;

    public CategorieFrame() {
        initComponents();
        setTitle("Gestion des Catégories");
        setLocationRelativeTo(null);
        chargerCategories();
    }
    /**
     * Récupère les données de la base (via le DAO) et les affiche dans la JTable.
     */
    private void chargerCategories() {
        try {
            CategorieDAO dao = new CategorieDAO();
            List<Categorie> categories = dao.listerToutes();
            
            // Création du modèle de table (colonnes ID et Libellé)
            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Libellé"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Empêche l'édition directe dans les cellules du tableau
                }
            };
            
            // Ajout des lignes au modèle
            for (Categorie cat : categories) {
                model.addRow(new Object[]{cat.getId(), cat.getLibelle()});
            }
            
            tableCategories.setModel(model); // Mise à jour de l'view
            viderFormulaire(); // Réinitialise le champ texte et la sélection
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement : " + e.getMessage());
        }
    }
    /**
     * Nettoie le champ de saisie et réinitialise la mémoire de sélection.
     */
    private void viderFormulaire() {
        txtLibelle.setText("");
        categorieSelectionneeData = null;
    }

    // --- LOGIQUE MÉTIER APPELÉE PAR LES BOUTONS ---

    private void executerAjout() {
        String libelle = txtLibelle.getText().trim();
        if (libelle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir un libellé");
            return;
        }
        try {
            CategorieDAO dao = new CategorieDAO();
            // Création d'un nouvel objet Categorie sans ID (auto-incrémenté en base)
            if (dao.ajouter(new Categorie(libelle))) {
                JOptionPane.showMessageDialog(this, "Catégorie ajoutée !");
                chargerCategories(); // Rafraîchit la liste
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void executerModification() {
        // On vérifie qu'une ligne a bien été cliquée au préalable
        if (categorieSelectionneeData == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez d'abord une catégorie dans le tableau");
            return;
        }
        
        String nouveauLibelle = txtLibelle.getText().trim();
        try {
            CategorieDAO dao = new CategorieDAO();
            // On met à jour l'objet en mémoire avec le nouveau texte
            categorieSelectionneeData.setLibelle(nouveauLibelle);
            
            if (dao.modifier(categorieSelectionneeData)) {
                JOptionPane.showMessageDialog(this, "Modification effectuée !");
                chargerCategories();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur modification : " + e.getMessage());
        }
    }

    private void executerSuppression() {
        if (categorieSelectionneeData == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une catégorie à supprimer");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Supprimer la catégorie '" 
                + categorieSelectionneeData.getLibelle() + "' ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                CategorieDAO dao = new CategorieDAO();
                if (dao.supprimer(categorieSelectionneeData.getId())) {
                    chargerCategories();
                }
            } catch (Exception e) {
                // Erreur souvent due à une contrainte de clé étrangère (catégorie utilisée par un produit)
                JOptionPane.showMessageDialog(this, "Erreur : La catégorie est utilisée par des produits.");
            }
        }
    }// --- GESTIONNAIRES D'ÉVÉNEMENTS (LIÉS À NETBEANS) ---

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
       
        executerAjout();
    }                                        

    private void categorieSelectionneeActionPerformed(java.awt.event.ActionEvent evt) {                                                      
         executerModification();
    }                                                     

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
         executerSuppression();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtLibelle = new javax.swing.JTextField();
        btnAjouter = new javax.swing.JButton();
        btnSupprimer = new javax.swing.JButton();
        btnModifier = new javax.swing.JButton();
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

        btnAjouter.setBackground(new java.awt.Color(0, 204, 51));
        btnAjouter.setText("➕ Ajouter");
        btnAjouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAjouterActionPerformed(evt);
            }
        });

        btnSupprimer.setBackground(new java.awt.Color(255, 0, 0));
        btnSupprimer.setText("🗑️ Supprimer");
        btnSupprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupprimerActionPerformed(evt);
            }
        });

        btnModifier.setBackground(new java.awt.Color(255, 255, 0));
        btnModifier.setText("✏️ Modifier");
        btnModifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifierActionPerformed(evt);
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
                    .addComponent(btnAjouter))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnModifier)
                        .addGap(47, 47, 47)
                        .addComponent(btnSupprimer))
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
                    .addComponent(btnAjouter)
                    .addComponent(btnSupprimer)
                    .addComponent(btnModifier))
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
        tableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoriesMouseClicked(evt);
            }
        });
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

    private void btnSupprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupprimerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSupprimerActionPerformed

    private void btnAjouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAjouterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAjouterActionPerformed

    private void btnModifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModifierActionPerformed

    private void tableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoriesMouseClicked
        /**
     * Se déclenche quand on clique sur une ligne du tableau.
     * Permet de remplir le champ 'Libellé' automatiquement pour modification.
     */
        int ligne = tableCategories.getSelectedRow();
        if (ligne != -1) {
            // Récupération des valeurs de la ligne sélectionnée
            int id = (int) tableCategories.getValueAt(ligne, 0);
            String libelle = tableCategories.getValueAt(ligne, 1).toString();
            
            // On crée l'objet complet pour que le DAO sache quel ID modifier plus tard
            categorieSelectionneeData = new Categorie(id, libelle);
            
            // On affiche le nom dans le champ texte
            txtLibelle.setText(libelle);
    
        // TODO add your handling code here:
    }//GEN-LAST:event_tableCategoriesMouseClicked
    }
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
    private javax.swing.JButton btnAjouter;
    private javax.swing.JButton btnModifier;
    private javax.swing.JButton btnSupprimer;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Business.Categorie;
import Business.Produit;
import DAO.CategorieDAO;
import DAO.ProduitDAO;
import DAO.DBException;
import DAO.LigneCommandeDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class ProduitPanel extends javax.swing.JPanel {
    private ProduitDAO produitDAO;
    private CategorieDAO categorieDAO;
    private LigneCommandeDAO ligneCommandeDAO;
    private DefaultTableModel tableModel;
    private Produit produitSelectionne = null;
    private TableRowSorter<TableModel> sorter;
    
    /**
     * Creates new form ProduitPanel
     */
    public ProduitPanel() {
        initComponents();
        produitDAO = new ProduitDAO();
        categorieDAO = new CategorieDAO();
        ligneCommandeDAO = new LigneCommandeDAO();
        preparerTableau();
        chargerCategories();
        chargerProduits();
        ajouterRecherche();
        
        // Désactiver Modifier/Supprimer au départ
        modifier.setEnabled(false);
        supprimer.setEnabled(false);
    }
    
    private void preparerTableau() {
        tableModel = (DefaultTableModel) jTable1.getModel();
        tableModel.setRowCount(0);
        
        // Ajouter un renderer pour la colonne Alerte
        jTable1.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    boolean enAlerte = "⚠️ OUI".equals(value);
                    if (enAlerte) {
                        c.setBackground(new java.awt.Color(255, 200, 200)); // Rouge clair
                    } else {
                        c.setBackground(table.getBackground());
                    }
                }
                return c;
            }
        });
    }
    
    private void chargerCategories() {
        try {
            List<Categorie> categories = categorieDAO.listerToutes();
            txtCategorie.removeAllItems();
            for (Categorie cat : categories) {
                txtCategorie.addItem(cat);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement catégories: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void chargerProduits() {
        try {
            tableModel.setRowCount(0);
            List<Produit> produits = produitDAO.listerTous();
            
            for (Produit p : produits) {
                Object[] ligne = {
                    p.getId(),
                    p.getNom(),
                    p.getCategorie().getLibelle(),
                    p.getPrixVente(),
                    p.getStockActuel(),
                    p.getSeuilAlerte(),
                    p.isStockEnAlerte() ? "⚠️ OUI" : "Non"
                };
                tableModel.addRow(ligne);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                "Erreur chargement produits: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ajouterRecherche() {
        sorter = new TableRowSorter<>(tableModel);
        jTable1.setRowSorter(sorter);
    }
    
    private void viderFormulaire() {
        txtNom.setText("");
        txtPrixVente.setText("");
        txtStockActuel.setText("");
        txtSeuilAlerte.setText("");
        if (txtCategorie.getItemCount() > 0) {
            txtCategorie.setSelectedIndex(0);
        }
        produitSelectionne = null;
        supprimer.setEnabled(false);
        modifier.setEnabled(false);
        ajouter.setEnabled(true);
        jTable1.clearSelection();
    }
    
    private boolean validerSaisie() {
        if (txtNom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir le nom du produit");
            return false;
        }
        
        if (txtCategorie.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une catégorie");
            return false;
        }
        
        try {
            double prix = Double.parseDouble(txtPrixVente.getText().trim());
            if (prix <= 0) {
                JOptionPane.showMessageDialog(this, "Le prix doit être > 0");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Prix de vente invalide");
            return false;
        }
        
        try {
            int stock = Integer.parseInt(txtStockActuel.getText().trim());
            if (stock < 0) {
                JOptionPane.showMessageDialog(this, "Le stock ne peut pas être négatif");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stock invalide");
            return false;
        }
        
        try {
            int seuil = Integer.parseInt(txtSeuilAlerte.getText().trim());
            if (seuil < 0) {
                JOptionPane.showMessageDialog(this, "Le seuil d'alerte ne peut pas être négatif");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seuil d'alerte invalide");
            return false;
        }
        
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        txtPrixVente = new javax.swing.JTextField();
        txtSeuilAlerte = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCategorie = new javax.swing.JComboBox<Business.Categorie>();
        txtStockActuel = new javax.swing.JTextField();
        ajouter = new javax.swing.JButton();
        modifier = new javax.swing.JButton();
        supprimer = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAlerte = new javax.swing.JButton();
        btnRafraichir = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Script MT Bold", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTION DES PRODUITS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 6, 48, 347);
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trending_down_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(36, 353, 48, 0);
        jPanel2.add(jLabel5, gridBagConstraints);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Nouveau Produit"));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Nom :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(27, 48, 0, 0);
        jPanel4.add(jLabel2, gridBagConstraints);

        jLabel3.setText("Prix de Vente :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 48, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel4.setText("Seuil Alerte");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 48, 0, 0);
        jPanel4.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 237;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 18, 0, 0);
        jPanel4.add(txtNom, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 237;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 18, 0, 0);
        jPanel4.add(txtPrixVente, gridBagConstraints);

        txtSeuilAlerte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeuilAlerteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 237;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 18, 0, 0);
        jPanel4.add(txtSeuilAlerte, gridBagConstraints);

        jLabel6.setText("Catégorie :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(35, 3, 0, 0);
        jPanel4.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Stock Actuel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 0);
        jPanel4.add(jLabel7, gridBagConstraints);

        txtCategorie = new javax.swing.JComboBox<Business.Categorie>();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 186;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(32, 32, 0, 46);
        jPanel4.add(txtCategorie, gridBagConstraints);

        txtStockActuel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockActuelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 194;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 32, 0, 46);
        jPanel4.add(txtStockActuel, gridBagConstraints);

        ajouter.setText("AJOUTER");
        ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajouterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 114, 23, 0);
        jPanel4.add(ajouter, gridBagConstraints);

        modifier.setText("MODIFIER");
        modifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 93, 23, 0);
        jPanel4.add(modifier, gridBagConstraints);

        supprimer.setText("SUPPRIMER");
        supprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 32, 23, 0);
        jPanel4.add(supprimer, gridBagConstraints);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Liste des Produits")));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom", "Catégorie", "Prix", "Stock ", "Seuil", "Alerte"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        btnAlerte.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAlerte.setForeground(new java.awt.Color(255, 102, 102));
        btnAlerte.setText("Voir Alerte");
        btnAlerte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlerteActionPerformed(evt);
            }
        });

        btnRafraichir.setText("Rafraichir");
        btnRafraichir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRafraichirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRafraichir, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnAlerte, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRafraichir)
                    .addComponent(btnAlerte))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>                        

    private void txtStockActuelActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Cette méthode peut rester vide
    }                                              

    private void ajouterActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (!validerSaisie()) return;
        
        try {
            // Vérifier si un produit avec le même nom existe déjà
            // (optionnel - vous pouvez ajouter cette vérification si vous voulez)
            
            Produit p = new Produit();
            p.setNom(txtNom.getText().trim());
            p.setCategorie((Categorie) txtCategorie.getSelectedItem());
            p.setPrixVente(Double.parseDouble(txtPrixVente.getText().trim()));
            p.setStockActuel(Integer.parseInt(txtStockActuel.getText().trim()));
            p.setSeuilAlerte(Integer.parseInt(txtSeuilAlerte.getText().trim()));
            
            if (produitDAO.ajouter(p)) {
                JOptionPane.showMessageDialog(this, "✅ Produit ajouté avec succès");
                viderFormulaire();
                chargerProduits();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Échec de l'ajout", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }                                       

    private void txtSeuilAlerteActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // Cette méthode est liée au champ seuil alerte
    }                                              

    private void btnRafraichirActionPerformed(java.awt.event.ActionEvent evt) {                                              
        chargerCategories();
        chargerProduits();
        viderFormulaire();
    }                                             

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            try {
                produitSelectionne = produitDAO.trouverParId(id);
                if (produitSelectionne != null) {
                    txtNom.setText(produitSelectionne.getNom());
                    txtPrixVente.setText(String.valueOf(produitSelectionne.getPrixVente()));
                    txtStockActuel.setText(String.valueOf(produitSelectionne.getStockActuel()));
                    txtSeuilAlerte.setText(String.valueOf(produitSelectionne.getSeuilAlerte()));
                    txtCategorie.setSelectedItem(produitSelectionne.getCategorie());
                    
                    supprimer.setEnabled(true);
                    modifier.setEnabled(true);
                    ajouter.setEnabled(false);
                }
            } catch (DBException e) {
                JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                    

    private void supprimerActionPerformed(java.awt.event.ActionEvent evt) {                                          
        if (produitSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un produit",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer le produit : " + produitSelectionne.getNom() + " ?\n" +
            "Cette action est irréversible.",
            "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (produitDAO.supprimer(produitSelectionne.getId())) {
                    JOptionPane.showMessageDialog(this, "✅ Produit supprimé avec succès");
                    viderFormulaire();
                    chargerProduits();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Échec de la suppression",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DBException e) {
                JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                         

    private void modifierActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (produitSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un produit",
                "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validerSaisie()) return;
        
        try {
            produitSelectionne.setNom(txtNom.getText().trim());
            produitSelectionne.setCategorie((Categorie) txtCategorie.getSelectedItem());
            produitSelectionne.setPrixVente(Double.parseDouble(txtPrixVente.getText().trim()));
            produitSelectionne.setStockActuel(Integer.parseInt(txtStockActuel.getText().trim()));
            produitSelectionne.setSeuilAlerte(Integer.parseInt(txtSeuilAlerte.getText().trim()));
            
            if (produitDAO.modifier(produitSelectionne)) {
                JOptionPane.showMessageDialog(this, "✅ Produit modifié avec succès");
                viderFormulaire();
                chargerProduits();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Échec de la modification",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }                                         

    private void btnAlerteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            List<Produit> alertes = produitDAO.produitsEnAlerte();
            if (alertes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "✅ Aucun produit en alerte",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("⚠️ PRODUITS EN ALERTE ⚠️\n\n");
                for (Produit p : alertes) {
                    sb.append("• ").append(p.getNom())
                      .append("\n  Stock: ").append(p.getStockActuel())
                      .append(" (Seuil: ").append(p.getSeuilAlerte()).append(")\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString(),
                    "Alertes Stock", JOptionPane.WARNING_MESSAGE);
            }
        } catch (DBException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton ajouter;
    private javax.swing.JButton btnAlerte;
    private javax.swing.JButton btnRafraichir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton modifier;
    private javax.swing.JButton supprimer;
    private javax.swing.JComboBox<Business.Categorie> txtCategorie;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtPrixVente;
    private javax.swing.JTextField txtSeuilAlerte;
    private javax.swing.JTextField txtStockActuel;
    // End of variables declaration                   
}
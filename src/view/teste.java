/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class teste extends javax.swing.JFrame { 
 
    // pour declarez les panal
    private CategoriePanel categoriePanel;
    private ProduitPanel produitPanel;
    private MouvementStockPanel mouvementPanel;
    private CommandePanel commandePanel;
    private StatistiquePanel statistiquePanel;
    private DashboardPanel dashboardPanel;
    

    @SuppressWarnings("unchecked")
    public teste() {
        initComponents();
        //pour redimensionniser l'ecran
        initialiserNavigation();
       
        //Initialisez les panels
        categoriePanel = new CategoriePanel();
        produitPanel = new ProduitPanel();
        mouvementPanel = new MouvementStockPanel();
        commandePanel = new CommandePanel();
        statistiquePanel = new StatistiquePanel();
        dashboardPanel = new DashboardPanel();
       // 2. Changez le layout de jPanel1
    jPanel1.setLayout(new BorderLayout());
    
    // 3. Retirez les composants de leurs positions actuelles
    jPanel1.remove(jPanel2);
    jPanel1.remove(jPanel3);
    jPanel1.remove(jLabel1); 
    
    //4. Ajoutez la photo en premier
    jLabel1.setHorizontalAlignment(JLabel.CENTER);
    jLabel1.setVerticalAlignment(JLabel.CENTER);
    jPanel1.add(jLabel1, BorderLayout.CENTER);
    
    //5. Ajoutez le menu
    jPanel2.setPreferredSize(new Dimension(230, 0));
    jPanel1.add(jPanel2, BorderLayout.WEST);
    
    //6. Ajoutez le contenu
    jPanel3.setLayout(new BorderLayout());
    jPanel3.setOpaque(false); // Transparent pour voir la photo
    jPanel1.add(jPanel3, BorderLayout.CENTER);
    
    //7. Ordre des calques
    jPanel1.setComponentZOrder(jLabel1, 2);  // Photo derrière
    jPanel1.setComponentZOrder(jPanel2, 1);  // Menu au milieu
    jPanel1.setComponentZOrder(jPanel3, 0);  // Contenu devant
    
    //8. Affichez Dashboard
    jPanel3.removeAll();
    jPanel3.add(dashboardPanel, BorderLayout.CENTER);
    jPanel3.revalidate();
    
    // 9. Navigation
    initialiserNavigation();
        
    }
    private void initialiserNavigation() {
        
        // Pour CATEGORIE
        btnProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(produitPanel);
                highlightSelected(btnProduct);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProduct.setBackground(new Color(94, 63, 119));
                btnProduct.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnProduct.getBackground() != new Color(124, 93, 149)) {
                    btnProduct.setBackground(new Color(54, 33, 89));
                    btnProduct.setOpaque(true);
                }
            }
        });
        // Pour produit
        btnCategorie.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(categoriePanel);
                highlightSelected(btnCategorie);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCategorie.setBackground(new Color(94, 63, 119));
                btnCategorie.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnCategorie.getBackground() != new Color(124, 93, 149)) {
                    btnCategorie.setBackground(new Color(54, 33, 89));
                    btnCategorie.setOpaque(true);
                }
            }
        });
        
        // Pour commande
        btnCommande.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(commandePanel);
                highlightSelected(btnCommande);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCommande.setBackground(new Color(94, 63, 119));
                btnCommande.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnCommande.getBackground() != new Color(124, 93, 149)) {
                    btnCommande.setBackground(new Color(54, 33, 89));
                    btnCommande.setOpaque(true);
                }
            }
        });
        
        // Pour MOUVEMENT STOCK
        btnMovementStock.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(mouvementPanel);
                highlightSelected(btnMovementStock);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMovementStock.setBackground(new Color(94, 63, 119));
                btnMovementStock.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnMovementStock.getBackground() != new Color(124, 93, 149)) {
                    btnMovementStock.setBackground(new Color(54, 33, 89));
                    btnMovementStock.setOpaque(true);
                }
            }
        });
        
        // Pour Statistique
        btnStatistique.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(statistiquePanel);
                highlightSelected(btnStatistique);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnStatistique.setBackground(new Color(94, 63, 119));
                btnStatistique.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnStatistique.getBackground() != new Color(124, 93, 149)) {
                    btnStatistique.setBackground(new Color(54, 33, 89));
                    btnStatistique.setOpaque(true);
                }
            }
        });
       
        
        // Pour DASHBOARD
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changerPanel(dashboardPanel);
                highlightSelected(btnDashboard);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboard.setBackground(new Color(94, 63, 119));
                btnDashboard.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btnDashboard.getBackground() != new Color(124, 93, 149)) {
                    btnDashboard.setBackground(new Color(54, 33, 89));
                    btnDashboard.setOpaque(true);
                }
            }
        });
        
        // Pour DECONNEXION
        btnDeConnection.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int reponse = JOptionPane.showConfirmDialog(null,
                    "Voulez-vous vraiment vous déconnecter ?",
                    "Déconnexion",
                    JOptionPane.YES_NO_OPTION);
                if (reponse == JOptionPane.YES_OPTION) {
                    dispose(); // Ferme la fenêtre
                }
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDeConnection.setBackground(new Color(231, 76, 60)); // Rouge
                btnDeConnection.setOpaque(true);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDeConnection.setBackground(new Color(54, 33, 89));
                btnDeConnection.setOpaque(true);
            }
        });
    }
    
    void changerPanel(JPanel nouveauPanel) {
        jPanel3.removeAll();
        jPanel3.add(nouveauPanel, BorderLayout.CENTER);
        jPanel3.revalidate();
        jPanel3.repaint();
    }
void highlightSelected(JLabel selected) {
        // Liste de tous vos boutons
        JLabel[] labels = {btnDashboard, btnCategorie, btnProduct, 
                           btnMovementStock, btnStatistique, btnDeConnection};
        
        // Remettre tous en couleur normale
        for (JLabel label : labels) {
            if (label != null) {
                label.setBackground(new Color(54, 33, 89));
                label.setOpaque(true);
            }
        }
        
        // Mettre le sélectionné en surbrillance
        if (selected != null) {
            selected.setBackground(new Color(124, 93, 149));
            selected.setOpaque(true);
        }
    }

    @SuppressWarnings("unchecked")
    

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                teste frame = new teste();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnDeConnection = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnCategorie = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        btnMovementStock = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtProduit = new javax.swing.JPanel();
        btnProduct = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        btnStatistique = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnDashboard = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        btnCommande = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(54, 33, 69));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(54, 33, 89));

        btnDeConnection.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnDeConnection.setForeground(new java.awt.Color(255, 255, 255));
        btnDeConnection.setText("deconnection");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/settings_power_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeConnection, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnDeConnection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 230, -1));

        jPanel13.setBackground(new java.awt.Color(54, 33, 89));
        jPanel13.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 230, -1));

        jPanel11.setBackground(new java.awt.Color(54, 33, 89));

        btnCategorie.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnCategorie.setForeground(new java.awt.Color(255, 255, 255));
        btnCategorie.setText("CATEGORIE");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/category_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCategorie, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnCategorie, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 230, -1));

        jPanel14.setBackground(new java.awt.Color(54, 33, 89));

        btnMovementStock.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnMovementStock.setForeground(new java.awt.Color(255, 255, 255));
        btnMovementStock.setText("MOUVEMENT STOCK");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/swap_vert_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMovementStock)
                .addGap(17, 17, 17))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnMovementStock, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 230, -1));

        txtProduit.setBackground(new java.awt.Color(54, 33, 89));

        btnProduct.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnProduct.setForeground(new java.awt.Color(255, 255, 255));
        btnProduct.setText("PRODUIT");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bookmark_stacks_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout txtProduitLayout = new javax.swing.GroupLayout(txtProduit);
        txtProduit.setLayout(txtProduitLayout);
        txtProduitLayout.setHorizontalGroup(
            txtProduitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtProduitLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        txtProduitLayout.setVerticalGroup(
            txtProduitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtProduitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(txtProduitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtProduitLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        jPanel2.add(txtProduit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 230, -1));

        jPanel16.setBackground(new java.awt.Color(54, 33, 89));

        btnStatistique.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnStatistique.setForeground(new java.awt.Color(255, 255, 255));
        btnStatistique.setText("STATISTIQUE");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/finance_mode_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStatistique, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnStatistique, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 230, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 190, 10));

        btnDashboard.setFont(new java.awt.Font("Showcard Gothic", 2, 24)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setText("DASHBORD");
        jPanel2.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 170, 40));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 30, -1));

        jPanel17.setBackground(new java.awt.Color(54, 33, 89));

        btnCommande.setFont(new java.awt.Font("Stencil", 1, 18)); // NOI18N
        btnCommande.setForeground(new java.awt.Color(255, 255, 255));
        btnCommande.setText("COMMANDE");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dining_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCommande, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(btnCommande, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
        );

        jPanel2.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 230, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 590));

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 720, 500));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Gemini_Generated_Image_hqx7ulhqx7ulhqx7.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 720, 290));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnCategorie;
    private javax.swing.JLabel btnCommande;
    private javax.swing.JLabel btnDashboard;
    private javax.swing.JLabel btnDeConnection;
    private javax.swing.JLabel btnMovementStock;
    private javax.swing.JLabel btnProduct;
    private javax.swing.JLabel btnStatistique;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel txtProduit;
    // End of variables declaration//GEN-END:variables

   
}
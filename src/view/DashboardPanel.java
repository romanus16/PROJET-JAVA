package view;

import Business.Produit;
import Business.Commande;
import DAO.CommandeDAO;
import DAO.ProduitDAO;
import DAO.DBException;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DashboardPanel extends JPanel {

    private CommandeDAO commandeDAO;
    private ProduitDAO produitDAO;

    // Labels statistiques
    private JLabel lblDate;
    private JLabel lblCAJour;
    private JLabel lblCommandesJour;
    private JLabel lblAlertes;
    private JLabel lblTotalStock;
    private JLabel lblTopProduit;

    // Boutons
    private JButton btnNouvelleCommande;
    private JButton btnNouveauProduit;
    private JButton btnMouvementStock;

    public DashboardPanel() {

        setLayout(new BorderLayout()); 
        initComponents();

        commandeDAO = new CommandeDAO();
        produitDAO = new ProduitDAO();

        chargerInfos();

        // Rafraîchissement automatique toutes les 30 secondes
        new Timer(30000, e -> chargerInfos()).start();
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.setBackground(Color.WHITE);

        // HEADER 
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titre = new JLabel("TABLEAU DE BORD");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));

        lblDate = new JLabel();
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        header.add(titre, BorderLayout.WEST);
        header.add(lblDate, BorderLayout.EAST);

        //  CENTER 
        JPanel center = new JPanel(new GridLayout(2,3,20,20));
        center.setOpaque(false);

        lblCAJour = createCard(center, "Chiffre d'affaires du jour");
        lblCommandesJour = createCard(center, "Commandes aujourd'hui");
        lblAlertes = createCard(center, "Produits en alerte");
        lblTotalStock = createCard(center, "Stock total");
        lblTopProduit = createCard(center, "Top produit");

        //  FOOTER 
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        footer.setOpaque(false);

        btnNouvelleCommande = new JButton("Nouvelle Commande");
        btnNouveauProduit = new JButton("Nouveau Produit");
        btnMouvementStock = new JButton("Mouvement Stock");

        footer.add(btnNouvelleCommande);
        footer.add(btnNouveauProduit);
        footer.add(btnMouvementStock);

        //  ACTIONS 
        btnNouvelleCommande.addActionListener(e -> btnNouvelleCommandeActionPerformed());
        btnNouveauProduit.addActionListener(e -> btnNouveauProduitActionPerformed());
        btnMouvementStock.addActionListener(e -> btnMouvementStockActionPerformed());

        //  ASSEMBLAGE 
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JLabel createCard(JPanel parent, String titre) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,1));
        card.setBackground(Color.WHITE);

        JLabel lblTitre = new JLabel(titre);
        lblTitre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitre.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));

        JLabel lblValeur = new JLabel("0", SwingConstants.CENTER);
        lblValeur.setFont(new Font("Segoe UI", Font.BOLD, 22));

        card.add(lblTitre, BorderLayout.NORTH);
        card.add(lblValeur, BorderLayout.CENTER);

        parent.add(card);

        return lblValeur;
    }

    //  CHARGEMENT DES DONNEES 
    private void chargerInfos() {

        try {
            // Date du jour
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMMM yyyy");
            lblDate.setText(sdf.format(new Date()).toUpperCase());

            // CA du jour
            double caJour = commandeDAO.chiffreAffairesDuJour();
            lblCAJour.setText(String.format("%,.0f FCFA", caJour));

            // Commandes du jour
            List<Commande> commandes = commandeDAO.listerCommandes();
            SimpleDateFormat sdfJour = new SimpleDateFormat("yyyyMMdd");
            String today = sdfJour.format(new Date());

            long commandesJour = commandes.stream()
                    .filter(c -> sdfJour.format(c.getDateCommande()).equals(today))
                    .count();

            lblCommandesJour.setText(String.valueOf(commandesJour));

            // Produits en alerte
            List<Produit> alertes = produitDAO.produitsEnAlerte();
            lblAlertes.setText(String.valueOf(alertes.size()));

            // Total stock
            List<Produit> tousProduits = produitDAO.listerTous();
            int totalStock = tousProduits.stream()
                    .mapToInt(Produit::getStockActuel)
                    .sum();

            lblTotalStock.setText(String.valueOf(totalStock));

            // Top produit (exemple simple)
            if (!tousProduits.isEmpty()) {
                Produit top = tousProduits.get(0);
                lblTopProduit.setText(top.getNom());
            } else {
                lblTopProduit.setText("Aucun");
            }

        } catch (DBException e) {
            JOptionPane.showMessageDialog(this,
                    "Erreur chargement dashboard : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //  NAVIGATION 
    private void btnNouvelleCommandeActionPerformed() {
        ouvrirPanel(new CommandePanel(), "COMMANDE");
    }

    private void btnNouveauProduitActionPerformed() {
        ouvrirPanel(new ProduitPanel(), "PRODUIT");
    }

    private void btnMouvementStockActionPerformed() {
        ouvrirPanel(new MouvementStockPanel(), "MOUVEMENT STOCK");
    }

    private void ouvrirPanel(JPanel panel, String menu) {

        Container parent = getParent();

        while (parent != null && !(parent instanceof teste)) {
            parent = parent.getParent();
        }

        if (parent != null) {
            teste frame = (teste) parent;
            frame.changerPanel(panel);

            // Highlight menu
            for (Component comp : frame.getContentPane().getComponents()) {
                if (comp instanceof JPanel) {
                    for (Component c : ((JPanel) comp).getComponents()) {
                        if (c instanceof JLabel &&
                                ((JLabel) c).getText().equals(menu)) {

                            frame.highlightSelected((JLabel) c);
                            return;
                        }
                    }
                }
            }
        }
    }
}
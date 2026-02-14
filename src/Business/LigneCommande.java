/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

/**
 *
 * @author HP
 */

public class LigneCommande {
    private int id;
    private Produit produit;
    private int quantite;
    private double prixUnitaire;
    private double montantLigne;
    
    public LigneCommande() {}
    
    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = produit.getPrixVente();
        calculerMontant();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { 
        this.produit = produit;
        calculerMontant();
    }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { 
        this.quantite = quantite;
        calculerMontant();
    }
    
    public double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(double prixUnitaire) { 
        this.prixUnitaire = prixUnitaire;
        calculerMontant();
    }
    
    public double getMontantLigne() { return montantLigne; }
    
    private void calculerMontant() {
        this.montantLigne = quantite * prixUnitaire;
    }
    
    @Override
    public String toString() {
        return produit.getNom() + " x" + quantite + " = " + montantLigne + " FCFA";
    }
}


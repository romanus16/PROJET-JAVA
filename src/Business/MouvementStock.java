/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import java.util.Date;


/**
 *
 * @author HP
 */

public class MouvementStock {
    public enum TypeMouvement { ENTREE, SORTIE }
    
    private int id;
    private Produit produit;
    private TypeMouvement type;
    private int quantite;
    private Date dateMouvement;
    private String motif;
    
    public MouvementStock() {}
    
    public MouvementStock(Produit produit, TypeMouvement type, int quantite, String motif) {
        this.produit = produit;
        this.type = type;
        this.quantite = quantite;
        this.dateMouvement = new Date();
        this.motif = motif;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    
    public TypeMouvement getType() { return type; }
    public void setType(TypeMouvement type) { this.type = type; }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    
    public Date getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(Date dateMouvement) { this.dateMouvement = dateMouvement; }
    
    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }
    
    @Override
    public String toString() {
        return type + " - " + produit.getNom() + " (" + quantite + ")";
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP
 */

public class Commande {
    public enum EtatCommande { EN_COURS, VALIDEE, ANNULEE }
    
    private int id;
    private Date dateCommande;
    private EtatCommande etat;
    private double total;
    
    public Commande() {
        this.dateCommande = new Date();
        this.etat = EtatCommande.EN_COURS;
        this.total = 0.0;
    }
    
    public Commande(int id, Date dateCommande, EtatCommande etat, double total) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.etat = etat;
        this.total = total;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Date getDateCommande() { return dateCommande; }
    public void setDateCommande(Date dateCommande) { this.dateCommande = dateCommande; }
    
    public EtatCommande getEtat() { return etat; }
    public void setEtat(EtatCommande etat) { this.etat = etat; }
    
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    
    @Override
    public String toString() {
        return "Commande #" + id + " - " + dateCommande + " - " + total + " FCFA";
    }
}


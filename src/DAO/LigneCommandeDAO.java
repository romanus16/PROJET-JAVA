/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */

import Business.LigneCommande;
import Business.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LigneCommandeDAO {
    
    public boolean ajouterLigne(int idCommande, LigneCommande ligne) throws SQLException, DBException {
        String sql = "INSERT INTO LigneCommande (id_commande, id_produit, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCommande);
            pstmt.setInt(2, ligne.getProduit().getId());
            pstmt.setInt(3, ligne.getQuantite());
            pstmt.setDouble(4, ligne.getPrixUnitaire());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur ajout ligne: " + e.getMessage());
            return false;
        }
    }
    
    public List<LigneCommande> lignesParCommande(int idCommande) throws DBException {
        List<LigneCommande> lignes = new ArrayList<>();
        String sql = "SELECT l.*, p.nom as produit_nom, p.prix_vente FROM LigneCommande l " +
                     "JOIN Produit p ON l.id_produit = p.id " +
                     "WHERE l.id_commande = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCommande);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id_produit"));
                produit.setNom(rs.getString("produit_nom"));
                produit.setPrixVente(rs.getDouble("prix_vente"));
                
                LigneCommande ligne = new LigneCommande();
                ligne.setId(rs.getInt("id"));
                ligne.setProduit(produit);
                ligne.setQuantite(rs.getInt("quantite"));
                ligne.setPrixUnitaire(rs.getDouble("prix_unitaire"));
                
                lignes.add(ligne);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return lignes;
    }
    
    public boolean supprimerLignesCommande(int idCommande) throws DBException {
        String sql = "DELETE FROM LigneCommande WHERE id_commande = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCommande);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur suppression lignes: " + e.getMessage());
            return false;
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */

import Business.MouvementStock;
import Business.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MouvementStockDAO {
    
    public boolean enregistrerMouvement(MouvementStock mouvement) throws DBException {
        String sql = "INSERT INTO MouvementStock (id_produit, type, quantite, date_mouvement, motif) VALUES (?, ?, ?, NOW(), ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, mouvement.getProduit().getId());
            pstmt.setString(2, mouvement.getType().toString());
            pstmt.setInt(3, mouvement.getQuantite());
            pstmt.setString(4, mouvement.getMotif());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur d'enregistrement: " + e.getMessage());
            return false;
        }
    }
    
    public List<MouvementStock> listerMouvements() throws DBException {
        List<MouvementStock> mouvements = new ArrayList<>();
        String sql = "SELECT m.*, p.nom as produit_nom FROM MouvementStock m " +
                     "JOIN Produit p ON m.id_produit = p.id " +
                     "ORDER BY m.date_mouvement DESC";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getInt("id_produit"));
                produit.setNom(rs.getString("produit_nom"));
                
                MouvementStock mouvement = new MouvementStock();
                mouvement.setId(rs.getInt("id"));
                mouvement.setProduit(produit);
                mouvement.setType(MouvementStock.TypeMouvement.valueOf(rs.getString("type")));
                mouvement.setQuantite(rs.getInt("quantite"));
                mouvement.setDateMouvement(rs.getTimestamp("date_mouvement"));
                mouvement.setMotif(rs.getString("motif"));
                
                mouvements.add(mouvement);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return mouvements;
    }
}
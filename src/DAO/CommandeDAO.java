/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */


import Business.Commande;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CommandeDAO {
    
    public int creerCommande(Commande commande) throws DBException {
        String sql = "INSERT INTO Commande (date_commande, etat, total) VALUES (NOW(), ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, commande.getEtat().toString());
            pstmt.setDouble(2, commande.getTotal());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur création commande: " + e.getMessage());
        }
        return -1;
    }
    
    public List<Commande> listerCommandes() throws DBException {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM Commande ORDER BY date_commande DESC";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId(rs.getInt("id"));
                commande.setDateCommande(rs.getTimestamp("date_commande"));
                commande.setEtat(Commande.EtatCommande.valueOf(rs.getString("etat")));
                commande.setTotal(rs.getDouble("total"));
                
                commandes.add(commande);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return commandes;
    }
    
    public boolean validerCommande(int idCommande) throws DBException {
        String sql = "UPDATE Commande SET etat = 'VALIDEE' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCommande);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur validation: " + e.getMessage());
            return false;
        }
    }
    
    public boolean annulerCommande(int idCommande) throws DBException {
        String sql = "UPDATE Commande SET etat = 'ANNULEE' WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCommande);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur annulation: " + e.getMessage());
            return false;
        }
    }
    
    public double chiffreAffairesDuJour() throws DBException {
        String sql = "SELECT COALESCE(SUM(total), 0) as ca FROM Commande " +
                     "WHERE DATE(date_commande) = CURDATE() AND etat = 'VALIDEE'";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("ca");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur CA: " + e.getMessage());
        }
        return 0.0;
    }
}
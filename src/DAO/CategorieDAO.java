/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Business.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author HP
 */

public class CategorieDAO {
    
    public boolean ajouter(Categorie categorie) throws DBException {
        String sql = "INSERT INTO Categorie (libelle) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categorie.getLibelle());
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout: " + e.getMessage());
            return false;
        }
    }
    
    public boolean modifier(Categorie categorie) throws DBException {
        String sql = "UPDATE Categorie SET libelle = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, categorie.getLibelle());
            pstmt.setInt(2, categorie.getId());
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) throws DBException {
        String sql = "DELETE FROM Categorie WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }
    
    public List<Categorie> listerToutes() throws DBException {
        List<Categorie> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categorie ORDER BY libelle";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categorie cat = new Categorie(
                    rs.getInt("id"),
                    rs.getString("libelle")
                );
                categories.add(cat);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement: " + e.getMessage());
        }
        return categories;
    }
    
    public Categorie trouverParId(int id) throws DBException {
        String sql = "SELECT * FROM Categorie WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Categorie(
                    rs.getInt("id"),
                    rs.getString("libelle")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return null;
    }
    
    public boolean existeLibelle(String libelle) throws DBException {
        String sql = "SELECT COUNT(*) FROM Categorie WHERE libelle = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libelle);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return false;
    }

}

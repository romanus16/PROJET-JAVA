/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author HP
 */
import Business.Produit;
import Business.Categorie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProduitDAO {
    
    public boolean ajouter(Produit produit) throws DBException {
        String sql = "INSERT INTO Produit (nom, id_categorie, prix_vente, stock_actuel, seuil_alerte) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, produit.getNom());
            pstmt.setInt(2, produit.getCategorie().getId());
            pstmt.setDouble(3, produit.getPrixVente());
            pstmt.setInt(4, produit.getStockActuel());
            pstmt.setInt(5, produit.getSeuilAlerte());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout: " + e.getMessage());
            return false;
        }
    }
    
    public boolean modifier(Produit produit) throws DBException {
        String sql = "UPDATE Produit SET nom = ?, id_categorie = ?, prix_vente = ?, stock_actuel = ?, seuil_alerte = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, produit.getNom());
            pstmt.setInt(2, produit.getCategorie().getId());
            pstmt.setDouble(3, produit.getPrixVente());
            pstmt.setInt(4, produit.getStockActuel());
            pstmt.setInt(5, produit.getSeuilAlerte());
            pstmt.setInt(6, produit.getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la modification: " + e.getMessage());
            return false;
        }
    }
    
    public boolean supprimer(int id) throws DBException {
        String sql = "DELETE FROM Produit WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }
    
    public List<Produit> listerTous() throws DBException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.*, c.libelle as categorie_libelle FROM Produit p " +
                     "JOIN Categorie c ON p.id_categorie = c.id " +
                     "ORDER BY p.nom";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("id_categorie"),
                    rs.getString("categorie_libelle")
                );
                
                Produit produit = new Produit(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    categorie,
                    rs.getDouble("prix_vente"),
                    rs.getInt("stock_actuel"),
                    rs.getInt("seuil_alerte")
                );
                produits.add(produit);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement: " + e.getMessage());
        }
        return produits;
    }
    
    public List<Produit> produitsEnAlerte() throws DBException {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT p.*, c.libelle as categorie_libelle FROM Produit p " +
                     "JOIN Categorie c ON p.id_categorie = c.id " +
                     "WHERE p.stock_actuel <= p.seuil_alerte " +
                     "ORDER BY p.stock_actuel ASC";
        
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("id_categorie"),
                    rs.getString("categorie_libelle")
                );
                
                Produit produit = new Produit(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    categorie,
                    rs.getDouble("prix_vente"),
                    rs.getInt("stock_actuel"),
                    rs.getInt("seuil_alerte")
                );
                produits.add(produit);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return produits;
    }
    
    public Produit trouverParId(int id) throws DBException {
        String sql = "SELECT p.*, c.libelle as categorie_libelle FROM Produit p " +
                     "JOIN Categorie c ON p.id_categorie = c.id " +
                     "WHERE p.id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Categorie categorie = new Categorie(
                    rs.getInt("id_categorie"),
                    rs.getString("categorie_libelle")
                );
                
                return new Produit(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    categorie,
                    rs.getDouble("prix_vente"),
                    rs.getInt("stock_actuel"),
                    rs.getInt("seuil_alerte")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur: " + e.getMessage());
        }
        return null;
    }
    
    public boolean mettreAJourStock(int idProduit, int quantite) throws DBException {
        String sql = "UPDATE Produit SET stock_actuel = stock_actuel + ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, quantite);
            pstmt.setInt(2, idProduit);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur de mise à jour stock: " + e.getMessage());
            return false;
        }
    }
}
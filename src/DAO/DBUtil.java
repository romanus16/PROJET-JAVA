/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    
    // Supprimez la variable statique 'connection' qui posait problème
    
    private DBUtil() { }

    public static Connection getConnection() throws DBException, SQLException {
        // Paramètres pour assurer la stabilité
        String url = "jdbc:mysql://localhost:3306/gestion_restaurant?autoReconnect=true&useSSL=false";
        String username = "root";
        String password = "";
        
        // On retourne toujours une NOUVELLE connexion
        return DriverManager.getConnection(url, username, password);
    }
    
    // La méthode closeConnection n'est plus nécessaire car le try-with-resources s'en occupe
}
    
    


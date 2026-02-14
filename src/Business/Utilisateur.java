/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

/**
 *
 * @author HP
 */


public class Utilisateur {
    private int id;
    private String login;
    private String motDePasse;
    
    public Utilisateur() {}
    
    public Utilisateur(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    
    @Override
    public String toString() {
        return login;
    }
}

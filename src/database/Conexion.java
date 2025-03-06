/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/puntoventa";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "79079308";
   
    private static Conexion singleConnection;
    private Connection conectar;
    
    private Conexion() {
        this.conectar = null;
    }
    
    public static Conexion getInstance() {
        if (singleConnection == null) {
            singleConnection = new Conexion();
        }
        return singleConnection;
    }
    
    public Connection getConnection() {
        if (conectar == null) {
            try {
                Class.forName(DB_DRIVER);
                conectar = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
            }
        }
        return conectar;
    }
    
    public void desconectar() {
        if (conectar != null) {
            try {
                conectar.close();
                conectar = null;  // Para indicar que la conexión está cerrada
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}

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
   
    Connection conectar;
    public static Conexion singleConnection;
    
    public Conexion() {
        this.conectar = null;
    }
    
    public Connection conectar() {
        try {
            Class.forName(DB_DRIVER);
            this.conectar = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }
        return this.conectar;
    }
    
    public static Conexion getInstance()
    {
        if(singleConnection == null)
        {
            singleConnection = new Conexion();
        }
        return singleConnection;
    }
    
    public void desconectar()
    {
        try 
        {
            this.conectar.close();
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
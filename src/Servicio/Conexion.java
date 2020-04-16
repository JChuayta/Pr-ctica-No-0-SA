/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicio;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Carlos
 */
public class Conexion {

    private static Conexion instance = null;

    private final String db_name = "db_grupo08sc";
    private final String port = "5432";
    private final String host = "virtual.fcet.uagrm.edu.bo";
    private final String usuario = "grupo08sc";
    private final String password = "grupo08grupo08";

    private Connection conn;
    private Conexion() {
    }
     private void contectar() {
        String urlDatabase = "jdbc:postgresql://" + host + ":" + port + "/" + db_name;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDatabase, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
        }
    }
    public synchronized static Conexion getInstance() {

        instance = new Conexion();
        instance.contectar();
        return instance;
    }
    public Connection getConnection() {
        return conn;
    }

    public void cerrarConexion() {
        try {
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexion.instance = null;
        }
    }

   
}

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

//    private final String db_name = "db_grupo09sc";//"laboratorios";
//    private final String port = "5432";//"5454";
//    private final String host = "mail.ficct.uagrm.edu.bo";//"127.0.0.1";
//    private final String usuario = "grupo09sc";//"postgres";
//    private final String password = "grupo09grupo09";//"tecnoweb";
    private final String db_name = "db_agenda";
    private final String port = "5454";
    private final String host = "127.0.0.1";
    private final String usuario = "postgres";
    private final String password = "tecnoweb";

    private Connection conn;

    private Conexion() {
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

    private void contectar() {
        String urlDatabase = "jdbc:postgresql://" + host + ":" + port + "/" + db_name;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDatabase, usuario, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
        }
    }
}

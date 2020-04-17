/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dato;

import Servicio.Conexion;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Carlos
 */
public class DAmigo {

    private int amig_ci;
    private String amig_nombre;
    private String amig_appm;
    private String amig_telf;
    private String amig_cel;
    private String amig_dir;
    private String amig_fnac;

    public static ArrayList<DAmigo> All(String query) {
        ArrayList<DAmigo> usuarios = new ArrayList<>();
        Connection con = Conexion.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from amigo where amig_nombre like '%" + query.toUpperCase() + "%' or amig_nombre like '%" + query + "%' ";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    DAmigo usuario = new DAmigo();
                    usuario.amig_ci = rs.getInt("amig_ci");
                    usuario.amig_nombre = rs.getString("amig_nombre");
                    usuario.amig_appm = rs.getString("amig_appm");
                    usuario.amig_telf = rs.getString("amig_telf");
                    usuario.amig_cel = rs.getString("amig_cel");
                    usuario.amig_dir = rs.getString("amig_dir");
                    usuario.amig_fnac = rs.getString("amig_fnac");
                    usuarios.add(usuario);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {System.out.println("Error al buscar usuario por NumeroID: " + ex.getMessage());
            } catch (Exception ex) {
            }
        }
        return usuarios;
    }

    public int getAmig_ci() {
        return amig_ci;
    }

    public void setAmig_ci(int amig_ci) {
        this.amig_ci = amig_ci;
    }

    public String getAmig_nombre() {
        return amig_nombre;
    }

    public void setAmig_nombre(String amig_nombre) {
        this.amig_nombre = amig_nombre;
    }

    public String getAmig_appm() {
        return amig_appm;
    }

    public void setAmig_appm(String amig_appm) {
        this.amig_appm = amig_appm;
    }

    public String getAmig_telf() {
        return amig_telf;
    }

    public void setAmig_telf(String amig_telf) {
        this.amig_telf = amig_telf;
    }

    public String getAmig_cel() {
        return amig_cel;
    }

    public void setAmig_cel(String amig_cel) {
        this.amig_cel = amig_cel;
    }

    public String getAmig_dir() {
        return amig_dir;
    }

    public void setAmig_dir(String amig_dir) {
        this.amig_dir = amig_dir;
    }

    public String getAmig_fnac() {
        return amig_fnac;
    }

    public void setAmig_fnac(String amig_fnac) {
        this.amig_fnac = amig_fnac;
    }

    

}

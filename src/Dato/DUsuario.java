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
public class DUsuario {

    private int ci;
    private String nombre;
    private String sexo;
    private int telefono;

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    
    public static ArrayList<DUsuario> All(String query) {
        ArrayList<DUsuario> usuarios = new ArrayList<>();
        Connection con = Conexion.getInstance().getConnection();
        if (con != null) {
            String sql = "select * from amigo where nombre like '%" + query.toUpperCase() + "%' or nombre like '%" + query + "%' ";
            PreparedStatement preparedStatement;
            try {
                preparedStatement = con.prepareStatement(sql);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    DUsuario usuario = new DUsuario();
                    usuario.ci = rs.getInt("ci");
                    usuario.nombre = rs.getString("nombre");
                    usuario.sexo = rs.getString("sexo");
                    usuario.telefono = rs.getInt("telefono");
                    usuarios.add(usuario);
                }
                preparedStatement.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println("Error al buscar usuario por NumeroID: " + ex.getMessage());
            } catch (Exception ex) {
            }
        }
        return usuarios;
    }

}

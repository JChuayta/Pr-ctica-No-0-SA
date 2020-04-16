/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package práctica.no.pkg0.sa;

import Servicio.Conexion;
import Servicio.Esclavo;
import java.sql.Connection;

/**
 *
 * @author Carlos
 */
public class PrácticaNo0SA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        
         Esclavo esclavo;
        Connection con = Conexion.getInstance().getConnection();
        if (con != null) {
            System.out.println(" ¡¡¡ Conexion exitosa !!!");;
            esclavo = new Esclavo();
            esclavo.start();
        } else {
            System.out.println("Fallo en la Conexion");
        }
    }
    
}

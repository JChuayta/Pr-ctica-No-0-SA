package Negocio;

import Dato.DAmigo;
import Servicio.*;
import java.util.*;

/**
 *
 * @author Carlos
 */
public class NAmigo {

    String contenido;
    Mensaje mensaje;

    public NAmigo(String contenido) {
        this.contenido = contenido;
    }

    public void analizar(Mensaje mensaje) {
        this.mensaje = mensaje;
        Mensaje myMensaje = null;
        myMensaje = new Mensaje(mensaje.getCorreo(), "Listar ", "\n" + obtenerDatos(contenido));
    }

    private String obtenerDatos(String query) {
        ArrayList<DAmigo> lista = DAmigo.All(query);

        String leftAlignFormat = " | %-4d | %-15s | %-4d |%n";

        System.out.format(" +-------+---------------------+----------+%n");
        System.out.format(" |   CI  |        NOMBRE      |  TELEFONO |%n");
        System.out.format(" +-------+---------------------+----------+%n");
        for (int i = 0; i < lista.size(); i++) {
            DAmigo modelo = lista.get(i);
            System.out.format(leftAlignFormat, modelo.getCi(), modelo.getNombre(), modelo.getTelefono());
        }
        return ("+----+--------+----------+%n");

    }

    public Mensaje generarMensajeError(int nroComandos) {
        String subject = "ERROR";
        String data = "";
        if (nroComandos == 0) {
            data = "El comando enviado no es correcto\n"
                    + "Si desea saber los comandos del sistema ingrese :\n"
                    + "\t{HELP}\n\n";
        } else {
            data = "El Caso de Uso solicitado es incorrecto\n"
                    + "Si desea saber los comandos del sistema ingrese :\n"
                    + "\t{HELP}\n\n";
        }
        Mensaje myMensaje = new Mensaje(mensaje.getCorreo(), subject, data);
        return myMensaje;
    }

}

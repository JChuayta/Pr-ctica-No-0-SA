package Negocio;

import Dato.DUsuario;
import Servicio.*;
import java.util.*;

/**
 *
 * @author Carlos
 */
public class NUsuario {

    String contenido;
    Mensaje mensaje;

    public NUsuario(String contenido) {
        this.contenido = contenido;
    }

    public void analizar(Mensaje mensaje) {
        this.mensaje = mensaje;
        Mensaje myMensaje = null;

        myMensaje = new Mensaje(mensaje.getCorreo(), "GESTIONAR PERSONA", "\n" + obtenerDatos(contenido));

        System.out.println(myMensaje.toString());
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

    private String obtenerDatos(String query) {
        ArrayList<DUsuario> lista = DUsuario.All(query);
        String result = "<h1>LISTADO DE PERSONA</h1>"
                + "<table>"
                + "<tr>\n"
                + "<th>CI</th>\n"
                + "<th>NOMBRE</th>\n"
                + "<th>SEXO</th>\n"
                + "<th>TELEFONO</th>\n"
                + "</tr>\n";
        for (int i = 0; i < lista.size(); i++) {
            DUsuario modelo = lista.get(i);
            result = result + "<tr>\n"
                    + "<td>" + Integer.toString(modelo.getCi()) + "</td>\n"
                    + "<td>" + modelo.getNombre() + "</td>\n"
                    + "<td>" + modelo.getSexo() + "</td>\n"
                    + "<td>" + Integer.toString(modelo.getTelefono()) + "</td>\n"
                    + "</tr>\n";
        }
        result = result + "</table>";
        return result;
    }

}

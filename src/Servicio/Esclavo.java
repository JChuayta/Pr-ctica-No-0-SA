package Servicio;

import Negocio.NAmigo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Esclavo {

    private Timer temporizador;
    private int milisegundosDeRetraso = 7000;

    public void start() {
        stop();
        temporizador = new Timer();
        planificar();
    }

    private void planificar() {
        if (temporizador != null) {
            Date timeToRun = new Date(System.currentTimeMillis() + milisegundosDeRetraso);
            temporizador.schedule(new TimerTask() {
                public void run() {
                    verificarBandeja();
                }
            }, timeToRun);
        }
    }

    /*Este metodo verificara cada rato el servidor
     */
    private synchronized void verificarBandeja() {
        System.out.println("verificado en " + System.currentTimeMillis());
        ClientePOP pop = new ClientePOP();
        Mensaje mensaje = pop.LeerCorreo();

        if (mensaje != null) { // aqui se procesa el contenido del mensaje de gmail

            String contenidoMensaje = mensaje.getSubject(); /// este es el parametro de entrada para nuestra funcion de consulta
            if (contenidoMensaje != "") {
                NAmigo amigo = new NAmigo(contenidoMensaje); /// el atributo contenifoMensaje es el que tiene el subject que es envaido x correo
                Mensaje result = amigo.analizar(mensaje);  // el atributo mensaje no se utiliza
                ClienteSMTP smtp = new ClienteSMTP();
                if (smtp.enviarCorreo(result)) {
                    System.out.println(" S : Se envio el mensaje");
                    //pop.eliminarCorreo(mensaje.getId());
                }
            }
            pop.eliminarCorreo(mensaje.getId());

        } else {
            System.out.println(" S : No hay mensajes nuevos");
        }
        planificar();
    }

    public void stop() {
        if (temporizador != null) {
            temporizador.cancel();
            temporizador = null;
        }
    }
}

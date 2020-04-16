package Servicio;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeoutException;

 public class ClientePOP {

    private static int UNILINE = 0;
    private static int BILINE = 1;
    private static int MULTILINE = 2;
    private static String Servidor = "virtual.fcet.uagrm.edu.bo";
    private static String Usuario = "grupo08sc";
    private static String Contraseña = "grupo08grupo08";
    private int Puerto = 110;

    public Mensaje LeerCorreo() {
        Mensaje msj = null;
        try {
            String comando = "";
            Socket socket = new Socket(Servidor, Puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            String respuesta = "";
            if (socket != null && entrada != null && salida != null) {
                comando = "USER " + Usuario + "\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
                if (respuesta.indexOf("+OK") == -1) {
                    return null;
                }
                comando = "PASS " + Contraseña + "\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, BILINE);
                if (respuesta.indexOf("+OK") == -1) {
                    return null;
                }
                comando = "LIST \r\n";
                respuesta = ejecutarComando(entrada, salida, comando, MULTILINE);

                if (getNumeroMensajes(respuesta) > 0) {
                    int idmensaje = getIdPrimerMensaje(respuesta);
                    comando = "RETR " + idmensaje + "\n";
                    String data = ejecutarComandoSubject(entrada, salida, comando, MULTILINE);
                    if (data.indexOf("+OK") != -1) {
                        String correo = getCorreoUser(data);
                        String subject = getSubject(data);
                        String mensaje = getSubject(data);
                        msj = new Mensaje(idmensaje, correo, subject, mensaje);
                    }
                }
                comando = "QUIT\r\n";
                salida.writeBytes(comando);
            }
            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return msj;
    }

    private String getSubject(String entrada) {  /////seguimos parcheando el codigo
        String aux = entrada.toUpperCase();
        //  System.out.println("S : getSubject\n");
        int pos = aux.indexOf("SUBJECT: ") + 8;

        int fin = 0;
        if (entrada.contains("@outlook.com") || entrada.contains("@hotmail.com")) {
            fin = entrada.indexOf("Thread-Topic:", pos);
        } else if (entrada.contains("@gmail.com")) {
            fin = entrada.indexOf("To:", pos);
        } else {
            fin = entrada.indexOf("Content-Type:", pos);
        }

        String cadenita = entrada.substring(pos, fin - 1).trim();
        //cadenita.replaceAll("\n", " ");
        //System.out.println(cadenita);
        return cadenita;
    }

    private String getCorreoUser(String entrada) {
        String aux = entrada.toUpperCase();
        int pos = aux.indexOf("FROM: ") + 6;
        int fin = entrada.indexOf("Date:", pos);   ///modifique esto por un salto de linea

        //correos enviados desde un servidor de correo ej. Gmail
        int auxini = entrada.indexOf("<", pos);
        int auxfin = entrada.indexOf(">", pos);
        if (auxini != -1 && auxfin != -1) {
            if (pos < auxini && fin > auxfin) {
                pos = auxini + 1;
                fin = auxfin;
            }
        }

        return entrada.substring(pos, fin).trim();
    }

    private int getIdPrimerMensaje(String entrada) {
        int pos = entrada.indexOf("messages:") + 9;
        int fin = entrada.indexOf(" ", pos);
        String valor = entrada.substring(pos, fin).trim();
        return Integer.parseInt(valor);
    }

    private int getNumeroMensajes(String entrada) {
        int pos = entrada.indexOf("+OK") + 3;
        int fin = entrada.indexOf("messages");
        String valor = entrada.substring(pos, fin).trim();
        return Integer.parseInt(valor);
    }

    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + "\r\n" + line;
        }
        return lines;
    }

    static protected String getMultilineSubject(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + " " + line;
        }
        return lines;
    }

    private String ejecutarComandoSubject(BufferedReader entrada, DataOutputStream salida, String comando, int tipoRespuesta) {
        try {
            //   System.out.print("C : "+comando);
            salida.writeBytes(comando);
            String respuesta;
            if (tipoRespuesta == UNILINE) {
                respuesta = entrada.readLine();
            } else if (tipoRespuesta == BILINE) {
                respuesta = entrada.readLine();
                respuesta += "\n" + entrada.readLine();
            } else {
                respuesta = getMultilineSubject(entrada);
            }
            //  System.out.println("S : "+respuesta+"\n");
            return respuesta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String ejecutarComando(BufferedReader entrada, DataOutputStream salida, String comando, int tipoRespuesta) {
        try {
            //   System.out.print("C : "+comando);
            salida.writeBytes(comando);
            String respuesta;
            if (tipoRespuesta == UNILINE) {
                respuesta = entrada.readLine();
            } else if (tipoRespuesta == BILINE) {
                respuesta = entrada.readLine();
                respuesta += "\n" + entrada.readLine();
            } else {
                respuesta = getMultiline(entrada);
            }
            //  System.out.println("S : "+respuesta+"\n");
            return respuesta;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean eliminarCorreo(int idmensaje) {
        try {
            System.out.println(" S : Eliminando correo");
            String comando = "";
            Socket socket = new Socket(Servidor, Puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            String respuesta = "";
            if (socket != null && entrada != null && salida != null) {
                comando = "USER " + Usuario + "\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
                if (respuesta.indexOf("+OK") == -1) {
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }

                comando = "PASS " + Contraseña + "\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, BILINE);
                if (respuesta.indexOf("+OK") == -1) {
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }

                comando = "DELE " + idmensaje + "\r\n";
                respuesta = ejecutarComando(entrada, salida, comando, UNILINE);
                if (respuesta.indexOf("+OK") == -1) {
                    salida.close();
                    entrada.close();
                    socket.close();
                    return false;
                }

                comando = "QUIT\r\n";
                //     System.out.print("C : "+comando);
                salida.writeBytes(comando);
                //     System.out.println("S : "+entrada.readLine()+"\r\n");

            }
            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
        return true;
    }
}

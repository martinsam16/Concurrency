package websocket;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Arrays;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import javax.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;

/**
 * Ejemplo cliente ws java
 *
 * @author MartinSaman
 */
@ClientEndpoint
public class ClientW<T> {

    Class<T> modelo;

    public List<T> listaRecibida;

    Session mySesion;

    public ClientW(Class<T> modelo) {
        try {
            this.modelo = modelo;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void conexionAbierta(Session sesion) {
        try {
            this.mySesion = sesion;
            System.out.println("Conexion con el servidor exitoso C:");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void mensajeRecibido(String data, Session sesion) throws IOException {
        try {
            System.out.println("DATA RECIBIDA ---------");
            System.out.println("JSON");
            System.out.println(data);
            setearDatos(data);
            System.out.println("Lista");
            System.out.println(Arrays.toString(listaRecibida.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) throws Exception {
        try {
            this.mySesion.getBasicRemote().sendText(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setearDatos(String data) throws Exception {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<T>>() {
            }.getType();
            this.listaRecibida = gson.fromJson(data, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void conexionCerrada(Session sesion) throws Exception {
        try {
            this.mySesion = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desconectar(Session sesion) throws Exception {
        try {
            sesion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void error(Throwable e) {
        e.printStackTrace();
    }
}

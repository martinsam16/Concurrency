package websocket;

import com.google.gson.Gson;
import dao.Datos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import modelo.Persona;

/**
 * Para el servidor Se maneja JSON para que sea escalable
 *
 * @author MartinSaman
 * @param <T>
 */
public class ServerW<T> {

    List<Session> sesiones;

    List<Persona> listaDatos;

    String listaDatosJSON;

    Class<T> modelo;

    ServerW(Class<T> model) {
        try {
            this.modelo = (Class<T>) model;
            sesiones = new ArrayList<>();
            System.out.println("Listando");
            actualizarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conexionAbierta(Session sesion) {
        try {
            if (sesiones.contains(sesion) == false) {
                sesiones.add(sesion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mensaje(String data, Session sesion) throws IOException {
        try {
            if (data.equals("1")) {
                mandarDatosOne(sesion);

            } else {
                mandarDatosActualizados();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mandarDatosOne(Session sesion) throws IOException {
        try {
            sesion.getBasicRemote().sendText(listaDatosJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mandarDatosAll() throws IOException {
        try {
            for (Session s : sesiones) {
                s.getBasicRemote().sendText(listaDatosJSON);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mandarDatosActualizados() throws Exception {
        try {
            actualizarDatos();
            mandarDatosAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarDatos() throws Exception {
        try {
            Gson gson = new Gson();

            listaDatos = new Datos(modelo).listar();
            listaDatosJSON = gson.toJson(listaDatos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void conexionCerrada(Session sesion) {
        try {
            sesiones.remove(sesion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(Throwable e) {
        e.printStackTrace();
    }

}

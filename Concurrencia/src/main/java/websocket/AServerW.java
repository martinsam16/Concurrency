package websocket;

import com.google.gson.Gson;
import dao.Datos;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * Para el servidor Se maneja JSON para que sea escalable
 *
 * @author MartinSaman
 */
public abstract class AServerW<T> {

    private Class<T> modelo;

    private List<Session> sesiones;

    private List<T> listaDatos;

    private String listaDatosJSON;

    public AServerW(Class<T> modelo) {
        this.modelo = modelo;
        sesiones = Collections.synchronizedList(new ArrayList<Session>());
    }

    @PostConstruct
    public void onInit() {
        try {
            actualizarDatos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    void conexionAbierta(Session sesion) {
        if (sesiones.contains(sesion) == false) {
            sesiones.add(sesion);
        }
    }

    @OnMessage
    void mensaje(String data, Session sesion) throws IOException {
        if (data.equals("1")) {
            mandarDatosAll();
        } else {
            mandarDatos(sesion);
        }
    }

    void mandarDatos(Session sesion) throws IOException {
        try {
            sesion.getBasicRemote().sendText(listaDatosJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mandarDatosAll() throws IOException {
        try {
            for (Session s : sesiones) {
                s.getBasicRemote().sendText(listaDatosJSON);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void mandarDatosActualizados() throws Exception {
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
            listaDatos = new Datos<>(modelo).listar();
            listaDatosJSON = gson.toJson(listaDatos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    void conexionCerrada(Session sesion) {
        sesiones.remove(sesion);
    }

    @OnError
    void error(Throwable e) {
        System.out.println(String.format(
                "[Error Server WS -> %s ] %s",
                modelo.getCanonicalName(),
                e.getMessage()
        ));
    }
}

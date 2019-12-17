package websocket;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import modelo.Persona;

/**
 *
 * @author MartinSaman
 */
@ApplicationScoped
@ServerEndpoint("/persona")
public class PersonaSW {
    
    ServerW server = new ServerW(Persona.class);
    
    @OnOpen
    public void onOpen(Session sesion) {
        server.conexionAbierta(sesion);
    }
    
    @OnClose
    public void onClose(Session sesion) {
        server.conexionCerrada(sesion);
    }
    
    @OnError
    public void onError(Throwable t) {
        server.error(t);
    }
    
    @OnMessage
    public void onMessage(String data, Session sesion) throws IOException {
        server.mensaje(data, sesion);
    }
    
}

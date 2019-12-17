package controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import modelo.Persona;
import org.primefaces.PrimeFaces;
import utils.NetData;
import websocket.ClientW;

/**
 *
 * @author MartinSaman
 */
@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    ClientW cliente;

    List<Persona> listaPersona;

    public PersonaC() {
        try {
            cliente = new ClientW(Persona.class);
            listaPersona = new ArrayList<>();
            crearClienteWS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void onInit() {
        try {
            System.out.println("Enviando my request xd");
            cliente.enviarMensaje("1");
            actualizarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarLista() {
        System.out.println("Seteando lista en el bean");
        listaPersona = cliente.listaRecibida;
        System.out.println("Bean seteado");
    }

    private void crearClienteWS() throws Exception {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://" + NetData.getIP() + ":" + NetData.getPORT() + "/Concurrencia/persona";
            System.out.println("Conectando: " + uri);
            container.connectToServer(cliente, URI.create(uri));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrar() throws Exception {
        try {
            System.out.println("Enviando request despues de registrar en un rest xd");
            cliente.enviarMensaje("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

}

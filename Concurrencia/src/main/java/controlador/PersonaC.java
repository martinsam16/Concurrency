package controlador;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author MartinSaman
 */
@Named(value = "personaC")
@SessionScoped
public class PersonaC implements Serializable {

    
    public PersonaC() {
    }
    
}

package modelo;

import anotaciones.ITabla;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author MartinSaman
 */
@ITabla(nombreTabla = "PERSONA")
@Data
public class Persona {

    @ITabla(nombreColumna = "IDPER")
    private BigDecimal IDPER;

    @ITabla(nombreColumna = "NOMPER")
    private String NOMPER;


}

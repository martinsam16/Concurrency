package modelo;

import anotaciones.ITabla;
import java.math.BigDecimal;

/**
 *
 * @author MartinSaman
 */
@ITabla(nombreTabla = "PERSONA")
public class Persona {

    @ITabla(nombreColumna = "IDPER")
    private BigDecimal IDPER;

    @ITabla(nombreColumna = "NOMPER")
    private String NOMPER;

    public BigDecimal getIDPER() {
        return IDPER;
    }

    public void setIDPER(BigDecimal IDPER) {
        this.IDPER = IDPER;
    }

    public String getNOMPER() {
        return NOMPER;
    }

    public void setNOMPER(String NOMPER) {
        this.NOMPER = NOMPER;
    }

    @Override
    public String toString() {
        return "Persona{" + "IDPER=" + IDPER + ", NOMPER=" + NOMPER + '}';
    }


}

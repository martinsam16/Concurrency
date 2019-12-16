package anotaciones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author MartinSaman
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ITabla {

    public String nombreTabla() default "";

    public String nombreColumna() default "";
}

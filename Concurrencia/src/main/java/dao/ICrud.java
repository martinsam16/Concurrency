package dao;

import java.util.List;

/**
 *
 * @author MartinSaman
 */
public interface ICrud<T> {

    void registrar(T modelo) throws Exception;

    void editar(T modelo) throws Exception;

    void eliminar(T modelo) throws Exception;    

}

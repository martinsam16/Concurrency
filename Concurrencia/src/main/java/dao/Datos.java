package dao;

import anotaciones.ITabla;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Persona;

/**
 * Lista una tabla a partir de un modelo
 * inspirado en JPA
 * @param <T> modelo.class
 * @author MartinSaman
 */
public class Datos<T> extends Conexion {

    Class<T> modelo;
    private String nombreTabla;

    public Datos(Class<T> modelo) {
        this.modelo = modelo;
        try {
            nombreTabla = modelo.newInstance().getClass().getAnnotation(ITabla.class).nombreTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> listar() throws SQLException, Exception {
        List<T> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + nombreTabla;

            PreparedStatement ps = this.conectar().prepareStatement(query);

            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                T modeloTemporal = (T) modelo.newInstance();
                setearModelo(rs, modeloTemporal);
                list.add(modeloTemporal);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.desconectar();
        }

        return list;
    }

    public static void setearModelo(ResultSet rs, Object modelo) throws Exception {
        Class<?> claseModelo = modelo.getClass();
        for (Field field : claseModelo.getDeclaredFields()) {
            field.setAccessible(true);
            ITabla columna = field.getAnnotation(ITabla.class);
            Object valor = rs.getObject(columna.nombreColumna());
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                valor = boxed.cast(valor);
            }
            field.set(modelo, valor);
        }
    }

    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "clase '" + type.getName() + "' no es primitiva";
            throw new IllegalArgumentException(string);
        }
    }

    public static void main(String[] args) {
        try {
            Datos d = new Datos(Persona.class);
            List<Persona> lista = d.listar();
            for (Persona persona : lista) {
                System.out.println(persona.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MartinSaman
 */
public class Conexion {

    protected static Connection conexion = null;

    public static Connection conectar() throws Exception {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            conexion = DriverManager.getConnection(
                    "jdbc:oracle:thin:@34.69.77.101:1521:XE",
                    "db_IDOC",
                    "db_IDOC-2019");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public static void desconectar() throws SQLException {
        try {
            if (conexion.isClosed() == false) {
                conexion.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package datos;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_clientes?zeroDateTimeBehavior=CONVERT_TO_NULL&useTimeZone=true&serverTimeZone=UTC&allowPublicKeyRetrieval=true&useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "SpyasDev1.";
    
    //Variable privada para el ds (datasource) para no crear varias instancias de dataSource
    private static BasicDataSource dataSource;
    
    public static DataSource getDataSource(){
        //Si no se ha creado un datasource se crea uno y se retorna, si ya esta creado se retorna el ya existente:
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            dataSource.setUrl(JDBC_URL);
            dataSource.setUsername(JDBC_USER);
            dataSource.setPassword(JDBC_PASSWORD);
            dataSource.setInitialSize(50);
        }
        System.out.println("Me conecte a la base de datos ");
        return dataSource;
    }
    
    public static Connection getConnection() throws SQLException{
        return getDataSource().getConnection();
    }
    
    public static void close (ResultSet rs){
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close (PreparedStatement stmt){
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public static void close (Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
}

package entity.interfaces;

/**
 * Interface to Handle attributes that is essential for JDBC Connector to connect
 * with the Database
 */
public interface ConnectionManager {
    String host = "localhost";
    int port = 3306;
    String dbName = "andioop";
    String dbURL = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?useSSL=false";

}
package fr.ece.edu.ec.chess_tracker.dataAcces;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC_connection {
    private static final String PROPERTY_FILE = "connection.properties";
    private static final String PROPERTY_USER = "user";
    private static final String PROPERTY_SERVER = "server";
    private static final String PROPERTY_PORT = "port";
    private static final String PROPERTY_PWD = "pwd";
    private static final String PROPERTY_DBNAME = "dbName";

    static Connection conn;

    /**
     * Private constructor that can be called only when there is no current instance of Connection, or if the current Connection is closed.
     * @param serv server adress
     * @param dbName database name
     * @param port port to connect
     * @param usr user id
     * @param pwd password
     * @throws Exception if connection failed
     */
    private JDBC_connection(String serv, String dbName, int port, String usr, String pwd) throws SQLException {
        String url = "jdbc:mysql://"+serv+":"+port+"/"+dbName;
        url+="?useUnicode=true";
        url+="&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&";
        url+="serverTimezone=UTC";
        this.conn = DriverManager.getConnection(url,usr,pwd);
        conn.setAutoCommit(false);
    }

    /**
     * Returning a single instace of Connection,
     * @return a instance of Connection that allows us to connect to the database
     * @throws Exception if connection failed
     */
    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        try (InputStream propertyFile = JDBC_connection.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {

            properties.load(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String server = properties.getProperty(PROPERTY_SERVER);
        String user = properties.getProperty(PROPERTY_USER);
        String pwd = properties.getProperty(PROPERTY_PWD);
        int port = Integer.parseInt(properties.getProperty(PROPERTY_PORT));
        String dbName = properties.getProperty(PROPERTY_DBNAME);

        if (conn == null || conn.isClosed()) {
            System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
            new JDBC_connection(server, dbName, port, user, pwd);
        } else {
            System.out.println("CONNEXION SQL EXISTANTE ! ");
        }
        return conn;
    }
}

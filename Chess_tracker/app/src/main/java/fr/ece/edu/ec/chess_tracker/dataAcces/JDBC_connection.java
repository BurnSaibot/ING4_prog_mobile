package fr.ece.edu.ec.chess_tracker.dataAcces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        //Class.forName("com.mysql.jdbc.Driver");
        String server = "remotemysql.com";
        String user = "Tm58uEFfeX";
        String pwd = "nyaplErePq";
        int port = 3306;
        String dbName = "Tm58uEFfeX";

        if (conn == null || conn.isClosed()) {
            System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
            new JDBC_connection(server, dbName, port, user, pwd);
        } else {
            System.out.println("CONNEXION SQL EXISTANTE ! ");
        }
        return conn;
    }
}

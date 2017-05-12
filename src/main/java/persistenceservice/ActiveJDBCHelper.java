package persistenceservice;

import org.javalite.activejdbc.Base;

import java.sql.SQLException;

/**
 * Created by Heinrich on 28.11.2015.
 */
public class ActiveJDBCHelper {

    private static final String connectionString = "jdbc:sqlserver://;ServerName=THINKPAD\\SQLSERVER2012;DatabaseName=KundenverwaltungJava";
    private static final String databaseDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String login = "user";
    private static final String passwd = "password";

    /**
     * Oeffnet die Datenbankverbindung
     */
    public static void OpenDatabaseConnection() {
        Base.open(databaseDriver, connectionString, login, passwd);
    }

    public static void CloseDatabaseConnection() {
        Base.close();
    }

    public static void openTransaction(){
        Base.openTransaction();
    }

    public static void commitTransaction(){
        Base.commitTransaction();
        SetAutoCommit();
    }

    public static void rollbackTransaction(){
        Base.rollbackTransaction();
        SetAutoCommit();
    }

    /**
     * Auto-Commit wieder auf true setzen, da es bei Base.openTransaction() auf false gesetzt wird
     */
    public static void SetAutoCommit() {
        try {
            Base.connection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

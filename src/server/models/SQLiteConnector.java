package server.models;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Project Name: HangmanServer
 */

public class SQLiteConnector {

    private String JDBC_DRIVER, JDBC_URL;
    private Connection conn;

    public SQLiteConnector() {
        JDBC_DRIVER = "org.sqlite.JDBC";
        JDBC_URL = initJDBC_URL();
    }

    public void loadWordsFromDB(ArrayList<Word> words) {
    }

    public void insertWordToDB(Word word) {
    }

    public void removeWordFromDB(int ID) {
    }

    private Connection getDatabaseConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private String initJDBC_URL() {
        String jdbc_url = "jdbc:sqlite:";
        File dbFile = new File("/server/resources/library/Words.db");
        if (!dbFile.exists()) jdbc_url = jdbc_url + dbFile.getPath();
        return jdbc_url;
    }

}

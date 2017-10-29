package server.models;

import java.sql.*;
import java.util.ArrayList;

/**
 * Project Name: HangmanClient
 * Created by: Trosalio
 * Name: Thanapong Supalak
 * ID: 5810405029
 */

public class SQLiteConnector {

    private String JDBC_DRIVER, JDBC_URL;

    /**
     * Initialize JDBC_DRIVER and JDBC_URL
     */
    public SQLiteConnector() {
        JDBC_DRIVER = "org.sqlite.JDBC";
        JDBC_URL = "jdbc:sqlite:resources/server/library/Words.db";
    }

    /**
     * This method will pull the data from database
     * and then put it in words::ArrayList<String>.
     *
     * @param words an ArrayList<String> passed to get the data from database
     */
    public void loadWordsFromDB(ArrayList<String> words) {
        String selectSQL = "SELECT * FROM WordTable";
        try (Connection conn = getDatabaseConnection();
             PreparedStatement pStmt = conn.prepareStatement(selectSQL)) {
            ResultSet resultSet = pStmt.executeQuery();
            while (resultSet.next()) {
                String strWord = resultSet.getString("WORD");
                words.add(strWord);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will put word::String into the database.
     *
     * @param word a word to put into the database
     */
    public void insertWordToDB(String word) {
        final String insertSQL = String.format("INSERT OR REPLACE INTO WordTable (Word) VALUES('%s')", word);
        updateDatabase(insertSQL);
    }

    /**
     * This method will remove word::String from database.
     *
     * @param word a word to remove from the database
     */
    public void removeWordFromDB(String word) {
        final String deleteSQL = String.format("DELETE FROM WordTable WHERE WORD = '%s'", word);
        updateDatabase(deleteSQL);
    }

    /**
     * This method serves as a common method that will be called from other method.
     * This method will execute a given query to update the database.
     *
     * @param updateSQL a query to execute
     */
    private void updateDatabase(String updateSQL) {
        try (Connection conn = getDatabaseConnection();
             PreparedStatement pStmt = conn.prepareStatement(updateSQL)) {
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will return a connection from SQLite's JDBC.
     *
     * @return Connection
     */
    private Connection getDatabaseConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

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

    public SQLiteConnector() {
        JDBC_DRIVER = "org.sqlite.JDBC";
        JDBC_URL = "jdbc:sqlite:resources/server/library/Words.db";
    }

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

    public void insertWordToDB(String word) {
        final String insertSQL = String.format("INSERT OR REPLACE INTO WordTable (Word) VALUES('%s')", word);
        updateDatabase(insertSQL);
    }

    public void removeWordFromDB(String word) {
        final String deleteSQL = String.format("DELETE FROM WordTable WHERE WORD = '%s'", word);
        updateDatabase(deleteSQL);
    }

    private void updateDatabase(String updateSQL) {
        try (Connection conn = getDatabaseConnection();
             PreparedStatement pStmt = conn.prepareStatement(updateSQL)) {
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

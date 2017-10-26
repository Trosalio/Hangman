package server.models;

import java.sql.*;
import java.util.ArrayList;

/**
 * Project Name: HangmanServer
 */

public class SQLiteConnector {

    private String JDBC_DRIVER, JDBC_URL;
    private Connection conn;

    public SQLiteConnector() {
        JDBC_DRIVER = "org.sqlite.JDBC";
        JDBC_URL = "jdbc:sqlite:resources/server/library/Words.db";
    }

    public void loadWordsFromDB(ArrayList<String> words) {
        PreparedStatement pStmt = null;
        try {
            conn = getDatabaseConnection();
            String selectSQL = "SELECT * FROM WordTable";
            pStmt = conn.prepareStatement(selectSQL);
            ResultSet resultSet = pStmt.executeQuery();
            while (resultSet.next()){
                String strWord = resultSet.getString("WORD");
                words.add(strWord);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { pStmt.close(); } catch (SQLException e) {/* ignored */}
            try { conn.close(); } catch (SQLException e) {/* ignored */}
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
        PreparedStatement pStmt = null;
        try {
            conn = getDatabaseConnection();
            pStmt = conn.prepareStatement(updateSQL);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { pStmt.close(); } catch (SQLException e) {/* ignored */}
            try { conn.close(); } catch (SQLException e) {/* ignored */}
        }
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
}

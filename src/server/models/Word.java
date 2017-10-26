package server.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Project Name: Hangman
 */
public class Word {

    private static int primaryKeyID;

    private int ID;
    private SimpleStringProperty word = new SimpleStringProperty(this, "wordProperty");

    public Word(String word, int ID) {
        this.word.set(word);
        this.ID = ID;
    }

    public static int getPrimaryKeyID() {
        return primaryKeyID;
    }

    public static void setPrimaryKeyID(int primaryKeyID) {
        Word.primaryKeyID = primaryKeyID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWord() {
        return word.get();
    }

    public SimpleStringProperty wordProperty() {
        return word;
    }

    public void setWord(String word) {
        this.word.set(word);
    }
}

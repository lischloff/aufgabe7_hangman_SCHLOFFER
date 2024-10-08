//Project: Hangman
//Author: Schloffer Lisa
//Date: 08.10.2024

package hangmanView;

import javax.swing.*;

public class hangmanView {
    private JTextField wordField;
    private JTextArea guessedLetters;
    private JPanel hangmanPanel;
    private JLabel hangmanPicture;
    private JTextField buchstabenEingabe;

    private String wordToGuess = "Fenster"; //Das Wort zu erraten

    public hangmanView() {
    wordField.setText(getHiddenWord(wordToGuess)); // Das Textfeld wird beim Erstellen des Objekts direkt mit den Unterstrichen befüllt
}
// Methode zur Umwandlung des Wortes in eine Zeichenkette mit Unterstrichen
private String getHiddenWord(String word) {
    // Erstellt einen StringBuilder für die Unterstrichdarstellung
    StringBuilder hiddenWord = new StringBuilder();

    // Durchlauft jedes Zeichen im Wort und füge einen Unterstrich hinzu
    for (int i = 0; i < word.length(); i++) {
        hiddenWord.append("_");
        if (i < word.length() - 1) {
            hiddenWord.append(" "); // Fügt ein Leerzeichen zwischen die Unterstriche hinzu
        }
    }
    return hiddenWord.toString();  // Gibt die Zeichenkette mit den Unterstrichen zurück
}

    public static void main(String[] args) {
        JFrame frame = new JFrame("hangmanView");
        hangmanView hangmanViewInstance = new hangmanView();
        frame.setContentPane(hangmanViewInstance.hangmanPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

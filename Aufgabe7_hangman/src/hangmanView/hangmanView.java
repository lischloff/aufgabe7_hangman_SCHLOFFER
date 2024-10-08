//Project: Hangman
//Author: Schloffer Lisa
//Date: 08.10.2024

package hangmanView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hangmanView {
    private JPanel hangmanPanel;
    private JTextField wordField;
    private JTextArea guessedLetters;
    private JLabel hangmanPicture;
    private JTextField letterInputField; // Neues Textfeld für Buchstabeneingaben

    private String wordToGuess = "Fenster"; // Das zu ratende Wort
    private StringBuilder currentGuess; // Hält den aktuellen Stand der erratenen Buchstaben

    public hangmanView() {
        // Initialisiere den aktuellen Stand mit Unterstrichen
        currentGuess = new StringBuilder(getHiddenWord(wordToGuess));
        wordField.setText(currentGuess.toString());

        // Hinzufügen eines ActionListeners für die Buchstabeneingabe
        letterInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Den eingegebenen Buchstaben erhalten
                String input = letterInputField.getText().toLowerCase();
                letterInputField.setText(""); // Textfeld nach Eingabe leeren

                // Sicherstellen, dass nur ein Buchstabe eingegeben wird
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    checkLetter(input.charAt(0));
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte einen gültigen Buchstaben eingeben.");
                }
            }
        });
    }

    // Methode zur Überprüfung, ob der Buchstabe im Wort vorkommt
    private void checkLetter(char letter) {
        boolean found = false;

        // Durchlaufen des Wortes, um zu sehen, ob der Buchstabe vorkommt
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.toLowerCase().charAt(i) == letter) {
                // Ersetze den Unterstrich durch den richtigen Buchstaben
                currentGuess.setCharAt(i * 2, wordToGuess.charAt(i)); // i * 2 wegen Leerzeichen
                found = true;
            }
        }

        if (found) {
            // Aktualisiere das Textfeld mit dem erratenen Wort
            wordField.setText(currentGuess.toString());
        } else {
            // Wenn der Buchstabe nicht im Wort vorkommt, zeige ihn in guessedLetters an
            guessedLetters.append(letter + "\n");
        }
    }

    // Methode zur Umwandlung des Wortes in eine Zeichenkette mit Unterstrichen
    private String getHiddenWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();

        // Durchlaufe jedes Zeichen im Wort und füge einen Unterstrich hinzu
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");
            if (i < word.length() - 1) {
                hiddenWord.append(" "); // Füge ein Leerzeichen zwischen die Unterstriche hinzu
            }
        }

        return hiddenWord.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman Spiel");
        frame.setContentPane(new hangmanView().hangmanPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

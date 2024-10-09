package hangmanView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class hangmanView {
    private JPanel hangmanPanel;
    private JTextField wordField;
    private JTextArea guessedLetters;
    private JLabel hangmanPicture;
    private JTextField letterInputField;
    private JLabel labelText;
    private JButton newGameBtn;

    private String wordToGuess;
    private StringBuilder currentGuess; // Hält den aktuellen Stand der erratenen Buchstaben
    private int errorCount = 0; // Anzahl der verlorenen Versuche
    private final int MAX_ERRORS = 10; // Maximale Anzahl der Fehler
    private ArrayList<String> easyWords;
    private ArrayList<String> mediumWords;
    private ArrayList<String> hardWords;

    public hangmanView() {
        initializeWordLists(); // Initialisiere die Wortlisten
        setupNewGame();

        // Hinzufügen eines ActionListeners für die Buchstabeneingabe
        letterInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        // Hinzufügen eines ActionListeners für den neuen Spielstart
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        });
    }

    // Methode zur Initialisierung eines neuen Spiels
    private void setupNewGame() {
        errorCount = 0; // Fehlerzähler zurücksetzen
        guessedLetters.setText(""); // Bereich für geratene Buchstaben leeren

        // Auswahl der Schwierigkeitsstufe durch den Benutzer
        String[] options = {"Einfach", "Mittel", "Schwer"};
        int difficulty = JOptionPane.showOptionDialog(
                null,
                "Wähle eine Schwierigkeitsstufe:",
                "Schwierigkeitsgrad",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Basierend auf der Auswahl ein Wort zufällig aus der entsprechenden Liste auswählen
        switch (difficulty) {
            case 0: // Einfach
                wordToGuess = getRandomWord(easyWords);
                break;
            case 1: // Mittel
                wordToGuess = getRandomWord(mediumWords);
                break;
            case 2: // Schwer
                wordToGuess = getRandomWord(hardWords);
                break;
            default:
                wordToGuess = getRandomWord(easyWords); // Standard: einfach
        }

        // Bereite das aktuelle Wort zum Erraten vor
        currentGuess = new StringBuilder(getHiddenWord(wordToGuess));
        wordField.setText(currentGuess.toString());
    }

    // Methode zur Überprüfung, ob der Buchstabe im Wort vorkommt
    private void checkLetter(char letter) {
        boolean found = false;

        // Überprüfe, ob das Spiel bereits verloren ist
        if (errorCount >= MAX_ERRORS) {
            return; // Keine weiteren Eingaben mehr möglich
        }

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

            // Überprüfe, ob das gesamte Wort erraten wurde
            if (!currentGuess.toString().contains("_")) {
                JOptionPane.showMessageDialog(null, "Geschafft! Das Wort ist: " + wordToGuess);
                int response = JOptionPane.showConfirmDialog(null, "Möchtest du ein neues Spiel starten?", "Neues Spiel", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    setupNewGame();
                }
            }
        } else {
            // Wenn der Buchstabe nicht im Wort vorkommt, zeige ihn in guessedLetters an
            guessedLetters.append(letter + "\n");

            // Erhöhe den Fehlerzähler
            errorCount++;

            // Überprüfe, ob das Spiel bereits verloren ist
            if (errorCount >= MAX_ERRORS) {
                JOptionPane.showMessageDialog(null, "Leider verloren! Das Wort war: " + wordToGuess);
                int response = JOptionPane.showConfirmDialog(null, "Möchtest du ein neues Spiel starten?", "Neues Spiel", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    setupNewGame();
                }
            }
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

    // Methode, die ein zufälliges Wort aus einer Liste zurückgibt
    private String getRandomWord(ArrayList<String> wordList) {
        int randomIndex = (int) (Math.random() * wordList.size());
        return wordList.get(randomIndex);
    }

    // Methode zur Initialisierung der Wortlisten
    private void initializeWordLists() {
        easyWords = new ArrayList<>();
        mediumWords = new ArrayList<>();
        hardWords = new ArrayList<>();

        // Einfache Wörter
        easyWords.add("Haus");
        easyWords.add("Baum");
        easyWords.add("Hund");
        easyWords.add("Katze");
        easyWords.add("Tisch");
        easyWords.add("Stuhl");
        easyWords.add("Auto");
        easyWords.add("Buch");
        easyWords.add("Fisch");
        easyWords.add("Brot");

        // Mittlere Wörter
        mediumWords.add("Schule");
        mediumWords.add("Garten");
        mediumWords.add("Fenster");
        mediumWords.add("Lampe");
        mediumWords.add("Blume");
        mediumWords.add("Wasser");
        mediumWords.add("Sonne");
        mediumWords.add("Regen");
        mediumWords.add("Vogel");
        mediumWords.add("Apfel");

        // Schwierige Wörter
        hardWords.add("Schmetterling");
        hardWords.add("Bibliothek");
        hardWords.add("Abenteuer");
        hardWords.add("Freundschaft");
        hardWords.add("Geheimnis");
        hardWords.add("Wissenschaft");
        hardWords.add("Entdeckung");
        hardWords.add("Herausforderung");
        hardWords.add("Unabhängigkeit");
        hardWords.add("Verantwortung");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman Spiel");
        frame.setContentPane(new hangmanView().hangmanPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

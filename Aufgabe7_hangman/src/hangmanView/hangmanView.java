package hangmanView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class hangmanView {
    // Komponenten für das Spiel
    private JPanel hangmanPanel;
    private JTextField wordField;
    private JTextArea guessedLetters;
    private JLabel hangmanPicture;
    private JTextField letterInputField;
    private JLabel labelText;
    private JButton newGameBtn; // Button für neues Spiel
    private JLabel wordLabel;

    private String wordToGuess;  // Das zu erratende Wort
    private StringBuilder currentGuess;  // Der aktuelle Stand des geratenen Wortes (z.B. "_ _ _")
    private int errorCount = 0;
    private final int MAX_ERRORS = 10;
    private ArrayList<String> wordList;  // Liste der Wörter

    public hangmanView() {
        wordList = new ArrayList<>(); // Initialisiere die Wortliste
        loadWords(); // Lade die Wörter aus der Datei
        setupNewGame(); // Starte ein neues Spiel, um ein Wort auszuwählen

        // Listener für das Eingabefeld des Buchstabens
        letterInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = letterInputField.getText().toLowerCase();  // Eingabe in Kleinbuchstaben umwandeln
                letterInputField.setText("");  // Eingabefeld nach der Eingabe leeren
                if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                    checkLetter(input.charAt(0));
                } else {
                    // Zeigt eine Fehlermeldung an, wenn die Eingabe ungültig ist
                    JOptionPane.showMessageDialog(
                            null,
                            "Bitte gib nur einen einzelnen Buchstaben ein.",
                            "Ungültige Eingabe",
                            JOptionPane.ERROR_MESSAGE // Zeigt die Fehlermeldung an
                    );
                }
            }
        });

        // Listener für den "Neues Spiel"-Button
        newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();  // Startet ein neues Spiel, wenn der Button gedrückt wird
            }
        });
    }

    private void loadWords() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\lisas\\Berufsschule\\September_November_24\\Labor\\ITL1_2\\aufgabe7_hangman_SCHLOFFER\\Aufgabe7_hangman\\src\\words.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.trim()); // Füge jedes Wort zur Liste hinzu
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Fehler beim Laden der Wortliste: " + e.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupNewGame() {
        errorCount = 0;  // Fehlerzähler zurücksetzen
        guessedLetters.setText("");  // Liste der falsch geratenen Buchstaben leeren
        if (!wordList.isEmpty()) {
            Random rand = new Random();
            wordToGuess = wordList.get(rand.nextInt(wordList.size())); // Wähle ein zufälliges Wort aus der Liste
        } else {
            wordToGuess = "Katze"; // Fallback-Wort, falls die Liste leer ist
        }
        currentGuess = new StringBuilder(getHiddenWord(wordToGuess)); // Das Wort wird zufällig ausgewählt
        wordField.setText(currentGuess.toString());  // Zeigt das verborgene Wort im Feld an
        updateImage(0);  // Setzt das Bild des Galgenmännchens zurück
    }

    // Methode zur Überprüfung des eingegebenen Buchstabens
    private void checkLetter(char letter) {
        boolean found = false;  // Gibt an, ob der Buchstabe im Wort gefunden wurde

        // Beendet die Methode, wenn die maximale Anzahl an Fehlern erreicht wurde
        if (errorCount >= MAX_ERRORS) {
            return;
        }

        // Durchläuft das zu erratende Wort und ersetzt Unterstriche durch den richtigen Buchstaben
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.toLowerCase().charAt(i) == letter) {  // Vergleicht den Buchstaben
                currentGuess.setCharAt(i * 2, wordToGuess.charAt(i));  // Setzt den gefundenen Buchstaben an die richtige Position
                found = true;  // Setzt auf true, wenn der Buchstabe gefunden wurde
            }
        }

        // Wenn der Buchstabe im Wort gefunden wurde
        if (found) {
            wordField.setText(currentGuess.toString());  // Aktualisiert das erratene Wortfeld
            // Überprüft, ob das ganze Wort erraten wurde
            if (!currentGuess.toString().contains("_")) {
                wordLabel.setText("Das Wort ist: " + wordToGuess);  // Zeigt das vollständige Wort an
            }
        } else {
            // Wenn der Buchstabe nicht gefunden wurde
            guessedLetters.append(letter + "\n");  // Fügt den Buchstaben zur Liste der falsch geratenen Buchstaben hinzu
            errorCount++;  // Erhöht die Fehleranzahl
            updateImage(errorCount);  // Aktualisiert das Bild des Galgenmännchens

            // Wenn die maximale Anzahl an Fehlern erreicht wurde
            if (errorCount >= MAX_ERRORS) {
                wordLabel.setText("Leider verloren! Das Wort war: " + wordToGuess);  // Zeigt eine Verlustnachricht an
            }
        }
    }

    // Methode, um ein Wort als verborgenes Wort (mit Unterstrichen) anzuzeigen
    private String getHiddenWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();

        // Fügt für jeden Buchstaben im Wort einen Unterstrich hinzu
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");  // Fügt einen Unterstrich hinzu
            if (i < word.length() - 1) {
                hiddenWord.append(" ");  // Fügt ein Leerzeichen zwischen den Buchstaben hinzu
            }
        }

        return hiddenWord.toString();  // Gibt das verborgene Wort zurück
    }

    // Aktualisiert das Bild des Galgenmännchens basierend auf der Anzahl der Fehlversuche
    private void updateImage(int attempts) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/hangman" + attempts + ".png"));
        if (icon.getIconWidth() == -1) {
            System.err.println("Bild nicht gefunden: /images/hangman" + attempts + ".png");
        }
        hangmanPicture.setIcon(icon);
    }

    // Hauptmethode zum Starten des Spiels
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hangman Spiel");  // Erstellt ein neues Fenster für das Spiel
        frame.setContentPane(new hangmanView().hangmanPanel);  // Setzt das Panel des Spiels in das Fenster
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Schließt das Fenster, wenn auf "Schließen" geklickt wird
        frame.pack();  // Passt die Größe des Fensters an den Inhalt an
        frame.setVisible(true);
    }
}

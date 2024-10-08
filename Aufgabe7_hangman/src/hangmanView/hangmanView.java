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

    public static void main(String[] args) {
        JFrame frame = new JFrame("hangmanView");
        hangmanView hangmanViewInstance = new hangmanView();
        frame.setContentPane(hangmanViewInstance.hangmanPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

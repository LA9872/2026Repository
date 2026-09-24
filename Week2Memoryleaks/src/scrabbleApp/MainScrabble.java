package scrabbleApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple GUI application that lets a user type in up to seven Scrabble
 * tile letters and displays every possible arrangement of those tiles.
 * WindowBuilder plug-in: the constructor lays out standard Swing
 *
 * @author Leo A
 * @version 2.0
 */
public class MainScrabble extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Text field where the user types their Scrabble tiles. */
    private JTextField tilesField;

    /** Area where all generated arrangements are displayed. */
    private JTextArea outputArea;

    /** Label used to show a running count of arrangements found. */
    private JLabel countLabel;

    /**
     * Constructs the frame and lays out all GUI components.
     */
    public MainScrabble() {
        setTitle("Scrabble Tile Arranger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 450);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel promptLabel = new JLabel("Enter up to 7 letters:");
        tilesField = new JTextField(10);
        JButton generateButton = new JButton("Generate Arrangements");

        topPanel.add(promptLabel);
        topPanel.add(tilesField);
        topPanel.add(generateButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        countLabel = new JLabel("Arrangements found: 0");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(countLabel, BorderLayout.SOUTH);

        generateButton.addActionListener(new ActionListener() {
            /**
             * Handles the Generate button click: validates the tiles the
             * user entered, then displays every arrangement or an error
             * message. This is a good spot to set a breakpoint before
             * clicking the button, then use Step Into to walk down into
             * {@link ScrabbleUtil#validateTiles(String)} and
             * {@link ScrabbleUtil#generateArrangements(String)}.
             *
             * @param e the button click event (unused)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                onGenerateClicked();
            }
        });
    }

    /**
     * Reads the tiles from the input field, validates them, and either
     * displays all arrangements in {@link #outputArea} or shows an error
     * dialog describing what was wrong with the input.
     */
    private void onGenerateClicked() {
        String tiles = tilesField.getText().trim();

        try {
            Scrabbleutil.validateTiles(tiles);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Set<String> arrangements = Scrabbleutil.generateArrangements(tiles);

        StringBuilder display = new StringBuilder();
        for (String arrangement : arrangements) {
            display.append(arrangement).append(System.lineSeparator());
        }

        outputArea.setText(display.toString());
        countLabel.setText("Arrangements found: " + arrangements.size());
    }

    /**
     * Application entry point. Launches the GUI on the Swing Event
     * Dispatch Thread, as is standard practice for Swing applications.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainScrabble frame = new MainScrabble();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

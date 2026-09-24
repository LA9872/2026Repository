package passwordApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A simple GUI application that lets a user type in a password between 8
 * and 12 characters (no spaces) and reports the length of its largest
 * "block" &mdash; a run of adjacent, case-sensitive identical characters
 * &mdash; along with whether the password is decent or could be improved.
 * <p>
 * This class was built to be opened and edited with the Eclipse
 * WindowBuilder plug-in: the constructor lays out standard Swing
 * components ({@link JLabel}, {@link JTextField}, {@link JButton}) inside a
 * {@link JFrame} using simple layout managers so that WindowBuilder can
 * parse and re-render the design.
 *
 * @author  Leo A
 * @version 2.0
 */
public class Mainpassword extends JFrame {

    private static final long serialVersionUID = 1L;

    /** Text field where the user types their password. */
    private JTextField passwordField;

    /** Label used to display the strength result or a hint before checking. */
    private JLabel resultLabel;

    /**
     * Constructs the frame and lays out all GUI components.
     */
    public Mainpassword() {
        setTitle("Password Strength Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 200);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel promptLabel = new JLabel("Enter a password (8-12 characters, no spaces):");
        passwordField = new JTextField(15);
        JButton checkButton = new JButton("Check Strength");

        topPanel.add(promptLabel);
        topPanel.add(passwordField);
        topPanel.add(checkButton);

        resultLabel = new JLabel(" ");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(resultLabel, BorderLayout.CENTER);

        checkButton.addActionListener(new ActionListener() {
            /**
             * Handles the Check Strength button click: validates the
             * password the user entered, then displays the strength
             * message or an error. This is a good spot to set a breakpoint
             * before clicking the button, then use Step Into to walk down
             * into {@link PasswordUtil#validatePassword(String)} and
             * {@link PasswordUtil#describeStrength(String)}.
             *
             * @param e the button click event (unused)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                onCheckClicked();
            }
        });
    }

    /**
     * Reads the password from the input field, validates it, and either
     * displays the strength message in {@link #resultLabel} or shows an
     * error dialog describing what was wrong with the input.
     */
    private void onCheckClicked() {
        String password = passwordField.getText();

        try {
            Passwordutil.validatePassword(password);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultLabel.setText(Passwordutil.describeStrength(password));
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
                    Mainpassword frame = new Mainpassword();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
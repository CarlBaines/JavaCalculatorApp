import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends JPanel implements ActionListener {
    // Attributes
    private JFrame calculatorFrame;
    private JPanel calculatorPanel;
    private JTextField display;
    private JPanel numberPad;

    public CalculatorPanel() {
        // Constructor
        ImageIcon appIcon = new ImageIcon("Assets/app-icon.png");
        calculatorFrame = new JFrame("Java Calculator");
        calculatorFrame.setIconImage(appIcon.getImage());
        calculatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculatorFrame.setSize(400, 600);
        calculatorFrame.setResizable(false);
        calculatorFrame.setLocationRelativeTo(null); // Center the frame on the screen

        calculatorPanel = new JPanel();
        calculatorPanel.setLayout(new BoxLayout(calculatorPanel, BoxLayout.PAGE_AXIS));
        calculatorPanel.setVisible(true);

        // Add components to the calculatorPanel
        addComponents(calculatorPanel);

        calculatorFrame.add(calculatorPanel, "North");
        calculatorFrame.setVisible(true);

    }

    // Method to add components to the calculatorPanel
    public JPanel addComponents(JPanel calculatorPanel) {
        // Add buttons, text fields, etc. to the calculatorPanel
        display = new JTextField("0");
        display.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        display.setEditable(false);
        display.setFocusable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        calculatorPanel.add(display);

        numberPad = new JPanel();
        numberPad.setLayout(new GridLayout(5, 3));

        // Create buttons for the number pad using an array of strings.
        String[] buttonLabels = {
                "","", "CE",
                "7", "8", "9",
                "4", "5", "6",
                "1", "2", "3",
                ".", "0", "="
        };

        // Create JButtons for each label and add them to the numberPad panel.
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
            button.addActionListener(this);
            numberPad.add(button);
        }

        numberPad.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Add the numberPad to the calculatorPanel
        calculatorPanel.add(numberPad);

        return calculatorPanel;
    }


    // ActionListener method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            String buttonText = ((JButton) e.getSource()).getText();
            
            // Handle CE button
            if (buttonText.equals("CE")) {
                display.setText("0");
                return;
            }
            
            // Handle other buttons
            if (display.getText().equals("0")) {
                // Only replace the 0 if the button pressed is not empty and not CE
                if (!buttonText.isEmpty() && !buttonText.equals("CE")) {
                    display.setText(buttonText);
                }
            } else {
                display.setText(display.getText() + buttonText);
            }
        }
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculatorPanel extends JPanel implements ActionListener {
    // Attributes
    private JFrame calculatorFrame;
    private JPanel calculatorPanel;
    private JTextField display;
    private JTextField previousDisplay;
    private JPanel numberPad;

    // Initialises an arraylist of integers to store the numbers inputted from the
    // number pad.
    private ArrayList<Integer> numbers = new ArrayList<>();
    private boolean newInput = false;
    private String currentOperator = "";

    private ArrayList<String> previousDisplayStrs = new ArrayList<>();

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

        // Set text to empty initially.
        previousDisplay = new JTextField("");
        previousDisplay.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        previousDisplay.setEditable(false);
        previousDisplay.setFocusable(false);
        previousDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        previousDisplay.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        calculatorPanel.add(previousDisplay);

        // Add buttons, text fields, etc. to the calculatorPanel
        display = new JTextField("0");
        display.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        display.setEditable(false);
        display.setFocusable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 25));
        calculatorPanel.add(display);

        numberPad = new JPanel();
        numberPad.setLayout(new GridLayout(5, 4));

        // Create buttons for the number pad using an array of strings.
        String[] buttonLabels = {
                "", "", "CE", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "", "0", ".", "="
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

    // Method that performs addition calculation.
    private int addition(ArrayList<Integer> numbers) {
        int result = 0;
        // loop through the numbers arraylist.
        // get each number stored at each index.
        // add each number together.
        for (int i = 0; i < numbers.size(); i++) {
            result += numbers.get(i);
        }

        System.out.println("Result of addition: " + result);

        return result;
    }

    // Method that handles numeric inputs from the number pad.
    private void numberInputs(String digit, ArrayList<String> previousDisplayStrs) {
        // Checks if the JTextField text is equal to 0...
        if (display.getText().equals("0") || newInput) {
            // Sets the JTextField text to the digit.
            display.setText(digit);

            // set the previousDisplay JTextField content to the contents of the
            // previousDisplayText arraylist.
            // adds the digit to the arraylist of strings.
            previousDisplayStrs.add(digit);

            // Update the previous display with the method call.
            formatPreviousDisplay(previousDisplayStrs);

            // flag for new number is set to false.
            newInput = false;
        } else {
            // Sets the JTextField text to the current text stored in it + the passed digit.
            display.setText(display.getText() + digit);
        }
    }

    // Method that handles operator inputs.
    private void handleOperatorInputs(String operator, ArrayList<String> previousDisplayStrs) {
        // Stores the current number as a variable by parsing the number stored in the
        // JTextField, as a string, to an integer.
        int currentNumber = Integer.parseInt(display.getText());
        // adds it to the numbers arraylist.
        numbers.add(currentNumber);
        System.out.println("Number added to the arraylist: " + currentNumber);
        System.out.println(numbers.toString());

        // sets the current operator.
        currentOperator = operator;

        // add the currentOperator to the previousDisplayStrs arraylist of strings.
        previousDisplayStrs.add(currentOperator);
        // Update the previous display with the method call.
        formatPreviousDisplay(previousDisplayStrs);

        // flag for new number is set to true.
        newInput = true;
    }

    // Method to calculate the result of the calculations made on the number pad.
    private void calculateResult(ArrayList<String> previousDisplayStrs) {
        // Add the current number to the numbers arraylist.
        int currentNumber = Integer.parseInt(display.getText());
        numbers.add(currentNumber);
        System.out.println("Number added to the arraylist: " + currentNumber);
        System.out.println(numbers.toString());

        // Calculate the result based on the operator inputted.
        int result = 0;
        if (currentOperator.equals("+")) {
            result = addition(numbers);
        }

        // Display the result in the JTextField.
        display.setText(String.valueOf(result));

        // Display the full calculation you made in the previousDisplay JTextField.
        // e.g. 1 + 2 =
        formatPreviousDisplay(previousDisplayStrs);

        previousDisplay.setText(previousDisplay.getText() + "=");

        // Clear the numbers arraylist for the next operation.
        numbers.clear();
        previousDisplayStrs.clear();
        newInput = true;
    }

    // Method to check if the text of a button that has been clicked on is numeric.
    private boolean isNumeric(String buttonText) {
        try {
            Integer.parseInt(buttonText);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void formatPreviousDisplay(ArrayList<String> previousDisplayStrs) {
        String previousDisplayString = "";
        // loops through the arraylist of strings
        for (String s : previousDisplayStrs) {
            // appends each string to the previousDisplayString.
            previousDisplayString += s;
        }
        // sets the text of the previousDisplay JTextField to the final string.
        previousDisplay.setText(previousDisplayString);
    }

    // ActionListener method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            // Retrieves the text content of the JButton that was clicked.
            String buttonText = ((JButton) e.getSource()).getText();

            // Handle CE button
            if (buttonText.equals("CE")) {
                display.setText("0");
                // clears the numbers arraylist and sets the currentOperator string to empty.
                numbers.clear();
                currentOperator = "";
                return;
            }

            // Handle numeric buttons.
            // Calls method that checks if the text of the button that has been clicked on
            // is numeric.
            boolean numeric = isNumeric(buttonText);
            // if the button text is numeric, we parse the number stored as a string to an
            // integer.
            if (numeric) {
                int numTextToInt = Integer.parseInt(buttonText);
                // check if the number is between 0 and 9.
                if (numTextToInt >= 0 || numTextToInt <= 9) {
                    // call the method that handles the numeric inputs.
                    numberInputs(buttonText, previousDisplayStrs);
                }
            }
            // Handle operator buttons.
            else if (buttonText.equals("+")) {
                handleOperatorInputs(buttonText, previousDisplayStrs);
            }
            // Handle equals button
            else if (buttonText.equals("=")) {
                // if the equals button is clicked, call the calculateResult method.
                calculateResult(previousDisplayStrs);
            }
        }
    }

}

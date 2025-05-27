import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CalculatorPanel extends JPanel implements ActionListener {
    // Swing Elements
    private JFrame calculatorFrame;
    private JPanel calculatorPanel;
    private JTextField display;
    private JTextField previousDisplay;
    private JPanel numberPad;
    private JLabel footerLabel;

    // Initialises an arraylist of doubles to store the numbers inputted from the
    // number pad.
    private ArrayList<Double> numbers = new ArrayList<>();
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
        // Centers the frame on the screen
        calculatorFrame.setLocationRelativeTo(null);

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

        footerLabel = new JLabel("Created by Carl Baines.");
        footerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setVisible(true);

        calculatorPanel.add(footerLabel);

        return calculatorPanel;
    }

    // Method that performs addition calculations.
    private double addition(ArrayList<Double> numbers) {
        double result = 0;
        // loop through the numbers arraylist.
        // get each number stored at each index.
        // add each number together.
        for (int i = 0; i < numbers.size(); i++) {
            result += numbers.get(i);
        }
        // System.out.println("Result of addition: " + result);
        return result;
    }

    // Method that performs subtraction calculations
    private double subtraction(ArrayList<Double> numbers) {
        double result = 0;
        // retrieve the number stored at the first index in the arraylist.
        double subtractNumber = numbers.get(0);
        // loop through the numbers arraylist from the first index (to skip index 0).
        // subtract continually from the number.
        for (int i = 1; i < numbers.size(); i++) {
            // System.out.println("Number to be subtracted: " + subtractNumber);
            subtractNumber -= numbers.get(i);
            // System.out.println("After subtraction: " + subtractNumber);
            result = subtractNumber;
        }
        return result;
    }

    // Method that performs multiplication calculations.
    private double multiplication(ArrayList<Double> numbers) {
        double result = 0;
        double multiplyNumber = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            // multiply the first number in the numbers arraylist continually
            // with each number stored in the arraylist from index one, until the arraylist
            // has been fully traversed.
            multiplyNumber *= numbers.get(i);
            result = multiplyNumber;
        }
        // System.out.println("Result of multiplication: " + result);
        return result;
    }

    // Method that performs division calculations.
    private double division(ArrayList<Double> numbers) {
        double result = 0;
        double divisionNumber = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            // divide from the first number in the numbers arraylist continually
            // with the values stored in the arraylist from index one, until the arraylist
            // has been fully traversed.
            divisionNumber /= numbers.get(i);
            result = divisionNumber;
        }
        // System.out.println("Result of division: " + result);
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
        // JTextField, as a string, to a double.
        double currentNumber = Double.parseDouble(display.getText());
        // adds it to the numbers arraylist.
        numbers.add(currentNumber);
        // System.out.println("Number added to the arraylist: " + currentNumber);
        // System.out.println(numbers.toString());
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
        double currentNumber = Double.parseDouble(display.getText());
        numbers.add(currentNumber);
        // System.out.println("Number added to the arraylist: " + currentNumber);
        // System.out.println(numbers.toString());

        // Calculate the result based on the operator inputted.
        double result = 0;

        switch (currentOperator) {
            case "+":
                result = addition(numbers);
                break;
            case "-":
                result = subtraction(numbers);
                break;
            case "*":
                result = multiplication(numbers);
                break;
            case "/":
                result = division(numbers);
                break;
            default:
                result = 0;
        }

        // Retrieves the result and removes the '.0' from the end
        // as the result is stored as a double.
        String resultStr = String.valueOf(result);
        if (resultStr.endsWith(".0")) {
            resultStr = resultStr.substring(0, resultStr.length() - 2);
        }
        // Display the result in the JTextField.
        display.setText(resultStr);

        // Display the full calculation you made in the previousDisplay JTextField.
        // e.g. 1 + 2 =
        formatPreviousDisplay(previousDisplayStrs);

        // Adds the equals sign on to the end of the previousDisplay JTextField to
        // output the full calculation that was made to get the result.
        previousDisplay.setText(previousDisplay.getText() + "=");

        // Clear the numbers arraylist and the previous display strings arraylist for
        // the next operation.
        numbers.clear();
        previousDisplayStrs.clear();
        newInput = true;
    }

    // Method to check if the text of a button that has been clicked on is numeric.
    private boolean isNumeric(String buttonText) {
        try {
            Double.parseDouble(buttonText);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method that formats and updates the previousDisplay JTextField on the
    // calculator's display.
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
                previousDisplay.setText("");
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
            // double.
            if (numeric) {
                double numTextToDouble = Double.parseDouble(buttonText);
                // check if the number is between 0 and 9.
                if (numTextToDouble >= 0 || numTextToDouble <= 9) {
                    // call the method that handles the numeric inputs.
                    numberInputs(buttonText, previousDisplayStrs);
                }
            }

            // Handle operator buttons.
            switch (buttonText) {
                case "+":
                    handleOperatorInputs("+", previousDisplayStrs);
                    break;
                case "-":
                    handleOperatorInputs("-", previousDisplayStrs);
                    break;
                case "*":
                    handleOperatorInputs("*", previousDisplayStrs);
                    break;
                case "/":
                    handleOperatorInputs("/", previousDisplayStrs);
                    break;
                // Handle equals button.
                case "=":
                    calculateResult(previousDisplayStrs);
                    break;
            }
        }
    }

}

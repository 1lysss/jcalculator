// graphics library and event handling
import java.awt.*;
import java.awt.event.*;
// key handling and borders of window
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 400;
    int boardheight = 600;

    // Color palette (gruvbox)
    Color customLightGray = new Color(189, 174, 147);  
    Color customDarkgray = new Color(50, 48, 47);      
    Color customBlack = new Color(40, 40, 40);         
    Color customOrange = new Color(254, 128, 25);      

    
    String[] buttonValues = {
        "AC",  "+/-",  "%",   "÷", 
        "7",   "8",    "9",   "×", 
        "4",   "5",    "6",   "-",
        "1",   "2",    "3",   "+",
        "0",   ".",    "√",   "=" 
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    String defaultText = "0";
    String var1 = "0";
    String operator = null;
    String var2 = null;

    JFrame frame = new JFrame("Calculator app"); // frame is our window
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel(); // place panel inside label then place that in the window (frame)
    JPanel buttonsPanel = new JPanel();


    Calculator() {
        // make window a visible window
        frame.setSize(boardWidth, boardheight); // size of window
        frame.setLocationRelativeTo(null); // this centers the window?
        frame.setResizable(false);  // user will not be able to change width and height of window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // what to happen when u close window
        frame.setLayout(new BorderLayout()); // so we can place things where we want 

        // SET LABEL ATTRIBUTES
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white); // aka text color
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT); // where to display text (Calculations)
        displayLabel.setText(defaultText); // default text to show
        displayLabel.setOpaque(true);

        // SET PANEL ATTRIBUTES
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel); // add label inside panel
        frame.add(displayPanel, BorderLayout.NORTH); // add our panel isnide the window + place in north of window! so we can add buttons under

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // Add buttons in the grid of buttons label
        for (int i = 0; i < buttonValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            // Styling buttons now 
            // for operators
            if (Arrays.asList(topSymbols).contains(buttonValue)) { // style if a top symbol
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) { // style if right symbol
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(customDarkgray);
                button.setForeground(Color.white);
            }
            buttonsPanel.add(button);

            // add functionality
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource(); // get event (which click)
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (var1 != null) 
                            {
                                var2 = displayLabel.getText();
                                double num1 = Double.parseDouble(var1);
                                double num2 = Double.parseDouble(var2);
                                switch(operator) 
                                {
                                    case "+":
                                        displayLabel.setText(removeDecimal(num1+num2));
                                        break;
                                    case "-":
                                        displayLabel.setText(removeDecimal(num1-num2));
                                        break;
                                    case "×":
                                        displayLabel.setText(removeDecimal(num1*num2));
                                        break;
                                    case "÷":
                                        if (num2 == 0) {displayLabel.setText("null");}
                                        else {
                                            displayLabel.setText(removeDecimal(num1/num2));
                                        }
                                        break;
                                    // case "√":
                                        // double asqrt = Double.parseDouble(displayLabel.getText());
                                        // if (asqrt < 0) displayLabel.setText("null");
                                        // else {
                                        //     asqrt = Math.sqrt(asqrt);
                                        //     displayLabel.setText(removeDecimal(asqrt));
                                        // }
                                    //     break;
                                }
                                clearAll(); // after each calculation, reset variables
                            }
                        }
                        else if ("-+×÷".contains(buttonValue)) {
                            if (operator == null) {
                                var1 = displayLabel.getText();
                                displayLabel.setText(defaultText);
                                var2 = defaultText;
                            }
                            operator = buttonValue;
                            //displayLabel.setText(defaultText);
                        }
                    } 
                    else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") { clearAll(); displayLabel.setText(defaultText);}
                        else if (buttonValue == "+/-") {
                            double num = Double.parseDouble(displayLabel.getText());
                            num *= -1;
                            displayLabel.setText(removeDecimal(num));
                        }
                        else if (buttonValue == "%") {
                            double num = Double.parseDouble(displayLabel.getText());
                            num /= 100;
                            displayLabel.setText(removeDecimal(num));
                        }
                    } 
                    else { // numbers or '.' or '√'
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {            // if no '.' add it
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if (buttonValue == "√") {
                            double asqrt = Double.parseDouble(displayLabel.getText());
                                if (asqrt < 0) displayLabel.setText("null");
                                else {
                                    asqrt = Math.sqrt(asqrt);                                        
                                    displayLabel.setText(removeDecimal(asqrt));
                                }
                        }
                        else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue);
                            } 
                            else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }

                    }
                }
                
            });
            frame.setVisible(true);
        }
    }

    void clearAll() {
        var1 = "0";
        operator = null;
        var2 = null;
    }

    String removeDecimal(double d) {return d % 1 == 0 ? Integer.toString((int) d) : Double.toString((double)Math.round(d * 100) / 100);}
    
    // we need components to display on the window, we will need 2 here, one for the buttons (numbers + operators) and one for displaying calculations

}




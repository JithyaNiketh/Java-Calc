import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Calculator {
    private static JTextField display;
    private static String expression = "";
    

    public static void main(String[] args) {
        JFrame frame = new JFrame("Basic Java Calculator");
        frame.setSize(400, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 40));
        display.setBackground(Color.DARK_GRAY);
        display.setForeground(Color.WHITE);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        displayPanel.add(display, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));
        buttonPanel.setBackground(Color.DARK_GRAY);

        Font buttonFont = new Font("Arial", Font.BOLD, 30);
        String[] buttonLabels = {
            "1", "2", "3", "+",
            "4", "5", "6", "-",
            "7", "8", "9", "*",
            ".", "0", "=", "/",
            "C", "DEL", "(", ")"
        };
        
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setBackground(label.equals("C") ? Color.RED : Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.addActionListener(new ButtonClickListener(label));
            buttonPanel.add(button);
        }
        
        frame.setLayout(new BorderLayout());
        frame.add(displayPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    static class ButtonClickListener implements ActionListener {
        private String value;
        
        public ButtonClickListener(String value) {
            this.value = value;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (value.equals("C")) {
                expression = "";
                display.setText("");
            } else if (value.equals("DEL")) {
                if (!expression.isEmpty()) {
                    expression = expression.substring(0, expression.length() - 1);
                    display.setText(expression);
                }
            } else if (value.equals("=")) {
                try {
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    Object result = engine.eval(expression);
                    display.setText(result.toString());
                    expression = result.toString();
                } catch (ScriptException ex) {
                    display.setText("Error");
                    expression = "";
                }
            } else {
                expression += value;
                display.setText(expression);
            }
        }
    }
}
//ddddddddddddddddddddddddddddddd
package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Stack;

public class InfijaController {

    @FXML
    private TextField inputExpr;

    @FXML
    private TextArea outputIntermediate;

    @FXML
    private TextArea outputBytecode;

    private int tempCount = 1;

    @FXML
    private void generateIntermediate() {
        String expr = inputExpr.getText().replaceAll("\\s+", "");
        tempCount = 1;

        try {
            // Generar código intermedio
            String intermediate = generateCode(expr);
            outputIntermediate.setText(intermediate);

            // Simular bytecode
            String bytecode = simulateBytecode(expr);
            outputBytecode.setText(bytecode);

        } catch (Exception e) {
            outputIntermediate.setText("Error: expresión inválida.");
            outputBytecode.setText("");
        }
    }

    // =========================
    // Generación de código intermedio
    // =========================
    private String generateCode(String expr) {
        Stack<Character> ops = new Stack<>();
        Stack<String> vals = new Stack<>();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            // Si es letra o dígito → operando
            if (Character.isLetterOrDigit(c)) {
                StringBuilder token = new StringBuilder();
                token.append(c);

                // Si es número con varios dígitos
                while (i + 1 < expr.length() && Character.isDigit(expr.charAt(i + 1))) {
                    token.append(expr.charAt(++i));
                }
                vals.push(token.toString());

            } else if (c == '(') {
                ops.push(c);

            } else if (c == ')') {
                // Resolver hasta el (
                while (!ops.isEmpty() && ops.peek() != '(') {
                    generateStep(code, vals, ops);
                }
                if (!ops.isEmpty() && ops.peek() == '(') {
                    ops.pop();
                }

            } else if (isOperator(c)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    generateStep(code, vals, ops);
                }
                ops.push(c);
            }
        }

        // Procesar operadores restantes
        while (!ops.isEmpty()) {
            generateStep(code, vals, ops);
        }

        return code.toString();
    }

    // Un paso de generación intermedia
    private void generateStep(StringBuilder code, Stack<String> vals, Stack<Character> ops) {
        String v2 = vals.pop();
        String v1 = vals.pop();
        char op = ops.pop();
        String temp = "t" + tempCount++;
        code.append(temp).append(" = ").append(v1).append(" ").append(op).append(" ").append(v2).append("\n");
        vals.push(temp);
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        switch (op) {
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    // =========================
    // Simulación de bytecode (sencillo)
    // =========================
    private String simulateBytecode(String expr) {
        StringBuilder bc = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                StringBuilder token = new StringBuilder();
                token.append(c);

                // Si es número multi-dígito
                while (i + 1 < expr.length() && Character.isDigit(expr.charAt(i + 1))) {
                    token.append(expr.charAt(++i));
                }
                bc.append("iload_").append(token).append("  ; cargar ").append(token).append("\n");

            } else if (c == '+') {
                bc.append("iadd     ; sumar\n");
            } else if (c == '-') {
                bc.append("isub     ; restar\n");
            } else if (c == '*') {
                bc.append("imul     ; multiplicar\n");
            } else if (c == '/') {
                bc.append("idiv     ; dividir\n");
            }
        }

        bc.append("istore result ; guardar en result\n");
        return bc.toString();
    }

}

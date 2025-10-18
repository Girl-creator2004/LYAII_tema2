package com.dixiesystems.menu_unidad2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class codigoPController {

    // ===== UI =====
    @FXML private TextArea taCodigoFuente;
    @FXML private TextArea taCodigoIntermedio;
    @FXML private Button btnGenerar;
    @FXML private Button btnLimpiar;

    // ===== Estado interno (equivale a service) =====
    private int tempCount = 0;

    @FXML
    public void initialize() {
        btnGenerar.setOnAction(e -> generarCodigo());
        btnLimpiar.setOnAction(e -> limpiar());
    }

    // ===== Lógica de botones =====
    private void generarCodigo() {
        String fuente = taCodigoFuente.getText();
        if (fuente == null || fuente.isBlank()) {
            mostrarAlerta("Ingrese código fuente.");
            return;
        }
        CodigoIntermedio ci = generateFromSource(fuente);
        taCodigoIntermedio.setText(ci.toString());
    }

    private void limpiar() {
        taCodigoFuente.clear();
        taCodigoIntermedio.clear();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // ===== "Service" integrado =====
    private CodigoIntermedio generateFromSource(String source) {
        CodigoIntermedio ic = new CodigoIntermedio();
        tempCount = 0;

        // Limpieza básica
        source = source.replaceAll("\\s+", "");
        if (source.isEmpty()) return ic;

        // Soporta múltiples sentencias separadas por ';'
        String[] lines = source.split(";");
        for (String line : lines) {
            if (line.isBlank()) continue;
            processLine(line, ic);
        }
        return ic;
    }

    private void processLine(String line, CodigoIntermedio ic) {
        // ejemplo: a=b+c*d
        String[] parts = line.split("=");
        if (parts.length != 2) return;

        String res = parts[0];
        String expr = parts[1];

        String postfix = infixToPostfix(expr);
        Stack<String> stack = new Stack<>();

        for (char c : postfix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stack.push(String.valueOf(c));
            } else if ("+-*/".indexOf(c) != -1) {
                String b = stack.pop();
                String a = stack.pop();
                String t = newTemp();
                ic.add(new CodigoIntermedio.Instruction(String.valueOf(c), a, b, t));
                stack.push(t);
            }
        }

        if (!stack.isEmpty()) {
            String t = stack.pop();
            ic.add(new CodigoIntermedio.Instruction("=", t, "", res));
        }
    }

    private String newTemp() {
        return "t" + tempCount++;
    }

    // Convierte infijo a postfijo (Shunting-yard simplificado)
    private String infixToPostfix(String expr) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]|[+\\-*/()]");
        Matcher matcher = pattern.matcher(expr);

        while (matcher.find()) {
            char token = matcher.group().charAt(0);
            if (Character.isLetterOrDigit(token)) {
                output.append(token);
            } else if (token == '(') {
                stack.push(token);
            } else if (token == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    output.append(stack.pop());
                if (!stack.isEmpty() && stack.peek() == '(') stack.pop();
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(token))
                    output.append(stack.pop());
                stack.push(token);
            }
        }

        while (!stack.isEmpty())
            output.append(stack.pop());

        return output.toString();
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }

    // ===== "Model" integrado =====
    private static class CodigoIntermedio {
        private final List<Instruction> inss = new ArrayList<>();

        void add(Instruction i) { inss.add(i); }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int idx = 1;
            for (Instruction ins : inss) {
                sb.append(idx++).append(": ").append(ins).append(System.lineSeparator());
            }
            return sb.toString();
        }

        private static class Instruction {
            private final String op, arg1, arg2, res;

            Instruction(String op, String arg1, String arg2, String res) {
                this.op = op; this.arg1 = arg1; this.arg2 = arg2; this.res = res;
            }

            @Override
            public String toString() {
                if ("=".equals(op))
                    return res + " = " + arg1;
                return res + " = " + arg1 + " " + op + " " + arg2;
            }
        }
    }
}

